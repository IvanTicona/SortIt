package com.example.sortit.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val completado: Boolean = false, // Estado
    val nombre: String, // Title
    val ubicacion: String?, // Ubicacion Asociada
    val todoElDia: Boolean,
    val fechaEmpieza: Long,
    val fechaTermina: Long, // Fecha Convertida
    val horaEmpieza: Long,
    val horaTermina: Long, // Hora Convertida
    val prioridad: Int,
    val correo: String?, // Correo Asociado
    val notas: String? //Notas
)