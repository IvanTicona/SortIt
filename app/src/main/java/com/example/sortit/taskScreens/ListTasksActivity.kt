package com.example.sortit.taskScreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.adapters.TaskAdapter
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityListTasksBinding
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
import com.example.sortit.utils.navigateToScreen
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

        // DB
        db = DatabaseProvider.getDatabase(this)

        // Inicializar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList(),
                onDelete = { deleteTask(it) },
                onTaskClick =  {}
            )
        recyclerView.adapter = taskAdapter

        // Cargar DB
        loadTasksFromDatabase()
    }
    override fun onResume() {
        super.onResume()
        loadTasksFromDatabase()
    }
    private fun loadTasksFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            // Cargamos la lista
            val tasks = db.taskDao().getAllTasks()
            runOnUiThread {
                taskAdapter = TaskAdapter(
                    tasks,
                    onDelete = { deleteTask(it) },
                    onTaskClick = { navigateToScreen(this@ListTasksActivity, EditTaskActivity::class.java, "TASK_ID", it.id) }
                )
                binding.recyclerViewTasks.adapter = taskAdapter
            }
        }
    }
    private fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch {
            db.taskDao().deleteTask(task)
            val updatedTasks = db.taskDao().getAllTasks()
            runOnUiThread {
                taskAdapter = TaskAdapter(updatedTasks, onDelete = { deleteTask(it) }, onTaskClick = {})
                binding.recyclerViewTasks.adapter = taskAdapter
            }
        }
    }
}