package com.example.sortit.settingScreens

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.sortit.HomeActivity
import com.example.sortit.ProfileActivity
import com.example.sortit.SearchActivity
import com.example.sortit.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.iconBuscar.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.navigationBar.homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

//        binding.navigationBar.calendarButton.setOnClickListener {
//            startActivity(Intent(this, CalendarActivity::class.java))
//        }

        binding.navigationBar.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val languages = listOf("Español", "Francés", "Inglés") // Lista de idiomas disponibles
        val languageAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> setAppLocale("es")
                        1 -> setAppLocale("fr")
                        2 -> setAppLocale("en")
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                }
            }
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        binding.root.requestLayout()
    }
}