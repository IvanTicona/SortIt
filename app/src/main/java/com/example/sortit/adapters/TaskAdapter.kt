package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.dataClasses.Task

class TaskAdapter(private val taskList: List<Task>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    // Esta clase representa cada item
    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTaskTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewTaskDescription)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewTaskDate)
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
        holder.textViewDescription.text = task.descripcion
        // Supongamos que conviertes el timestamp en fecha legible
        // Esto es sólo un ejemplo, en la práctica necesitarás formatear el timestamp.
        holder.textViewDate.text = "Fecha: ${task.fecha}"
    }
    // Retornar cantidad de item en la lista
    override fun getItemCount(): Int {
        return taskList.size
    }
}