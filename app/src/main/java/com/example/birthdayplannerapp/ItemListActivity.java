package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.birthdayplannerapp.Adapter.ItemCategoryListAdapter;
import com.example.birthdayplannerapp.Constants.ItemTypeConstants;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    ItemCategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        recyclerView = findViewById(R.id.recyclerView);

        getSupportActionBar().setTitle("Item List");

        addAllTitles();
        addAllImages();

        adapter = new ItemCategoryListAdapter(this, titles, images, ItemListActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }



    private  void  addAllTitles(){
        titles=new ArrayList<>();
        titles.add(ItemTypeConstants.Gifts);
        titles.add(ItemTypeConstants.Decoration);
        titles.add(ItemTypeConstants.Games);
        titles.add(ItemTypeConstants.Songs);
        titles.add(ItemTypeConstants.Snacks);
        titles.add(ItemTypeConstants.Beverages);
        titles.add(ItemTypeConstants.Cutlery);
        titles.add(ItemTypeConstants.Others);
        titles.add(ItemTypeConstants.MyList);
        titles.add(ItemTypeConstants.MySelections);
    }

    private  void  addAllImages(){
        images = new ArrayList<>();
        images.add(R.drawable.giftbox_icon);
        images.add(R.drawable.star);
        images.add(R.drawable.game_icon);
        images.add(R.drawable.music);
        images.add(R.drawable.baseline_fastfood_24);
        images.add(R.drawable.drink);
        images.add(R.drawable.baseline_food);
        images.add(R.drawable.plus);
        images.add(R.drawable.list);
        images.add(R.drawable.baseline_check_circle_24);
    }
}