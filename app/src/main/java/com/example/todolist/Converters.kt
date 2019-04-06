package com.example.todolist

import android.util.Log
import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        Log.d("fromTimestamp", value.toString())
        val res = value?.let { Date(it) }
        Log.d("fromTimestamp", res.toString())
        return res
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        val res = date?.time
        Log.d("dateToTimestamp", res.toString())
        return res
    }
}
