package com.example.todo.room

import androidx.room.*

@Dao
interface SectionsDao {
    @Query("SELECT * FROM `sections_table`")
    fun getSections(): List<Sections>

    @Insert
    fun addSection(sections: Sections)

    @Delete
    fun deleteSection(sections: Sections)

    @Update
    fun updateSection(sections: Sections)
}