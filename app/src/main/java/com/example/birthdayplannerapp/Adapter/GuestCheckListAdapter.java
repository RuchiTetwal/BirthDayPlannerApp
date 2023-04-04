package com.example.birthdayplannerapp.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdayplannerapp.Constants.ItemTypeConstants;
import com.example.birthdayplannerapp.Database.RoomDb;
import com.example.birthdayplannerapp.Models.Guests;
import com.example.birthdayplannerapp.Models.Items;
import com.example.birthdayplannerapp.R;

import java.util.List;

public class GuestCheckListAdapter extends RecyclerView.Adapter<GuestCheckListViewHolder>{

    Context context;
    List<Guests> guestsList;
    RoomDb database;
    public GuestCheckListAdapter(){

    }

    public GuestCheckListAdapter(Context context, List<Guests> guestsList, RoomDb database) {
        this.context = context;
        this.guestsList = guestsList;
        this.database = database;


        if(guestsList.size()==0)
            Toast.makeText(context.getApplicationContext(), "Guest list is empty", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public GuestCheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuestCheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_guest_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuestCheckListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.guestListCheckBox.setText(guestsList.get(position).getGuestname());
        holder.guestListCheckBox.setChecked(guestsList.get(position).getIschecked());
        holder.guestLinearlayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_border_grey));

        if(guestsList.get(position).getGuestemail()==null || guestsList.get(position).getGuestemail().isEmpty()){
            holder.guestMailBtn.setVisibility(View.GONE);
        }

        holder.guestListCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isCheck = holder.guestListCheckBox.isChecked();
                database.guestsDao().checkUnCheckGuest(guestsList.get(position).getId(), isCheck);

                    guestsList.get(position).setIschecked(isCheck);

                    notifyDataSetChanged();
                    Toast msg = null;

                    if(msg!=null){
                        msg.cancel();
                    }

                    if(guestsList.get(position).getIschecked()){
                        msg=Toast.makeText(context, holder.guestListCheckBox.getText()+" is invited", Toast.LENGTH_SHORT);
                    }
                    else{
                        msg=Toast.makeText(context, holder.guestListCheckBox.getText()+" not invited", Toast.LENGTH_SHORT);
                    }

                    msg.show();

            }
        });

        holder.guestDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete - "+guestsList.get(position).getGuestname())
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.guestsDao().deleteGuest(guestsList.get(position));
                                guestsList.remove(guestsList.get(position));
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setIcon(R.drawable.baseline_delete_24).show();
            }
        });

        holder.guestMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String guestName = guestsList.get(position).getGuestname();
                String guestEmail = guestsList.get(position).getGuestemail();
                String subject = "Birthday Invitation";
                String message = "Hello! "+guestName +"\n\n" + "Birthdays are special and so are you, I would like to invite you to <WRITE BIRTHDAY GIRL/BOYS NAME>'s birthday party, kindly grace us with your presence."
                         +"\n\n" + "Date and Time: " +'\n'+ "Venue: ";

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {guestEmail});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
               // context.startActivity(emailIntent);
                context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));

            }
        });
    }

    @Override
    public int getItemCount() {
        return guestsList.size();
    }
}

class GuestCheckListViewHolder extends  RecyclerView.ViewHolder{

    LinearLayout guestLinearlayout;
    CheckBox guestListCheckBox;
    Button guestDeleteBtn;
    Button guestMailBtn;


    public GuestCheckListViewHolder(@NonNull View itemView) {
        super(itemView);

        guestListCheckBox=itemView.findViewById(R.id.guestListCheckbox);
        guestDeleteBtn=itemView.findViewById(R.id.guestDeleteBtn);
        guestLinearlayout=itemView.findViewById(R.id.guestCheckLinearlayout);
        guestMailBtn=itemView.findViewById(R.id.guestMailBtn);

    }
}
