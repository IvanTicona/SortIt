package com.example.sortit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ItemDateDetailBinding
import com.example.sortit.room.DatabaseProvider
import com.example.sortit.taskScreens.EditTaskActivity
import com.example.sortit.utils.navigateToScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class DateDetailPagerAdapter(
    private val initialDate: Date,
    private val context: Context
) : RecyclerView.Adapter<DateDetailPagerAdapter.DateDetailViewHolder>() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    inner class DateDetailViewHolder(private val binding: ItemDateDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tasksAdapter: TasksOfDayAdapter

        init {
            binding.recyclerViewTaskDay.layoutManager = LinearLayoutManager(context)
            tasksAdapter = TasksOfDayAdapter(emptyList()) { task ->
                navigateToScreen(context, EditTaskActivity::class.java, "TASK_ID", task.id)
            }
            binding.recyclerViewTaskDay.adapter = tasksAdapter
        }

        fun bind(date: Date) {
            scope.launch {
//                val dateMilis = date.time
                val db = DatabaseProvider.getDatabase(context)
                val taskDao = db.taskDao()
                val tasks: List<Task> = withContext(Dispatchers.IO) {
//                    taskDao.getTasksForDate(dateMilis)
                    taskDao.getAllTasks()
                }
                tasksAdapter.updateTasks(tasks)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateDetailViewHolder {
        val binding = ItemDateDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateDetailViewHolder(binding)
    }
    override fun onBindViewHolder(holder: DateDetailViewHolder, position: Int) {
        val dateOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.DAY_OF_MONTH, dateOffset)
        val date = calendar.time
        holder.bind(date)
    }
    override fun getItemCount(): Int = Int.MAX_VALUE
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        job.cancel()
    }

    fun getDateAtPosition(position: Int): Date {
        val dateOffset = position - (Int.MAX_VALUE / 2)
        val calendar = Calendar.getInstance().apply { time = initialDate }
        calendar.add(Calendar.DAY_OF_MONTH, dateOffset)
        return calendar.time
    }
}
