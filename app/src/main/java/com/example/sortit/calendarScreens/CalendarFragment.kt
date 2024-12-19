package com.example.sortit.calendarScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sortit.adapters.WeekPagerAdapter
import com.example.sortit.databinding.FragmentCalendarBinding
import java.util.Date

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var weekPagerAdapter: WeekPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val initialDate = Date()
        weekPagerAdapter = WeekPagerAdapter(initialDate)
        binding.weekViewPager.adapter = weekPagerAdapter
        binding.weekViewPager.setCurrentItem(Int.MAX_VALUE / 2, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}