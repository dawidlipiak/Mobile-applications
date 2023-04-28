package com.example.calendar

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WeeklyActivity : AppCompatActivity(), WeekRecycleAdapter.OnItemListener, DailyEventsAdapter.OnItemListener {
    private lateinit var monthYearText : TextView
    private lateinit var dailyRecycler : RecyclerView
    private lateinit var hourlyRecycler : RecyclerView
    private lateinit var eventViewModel: EventModel
    private lateinit var eventList : ArrayList<Event>
    private lateinit var event : Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_mode)

        eventViewModel = ViewModelProvider(this)[EventModel::class.java]

        dailyRecycler = findViewById<RecyclerView>(R.id.daily_recycler)
        hourlyRecycler = findViewById(R.id.hourRecycler)

        monthYearText = findViewById<TextView>(R.id.monthYearInDaily)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(dailyRecycler)
        snapHelper.attachToRecyclerView(hourlyRecycler)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            eventList = intent.getSerializableExtra("EventList")!! as ArrayList<Event>
        }
        setWeekView(true)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("events", eventList)
        outState.putSerializable("date", CalendarUtils.selectedDate)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        eventList = savedInstanceState.getParcelableArrayList<Event>("events") as ArrayList<Event>
        CalendarUtils.selectedDate = savedInstanceState.getSerializable("date") as LocalDate
        setWeekView(true)
    }

    private fun setWeekView(scrollToSelected : Boolean) {
        dailyRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        hourlyRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

        monthYearText.text = monthYearFromDate(CalendarUtils.selectedDate)

        val daysInMonth = CalendarUtils.daysInWeekArray()
        dailyRecycler.adapter = WeekRecycleAdapter(daysInMonth, this)

        eventList.sortBy {list -> list.startTime}
        hourlyRecycler.adapter = DailyEventsAdapter(createListForDay(),this)

        if(scrollToSelected){
            dailyRecycler.scrollToPosition(CalendarUtils.selectedDate.dayOfMonth - 1)
        }
    }

    private fun createListForDay() : ArrayList<Event>{
        val events = ArrayList<Event>()

        eventList.forEach{
            event -> if(event.startDate.isEqual(CalendarUtils.selectedDate)){
                events.add(event)
            }
        }
        return events
    }

    private fun monthYearFromDate (date: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    override fun onItemClick(position: Int, dayClicked: LocalDate) {
        CalendarUtils.selectedDate = dayClicked
        setWeekView(true)
    }

    fun prevMonthDaily(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1)
        setWeekView(true)
    }
    fun nextMonthDaily(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1)
        setWeekView(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar_weekly,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_eventOption -> {
                val addEventIntent = Intent(this, EventAddActivity::class.java)
                addEventResultLauncher.launch(addEventIntent)
            }
            R.id.delete_eventOption -> {
                eventList.forEach {event ->
                    run {
                        if (event.startDate.isEqual(CalendarUtils.selectedDate)) eventList.remove(event)
                    }
                }
                setWeekView(true)
            }
            R.id.month_view -> {
                val  myIntent = Intent()
                myIntent.putParcelableArrayListExtra("EventList",eventList)
                setResult(Activity.RESULT_OK, myIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var addEventResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> val data = result.data

        if(data?.extras != null) {
            val event = Event()
            event.title = data.getStringExtra("EventTitle").toString()
            event.location = data.getStringExtra("EventLocation").toString()
            event.startDate = LocalDate.parse(data.getStringExtra("EventStartDate"))
            event.endDate = LocalDate.parse(data.getStringExtra("EventEndDate"))
            event.startTime = LocalTime.parse(data.getStringExtra("EventStartTime"))
            event.endTime = LocalTime.parse(data.getStringExtra("EventEndTime"))

            eventList.add(event)
            setWeekView(true)
        }
    }
    override fun onItemClick(position: Int, event: Event) {
        val eventViewIntent = Intent(this,EventViewActivity::class.java)
        this.event = event
        resultLauncher.launch(eventViewIntent)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            eventList.remove(event)
            Toast.makeText(this, "Event Deleted", Toast.LENGTH_LONG).show()
            eventViewModel.deleteEvent(EventForDB(event.title,event.startDate,event.endDate, event.startTime,event.endTime, event.location))
            setWeekView(true)
        }
    }
}