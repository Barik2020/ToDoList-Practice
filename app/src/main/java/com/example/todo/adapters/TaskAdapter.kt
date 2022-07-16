package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.RvTasksBinding
import com.example.todo.room.Tasks

class TaskAdapter(var task: List<Tasks>) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {


    fun submitList(newList: List<Tasks>) {
        task = newList
        notifyDataSetChanged() // это тяжело
    }
/*
    fun editText(item: View) {
        val binding = RvTasksBinding.bind(item)

        binding.taskFrame.setOnClickListener {
            editText?.invoke(
                Tasks(
                    task[adapterPosition].taskId,
                    task[adapterPosition].taskText,
                    task[adapterPosition].status
                )
            )
        }
    }

*/
    var checkIsClicked: ((Tasks) -> (Unit))? = null
    var onItemClick: ((Tasks) -> (Unit))? = null

    inner class TaskHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RvTasksBinding.bind(item)

        fun bind(task: Tasks) = with(binding) {
            taskText.text = task.taskText
            checkbox.isChecked = task.status
        }

        init {
            binding.checkbox.setOnCheckedChangeListener { _, b ->
                checkIsClicked?.invoke(
                    Tasks(
                        task[adapterPosition].taskId,
                        task[adapterPosition].taskText,
                        b
                    )
                )
            }
        }

        init {
            binding.taskFrame.setOnClickListener {
                onItemClick?.invoke(
                    Tasks(
                        task[adapterPosition].taskId,
                        task[adapterPosition].taskText,
                        task[adapterPosition].status
                    )
                )
            }
        }
    }


    // Надуватель шаблона, хранит в себе класс PlantHolder который в себе ранит ссылки на данные
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_tasks,
            parent,
            false
        )
        //Вместо использования R.layout спроси как нужно по халяльному искать айтемы
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(task[position])
    }

    override fun getItemCount(): Int {
        return task.size
    }

}