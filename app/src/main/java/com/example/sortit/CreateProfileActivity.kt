package com.example.sortit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sortit.databinding.ActivityCreateProfileBinding
import com.example.sortit.databinding.ActivityUserPlanBinding

class CreateProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openSortitButton.setOnClickListener(){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }
}