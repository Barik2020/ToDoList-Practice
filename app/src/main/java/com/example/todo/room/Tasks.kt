package com.example.todo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")

data class Tasks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    val taskId: Int,
    @ColumnInfo(name = "taskText")
    val taskText: String,
    @ColumnInfo(name = "status")
    val status: Boolean
)
