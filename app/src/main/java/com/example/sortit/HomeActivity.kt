package com.example.sortit

import android.content.Intent
import android.graphics.Rect
import android.icu.number.NumberFormatter.RoundingPriority
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.adapters.TaskAdapter
import com.example.sortit.adapters.TaskHomeAdapter
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityHomeBinding
import com.example.sortit.databinding.ActivityProfileBinding
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
import com.example.sortit.settingScreens.SettingsActivity
import com.example.sortit.taskScreens.CreateTaskActivity
import com.example.sortit.taskScreens.EditTaskActivity
import com.example.sortit.taskScreens.ListTasksActivity
import com.example.sortit.taskScreens.TaskActivity
import com.example.sortit.utils.navigateToScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: AppDatabase
    private lateinit var taskAdapter: TaskHomeAdapter
    private lateinit var priorityTaskAdapter: TaskHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DB
        db = DatabaseProvider.getDatabase(this)

        // Configurar el primer RecyclerView (por fecha)
        binding.recyclerViewTasksHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        taskAdapter = TaskHomeAdapter(
            emptyList(),
            onDelete = { deleteTask(it) },
            onTaskClick = { }
        )
        binding.recyclerViewTasksHome.adapter = taskAdapter

        // Configurar el segundo RecyclerView (por prioridad)
        binding.recyclerViewTasksPriority.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        priorityTaskAdapter = TaskHomeAdapter(
            emptyList(),
            onDelete = { deleteTask(it) },
            onTaskClick = { }
        )
        binding.recyclerViewTasksPriority.adapter = priorityTaskAdapter

        // Cargar tareas desde la base de datos
        loadTasksFromDatabase()

        // Navegar a ListTasksActivity
        binding.editCard.setOnClickListener {
            val intentShowList = Intent(this, ListTasksActivity::class.java)
            startActivity(intentShowList)
        }

        // Navegar a CreateTaskActivity
        binding.createCard.setOnClickListener {
            val intentCreateTask = Intent(this, CreateTaskActivity::class.java)
            startActivity(intentCreateTask)
        }

        binding.iconBuscar.setOnClickListener {
            val intentLookForTask = Intent(this, SearchActivity::class.java)
            startActivity(intentLookForTask)
        }

        binding.iconSettings.setOnClickListener {
            val intentGoToSettings = Intent(this, SettingsActivity::class.java)
            startActivity(intentGoToSettings)
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

    private fun loadTasksFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            // Cargamos la lista de tareas desde la base de datos
            var tasks = db.taskDao().getAllTasks()
            var tasksList = tasks.toMutableList()

            // Ordenar por fecha de inicio (fechaEmpieza)
            val dateComparator = Comparator<Task> { task1, task2 ->
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
                if (comparing != 0) {
                    comparing
                } else {
                    task1.horaEmpieza compareTo task2.horaEmpieza
                }
            }

            tasksList.sortWith(dateComparator)
            tasks = tasksList.toList()

            // Ordenar por prioridad
            val priorityComparator = Comparator<Task> { task1, task2 ->
                task2.prioridad.compareTo(task1.prioridad) // Orden descendente por prioridad
            }

            val tasksByPriority = tasksList.sortedWith(priorityComparator)

            // Actualizar el RecyclerView con las tareas ordenadas por fecha
            runOnUiThread {
                taskAdapter = TaskHomeAdapter(
                    tasks,
                    onDelete = { deleteTask(it) },
                    onTaskClick = { navigateToScreen(this@HomeActivity, TaskActivity::class.java, "TASK_ID", it.id) }
                )
                binding.recyclerViewTasksHome.adapter = taskAdapter

                // Actualizar el RecyclerView con las tareas ordenadas por prioridad
                priorityTaskAdapter = TaskHomeAdapter(
                    tasksByPriority,
                    onDelete = { deleteTask(it) },
                    onTaskClick = { navigateToScreen(this@HomeActivity, TaskActivity::class.java, "TASK_ID", it.id) }
                )
                binding.recyclerViewTasksPriority.adapter = priorityTaskAdapter
            }
        }
    }

    private fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            db.taskDao().deleteTask(task)
            val updatedTasks = db.taskDao().getAllTasks()
            runOnUiThread {
                taskAdapter = TaskHomeAdapter(updatedTasks, onDelete = { deleteTask(it) }, onTaskClick = {})
                binding.recyclerViewTasksHome.adapter = taskAdapter
            }
        }
    }
}