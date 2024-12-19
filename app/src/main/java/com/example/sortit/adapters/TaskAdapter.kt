package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.dataClasses.Task
import com.example.sortit.utils.longToDateString

class TaskAdapter(
    private val taskList: List<Task>,
    private val onDelete: (Task) -> Unit,
    private val onTaskClick: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Esta clase representa cada item
    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTaskTitle)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewTaskDate)
        val btnDelete: View = itemView.findViewById(R.id.deleteBtn)
    }
    // Crear la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_task_item, parent, false)
        return TaskViewHolder(view)
    }
    // Asignar los datos a las vistas
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTitle.text = task.nombre
        holder.textViewDate.text = longToDateString(task.fechaEmpieza)

        // Listeners
        holder.btnDelete.setOnClickListener {
            onDelete(task)
        }
        holder.textViewTitle.setOnClickListener {
            onTaskClick(task)
        }
    }
    // Retornar cantidad de item en la lista
    override fun getItemCount(): Int = taskList.size
}