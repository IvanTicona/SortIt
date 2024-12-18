package com.example.sortit.utils

import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getDateLong(dateText: String): Long {
    val dateParts = dateText.split("/")

    val day = dateParts[0].toInt()
    val month = dateParts[1].toInt() - 1
    val year = dateParts[2].toInt()

    val calDate = Calendar.getInstance()
    calDate.set(year, month, day, 0, 0, 0)
    return calDate.timeInMillis
}

fun getHourLong(timeText: String): Long {
    val timeParts = timeText.split(":")

    val hour = timeParts[0].toInt()
    val minute = timeParts[1].toInt()

    val calTime = Calendar.getInstance()
    calTime.set(Calendar.HOUR_OF_DAY, hour)
    calTime.set(Calendar.MINUTE, minute)
    calTime.set(Calendar.SECOND, 0)
    return calTime.timeInMillis
}

fun longToDateString(dateInMillis: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(Date(dateInMillis))
}

fun longToHourString(timeInMillis: Long): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(Date(timeInMillis))
}

fun dpToPx(dp: Int, density: Float): Int {
    return (dp.toFloat() * density).toInt()
}