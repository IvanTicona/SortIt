package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.dataClasses.Task
import com.example.sortit.utils.longToDateString
import com.example.sortit.utils.longToHourString

class TasksOfDayAdapter(
    private var taskList: List<Task>,
    private val onTaskClick: (Task) -> Unit
): RecyclerView.Adapter<TasksOfDayAdapter.TaskOfDayViewHolder>() {

    // Esta clase representa cada item
    inner class TaskOfDayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.textTaskName)
        val textViewTimeStart: TextView = itemView.findViewById(R.id.textTimeStart)
        val textViewTimeEnd: TextView = itemView.findViewById(R.id.textTimeEnd)
    }
    // Crear la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TaskOfDayViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_day, parent, false)
        return TaskOfDayViewHolder(view)
    }
    // Asignar los datos a las vistas
    override fun onBindViewHolder(holder: TaskOfDayViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTitle.text = task.nombre
        holder.textViewTimeStart.text = longToDateString(task.horaEmpieza)
        holder.textViewTimeEnd.text = longToHourString(task.horaTermina)

        // Listeners
        holder.textViewTitle.setOnClickListener {
            onTaskClick(task)
        }
    }
    // Retornar cantidad de item en la lista
    override fun getItemCount(): Int = taskList.size
    // Actualizar lista
    fun updateTasks(newTasks: List<Task>) {
        taskList = newTasks
    }
}