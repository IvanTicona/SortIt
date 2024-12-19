package com.example.sortit.taskScreens

import android.icu.util.Calendar
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

        binding.arrowLeft.setOnClickListener {
            runOnUiThread {
                finish()
            }
        }

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
            var tasks = db.taskDao().getAllTasks()
            //println(tasks)
            var tasksList = tasks.toMutableList()

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

            tasksList.sortWith(comparator)
            tasks = tasksList.toList()
            //println(tasks)

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