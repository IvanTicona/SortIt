package com.example.sortit.calendarScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sortit.databinding.FragmentCalendarBinding
import java.util.Calendar

class CalendarFragment : Fragment(), WeekFragment.OnWeekChangeListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private var currentWeekCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar WeekFragment y DayFragment
        loadWeekFragment()
        loadDayFragment()
    }

    private fun loadWeekFragment() {
        val weekFragment = WeekFragment.newInstance(currentWeekCalendar.time)
        childFragmentManager.beginTransaction()
            .replace(binding.weekFragmentContainer.id, weekFragment)
            .commit()
    }

    private fun loadDayFragment() {
        val dayFragment = DayFragment.newInstance(
            currentWeekCalendar.get(Calendar.YEAR),
            currentWeekCalendar.get(Calendar.MONTH),
            currentWeekCalendar.get(Calendar.DAY_OF_MONTH)
        )
        childFragmentManager.beginTransaction()
            .replace(binding.dayFragmentContainer.id, dayFragment)
            .commit()
    }

    override fun onWeekChanged(newWeekCalendar: Calendar) {
        currentWeekCalendar = newWeekCalendar
        // Actualizar DayFragment para mostrar el primer día de la nueva semana
        loadDayFragment()
    }

    fun updateDay(newDayCalendar: Calendar) {
        currentWeekCalendar = newDayCalendar
        // Actualizar WeekFragment para resaltar la nueva fecha
        loadWeekFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalendarFragment()
    }
}