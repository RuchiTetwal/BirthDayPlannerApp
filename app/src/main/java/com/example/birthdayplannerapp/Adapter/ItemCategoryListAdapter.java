package com.example.birthdayplannerapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdayplannerapp.ItemsCheckList;
import com.example.birthdayplannerapp.Constants.ItemTypeConstants;
import com.example.birthdayplannerapp.R;

import java.util.List;

public class ItemCategoryListAdapter extends RecyclerView.Adapter<ItemCategoryListAdapter.myViewHolder>{

    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;
    Activity activity;

    public ItemCategoryListAdapter(Context context, List<String> titles, List<Integer> images, Activity activity){
        this.titles=titles;
        this.images=images;
        this.activity=activity;
        this.inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemCategoryListAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return  new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCategoryListAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(titles.get(position));
        holder.img.setImageResource(images.get(position));
        //holder.linearLayout.setAlpha(0.8F);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), ItemsCheckList.class);
                i.putExtra("header", titles.get(position));
                if(ItemTypeConstants.MySelections.equals(titles.get(position)) || ItemTypeConstants.MyList.equals(titles.get(position))){
                    i.putExtra("isShow", "false");
                }
                else{
                    i.putExtra("isShow", "true");
                }
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public  class myViewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        ImageView img;
        LinearLayout linearLayout;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            linearLayout = itemView.findViewById(R.id.itemLinearLayout);
        }
    }
}
