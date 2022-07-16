package com.example.todo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.MyDatabase
import com.example.todo.adapters.SectionsAdapter
import com.example.todo.adapters.TaskAdapter
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.room.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val selectorAdapter = SectionsAdapter(emptyList())
    private var taskAdapter = TaskAdapter(emptyList())

    private lateinit var database: MyDatabase
    private lateinit var dao: TasksDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() = with(binding) {
        rvTopSections.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvTopSections.adapter = selectorAdapter

        //TODO: move this to viewModel
        database = MyDatabase.getInstance(this@MainActivity)
        dao = database.tasksDao()

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
/*
        selectorAdapter.iCanStartActivity = {
            //taskAdapter.submitList(tskList)
            taskAdapter.submitList(Tasks)
        }
*/
        rvTasksSection.layoutManager = LinearLayoutManager(this@MainActivity)
        rvTasksSection.adapter = taskAdapter

        taskAdapter.checkIsClicked = {
            lifecycleScope.launch(Dispatchers.IO) {
                dao.updateTask(it)
            }
        }

        taskAdapter.onItemClick = { task ->
            lifecycleScope.launch(Dispatchers.IO) {
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                intent.putExtra("TASK_TEXT", task.taskText)
                intent.putExtra("TASK_ID", task.taskId)
                intent.putExtra("TASK_STATUS", task.status)
                startActivity(intent)

//                dao.updateTask(it)
            }
        }
        //dao.deleteTask(listOf<Task>().toTypedArray())


    }

    override fun onResume() {
        super.onResume()
        submitList()
    }

    private fun submitList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = dao.getTasks()
            withContext(Dispatchers.Main) {
                taskAdapter.submitList(list)
            }
        }
    }
}