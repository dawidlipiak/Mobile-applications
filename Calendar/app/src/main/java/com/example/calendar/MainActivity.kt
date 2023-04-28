package com.example.calendar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.CalendarUtils.Companion.selectedDate
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), RecycleAdapter.OnItemListener {
    private lateinit var monthYearText : TextView
    private lateinit var calendarRecyclerView : RecyclerView
    private lateinit var eventViewModel: EventModel
    var eventList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        monthYearText = findViewById<TextView>(R.id.monthYearTextView)

        eventViewModel = ViewModelProvider(this)[EventModel::class.java]
        eventViewModel.readAllData.observe(this) {
            setMonthView()
            setData(it)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putParcelableArrayList("events", Event.eventsList)
        outState.putSerializable("date", selectedDate)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//        Event.eventsList = savedInstanceState.getParcelableArrayList<Event>("events") as ArrayList<Event>
        selectedDate = savedInstanceState.getSerializable("date") as LocalDate
        setMonthView()
    }
    override fun onResume() {
        super.onResume()
        setMonthView()
    }

    private fun setMonthView(){
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendarRecycleView)
        calendarRecyclerView.layoutManager = GridLayoutManager(this,7)
        monthYearText.text = monthYearFromDate(selectedDate)

        val daysInMonth = CalendarUtils.daysInMonthArray()

        calendarRecyclerView.adapter = RecycleAdapter(daysInMonth,this)
    }

    private fun monthYearFromDate (date: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    fun previousMonthAction(view: View) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }


    override fun onItemClick(position: Int, dayClicked : LocalDate) {
        selectedDate = dayClicked
        setMonthView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar_monthly,menu)
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
                            if (event.startDate.isEqual(selectedDate)) eventList.remove(event)
                        }
                    }
                    setMonthView()
                }
                R.id.week_view -> {
                    val weekIntent = Intent(this, WeeklyActivity::class.java)
                    weekIntent.putExtra("EventList",eventList)
                    resultLaunncher.launch(weekIntent)
                }
            }
        return super.onOptionsItemSelected(item)
    }

    private var resultLaunncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> val data = result.data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            eventList = data!!.getSerializableExtra("EventList") as ArrayList<Event>
        }
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
            setMonthView()
        }
    }

    fun setData(eventsList: List<EventForDB>) {
        eventsList.forEach { eventForDb: EventForDB ->
            run{
                var event = Event()
                event.title = eventForDb.title
                event.location = eventForDb.location
                event.startDate = eventForDb.startDate
                event.endDate = eventForDb.endDate
                event.startTime = eventForDb.startTime
                event.endTime = eventForDb.endTime
                eventList.add(event)
            }
        }
    }
}