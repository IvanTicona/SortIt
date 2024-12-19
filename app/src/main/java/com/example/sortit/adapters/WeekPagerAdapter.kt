package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.databinding.ItemWeekBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeekPagerAdapter(
    private val initialDate: Date
) : RecyclerView.Adapter<WeekPagerAdapter.WeekViewHolder>() {

    inner class WeekViewHolder(private val binding: ItemWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weekStartDate: Date) {
            val calendar = Calendar.getInstance().apply { time = weekStartDate }
            val dateFormat = SimpleDateFormat("d", Locale.getDefault())

            binding.day1.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day2.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day3.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day4.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day5.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day6.text = dateFormat.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            binding.day7.text = dateFormat.format(calendar.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val binding = ItemWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        // Calcular el desplazamiento de semanas basado en la posición
        val weekOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)
        val weekStartDate = getStartOfWeek(calendar)
        holder.bind(weekStartDate)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    private fun getStartOfWeek(calendar: Calendar): Date {
        calendar.firstDayOfWeek = Calendar.MONDAY
        val currentDow = calendar.get(Calendar.DAY_OF_WEEK)
        val diff = when (currentDow) {
            Calendar.SUNDAY -> -6
            else -> Calendar.MONDAY - currentDow
        }
        calendar.add(Calendar.DAY_OF_MONTH, diff)
        return calendar.time
    }
}