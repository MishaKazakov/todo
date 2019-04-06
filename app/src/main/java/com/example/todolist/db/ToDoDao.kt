package com.example.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Insert
    fun saveToDo(toDoRecord: ToDoRecord)

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getToDos(): LiveData<List<ToDoRecord>>

    @Update
    fun updateToDo(toDoRecord: ToDoRecord)

    @Delete
    fun deleteToDo(toDoRecord: ToDoRecord)
}