package com.example.sortit.calendarScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.sortit.adapters.DateDetailPagerAdapter
import com.example.sortit.adapters.WeekPagerAdapter
import com.example.sortit.databinding.FragmentCalendarBinding
import com.example.sortit.viewmodels.CalendarViewModel
import java.util.Calendar
import java.util.Date

class CalendarFragment : Fragment() {

    private lateinit var weekPagerAdapter: WeekPagerAdapter
    private lateinit var dateDetailPagerAdapter: DateDetailPagerAdapter
    private lateinit var calendarViewModel: CalendarViewModel
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val centralPosition = Int.MAX_VALUE / 2
    private var isUpdatingFromWeekPager = false
    private var isUpdatingFromDateDetailPager = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val initialDate = Date()

        calendarViewModel = ViewModelProvider(requireActivity())[CalendarViewModel::class.java]
        weekPagerAdapter = WeekPagerAdapter(initialDate, calendarViewModel)
        dateDetailPagerAdapter = DateDetailPagerAdapter(initialDate, requireContext())

        binding.weekViewPager.adapter = weekPagerAdapter
        binding.dateDetailViewPager.adapter = dateDetailPagerAdapter
        binding.weekViewPager.setCurrentItem(centralPosition, false)
        binding.dateDetailViewPager.setCurrentItem(centralPosition, false)

        calendarViewModel.pickedDate.observe(viewLifecycleOwner) { pickedDate ->
            if (isUpdatingFromWeekPager || isUpdatingFromDateDetailPager) {
                isUpdatingFromWeekPager = false
                isUpdatingFromDateDetailPager = false
                return@observe
            }

            val weekPosition = centralPosition + getWeekOffset(initialDate, pickedDate)
            if (binding.weekViewPager.currentItem != weekPosition) {
                isUpdatingFromDateDetailPager = true
                binding.weekViewPager.setCurrentItem(weekPosition, true)
            }

            val datePosition = centralPosition + getDateOffset(initialDate, pickedDate)
            if (binding.dateDetailViewPager.currentItem != datePosition) {
                isUpdatingFromWeekPager = true
                binding.dateDetailViewPager.setCurrentItem(datePosition, true)
            }

            weekPagerAdapter.setPickedDate(pickedDate)
        }

        // Listeners
        binding.weekViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val newWeekStartDate = weekPagerAdapter.getWeekStartDate(position)
                val currentPickedDate = calendarViewModel.pickedDate.value ?: newWeekStartDate
                val currentDayOfWeek = Calendar.getInstance().apply { time = currentPickedDate }.get(Calendar.DAY_OF_WEEK)
                val adjustedDayOfWeek = when (currentDayOfWeek) {
                    Calendar.SUNDAY -> 6
                    else -> currentDayOfWeek - Calendar.MONDAY
                }
                val calendar = Calendar.getInstance().apply { time = newWeekStartDate }
                calendar.add(Calendar.DAY_OF_MONTH, adjustedDayOfWeek)
                val newPickedDate = calendar.time
                calendarViewModel.setPickedDate(newPickedDate)
            }
        })
        binding.dateDetailViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val pickedDate = dateDetailPagerAdapter.getDateAtPosition(position)
                println("SWIPEEEE ======== $pickedDate")
                if (isUpdatingFromWeekPager) {
                    isUpdatingFromWeekPager = false
                } else {
                    isUpdatingFromDateDetailPager = true
                    calendarViewModel.setPickedDate(pickedDate)
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWeekOffset(initialDate: Date, targetDate: Date): Int {
        val initialCal = Calendar.getInstance().apply { time = initialDate }
        val targetCal = Calendar.getInstance().apply { time = targetDate }
        val initialWeekStart = getStartOfWeek(initialCal)
        val targetWeekStart = getStartOfWeek(targetCal)
        val diffMillis = targetWeekStart.timeInMillis - initialWeekStart.timeInMillis
        val diffWeeks = (diffMillis / (7 * 24 * 60 * 60 * 1000)).toInt()
        return diffWeeks
    }
    private fun getDateOffset(initialDate: Date, targetDate: Date): Int {
        val initialCal = Calendar.getInstance().apply { time = initialDate }
        val targetCal = Calendar.getInstance().apply { time = targetDate }
        val diffMillis = targetCal.timeInMillis - initialCal.timeInMillis
        val diffDays = (diffMillis / (24 * 60 * 60 * 1000)).toInt()
        return diffDays
    }
    private fun getStartOfWeek(calendar: Calendar): Calendar {
        val weekCal = calendar.clone() as Calendar
        weekCal.firstDayOfWeek = Calendar.MONDAY
        val currentDow = weekCal.get(Calendar.DAY_OF_WEEK)
        val diff = when (currentDow) {
            Calendar.SUNDAY -> -6
            else -> Calendar.MONDAY - currentDow
        }
        weekCal.add(Calendar.DAY_OF_MONTH, diff)
        return weekCal
    }
}
