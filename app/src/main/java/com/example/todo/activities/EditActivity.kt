package com.example.todo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.todo.MyDatabase
import com.example.todo.databinding.TaskEditBinding
import com.example.todo.room.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditActivity : AppCompatActivity() {
    var binding_ : TaskEditBinding? = null
    val binding : TaskEditBinding get() = binding_!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = TaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskText = intent.getStringExtra("TASK_TEXT") ?: throw RuntimeException("task's text cannot be null!")
        val tasksLocal : Int = intent.getIntExtra("TASK_ID", -1)
        val taskStatus = intent.getBooleanExtra("TASK_STATUS", false)
        binding.taskTextBox.setText(taskText)

        val database = MyDatabase.getInstance(this)
        val dao = database.tasksDao()

        binding.submitButton.setOnClickListener {
            if(tasksLocal == -1) throw RuntimeException("Task id must not be null")

            lifecycleScope.launch(Dispatchers.IO) {

                dao.updateTask(
                    Tasks(
                        taskId = tasksLocal,
                        binding.taskTextBox.text.toString(),
                        taskStatus,
                    )
                )
                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }

        binding.deleteButton.setOnClickListener {
            if(tasksLocal == -1) throw RuntimeException("Task id must not be null")

            lifecycleScope.launch(Dispatchers.IO) {

                dao.deleteTask(
                    Tasks(
                        taskId = tasksLocal,
                        taskText,
                        taskStatus,
                    )
                )
                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }
    }
}