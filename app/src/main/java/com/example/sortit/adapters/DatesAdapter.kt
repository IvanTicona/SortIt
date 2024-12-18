package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.databinding.ItemDateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DatesAdapter(
    private val dates: List<Date>,
    private val currentDate: Date,
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<DatesAdapter.DateViewHolder>() {

    private var selectedPosition: Int = dates.indexOfFirst { isSameDay(it, currentDate) }

    inner class DateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date, position: Int) {
            val dayFormat = SimpleDateFormat("E", Locale.getDefault())
            val dateFormat = SimpleDateFormat("d", Locale.getDefault())

            binding.txtDay.text = dayFormat.format(date)
            binding.txtDate.text = dateFormat.format(date)

            // Destacar la fecha actual
            if (isSameDay(date, currentDate)) {
                binding.root.isSelected = true
            } else {
                binding.root.isSelected = false
            }

            binding.root.setOnClickListener {
                onDateClick(date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position], position)
    }

    override fun getItemCount(): Int = dates.size

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}