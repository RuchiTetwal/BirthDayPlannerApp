package com.example.birthdayplannerapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.birthdayplannerapp.Models.Items;
import com.example.birthdayplannerapp.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder>{

    Context context;
    List<Items> itemsList;
    RoomDb database;
    String isShow;

    String header;

    public CheckListAdapter(){

    }

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDb database, String isShow, String header) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.isShow = isShow;
        this.header=header;

        if(itemsList.size()==0)
            Toast.makeText(context.getApplicationContext(), "This item list is empty", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_check_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.itemListCheckBox.setText(itemsList.get(position).getItemname());
            holder.itemListCheckBox.setChecked(itemsList.get(position).getIschecked());
            holder.itemLinearlayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_border));

            if(isShow.equals("false")  ){
                holder.itemDeleteBtn.setVisibility(View.GONE);
            }

            holder.itemListCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isCheck = holder.itemListCheckBox.isChecked();
                    database.mainDao().checkUnCheck(itemsList.get(position).getId(), isCheck);

                    if(isShow.equals("false") && header.equals(ItemTypeConstants.MySelections) ){
                        itemsList= database.mainDao().getAllSelected(true);
                        notifyDataSetChanged();
                    }
                    else
                  if(isShow.equals("false") && header.equals(ItemTypeConstants.MyList)){
                        itemsList= database.mainDao().getAll();
                        notifyDataSetChanged();
                    }
                    else{
                       itemsList.get(position).setIschecked(isCheck);
                        notifyDataSetChanged();
                        Toast msg = null;

                        if(msg!=null){
                            msg.cancel();
                        }

                        if(itemsList.get(position).getIschecked()){
                            msg=Toast.makeText(context, holder.itemListCheckBox.getText()+" is arranged", Toast.LENGTH_SHORT);
                        }
                        else{
                            msg=Toast.makeText(context, holder.itemListCheckBox.getText()+" not arranged", Toast.LENGTH_SHORT);
                        }

                        msg.show();
                    }
                }
            });

            holder.itemDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setTitle("Delete - "+itemsList.get(position).getItemname())
                            .setMessage("Are you sure?")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    database.mainDao().deleteItem(itemsList.get(position));
                                    itemsList.remove(itemsList.get(position));
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
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class CheckListViewHolder extends  RecyclerView.ViewHolder{

    LinearLayout itemLinearlayout;
    CheckBox itemListCheckBox;
    Button itemDeleteBtn;


    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);

        itemListCheckBox=itemView.findViewById(R.id.itemListCheckbox);
        itemDeleteBtn=itemView.findViewById(R.id.itemDeleteBtn);
        itemLinearlayout=itemView.findViewById(R.id.itemCheckLinearlayout);

    }
}