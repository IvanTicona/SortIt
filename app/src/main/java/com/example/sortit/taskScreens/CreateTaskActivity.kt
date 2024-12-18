package com.example.sortit.taskScreens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.sortit.R
import com.example.sortit.dataClasses.Task
import com.example.sortit.databinding.ActivityCreateTaskBinding
import com.example.sortit.room.AppDatabase
import com.example.sortit.room.DatabaseProvider
import com.example.sortit.utils.getDateLong
import com.example.sortit.utils.getHourLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var db: AppDatabase
    private var targetPriority: Int = 0

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // DB
        db = DatabaseProvider.getDatabase(this)

        // Spinner
        val priorities = resources.getStringArray(R.array.priority_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.definePriority.adapter = adapter

        // Listeners
        binding.btnCreateTask.setOnClickListener {
            createTask()
        }
        binding.definePriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val valorSeleccionado = parent.getItemAtPosition(position).toString()
                targetPriority = valorSeleccionado.toInt()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.dateStartPicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val dateString = "$selectedDay/${selectedMonth+1}/$selectedYear"
                    binding.dateStartPicker.text = dateString
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        binding.dateEndPicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val dateString = "$selectedDay/${selectedMonth+1}/$selectedYear"
                    binding.dateEndPicker.text = dateString
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        binding.hourStartPicker.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.hourStartPicker.text = timeString
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }
        binding.hourEndPicker.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.hourEndPicker.text = timeString
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }
        binding.arrowLeft.setOnClickListener {
            runOnUiThread {
                finish()
            }
        }
    }
    private fun createTask(){
        val name = binding.createName.text.toString().trim()
        val ubication = binding.createUbication.text.toString().trim()
        val notes = binding.bindNotes.text.toString().trim()
        val allDay = binding.allDaySwitch.isChecked
        val email = binding.bindEmail.text.toString().trim()
        // Validaciones
        if (name.isEmpty()) {
            binding.createName.error = "Ingresa un título"
            return
        }
        if(targetPriority == 0){
            binding.labelPriority.error = "Define una prioridad"
            return
        }
        // Crear Nueva Task
        val newTask = Task(
            completado = false,
            nombre = name,
            ubicacion = ubication,
            todoElDia = allDay,
            fechaEmpieza = getDateLong(binding.dateStartPicker.text.toString()),
            fechaTermina = getDateLong(binding.dateEndPicker.text.toString()),
            horaEmpieza = getHourLong(binding.hourStartPicker.text.toString()),
            horaTermina = getHourLong(binding.hourEndPicker.text.toString()),
            prioridad = targetPriority,
            correo = email,
            notas = notes
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.taskDao().createTask(newTask)
            runOnUiThread {
                finish()
            }
        }
    }
}