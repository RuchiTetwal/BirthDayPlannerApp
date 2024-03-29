package com.example.birthdayplannerapp.Dao;

import static  androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthdayplannerapp.Models.Guests;

import java.util.List;

@Dao
public interface GuestsDao {

    @Insert(onConflict = REPLACE)
    void saveGuest(Guests guest);

    @Query("select * from guests order by guestname asc")
    List<Guests> getAllGuests();

    @Delete
    void  deleteGuest(Guests guest);

    @Query("update guests set ischecked=:ischecked where Id=:id")
    void checkUnCheckGuest(int id, boolean ischecked);

//    @Query("select count(*) from guests")
//    Integer getGuestsCount();

    @Query("delete from guests")
    Integer deleteAllGuests();

    @Query("select * from guests where ischecked=:isChecked order by guestname asc")
    List<Guests> getAllSelectedGuests(Boolean isChecked);
}