package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class DisplaySuperManBirthdayCardActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_superman_birthday_card);

        tv = (TextView) findViewById(R.id.userReceivedName);
        Intent intent = getIntent();
        String nameTo = intent.getStringExtra("uReceivedName");

        tv.setText(nameTo);
    }
}