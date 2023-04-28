package com.example.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyEventsAdapter(private val eventList: ArrayList<Event>, private val onItemListener: OnItemListener)
    : RecyclerView.Adapter<DailyEventsAdapter.DailyEventHolder>() {

    class DailyEventHolder(view: View, onItemListener : OnItemListener,var eventList: ArrayList<Event>)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        val timeLabel : TextView
        val eventLabel : TextView
        val timeTextView : TextView
        val placeTextView : TextView
        private val onItemListener : OnItemListener

        init {
            timeLabel = view.findViewById(R.id.timeLabel)
            eventLabel = view.findViewById(R.id.EventTextView)
            timeTextView = view.findViewById(R.id.timeTextView)
            placeTextView = view.findViewById(R.id.placeTextView)

            this.onItemListener = onItemListener
            view.setOnClickListener(this)
        }

        override fun onClick(view : View) {
            this.onItemListener.onItemClick(adapterPosition, eventList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyEventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_cell,parent, false)

        return DailyEventHolder(view,onItemListener,eventList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyEventHolder, position: Int) {
        val event = eventList[position]
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

        if(event.startDate.isEqual(CalendarUtils.selectedDate)) {
            holder.timeLabel.text = event.startTime.format(timeFormat)
            holder.eventLabel.text = event.title
            holder.timeTextView.text =
                "${event.startTime.format(timeFormat)} - ${event.endTime.format(timeFormat)}"
            holder.placeTextView.text = event.location
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, event: Event)
    }
}