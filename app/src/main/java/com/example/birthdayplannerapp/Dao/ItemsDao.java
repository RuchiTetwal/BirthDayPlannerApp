package com.example.birthdayplannerapp.Dao;

import static  androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthdayplannerapp.Models.Items;

import java.util.List;

@Dao
public interface ItemsDao {

    @Insert(onConflict = REPLACE)
    void saveItem(Items item);

    @Query("select * from items where category=:category order by Id asc")
    List<Items> getAllItems(String category);

    @Delete
    void  deleteItem(Items item);

    @Query("update items set ischecked=:ischecked where Id=:id")
    void checkUnCheck(int id, boolean ischecked);

    @Query("select * from items order by itemname asc")
    List<Items> getAll();

//    @Query("select count(*) from items")
//    Integer getItemsCount();

    @Query("delete from items where addedby=:addedBy")
    Integer deleteAllSystemItems(String addedBy);

    @Query("delete from items where category=:category")
    Integer deleteAllByCategory(String category);

    @Query("delete from items where category=:category and addedby=:addedBy")
    Integer deleteAllByCategoryAndAddedBy(String category, String addedBy);

    @Query("select * from items where ischecked=:isChecked order by itemname asc")
    List<Items> getAllSelected(Boolean isChecked);
}
