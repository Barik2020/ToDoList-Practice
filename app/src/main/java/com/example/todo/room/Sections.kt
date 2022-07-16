package com.example.todo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections_table")

data class Sections(
    @PrimaryKey(autoGenerate = true)
    val sectionId: Int,
    val sectionName: String
)
