package com.example.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class EventViewActivity : AppCompatActivity() {
    val myIntent = Intent()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.day_layout)
    }

    fun cancelDeleteButton(view: View) {
        myIntent.putExtra("operation","failure")
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
    fun deleteEventButton(view: View) {
        myIntent.putExtra("operation","success")
        setResult(Activity.RESULT_OK)
        finish()
    }


}