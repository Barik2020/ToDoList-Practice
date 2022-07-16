package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.room.Sections
import com.example.todo.room.SectionsDao
import com.example.todo.room.Tasks
import com.example.todo.room.TasksDao

@Database(
    entities = [Tasks::class, Sections::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun tasksDao() : TasksDao
    abstract fun sectionDao() : SectionsDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, MyDatabase::class.java, "todo_db")
                    .build()
                return INSTANCE!!
            }
        }
    }

}


