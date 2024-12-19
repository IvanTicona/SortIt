package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.databinding.ItemWeekBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeekPagerAdapter(
    private val initialDate: Date
) : RecyclerView.Adapter<WeekPagerAdapter.WeekViewHolder>() {

    private lateinit var dayTextViews: List<TextView>
    private lateinit var cardViews: List<ViewGroup>

    inner class WeekViewHolder(private val binding: ItemWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            dayTextViews = listOf(
                binding.day1,
                binding.day2,
                binding.day3,
                binding.day4,
                binding.day5,
                binding.day6,
                binding.day7
            )
            cardViews = listOf(
                binding.card1,
                binding.card2,
                binding.card3,
                binding.card4,
                binding.card5,
                binding.card6,
                binding.card7
            )
        }
        fun bind(weekStartDate: Date) {
            val today = Calendar.getInstance()
            val calendar = Calendar.getInstance().apply { time = weekStartDate }
            val dateFormat = SimpleDateFormat("d", Locale.getDefault())

            for (i in 0 until 7) {
                dayTextViews[i].text = dateFormat.format(calendar.time)
                cardViews[i].visibility = if (isSameDay(today.time, calendar.time)) ViewGroup.VISIBLE else ViewGroup.INVISIBLE
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        private fun isSameDay(date1: Date, date2: Date): Boolean {
            val cal1 = Calendar.getInstance().apply { time = date1 }
            val cal2 = Calendar.getInstance().apply { time = date2 }
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val binding = ItemWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekViewHolder(binding)
    }
    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        // Desplazamiento de semanas
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