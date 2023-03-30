package com.example.birthdayplannerapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class Items implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int Id=0;

    @ColumnInfo(name ="itemname")
    String itemname;

    @ColumnInfo(name = "category")
    String category;

    @ColumnInfo(name = "addedby")
    String addedby;

    @ColumnInfo(name = "ischecked")
    Boolean ischecked=false;

    public Items(){

    }

    public Items(String itemname, String category,  Boolean ischecked){
        this.itemname=itemname;
        this.category=category;
        this.addedby="system";
        this.ischecked=ischecked;
    }

    public Items(String itemname, String category, String addedby, Boolean ischecked){
        this.itemname=itemname;
        this.category=category;
        this.addedby=addedby;
        this.ischecked=ischecked;
    }

    public int getId(){
        return Id;
    }

    public void setId(int Id){
        this.Id=Id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }
}
