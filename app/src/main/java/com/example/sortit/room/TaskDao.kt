package com.example.sortit.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sortit.dataClasses.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int): Task
    @Insert
    fun createTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Update
    fun editTask(task: Task)
}