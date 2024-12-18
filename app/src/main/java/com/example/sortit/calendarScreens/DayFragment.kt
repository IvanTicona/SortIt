package com.example.sortit.calendarScreens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.sortit.R
import com.example.sortit.databinding.FragmentDayBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DayFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Calendar = Calendar.getInstance()

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val year = it.getInt(ARG_YEAR)
            val month = it.getInt(ARG_MONTH)
            val day = it.getInt(ARG_DAY)
            selectedDate = Calendar.getInstance().apply {
                set(year, month, day)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHoursScrollView()
        setupSwipeGestures()
    }

    private fun setupHoursScrollView() {
        val hoursLayout = binding.hoursLayout
        hoursLayout.removeAllViews()

        val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        for (hour in 0..23) {
            val time = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val hourText = hourFormat.format(time)

            val hourView = LayoutInflater.from(context).inflate(R.layout.item_hour, hoursLayout, false)
            val txtHour = hourView.findViewById<android.widget.TextView>(R.id.txtHour)
            txtHour.text = hourText

            // Aquí puedes agregar lógica para mostrar actividades en activityContainer si las tienes

            hoursLayout.addView(hourView)
        }
    }

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

        binding.hoursScrollView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun onSwipeRight() {
        // Navegar al día anterior
        selectedDate.add(Calendar.DAY_OF_MONTH, -1)
        updateDayView()
    }

    private fun onSwipeLeft() {
        // Navegar al día siguiente
        selectedDate.add(Calendar.DAY_OF_MONTH, 1)
        updateDayView()
    }

    private fun updateDayView() {
        // Actualizar la vista del día
        setupHoursScrollView()

        // Notificar a CalendarFragment que el día ha cambiado
        (parentFragment as? CalendarFragment)?.updateDay(selectedDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_YEAR = "year"
        private const val ARG_MONTH = "month"
        private const val ARG_DAY = "day"

        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100

        @JvmStatic
        fun newInstance(year: Int, month: Int, day: Int) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_YEAR, year)
                    putInt(ARG_MONTH, month)
                    putInt(ARG_DAY, day)
                }
            }
    }
}