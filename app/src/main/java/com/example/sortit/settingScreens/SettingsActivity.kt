package com.example.sortit.settingScreens

import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)

        loadSettings()

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveThemeSetting(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveThemeSetting(false)
            }
        }

        binding.iconBuscar.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.navigationBar.homeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.navigationBar.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val languages = listOf("Seleccionar", "Español", "Francés", "Inglés")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

        binding.languageSpinner.setSelection(0)

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        when (position) {
                            1 -> setAppLocale("es")
                            2 -> setAppLocale("fr")
                            3 -> setAppLocale("en")
                        }
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                }
            }
    }

    private fun loadSettings() {
        val language = sharedPreferences.getString("language", "es")
        language?.let { setAppLocale(it) }

        val isNightMode = sharedPreferences.getBoolean("theme", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        binding.themeSwitch.isChecked = isNightMode
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        sharedPreferences.edit().putString("language", language).apply()

        binding.root.requestLayout()
    }

    private fun saveThemeSetting(isNightMode: Boolean) {
        sharedPreferences.edit().putBoolean("theme", isNightMode).apply()
    }
}
