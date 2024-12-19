package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.databinding.ItemWeekBinding
import com.example.sortit.viewmodels.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeekPagerAdapter(
    private val initialDate: Date,
    private val viewModel: CalendarViewModel
) : RecyclerView.Adapter<WeekPagerAdapter.WeekViewHolder>() {

    private var pickedDate: Date? = null

    inner class WeekViewHolder(private val binding: ItemWeekBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var dayTextViews: List<TextView>
        private lateinit var cardViews: List<CardView>
        private var weekStartDate: Date? = null

        init {
            initializeViews()

            for (i in 0 until 7) {
                dayTextViews[i].setOnClickListener {
                    weekStartDate?.let { startDate ->
                        val calendar = Calendar.getInstance().apply { time = startDate }
                        calendar.add(Calendar.DAY_OF_MONTH, i)
                        val clickedDate = calendar.time
                        viewModel.setPickedDate(clickedDate)
                    }
                }
            }
        }
        private fun initializeViews() {
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
        private fun isSameDay(date1: Date, date2: Date): Boolean {
            val cal1 = Calendar.getInstance().apply { time = date1 }
            val cal2 = Calendar.getInstance().apply { time = date2 }
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        }
        fun bind(weekStartDate: Date, pickedDate: Date?) {
            this.weekStartDate = weekStartDate
            val calendar = Calendar.getInstance().apply { time = weekStartDate }
            val dateFormat = SimpleDateFormat("d", Locale.getDefault())

            for (i in 0 until 7) {
                dayTextViews[i].text = dateFormat.format(calendar.time)

                if (pickedDate != null && isSameDay(calendar.time, pickedDate)) {
                    cardViews[i].visibility = ViewGroup.VISIBLE
                } else {
                    cardViews[i].visibility = ViewGroup.INVISIBLE
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val binding = ItemWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeekViewHolder(binding, viewModel)
    }
    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val weekStartDate = getWeekStartDate(position)
        holder.bind(weekStartDate, pickedDate)
    }
    override fun getItemCount(): Int = Int.MAX_VALUE

    private fun getStartOfWeek(calendar: Calendar): Calendar {
        calendar.firstDayOfWeek = Calendar.MONDAY
        val currentDow = calendar.get(Calendar.DAY_OF_WEEK)
        val diff = when (currentDow) {
            Calendar.SUNDAY -> -6
            else -> Calendar.MONDAY - currentDow
        }
        calendar.add(Calendar.DAY_OF_MONTH, diff)
        return calendar
    }
    fun getWeekStartDate(position: Int): Date {
        val weekOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.WEEK_OF_YEAR, weekOffset)
        return getStartOfWeek(calendar).time
    }
    fun setPickedDate(date: Date) {
        pickedDate = date
        notifyDataSetChanged()
    }
}
