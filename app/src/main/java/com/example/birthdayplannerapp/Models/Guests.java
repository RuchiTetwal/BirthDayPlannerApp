package com.example.birthdayplannerapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "guests")
public class Guests implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int Id=0;

    @ColumnInfo(name ="guestname")
    String guestname;

    @ColumnInfo(name = "guestemail")
    String guestemail;

    @ColumnInfo(name = "ischecked")
    Boolean ischecked=false;

    public Guests(){

    }

    public Guests(String guestname, String guestemail, Boolean ischecked) {
        this.guestname = guestname;
        this.guestemail = guestemail;
        this.ischecked = ischecked;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getGuestemail() {
        return guestemail;
    }

    public void setGuestemail(String guestemail) {
        this.guestemail = guestemail;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }
}
