package com.example.todo.room

import androidx.room.*

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks_table")
    fun getTasks(): List<Tasks>

    @Query("DELETE FROM tasks_table")
    fun deleteAll()

    @Insert
    fun addTask(tasks: Tasks)

    @Delete
    fun deleteTask(tasks: Tasks)

    @Update
    fun updateTask(tasks: Tasks)
}