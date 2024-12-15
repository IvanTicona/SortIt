package com.example.sortit.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sortit.dataClasses.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>
    @Insert
    fun createTask(task: Task)
}