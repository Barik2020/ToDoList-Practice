package com.example.todo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.todo.MyDatabase
import com.example.todo.databinding.TaskAddBinding
import com.example.todo.room.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {
    var binding_ : TaskAddBinding? = null
    val binding : TaskAddBinding get() = binding_!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = TaskAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = MyDatabase.getInstance(this)
        val dao = database.tasksDao()

        //val data = binding.taskTextBox


        binding.submitButton.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                dao.addTask(
                    Tasks(
                        0,
                        binding.taskTextBox.text.toString(),
                        false,
                    )
                )
                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }
    }
}