package com.example.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.db.ToDoRecord
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.android.synthetic.main.content_list.*

class ToDoListActivity : AppCompatActivity(), ToDoListAdapter.ToDoEvents {

    private val DISPLAYED = "DISPLAYED"

    lateinit var toDoAdapter: ToDoListAdapter
    private lateinit var toDoListViewModel: ToDoListViewModel
    private var whatIsShown: ToDoListAdapter.Status = ToDoListAdapter.Status.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        to_do_list.layoutManager = LinearLayoutManager(this)
        toDoAdapter = ToDoListAdapter(this)
        to_do_list.adapter = toDoAdapter

        if (savedInstanceState != null) {
            val statusName = savedInstanceState.getString(DISPLAYED)
            whatIsShown = ToDoListAdapter.Status.valueOf(statusName)
        }

        toDoListViewModel = ViewModelProviders.of(this).get(ToDoListViewModel::class.java)
        toDoListViewModel.getToDos().observe(this,
            Observer {
                toDoAdapter.setToDoItems(it, whatIsShown)
            })

        initButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(DISPLAYED, whatIsShown.toString())
    }

    private fun initButtons() {
        add_to_do.setOnClickListener { addTask() }
        btnShowActive.setOnClickListener {
            toDoAdapter.showActive();
            whatIsShown = ToDoListAdapter.Status.ACTIVE
        }
        btnShowCompleted.setOnClickListener {
            toDoAdapter.showCompleted();
            whatIsShown = ToDoListAdapter.Status.COMPLETED
        }
        btnShowAll.setOnClickListener {
            toDoAdapter.showAll();
            whatIsShown = ToDoListAdapter.Status.ALL
        }
    }

    private fun addTask() {
        val intent = Intent(this@ToDoListActivity, CreateToDoItem::class.java)
        startActivityForResult(intent, Constants.ADD_TO_DO)
    }

    override fun onChangeStatus(todoRecord: ToDoRecord) {
        val todo = todoRecord
        todo.status = !todo.status
        toDoListViewModel.updateToDo(todo)
    }

    override fun onViewClicked(todoRecord: ToDoRecord) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.ADD_TO_DO) {
                val toDo = data?.getParcelableExtra<ToDoRecord>(Constants.obj)!!
                toDoListViewModel.saveToDo(toDo)
            }
        }
    }
}
