package com.example.calendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.CalendarUtils.Companion.selectedDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), RecycleAdapter.OnItemListener {
    private lateinit var monthYearText : TextView
    private lateinit var calendarRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        monthYearText = findViewById<TextView>(R.id.monthYearTextView)
        setMonthView()
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
        menuInflater.inflate(R.menu.menu_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId){
                R.id.add_eventOption -> Toast.makeText(this,"ADD",Toast.LENGTH_SHORT).show()
                R.id.delete_eventOption -> Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show()
                R.id.weekOption -> Toast.makeText(this,"week",Toast.LENGTH_SHORT).show()
                R.id.dailyOption -> Toast.makeText(this,"daily",Toast.LENGTH_SHORT).show()
            }
        return super.onOptionsItemSelected(item)
    }
}