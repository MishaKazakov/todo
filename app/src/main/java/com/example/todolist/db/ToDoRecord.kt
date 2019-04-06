package com.example.todolist.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.Importance
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "todo")
@Parcelize()
data class ToDoRecord(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name= "title") var title: String,
    @ColumnInfo(name= "status") var status: Boolean,
    @ColumnInfo(name= "importance") var importance: Int,
    @ColumnInfo(name= "description") var description: String?,
    @ColumnInfo(name= "date") var date: Date?
) : Parcelable

