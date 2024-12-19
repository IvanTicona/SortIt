package com.example.sortit

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityHomeBinding
import com.example.sortit.settingScreens.SettingsActivity
import com.example.sortit.taskScreens.CreateTaskActivity
import com.example.sortit.taskScreens.ListTasksActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val activities = mutableListOf<Task>()

        val comparator = Comparator<Task> { task1, task2 ->
            fun normalDate(fecha: Long): Long {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = fecha
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                return calendar.timeInMillis
            }
            val comparing = normalDate(task1.fechaEmpieza) compareTo normalDate(task2.fechaEmpieza)
            if(comparing != 0) {
                comparing
            } else {
                task1.horaEmpieza compareTo task2.horaEmpieza
            }
        }

        activities.sortWith(comparator)

        binding.showListBtn.setOnClickListener {
            val intentShowList = Intent(this, ListTasksActivity::class.java)
            startActivity(intentShowList)
        }
        binding.createTaskBtn.setOnClickListener {
            val intentCreateTask = Intent(this, CreateTaskActivity::class.java)
            startActivity(intentCreateTask)
        }

        binding.searchIcon.setOnClickListener(){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

        }
        binding.settingsIcon.setOnClickListener(){
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
}