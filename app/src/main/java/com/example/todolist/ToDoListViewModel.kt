package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todolist.db.ToDoDB
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoRecord
import com.example.todolist.db.ToDoRepository

class ToDoListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoRepository = ToDoRepository(application)
    private val toDoList: LiveData<List<ToDoRecord>> = repository.getAll()

    fun saveToDo(toDoRecord: ToDoRecord) {
        repository.insert(toDoRecord)
    }

    fun getToDos(): LiveData<List<ToDoRecord>> = toDoList

    fun updateToDo(toDoRecord: ToDoRecord) {
        repository.update(toDoRecord)
    }

    fun deleteToDo(toDoRecord: ToDoRecord) {
        repository.delete(toDoRecord)
    }
}