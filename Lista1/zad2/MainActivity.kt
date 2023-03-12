package com.example.guessingnumber

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomNumber = 0;
    private var number = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomNumber = Random.nextInt(100)
        findViewById<TextView>(R.id.hint).text = "Guess the number..."
    }

    @SuppressLint("SetTextI18n")
    fun checkNumber(){
        number = Integer.parseInt(findViewById<EditText>(R.id.GuessedNumber).text.toString())
        if(number == randomNumber){
            findViewById<TextView>(R.id.hint).text = "You've guessed the number $randomNumber !"
        }
        if (number > randomNumber){
            findViewById<TextView>(R.id.hint).text = "Less!"
            Toast.makeText(this,"Less !", Toast.LENGTH_SHORT).show()
        }
        if (number < randomNumber){
            findViewById<TextView>(R.id.hint).text = "More!"
            Toast.makeText(this,"More !", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun restartClick(view: View) {
        randomNumber = Random.nextInt(100)
        findViewById<TextView>(R.id.hint).text = "Guess the number..."
    }
    fun submitClick(view: View) {
        checkNumber()
    }
}