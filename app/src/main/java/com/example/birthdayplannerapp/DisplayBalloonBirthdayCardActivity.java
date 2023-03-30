package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayBalloonBirthdayCardActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_balloon_birthday_card);

        //Toast.makeText(this, "BDayCard onCreate", Toast.LENGTH_SHORT).show();

        tv = (TextView) findViewById(R.id.userReceivedName);
        Intent intent = getIntent();
        String nameTo = intent.getStringExtra("uReceivedName");

        tv.setText(nameTo);

    }
}