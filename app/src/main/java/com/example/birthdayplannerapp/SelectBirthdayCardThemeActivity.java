package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectBirthdayCardThemeActivity extends AppCompatActivity {

    ImageButton teddyB, flowerB, supermanB, balloonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_birthday_card_theme);

        teddyB=(ImageButton)findViewById(R.id.teddyThemeButton);
        flowerB=(ImageButton)findViewById(R.id.flowersThemeButton);
        supermanB=(ImageButton)findViewById(R.id.supermanThemeButton);
        balloonB=(ImageButton)findViewById(R.id.balloonThemeButton);

        teddyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectBirthdayCardThemeActivity.this, CreateBirthdayCardActivity.class);
                intent.putExtra("selectedTheme", "Teddy");
                startActivity(intent);
                //finish();
            }
        });

        flowerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectBirthdayCardThemeActivity.this, CreateBirthdayCardActivity.class);
                intent.putExtra("selectedTheme", "Flower");
                startActivity(intent);
                //finish();
            }
        });
        supermanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectBirthdayCardThemeActivity.this, CreateBirthdayCardActivity.class);
                intent.putExtra("selectedTheme", "SuperMan");
                startActivity(intent);
                //finish();
            }
        });
        balloonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SelectBirthdayCardThemeActivity.this, CreateBirthdayCardActivity.class);
                intent.putExtra("selectedTheme", "Balloon");
                startActivity(intent);
                //finish();
            }
        });

    }
}