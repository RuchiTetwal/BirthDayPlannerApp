package com.example.birthdayplannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birthdayplannerapp.Database.AppData;
import com.example.birthdayplannerapp.Models.Guests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayBalloonBirthdayCardActivity extends AppCompatActivity {

    TextView tv;
    ConstraintLayout cardLayout;

    @Override
    public  boolean onCreatePanelMenu(int featureId,@NonNull Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bdaycard_menu, menu);

        return  super.onCreatePanelMenu(featureId,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.shareBtn:
                saveImg();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveImg(){
        cardLayout.setDrawingCacheEnabled(true);
        cardLayout.buildDrawingCache();
        cardLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = cardLayout.getDrawingCache();
        save(bitmap);
    }


    private void  save(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new  File(root + "/Download");

        String fileName = "balloon_birthday_card_"+ System.currentTimeMillis() +".jpg";
        File myFile = new File(file, fileName);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(myFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, "Birthday Card Saved in Downloads..", Toast.LENGTH_SHORT).show();
                cardLayout.setDrawingCacheEnabled(false);
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_balloon_birthday_card);

        getSupportActionBar().setTitle("Birthday Card");

        tv = findViewById(R.id.userReceivedName);
        cardLayout = findViewById(R.id.balloonCardLayout);

        Intent intent = getIntent();
        String nameTo = intent.getStringExtra("uReceivedName");

        tv.setText(nameTo);

    }
}