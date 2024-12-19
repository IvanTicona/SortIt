package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.databinding.ItemDateDetailBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateDetailPagerAdapter(
    private val initialDate: Date
) : RecyclerView.Adapter<DateDetailPagerAdapter.DateDetailViewHolder>() {

    inner class DateDetailViewHolder(private val binding: ItemDateDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
            binding.dateTextView.text = dateFormat.format(date)
            binding.detailsTextView.text = "Detalles para ${dateFormat.format(date)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateDetailViewHolder {
        val binding = ItemDateDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateDetailViewHolder(binding)
    }
    override fun onBindViewHolder(holder: DateDetailViewHolder, position: Int) {
        val dateOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.DAY_OF_MONTH, dateOffset)
        val date = calendar.time
        holder.bind(date)
    }
    override fun getItemCount(): Int = Int.MAX_VALUE

    fun getDateAtPosition(position: Int): Date {
        val dateOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.DAY_OF_MONTH, dateOffset)
        return calendar.time
    }
}
