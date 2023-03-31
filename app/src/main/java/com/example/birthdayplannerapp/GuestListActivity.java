package com.example.birthdayplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.birthdayplannerapp.Adapter.GuestCheckListAdapter;
import com.example.birthdayplannerapp.Database.RoomDb;
import com.example.birthdayplannerapp.Models.Guests;
import com.example.birthdayplannerapp.Models.Items;

import java.util.ArrayList;
import java.util.List;

public class GuestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    GuestCheckListAdapter guestCheckListAdapter;

    RoomDb database;

    List<Guests> guestsList = new ArrayList<>();

    EditText txtGuestAdd, txtGuestEmailAdd;

    Button addGuestBtn;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        getSupportActionBar().setTitle("GuestList");

        recyclerView = findViewById(R.id.checkGuestListRecyclerView);
        txtGuestAdd = findViewById(R.id.guestNameEditText);
        txtGuestEmailAdd = findViewById(R.id.guestEmailEditText);
        addGuestBtn = findViewById(R.id.addGuestBtn);
        linearLayout = findViewById(R.id.checkGuestListLinearLayout);

        database = RoomDb.getInstance(this);
        guestsList = database.guestsDao().getAllGuests();

        updateRecycler(guestsList);

        addGuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guestNameText = txtGuestAdd.getText().toString();
                String guestEmailText = txtGuestEmailAdd.getText().toString();

                if(isValidEmail(guestEmailText)) {


                    if (guestNameText != null && !guestNameText.isEmpty()) {
                        addNewGuestToList(guestNameText);
                        Toast.makeText(GuestListActivity.this, "New Guest Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GuestListActivity.this, "Please Enter Guest Name", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(GuestListActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                }

                InputMethodManager imm = (InputMethodManager) txtGuestAdd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtGuestAdd.getWindowToken(), 0);

                InputMethodManager imm2 = (InputMethodManager) txtGuestEmailAdd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtGuestEmailAdd.getWindowToken(), 0);
            }
        });

    }

    private boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private  void  addNewGuestToList(String guestName){
        Guests guest = new Guests();
        guest .setIschecked(false);
        guest .setGuestname(guestName);
        database.guestsDao().saveGuest(guest);
        guestsList=database.guestsDao().getAllGuests();
        updateRecycler(guestsList);
        recyclerView.scrollToPosition(guestCheckListAdapter.getItemCount()-1);
        txtGuestAdd.setText("");
        txtGuestEmailAdd.setText("");
    }


    private  void  updateRecycler(List<Guests> guestsList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        guestCheckListAdapter = new GuestCheckListAdapter(GuestListActivity.this, guestsList, database);
        recyclerView.setAdapter(guestCheckListAdapter);
    }
}

