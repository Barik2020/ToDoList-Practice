package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.room.Sections

class SectionsAdapter(var sectionsList: List<Sections>) : RecyclerView.Adapter<SectionsAdapter.MyView>() {

    var iCanStartActivity: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        //создает столько вьюхолдеров сколько поместится на экран
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_sections,
            parent,
            false
        )
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        //будет вызвана тогда, когда элемент. вышедший за пределы экрана будет преиспользоваться
        holder.textView.text = sectionsList[position].sectionName
    }

    override fun getItemCount(): Int {
        // возвращает количество элементов в списке
        return sectionsList.size
    }

    inner class MyView(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.selectorButton)

        init {
            view.setOnClickListener {
                iCanStartActivity?.invoke(sectionsList[adapterPosition].sectionId)
            }
        }
    }

}