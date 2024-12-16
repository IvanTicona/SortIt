package com.example.sortit.taskScreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityCreateTaskBinding
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = DatabaseProvider.getDatabase(this)

        binding.btnCreateTask.setOnClickListener {
//            createTask()
        }
    }
//    fun createTask(){
//        val title = binding.createName.text.toString().trim()
//        val description = binding.createDescription.text.toString().trim()
//        val dateString = binding.createDate.text.toString().trim()
//
//        if (title.isEmpty()) {
//            binding.createName.error = "Ingresa un título"
//            return
//        }
//
//        if (description.isEmpty()) {
//            binding.createDescription.error = "Ingresa una descripción"
//            return
//        }
//
//        val dateLong = if (dateString.isEmpty()) {
//            System.currentTimeMillis()
//        } else {
//            dateString.toLongOrNull() ?: System.currentTimeMillis()
//        }
//
//        val newTask = Task(
//            nombre = title,
//            descripcion = description,
//            fecha = dateLong
//        )
//
//        CoroutineScope(Dispatchers.IO).launch {
//            db.taskDao().createTask(newTask)
//            runOnUiThread {
//                finish()
//            }
//        }
//    }
}