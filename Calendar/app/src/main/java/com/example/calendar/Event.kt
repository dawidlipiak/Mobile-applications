package com.example.calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class Event() : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,java.io.Serializable, Parcelable{
    lateinit var title : String

    lateinit var startTime : LocalTime
    lateinit var startDate : LocalDate

    lateinit var endDate : LocalDate
    lateinit var endTime : LocalTime

    lateinit var location : String

    lateinit var viewToChange : Button

    private var startOrEnd = true // true for start / false for end

    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0

    constructor(parcel: Parcel) : this() {
        title = parcel.readString().toString()
        location = parcel.readString().toString()
        startDate = parcel.readSerializable() as LocalDate
        endDate = parcel.readSerializable() as LocalDate
        startTime = parcel.readSerializable() as LocalTime
        endTime = parcel.readSerializable() as LocalTime
    }

    private fun getDateTimeCalendar(){
        val calendar = Calendar.getInstance()

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    fun startDatePick(context : Context, view : View){
        getDateTimeCalendar()
        startOrEnd = true

        viewToChange = view.findViewById(R.id.startDateButton)
        DatePickerDialog(context,this,year,month,day).show()
    }

    fun endDatePick(context : Context, view : View){
        getDateTimeCalendar()
        startOrEnd = false

        viewToChange = view.findViewById(R.id.endDateButton)
        DatePickerDialog(context,this,year,month,day).show()
    }

    fun startTimePick(context : Context, view : View){
        getDateTimeCalendar()
        startOrEnd = true

        viewToChange = view.findViewById(R.id.startTimeButton)
        TimePickerDialog(context, this, hour, minute,true).show()
    }

    fun endTimePick(context : Context, view : View){
        getDateTimeCalendar()
        startOrEnd = false

        viewToChange = view.findViewById(R.id.endTimeButton)
        TimePickerDialog(context, this, hour, minute,true).show()
    }

    fun setTitle (title : String, view : View){
        this.title = title
    }

    fun setLocalization (locationString : String) {
        this.location = locationString
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayInMonth: Int) {
        if(startOrEnd){
            startDate = LocalDate.of(year,month+1,dayInMonth)
            viewToChange.text = startDate.format(DateTimeFormatter.ofPattern("EEE, dd MMMM"))
        }
        else{
            endDate = LocalDate.of(year,month+1, dayInMonth)
            viewToChange.text = endDate.format(DateTimeFormatter.ofPattern("EEE, dd MMMM"))
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if(startOrEnd){
            startTime = LocalTime.of(hourOfDay,minute)
            viewToChange.text = startTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
        else {
            endTime = LocalTime.of(hourOfDay,minute)
            viewToChange.text = endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(title)
        parcel.writeString(location)
        parcel.writeSerializable(startDate)
        parcel.writeSerializable(endDate)
        parcel.writeSerializable(startTime)
        parcel.writeSerializable(endTime)
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}