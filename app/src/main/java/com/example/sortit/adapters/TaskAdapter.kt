package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.dataClasses.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        holder.textViewDescription.text = task.notas

        // Formatear la fecha
        val date = Date(task.fechaEmpieza) // task.date es Long, Date lo convierte a objeto fecha
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(date)
        holder.textViewDate.text = dateString

        // Formatear la hora
//        val time = Date(task.horaEmpieza) // task.time es Long, Date lo convierte a objeto fecha/hora
//        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val timeString = timeFormat.format(time)
//        holder.textViewTime.text = timeString
    }
    // Retornar cantidad de item en la lista
    override fun getItemCount(): Int {
        return taskList.size
    }
}