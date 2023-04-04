package com.example.birthdayplannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.example.birthdayplannerapp.Adapter.GuestCheckListAdapter;
import com.example.birthdayplannerapp.Constants.ItemTypeConstants;
import com.example.birthdayplannerapp.Database.AppData;
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

    Button addGuestBtn, readContactBtn;

    LinearLayout linearLayout;

    private static final int CONTACT_PERMISSION_CODE=1;
    private static final int CONTACT_PICK_CODE=2;


    @Override
    public  boolean onCreatePanelMenu(int featureId,@NonNull Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);


        MenuItem menuItem = menu.findItem(R.id.searchBtn);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                List<Guests> guestSearchList = new ArrayList<>();

                for(Guests guest:guestsList){
                    if(guest.getGuestname().toLowerCase().startsWith(searchText.toLowerCase())){
                        guestSearchList.add(guest);
                    }
                }

                updateRecycler(guestSearchList);

                return false;
            }
        });

        return  super.onCreatePanelMenu(featureId,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, GuestListActivity.class);
        AppData appData= new AppData(database, this);

        switch (item.getItemId()) {
            case R.id.deleteAllBtn:

                new AlertDialog.Builder(GuestListActivity.this).setTitle("Delete All guests")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                appData.persistGuestData();
                                guestsList=database.guestsDao().getAllGuests();
                                updateRecycler(guestsList);

                                guestCheckListAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_guest_list);



        getSupportActionBar().setTitle("GuestList");

        recyclerView = findViewById(R.id.checkGuestListRecyclerView);
        txtGuestAdd = findViewById(R.id.guestNameEditText);
        txtGuestEmailAdd = findViewById(R.id.guestEmailEditText);
        addGuestBtn = findViewById(R.id.addGuestBtn);
        linearLayout = findViewById(R.id.checkGuestListLinearLayout);
        readContactBtn = findViewById(R.id.readContactButton);

        database = RoomDb.getInstance(this);
        guestsList = database.guestsDao().getAllGuests();

        updateRecycler(guestsList);

        addGuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guestNameText = txtGuestAdd.getText().toString();
                String guestEmailText = txtGuestEmailAdd.getText().toString();

                if(isValidEmail(guestEmailText) || guestEmailText==null || guestEmailText.isEmpty()) {


                    if (guestNameText != null && !guestNameText.isEmpty()) {
                        addNewGuestToList(guestNameText, guestEmailText);
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

        readContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkContactPermissionGranted()){
                    pickContactIntent();
                }
                else{
                    requestContactPermission();
                }
            }
        });



    }

    private boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private  void  addNewGuestToList(String guestName, String guestEmail){
        Guests guest = new Guests();
        guest .setIschecked(false);
        guest .setGuestname(guestName);
        guest.setGuestemail(guestEmail);
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

    private  boolean checkContactPermissionGranted(){
        boolean answer = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_CONTACTS)==(PackageManager.PERMISSION_GRANTED);

        return answer;
    }

    private void  requestContactPermission(){
        String[] permission = {android.Manifest.permission.READ_CONTACTS};

        ActivityCompat.requestPermissions(this, permission, CONTACT_PERMISSION_CODE);
    }

    private void pickContactIntent(){
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CONTACT_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                pickContactIntent();
            }
            else{
                Toast.makeText(this, "Permission denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && data!=null){
            if(requestCode==CONTACT_PICK_CODE){

                txtGuestAdd.setText("");
                txtGuestEmailAdd.setText("");

                Cursor cursorName, cursorEmail;

                Uri uri = data.getData();

                cursorName=getContentResolver().query(uri, null, null, null, null);



                if(cursorName.moveToFirst()){
                    @SuppressLint("Range") String contactId = cursorName.getString(cursorName.getColumnIndex(ContactsContract.Contacts._ID));
                    @SuppressLint("Range") String contactName = cursorName.getString(cursorName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                  //  @SuppressLint("Range") String contactThumbnail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    @SuppressLint("Range") String idResults = cursorName.getString(cursorName.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);

                    if(contactName==null || contactName.isEmpty()){
                        Toast.makeText(GuestListActivity.this, "Guest Name is Empty", Toast.LENGTH_SHORT).show();
                        cursorName.close();
                        return;
                    }

                    if(idResultHold==1){
                        cursorEmail=getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID+" = ?", new String[]{contactId},null);

                        String contactEmail ="";

                        if (cursorEmail.moveToFirst()) {
                            @SuppressLint("Range") String email = cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                contactEmail=email;

                        }

//                        if(isValidEmail(contactEmail)) {
                           // addNewGuestToList(contactName, contactEmail);
                            txtGuestAdd.setText(contactName);
                            txtGuestEmailAdd.setText(contactEmail);

//                        }
//                        else{
//                            Toast.makeText(GuestListActivity.this, "InValid Email address", Toast.LENGTH_SHORT).show();
//                            cursorEmail.close();
//                            cursorName.close();
//                            return;
//                        }

                        cursorEmail.close();
                    }
                    cursorName.close();


                }
            }
        }
    }
}

