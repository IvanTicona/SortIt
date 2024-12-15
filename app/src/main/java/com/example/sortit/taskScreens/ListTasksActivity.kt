package com.example.sortit.taskScreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.sortit.adapters.TaskAdapter
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityListTasksBinding
import com.example.sortit.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTasksBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTasksBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerViewTasks
        val view = binding.root
        setContentView(view)

        // Crear instancia de DB
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

        // Inicializar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList())
        recyclerView.adapter = taskAdapter
        // Cargar DB
        loadTasksFromDatabase()
    }
    private fun loadTasksFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            // Cargamos la lista
            val tasks = db.taskDao().getAllTasks()
            // Si esta vacia
            if (tasks.isEmpty()) {
                // Prueba para datos
                val newTask = Task(
                    nombre = "Estudiar Room",
                    descripcion = "Repasar la implementación de Room en Android",
                    fecha = System.currentTimeMillis()
                )
                db.taskDao().createTask(newTask)
            }
            // Volver a actualizar la lista
            val updatedTasks = db.taskDao().getAllTasks()
            runOnUiThread {
                taskAdapter = TaskAdapter(updatedTasks)
                binding.recyclerViewTasks.adapter = taskAdapter
            }
        }
    }
}