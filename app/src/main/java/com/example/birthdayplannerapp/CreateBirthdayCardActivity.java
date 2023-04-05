package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class CreateBirthdayCardActivity extends AppCompatActivity {

    EditText userName;
    MaterialButton createCardButton;

    TextView themeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_birthday_card);


        userName = (EditText) findViewById(R.id.userName);
        createCardButton = (MaterialButton) findViewById(R.id.createCardButton);
        themeView=findViewById(R.id.userSelectedTheme);

        Intent i = getIntent();
        String themeName = i.getStringExtra("selectedTheme");

        themeView.setText("Your theme is "+themeName);

        createCardButton.setOnClickListener(view -> {

            String uName = userName.getText().toString();

            if(TextUtils.isEmpty(uName)){
                Toast.makeText(CreateBirthdayCardActivity.this, "Please enter name",Toast.LENGTH_SHORT ).show();
            }
            else{
                Intent intent=null;
                switch (themeName){
                    case "Teddy":
                        intent = new Intent(CreateBirthdayCardActivity.this, DisplayTeddyBirthdayCardActivity.class);
                        break;

                    case "Balloon":
                        intent = new Intent(CreateBirthdayCardActivity.this, DisplayBalloonBirthdayCardActivity.class);
                        break;

                    case "Flower":
                        intent = new Intent(CreateBirthdayCardActivity.this, DisplayFlowerBirthdayCardActivity.class);
                        break;

                    case "SuperMan":
                        intent = new Intent(CreateBirthdayCardActivity.this, DisplaySuperManBirthdayCardActivity.class);
                        break;

                }


                intent.putExtra("uReceivedName", uName);
                startActivity(intent);
            }
        });

    }}