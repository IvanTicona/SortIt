package com.example.sortit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sortit.R
import com.example.sortit.dataClasses.Task
import com.example.sortit.utils.longToDateString
import com.example.sortit.utils.longToHourString

class TaskHomeAdapter(
    private val tasks: List<Task>,
    private val onDelete: (Task) -> Unit,
    private val onTaskClick: (Task) -> Unit
): RecyclerView.Adapter<TaskHomeAdapter.TaskHomeViewHolder>() {

    inner class TaskHomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.title_task)
        val date: TextView = view.findViewById(R.id.datetext)
        val time: TextView = view.findViewById(R.id.timetext)
        val notes: TextView = view.findViewById(R.id.notes)
        val check: CheckBox = view.findViewById(R.id.done)
        val touch: CardView = view.findViewById(R.id.card)
        // Aquí puedes agregar más vistas si es necesario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHomeAdapter.TaskHomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_home, parent, false)
        return TaskHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHomeViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.text = task.nombre
        holder.date.text = longToDateString(task.fechaEmpieza)
        holder.time.text = longToHourString(task.horaEmpieza)
        holder.notes.text = task.notas
        holder.check.isChecked = task.completado
        // Aquí puedes asignar los demás datos de la tarea
        holder.touch.setOnClickListener {
            onTaskClick(task)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}
