package com.example.calendar

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.math.sign

class DailyMonthActivity : AppCompatActivity(), WeekRecycleAdapter.OnItemListener {
    private lateinit var monthYearText : TextView
    private lateinit var dailyRecycler : RecyclerView
    private lateinit var eventListView: ListView
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_mode)
        dailyRecycler = findViewById<RecyclerView>(R.id.daily_recycler)
        monthYearText = findViewById<TextView>(R.id.monthYearInDaily)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(dailyRecycler)

        setWeekView(true)
    }
    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putParcelableArrayList("events", Event.eventsList)
        outState.putSerializable("date", CalendarUtils.selectedDate)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//        Event.eventsList = savedInstanceState.getParcelableArrayList<Event>("events") as ArrayList<Event>
        CalendarUtils.selectedDate = savedInstanceState.getSerializable("date") as LocalDate
        setWeekView(true)
    }

    private fun setWeekView(scrollToSelected : Boolean) {
        dailyRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        monthYearText.text = monthYearFromDate(CalendarUtils.selectedDate)

        val daysInMonth = CalendarUtils.daysInWeekArray()
        dailyRecycler.adapter = WeekRecycleAdapter(daysInMonth, this)

        if(scrollToSelected){
            dailyRecycler.scrollToPosition(CalendarUtils.selectedDate.dayOfMonth)
        }
    }

    private fun monthYearFromDate (date: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    override fun onItemClick(position: Int, dayClicked: LocalDate) {
        CalendarUtils.selectedDate = dayClicked
        setWeekView(false)
    }

    fun prevMonthDaily(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1)
        setWeekView(true)
    }
    fun nextMonthDaily(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1)
        setWeekView(true)
    }
}