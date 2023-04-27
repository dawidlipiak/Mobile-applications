package com.example.calendar

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class RecycleAdapter(private val daysOfMonth: ArrayList<LocalDate>, private val onItemListener: OnItemListener)
    : RecyclerView.Adapter<RecycleAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View, onItemListener : OnItemListener,var daysOfMonth: ArrayList<LocalDate>)
        : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        val dayOfMonth : TextView
        val cellView : View
        val dayCircle : ImageView
        private val onItemListener : OnItemListener

        init {
            dayOfMonth = view.findViewById(R.id.textView)
            cellView = view.findViewById(R.id.calendar_cell)
            dayCircle = view.findViewById(R.id.imageView)
            this.onItemListener = onItemListener
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }
        override fun onClick(view : View) {
                this.onItemListener.onItemClick(adapterPosition, daysOfMonth.get(adapterPosition))
        }

        override fun onLongClick(view : View) : Boolean {

            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell,parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height / 6).toInt()
        return RecyclerViewHolder(view,onItemListener,daysOfMonth)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val date = daysOfMonth[position]
        holder.dayOfMonth.text = date.dayOfMonth.toString()
        holder.dayCircle.isVisible = false

        if (date == CalendarUtils.selectedDate) {
            holder.cellView.setBackgroundColor(Color.parseColor("#ADD8E6"))
        }
        if (date.month == CalendarUtils.selectedDate.month)
            holder.dayOfMonth.setTextColor(Color.BLACK)
        else{
            holder.dayOfMonth.setTextColor(Color.LTGRAY)
        }
        if(date == LocalDate.now()){
            holder.dayCircle.isVisible = true
        }
        if (date == CalendarUtils.selectedDate) {
            holder.dayOfMonth.setTextColor(Color.WHITE)
            holder.cellView.setBackgroundColor(Color.parseColor("#906d7e"))
        }
        else {
            holder.cellView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position: Int, dayClicked : LocalDate)
    }
}