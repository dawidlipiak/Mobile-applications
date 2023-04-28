package com.example.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.util.*

class WeekRecycleAdapter(private val daysOfMonth: ArrayList<LocalDate>, private val onItemListener: WeeklyActivity)
    : RecyclerView.Adapter<WeekRecycleAdapter.WeekViewHolder>() {

    class WeekViewHolder(view: View, onItemListener : OnItemListener,var daysOfMonth: ArrayList<LocalDate>)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        val dayOfWeek : TextView
        val cellView : View
        val dayInMonth : TextView
//        val dayCircle : ImageView
        private val onItemListener : OnItemListener

        init {
            dayOfWeek = view.findViewById(R.id.txt_day)
            dayInMonth = view.findViewById(R.id.txt_date)
            cellView = view.findViewById(R.id.daily_cell)
//            dayCircle = view.findViewById(R.id.imageView)
            this.onItemListener = onItemListener
            view.setOnClickListener(this)
        }
        override fun onClick(view : View) {
            this.onItemListener.onItemClick(adapterPosition, daysOfMonth[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_cell,parent, false)
        return WeekViewHolder(view,onItemListener,daysOfMonth)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val date = daysOfMonth[position]
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        holder.dayInMonth.text = date.dayOfMonth.toString()

        val dayInWeek = sdf.parse(date.toString())
        val outFormat = SimpleDateFormat("EEE")
        holder.dayOfWeek.text = outFormat.format(dayInWeek)

//        holder.dayCircle.isVisible = false
        if (date >= LocalDate.now()) {
            holder.dayInMonth.setTextColor(Color.BLACK)
            holder.dayOfWeek.setTextColor(Color.BLACK)
        }
        else {
            holder.dayInMonth.setTextColor(Color.LTGRAY)
            holder.dayOfWeek.setTextColor(Color.LTGRAY)
        }
        if (date == CalendarUtils.selectedDate) {
            holder.dayInMonth.setTextColor(Color.WHITE)
            holder.dayOfWeek.setTextColor(Color.WHITE)
            holder.cellView.setBackgroundColor(Color.parseColor("#906d7e"))
        }
        else {
            holder.cellView.setBackgroundColor(Color.TRANSPARENT)
        }
//        if(date == LocalDate.now()){
//            holder.dayCircle.isVisible = true
//        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayClicked : LocalDate)
    }
}