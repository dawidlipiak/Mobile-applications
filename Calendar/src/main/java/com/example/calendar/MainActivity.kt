package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecycleAdapter.OnItemListener {
    private lateinit var monthYearText : TextView
    private lateinit var calendarRecyclerView : RecyclerView
    private var selectedDate = LocalDate.now()
    private val currentDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        monthYearText = findViewById<TextView>(R.id.monthYearTextView)

        setMonthView()
    }

    private fun setMonthView(){
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendarRecycleView)
        calendarRecyclerView.layoutManager = GridLayoutManager(this,7)
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)

        calendarRecyclerView.adapter = RecycleAdapter(daysInMonth,this)

    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonth = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val nrOfDaysInMonth = yearMonth.lengthOfMonth()
        val firstOfMoth = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMoth.dayOfWeek.value

        for(i in 1..42){
            if (i < dayOfWeek || i >= nrOfDaysInMonth+dayOfWeek){
                daysInMonth.add("")
            }
            else {
                daysInMonth.add((i-dayOfWeek+1).toString())
            }
        }

        return daysInMonth
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


    override fun onItemClick(position: Int, dayText: String) {
        if (dayText != ""){
            val message = "Selected date $dayText ${monthYearFromDate(selectedDate)}"
            Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }
    }
}