package com.example.budilnik.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budilnik.databinding.List2Binding
import com.example.budilnik.room.AppDatabase
import com.example.budilnik.room.ListEntity

class ListAdapter2 (var list: List<ListEntity>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ListAdapter2.Vh>() {
    private var selectedItems = IntArray(list.size)
    lateinit var appDatabase: AppDatabase

    inner class Vh(var malumotItemBinding: List2Binding) :
        RecyclerView.ViewHolder(malumotItemBinding.root) {

        fun onBind(malumotlar: ListEntity,position: Int) {
            appDatabase = AppDatabase.getInstance(malumotItemBinding.root.context)
            val taskByListName = appDatabase.taskDao().getTaskByListName(malumotlar.title)
            malumotItemBinding.total.text = "${taskByListName.size.toString()} tasks"
            malumotItemBinding.listName.text = malumotlar.title
            malumotItemBinding.listName.setTextColor(Color.parseColor(malumotlar.textcolor))
            malumotItemBinding.total.setTextColor(Color.parseColor(malumotlar.textcolor))
            malumotItemBinding.cardview.setBackgroundColor(Color.parseColor(malumotlar.color))
            if(selectedItems[position] == 1) malumotItemBinding.checked.setVisibility(View.VISIBLE);
            else malumotItemBinding.checked.setVisibility(View.GONE);


            malumotItemBinding.root.setOnClickListener {

                if (position != RecyclerView.NO_POSITION) {
                    setSelectedItem(position);
                    notifyDataSetChanged();
                }

                onItemClickListener.onItemClick(malumotlar,malumotItemBinding,position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(List2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemClick(malumotlar: ListEntity,malumotItemBinding: List2Binding,position: Int)
    }

    private fun initializeSeledtedItems() {
        for (item in selectedItems) item == 1
    }
    private fun setSelectedItem(position: Int) {
        for (i in 0 until selectedItems.size) {
            if (i == position) selectedItems[i] = 1 else selectedItems[i] = 0
        }
    }
}