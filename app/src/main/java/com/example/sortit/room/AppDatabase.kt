package com.example.sortit.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sortit.dataClasses.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}