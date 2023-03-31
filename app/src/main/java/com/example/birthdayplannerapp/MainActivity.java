package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.birthdayplannerapp.Database.AppData;
import com.example.birthdayplannerapp.Database.RoomDb;


public class MainActivity extends AppCompatActivity {

    Button openCardActivityButton, openItemListButton, openGuestListButton;
    RoomDb database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openCardActivityButton = (Button) findViewById(R.id.openBirthdayCardActivityButton);
        openItemListButton = (Button) findViewById(R.id.openItemActivity);
        openGuestListButton = (Button) findViewById(R.id.openGuestActivity);

        addAppData();
        database = RoomDb.getInstance(this);


        openCardActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, SelectBirthdayCardThemeActivity.class);
                    startActivity(intent);
                    //finish();
            }
        });

        openItemListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        openGuestListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GuestListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void  addAppData(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        database= RoomDb.getInstance(this);
        AppData appData = new AppData(database);
        int last = sharedPrefs.getInt(AppData.LAST_VERSION, 0);

        if(!sharedPrefs.getBoolean("is_first_time", false)){
            appData.addAllData();
            editor.putBoolean("is_first_time", true);
            editor.commit();
        }
        else if(last<AppData.NEW_VERSION){
            database.mainDao().deleteAllSystemItems("system");
            appData.addAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.commit();
        }

    }

    private  static  final int TIME_INTERVAL=2000;
    private  long backPressedTime;

    @Override
    public void onBackPressed(){
        if(backPressedTime+TIME_INTERVAL>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(this, "Tap back button again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime=System.currentTimeMillis();

    }
}