package com.example.sortit.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val fecha: Long,
//    val hora: String,
//    val prioridad: Int,
//    val creadoEn: String,
//    val actualizadoEn: String,
//    val complete: Boolean = false,
//    val email: String,
//    val ubicacion: String
)