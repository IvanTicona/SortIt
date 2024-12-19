package com.example.sortit.calendarScreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.R
import com.example.sortit.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CalendarFragment())
                .commit()
        }
    }
}