package com.example.simpleapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var rightValue = 0
    private var leftValue = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        score = 0
        roll ()
    }

    @SuppressLint("SetTextI18n")
    private fun roll(){
        findViewById<TextView>(R.id.points).text = "Punkty: $score"
        rightValue = Random.nextInt(10)
        leftValue = Random.nextInt(10)
        findViewById<TextView>(R.id.leftButton).text = "$leftValue"
        findViewById<TextView>(R.id.rightButton).text = "$rightValue"
    }

    fun leftClick(view: View) {
        if(rightValue <= leftValue){
            Toast.makeText(this,"Dobrze!", Toast.LENGTH_SHORT).show()
            score++
        }
        else{
            Toast.makeText(this,"Źle!", Toast.LENGTH_SHORT).show()
            score--
        }
        roll()
    }
    fun rightClick(view: View) {
        if(rightValue >= leftValue){
            Toast.makeText(this,"Dobrze!", Toast.LENGTH_SHORT).show()
            score++
        }
        else{
            Toast.makeText(this,"Źle!", Toast.LENGTH_SHORT).show()
            score--
        }
        roll()
    }
}