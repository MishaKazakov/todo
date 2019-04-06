package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.db.ToDoRecord

class ToDoListAdapter(toDoEvents: ToDoEvents) :
    RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {

    private var todoList: List<ToDoRecord> = arrayListOf()
    private var displayedToDoList: List<ToDoRecord> = arrayListOf()
    private val handlers: ToDoEvents = toDoEvents

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.bind(displayedToDoList[position], handlers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.to_do_item, parent, false)

        return ToDoListViewHolder(binding)
    }

    fun setToDoItems(toDoItemData: List<ToDoRecord>, whatIsShown: Status) {
        todoList = toDoItemData
        when (whatIsShown) {
            Status.ALL -> showAll()
            Status.ACTIVE -> showActive()
            Status.COMPLETED -> showCompleted()
        }
    }

    override fun getItemCount(): Int = displayedToDoList.size

    fun showAll() {
        displayedToDoList = todoList
        notifyDataSetChanged()
    }

    fun showActive() = filter(false)

    fun showCompleted() = filter(true)

    fun filter(value: Boolean) {
        displayedToDoList = todoList.filter { it.status == value }
        notifyDataSetChanged()
    }

    class ToDoListViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ToDoRecord, handlers: ToDoEvents) {
            binding.setVariable(BR.data, data)
            binding.setVariable(BR.handlers, handlers)
            binding.executePendingBindings()
        }
    }

    interface ToDoEvents {
        fun onChangeStatus(todoRecord: ToDoRecord)
        fun onViewClicked(todoRecord: ToDoRecord)
    }

    enum class Status {
        ALL, ACTIVE, COMPLETED
    }
}