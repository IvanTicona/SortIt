package com.example.sortit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sortit.databinding.ActivityProfileBinding
import com.example.sortit.databinding.ActivitySearchBinding
import com.example.sortit.settingScreens.SettingsActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBuscar.setOnClickListener(){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        }
        binding.iconSettings.setOnClickListener(){
            val intent = Intent(this, SettingsActivity::class.java)
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
    }
}