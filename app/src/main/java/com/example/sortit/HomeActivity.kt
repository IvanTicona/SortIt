package com.example.sortit

import android.content.Intent
import android.graphics.Rect
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
import com.example.sortit.settingScreens.SettingsActivity
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskHomeAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerViewTasksHome
        val view = binding.root
        setContentView(view)

        db = DatabaseProvider.getDatabase(this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        taskAdapter = TaskHomeAdapter(
            emptyList(),
            onDelete = {deleteTask(it)},
            onTaskClick = {}
        )

        recyclerView.adapter = taskAdapter

        loadTasksFromDatabase()

        binding.editCard.setOnClickListener {
            val intentShowList = Intent(this, ListTasksActivity::class.java)
            startActivity(intentShowList)
        }
        binding.createCard.setOnClickListener {
            val intentCreateTask = Intent(this, CreateTaskActivity::class.java)
            startActivity(intentCreateTask)
        }

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
                taskAdapter = TaskHomeAdapter(
                    tasks,
                    onDelete = { deleteTask(it) },
                    onTaskClick = { navigateToScreen(this@HomeActivity, TaskActivity::class.java, "TASK_ID", it.id) }
                )
                binding.recyclerViewTasksHome.adapter = taskAdapter
            }
        }
    }

    private fun deleteTask(task: Task){
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