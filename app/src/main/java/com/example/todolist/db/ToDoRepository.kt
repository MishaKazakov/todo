package com.example.todolist.db

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoRepository(application: Application) {

    private val toDoDao: ToDoDao
    val toDos: LiveData<List<ToDoRecord>>

    init {
        val database = ToDoDB.getInstance(application.applicationContext)
        toDoDao = database!!.toDoDao()
        toDos = toDoDao.getToDos()
    }

    @WorkerThread
    fun insert(toDo: ToDoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoDao.saveToDo(toDo)
        }
    }

    fun getAll() = toDos

    @WorkerThread
    fun update(toDo: ToDoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoDao.updateToDo(toDo)
        }
    }

    @WorkerThread
    fun delete(toDo: ToDoRecord) = runBlocking {
        this.launch(Dispatchers.IO) {
            toDoDao.deleteToDo(toDo)
        }
    }
}