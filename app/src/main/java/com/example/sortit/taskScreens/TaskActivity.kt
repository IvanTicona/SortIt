package com.example.sortit.taskScreens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sortit.R
import com.example.sortit.databinding.ActivityEditTaskBinding
import com.example.sortit.databinding.ActivityTaskBinding
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
import com.example.sortit.utils.getDateLong
import com.example.sortit.utils.getHourLong
import com.example.sortit.utils.longToDateString
import com.example.sortit.utils.longToHourString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var db: AppDatabase
    private var targetPriority: Int = 0
    private var currentTaskId: Int = -1

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // DB
        db = DatabaseProvider.getDatabase(this)

        // Spinner
        /*val priorities = resources.getStringArray(R.array.priority_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.definePriority.adapter = adapter*/

        // Recibir Task y Llenar campos
        currentTaskId = intent.getIntExtra("TASK_ID", -1)
        if (currentTaskId != -1) loadTaskData(currentTaskId)

        //Listeners
        /*binding.definePriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val valorSeleccionado = parent.getItemAtPosition(position).toString()
                targetPriority = valorSeleccionado.toInt()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }*/

        binding.btnOK.setOnClickListener {
            runOnUiThread {
                finish()
            }
        }
        binding.arrowLeft.setOnClickListener {
            runOnUiThread {
                finish()
            }
        }
    }
    private fun loadTaskData(taskId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = db.taskDao().getTaskById(taskId)
            val priorities = resources.getStringArray(R.array.priority_options)
            runOnUiThread {
                binding.titleTask.setText(task.nombre)
                binding.createUbication.setText(task.ubicacion)
                binding.allDaySwitch.isChecked = task.todoElDia
                binding.dateStartPicker.text = longToDateString(task.fechaEmpieza)
                binding.dateEndPicker.text = longToDateString(task.fechaTermina)
                binding.hourStartPicker.text = longToHourString(task.horaEmpieza)
                binding.hourEndPicker.text = longToHourString(task.horaTermina)
                binding.definePriority.setSelection(priorities.indexOf(task.prioridad.toString()))
                binding.bindEmail.setText(task.correo)
                binding.bindNotes.setText(task.notas)

                binding.titleTask.isEnabled = false
                binding.createUbication.isEnabled = false
                binding.allDaySwitch.isEnabled = false
                binding.dateStartPicker.isEnabled = false
                binding.dateEndPicker.isEnabled = false
                binding.hourStartPicker.isEnabled = false
                binding.hourEndPicker.isEnabled = false
                binding.definePriority.isEnabled = false
                binding.bindEmail.isEnabled = false
                binding.bindNotes.isEnabled = false
            }
        }
    }
    private fun saveUpdatedTask() {
        // Obtener los datos de los campos editados
        val updatedName = binding.titleTask.text.toString().trim()
        val updatedUbication = binding.createUbication.text.toString().trim()
        val updatedAllDay = binding.allDaySwitch.isChecked
        val updatedDateStart = getDateLong(binding.dateStartPicker.text.toString())
        val updatedDateEnd = getDateLong(binding.dateEndPicker.text.toString())
        val updatedHourStart = getHourLong(binding.hourStartPicker.text.toString())
        val updatedHourEnd = getHourLong(binding.hourEndPicker.text.toString())
        val updatePriority = targetPriority
        val updatedEmail = binding.bindEmail.text.toString().trim()
        val updatedNotes = binding.bindNotes.text.toString().trim()
        // Actualizar la tarea
        CoroutineScope(Dispatchers.IO).launch {
            val task = db.taskDao().getTaskById(currentTaskId)
            val updatedTask = task.copy(
                nombre = updatedName,
                ubicacion = updatedUbication,
                todoElDia = updatedAllDay,
                fechaEmpieza = updatedDateStart,
                fechaTermina = updatedDateEnd,
                horaEmpieza = updatedHourStart,
                horaTermina = updatedHourEnd,
                prioridad = updatePriority,
                correo = updatedEmail,
                notas = updatedNotes
            )
            db.taskDao().editTask(updatedTask)
            runOnUiThread {
                finish()
            }
        }
    }
}