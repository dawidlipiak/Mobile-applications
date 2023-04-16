package com.example.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener : OnItemListener)
    : RecyclerView.Adapter<RecycleAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View, onItemListener : OnItemListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        val dayOfMonth : TextView
        private val onItemListener : OnItemListener

        init {
            dayOfMonth = view.findViewById(R.id.textView)
            this.onItemListener = onItemListener
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }
        override fun onClick(view : View) {
                this.onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString())
        }

        override fun onLongClick(view : View) : Boolean {

            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell,parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666).toInt()
        return RecyclerViewHolder(view,onItemListener)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
        if((position+1) % 7  == 0){
            holder.dayOfMonth.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    interface OnItemListener {
        fun onItemClick(position : Int, dayText : String)
    }
}