package com.example.sortit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {
    private val _pickedDate = MutableLiveData<Date>()
    val pickedDate: LiveData<Date> get() = _pickedDate

    init {
        _pickedDate.value = getStartOfToday()
    }

    fun setPickedDate(date: Date) {
        _pickedDate.value = getStartOfDay(date)
    }

    private fun getStartOfToday(): Date {
        val calendar = Calendar.getInstance()
        return getStartOfDay(calendar.time)
    }

    private fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance().apply { time = date }
        return calendar.time
    }
}
