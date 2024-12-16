package com.example.sortit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sortit.databinding.ActivityProfileBinding
import com.example.sortit.databinding.ActivityUserPlanBinding
import com.example.sortit.settingScreens.SettingsActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iconBuscar.setOnClickListener(){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        }
        binding.iconSettings.setOnClickListener(){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.homeButton.setOnClickListener(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.profileButton.setOnClickListener(){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}