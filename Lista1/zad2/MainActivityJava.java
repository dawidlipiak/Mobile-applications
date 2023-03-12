package com.example.guessingnumber;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

public final class MainActivityJava extends AppCompatActivity {
    private int randomNumber;

    @SuppressLint({"SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomNumber = Random.Default.nextInt(100);
        ((TextView)findViewById(R.id.hint)).setText("Guess the number...");
    }

    @SuppressLint({"SetTextI18n"})
    public void checkNumber() {
        int number = Integer.parseInt(((EditText) findViewById(R.id.GuessedNumber)).getText().toString());

        if (number == randomNumber) {
            ((TextView)findViewById(R.id.hint)).setText("You've guessed the number " + randomNumber + " !");
        }

        if (number > this.randomNumber) {
            ((TextView)findViewById(R.id.hint)).setText("Less!");
            Toast.makeText(this, "Less !", Toast.LENGTH_SHORT).show();
        }

        if (number < this.randomNumber) {
            ((TextView)findViewById(R.id.hint)).setText("More!");
            Toast.makeText(this, "More !", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint({"SetTextI18n"})
    public void restartClick(View view) {
        this.randomNumber = Random.Default.nextInt(100);
        ((TextView)findViewById(R.id.hint)).setText("Guess the number...");
    }

    public void submitClick(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.checkNumber();
    }
}
