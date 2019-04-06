package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.Converters

@Database(entities = arrayOf(ToDoRecord::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ToDoDB : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoDB? = null

        fun getInstance(context: Context): ToDoDB? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ToDoDB::class.java,
                    "todo_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}