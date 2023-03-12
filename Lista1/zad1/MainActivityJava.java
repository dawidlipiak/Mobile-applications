package com.example.simpleapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MainActivityJava extends AppCompatActivity {
    private int rightValue;
    private int leftValue;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = 0;
        roll();
    }

    @SuppressLint({"SetTextI18n"})
    private void roll() {
        ((TextView)findViewById(R.id.points)).setText((CharSequence)("Punkty: " + score));
        this.rightValue = Random.Default.nextInt(10);
        this.leftValue = Random.Default.nextInt(10);
        ((TextView)findViewById(R.id.leftButton)).setText((CharSequence)String.valueOf(leftValue));
        ((TextView)findViewById(R.id.rightButton)).setText((CharSequence)String.valueOf(rightValue));
    }

    public void leftClick(@NotNull View view) {
        if (this.rightValue <= this.leftValue) {
            Toast.makeText((Context)this, (CharSequence)"Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText((Context)this, (CharSequence)"Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }

    public void rightClick(@NotNull View view) {
        if (this.rightValue >= this.leftValue) {
            Toast.makeText((Context)this, (CharSequence)"Dobrze!", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText((Context)this, (CharSequence)"Źle!", Toast.LENGTH_SHORT).show();
            score--;
        }
        roll();
    }
}