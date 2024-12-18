package com.example.sortit.calendarScreens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sortit.adapters.DatesAdapter
import com.example.sortit.databinding.FragmentWeekBinding
import java.util.Calendar
import java.util.Date

class WeekFragment : Fragment() {

    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    private lateinit var datesAdapter: DatesAdapter
    private lateinit var listener: OnWeekChangeListener

    interface OnWeekChangeListener {
        fun onWeekChanged(newWeekCalendar: Calendar)
    }

    private lateinit var gestureDetector: GestureDetector

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parentFrag = parentFragment
        if (parentFrag is OnWeekChangeListener) {
            listener = parentFrag
        } else {
            throw RuntimeException("$parentFrag must implement OnWeekChangeListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekStartDate = arguments?.getSerializable(ARG_WEEK_DATE) as? Date ?: Date()
        val calendar = Calendar.getInstance().apply { time = weekStartDate }

        // Generar las 7 fechas de la semana
        val weekDates = getWeekDates(calendar)

        datesAdapter = DatesAdapter(weekDates, Calendar.getInstance().time) { selectedDate ->
            // Puedes implementar la lógica al seleccionar una fecha si lo deseas
        }

        binding.datesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = datesAdapter
        }

        setupSwipeGestures()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSwipeGestures() {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null || e2 == null) return false
                val diffX = e2.x - e1.x
                val diffY = e2.y - e1.y
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        return true
                    }
                }
                return false
            }
        })

        binding.datesRecyclerView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun onSwipeRight() {
        // Navegar a la semana anterior
        val newWeekCalendar = Calendar.getInstance().apply {
            time = arguments?.getSerializable(ARG_WEEK_DATE) as? Date ?: Date()
            add(Calendar.WEEK_OF_YEAR, -1)
        }
        listener.onWeekChanged(newWeekCalendar)
    }

    private fun onSwipeLeft() {
        // Navegar a la semana siguiente
        val newWeekCalendar = Calendar.getInstance().apply {
            time = arguments?.getSerializable(ARG_WEEK_DATE) as? Date ?: Date()
            add(Calendar.WEEK_OF_YEAR, 1)
        }
        listener.onWeekChanged(newWeekCalendar)
    }

    private fun getWeekDates(calendar: Calendar): List<Date> {
        val weekDates = mutableListOf<Date>()
        val firstDayOfWeek = Calendar.getInstance().apply {
            time = calendar.time
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        for (i in 0..6) {
            weekDates.add(firstDayOfWeek.time)
            firstDayOfWeek.add(Calendar.DAY_OF_MONTH, 1)
        }

        return weekDates
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_WEEK_DATE = "week_date"

        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100

        @JvmStatic
        fun newInstance(weekDate: Date) =
            WeekFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_WEEK_DATE, weekDate)
                }
            }
    }
}