package com.example.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class EventAddActivity : AppCompatActivity() {

    private val event = Event()
    private lateinit var eventViewModel : EventModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_event)
        eventViewModel = ViewModelProvider(this)[EventModel::class.java]
    }

    fun pickStartDate(view: View) { event.startDatePick(this,view) }
    fun pickStartTime(view: View) { event.startTimePick(this,view) }
    fun pickEndDate(view: View) { event.endDatePick(this,view) }
    fun pickEndTime(view: View) { event.endTimePick(this,view) }

    fun cancelAddButton(view: View) {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }
    fun saveAddButton(view: View) {
        event.setTitle(findViewById<EditText>(R.id.titlePlainText).text.toString(), view)
        event.setLocalization(findViewById<EditText>(R.id.locationEditText).text.toString())
        val myIntent = Intent()

        myIntent.putExtra("EventTitle", event.title)
        myIntent.putExtra("EventLocation", event.location)
        myIntent.putExtra("EventStartDate",event.startDate.toString())
        myIntent.putExtra("EventEndDate",event.endDate.toString())
        myIntent.putExtra("EventStartTime",event.startTime.toString())
        myIntent.putExtra("EventEndTime",event.endTime.toString())

        eventViewModel.insertEvent(EventForDB(event.title,  event.startDate, event.endDate,event.startTime,event.endTime , event.location))

        setResult(Activity.RESULT_OK,myIntent)
        finish()
    }


}