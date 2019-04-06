package com.example.todolist

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.example.todolist.db.ToDoRecord
import kotlinx.android.synthetic.main.activity_create_to_do_item.*
import kotlinx.android.synthetic.main.to_do_item.*
import java.text.SimpleDateFormat
import java.util.*

class CreateToDoItem : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TITLE = "title"
    private val IMPORTANCE = "importance"
    private val DESCRITION = "description"
    private val DATE = "date"

    private var selectedItem = 0
    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_to_do_item)

        editImportant.onItemSelectedListener = this
        btnCreateToDo.setOnClickListener { saveToDo() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(TITLE, editTitle.text.toString())
        outState.putInt(IMPORTANCE, selectedItem)
        outState.putString(DESCRITION, editDescription.text.toString())
        val date: Long = if (selectedDate == null) 0.toLong() else selectedDate!!.time
        outState.putLong(DATE, date)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        editTitle.setText(savedInstanceState?.getString(TITLE))
        selectedItem = savedInstanceState!!.getInt(IMPORTANCE)
        editImportant.setSelection(selectedItem)
        editDescription.setText(savedInstanceState.getString(DESCRITION))
        val dateSec = savedInstanceState.getLong(DATE)
        if (dateSec != 0.toLong()) {
            selectedDate = Date(dateSec)
            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            dateView.text = sdf.format(selectedDate)
        }
    }

    override fun onResume() {
        super.onResume()
        initDatePickerDialog()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        selectedItem = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun saveToDo() {
        val toDo = ToDoRecord(
            id = null,
            title = editTitle.text.toString(),
            status = false,
            importance = selectedItem,
            description = editDescription.text.toString(),
            date = selectedDate
        )
        val intent = Intent()

        intent.putExtra(Constants.obj, toDo)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun initDatePickerDialog() {
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(year, monthOfYear, dayOfMonth)

            selectedDate = Date(cal.timeInMillis)
            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            dateView.text = sdf.format(cal.time)

        }

        editDate.setOnClickListener {
            DatePickerDialog(
                this@CreateToDoItem,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

}
