package com.example.budilnik.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.databinding.TaskListBinding
import com.example.budilnik.room.TaskEntity

class TaskAdapter(var list: List<TaskEntity>,var color: String,var textcolor: String) : RecyclerView.Adapter<TaskAdapter.Vh>() {

    inner class Vh(var malumotItemBinding: TaskListBinding) :
        RecyclerView.ViewHolder(malumotItemBinding.root) {

        fun onBind(malumotlar: TaskEntity, position: Int) {
            malumotItemBinding.listName.text = malumotlar.task
            malumotItemBinding.qalandar.text = malumotlar.calendar
            malumotItemBinding.containerer.setBackgroundColor(Color.parseColor(color))
            malumotItemBinding.listName.setTextColor(Color.parseColor(textcolor))
            malumotItemBinding.qalandar.setTextColor(Color.parseColor(textcolor))
            malumotItemBinding.vaqt.text = ":${malumotlar.time}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(TaskListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}