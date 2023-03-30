package com.example.birthdayplannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.birthdayplannerapp.Adapter.CheckListAdapter;
import com.example.birthdayplannerapp.Constants.ItemTypeConstants;
import com.example.birthdayplannerapp.Database.AppData;
import com.example.birthdayplannerapp.Database.RoomDb;
import com.example.birthdayplannerapp.Models.Items;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckListAdapter checkListAdapter;
    RoomDb database;
    List<Items> itemsList  = new ArrayList<>();
    String header, isShow;
    EditText txtItemAdd;
    Button btnAddItem;
    LinearLayout linearLayout;

    @Override
    public  boolean onCreatePanelMenu(int featureId,@NonNull Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);

        if(ItemTypeConstants.MySelections.equals(header) || ItemTypeConstants.MyList.equals(header)){
            menu.getItem(1).setVisible(false);
        }

        MenuItem menuItem = menu.findItem(R.id.searchBtn);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                List<Items> itemSearchList = new ArrayList<>();

                for(Items items:itemsList){
                    if(items.getItemname().toLowerCase().startsWith(searchText.toLowerCase())){
                        itemSearchList.add(items);
                    }
                }

                updateRecycler(itemSearchList);

                return false;
            }
        });

        return  super.onCreatePanelMenu(featureId,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, CheckList.class);
        AppData appData= new AppData(database, this);

        switch (item.getItemId()) {
            case R.id.deleteAllBtn:

                new AlertDialog.Builder(CheckList.this).setTitle("Delete All items of "+header+" category")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                appData.persistDataByCategory(header);
                                itemsList=database.mainDao().getAllItems(header);
                                updateRecycler(itemsList);
                                checkListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.baseline_delete_24).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Intent intent = getIntent();
        header = intent.getStringExtra("header");
        isShow = intent.getStringExtra("isShow");

        getSupportActionBar().setTitle(header);

        recyclerView = findViewById(R.id.checkListRecyclerView);
        txtItemAdd = findViewById(R.id.addItemEditText);
        btnAddItem = findViewById(R.id.addItemBtn);
        linearLayout= findViewById(R.id.checkListLinearLayout);

        database = RoomDb.getInstance(this);

        if(isShow.equals("false") && header.equals(ItemTypeConstants.MySelections) ){
            linearLayout.setVisibility(View.GONE);
            itemsList = database.mainDao().getAllSelected(true);
        }
        else if(isShow.equals("false") && header.equals(ItemTypeConstants.MyList) ){
            linearLayout.setVisibility(View.GONE);
            itemsList = database.mainDao().getAll();
        }
        else{
            itemsList = database.mainDao().getAllItems(header);
        }

        updateRecycler(itemsList);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemNameText = txtItemAdd.getText().toString();

                if(itemNameText!=null&& !itemNameText.isEmpty()){
                    addNewItemToList(itemNameText);
                    Toast.makeText(CheckList.this, "New Item Added",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CheckList.this, "New Item Added",Toast.LENGTH_SHORT).show();
                }

                InputMethodManager imm = (InputMethodManager) txtItemAdd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtItemAdd.getWindowToken(), 0);
            }
        });

    }

    private  void  addNewItemToList(String itemName){
        Items item = new Items();
        item.setIschecked(false);
        item.setCategory(header);
        item.setItemname(itemName);
        item.setAddedby("user");
        database.mainDao().saveItem(item);
        itemsList=database.mainDao().getAllItems(header);
        updateRecycler(itemsList);
        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        txtItemAdd.setText("");
    }

    private  void  updateRecycler(List<Items> itemsList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter = new CheckListAdapter(CheckList.this, itemsList, database, isShow, header);
        recyclerView.setAdapter(checkListAdapter);
    }
}