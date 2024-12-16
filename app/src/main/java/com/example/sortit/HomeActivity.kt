package com.example.sortit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.databinding.ActivityHomeBinding
import com.example.sortit.taskScreens.CreateTaskActivity
import com.example.sortit.taskScreens.ListTasksActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.showListBtn.setOnClickListener {
            val intentShowList = Intent(this, ListTasksActivity::class.java)
            startActivity(intentShowList)
        }
        binding.createTaskBtn.setOnClickListener {
            val intentCreateTask = Intent(this, CreateTaskActivity::class.java)
            startActivity(intentCreateTask)
        }
    }
}