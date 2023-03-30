package com.example.birthdayplannerapp.Database;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.AsyncListDiffer;

import com.example.birthdayplannerapp.Constants.ItemTypeConstants;
import com.example.birthdayplannerapp.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class AppData extends Application {

    RoomDb database;
    String category;
    Context context;

    public  static  final String LAST_VERSION= "LAST_VERSION";
    public static final int NEW_VERSION =1;

    public AppData(RoomDb database) {
        this.database = database;
    }

    public AppData(RoomDb database, Context context) {
        this.context = context;
        this.database=database;
    }

    public List<Items> getGiftsData(){
        String []data = {"Toy Car", "SweatShirts"};

        return makeItemsList(ItemTypeConstants.Gifts,data);
    }

    public  List<Items> getDecorData(){
        String []data = {"Balloons", "Ribbons", "Lamps", "Candels"};

        return makeItemsList(ItemTypeConstants.Decoration,data);
    }

    public List<Items> getGamesData(){
        String []data = {"Ludo", "Uno", "Dumsharas"};

        return makeItemsList(ItemTypeConstants.Games,data);
    }
    public List<Items> getSongsData(){
        String []data = {"Happy Birthday to you..", "Badtamiz dil"};

        return makeItemsList(ItemTypeConstants.Songs,data);
    }
    public List<Items> getSnacksData(){
        String []data = {"Chips", "Cookies", "Waffles"};

        return makeItemsList(ItemTypeConstants.Snacks,data);
    }
    public List<Items> getBeveragesData(){
        String []data = {"Cold Drinks", "Mojito"};

        return makeItemsList(ItemTypeConstants.Beverages,data);
    }

    public List<Items> getCutleryData(){
        String []data = {"Plates", "Bowls", "Spoons"};

        return makeItemsList(ItemTypeConstants.Cutlery,data);
    }
    public List<Items> getOthersData(){
        String []data = {"Cake", "Birthday Caps"};

        return makeItemsList(ItemTypeConstants.Others,data);
    }



    public List<Items> makeItemsList(String category, String [] data){
        List<String> dataList = Arrays.asList(data);
        List<Items> itemList = new ArrayList<>();
        itemList.clear();

        for(int i=0;i<dataList.size();i++)
        {
            itemList.add(new Items(dataList.get(i),category, false));
        }

        return itemList;
    }

    public  List<List<Items>> getAllItemsData(){
        List<List<Items>> allItemList = new ArrayList<>();
        allItemList.clear();
        allItemList.add(getGiftsData());
        allItemList.add(getDecorData());
        allItemList.add(getGamesData());
        allItemList.add(getSongsData());
        allItemList.add(getSnacksData());
        allItemList.add(getBeveragesData());
        allItemList.add(getCutleryData());
        allItemList.add(getOthersData());

        return allItemList;
    }

    public void  addAllData(){
        List<List<Items>> allItemListData = getAllItemsData();

        for(List<Items> list: allItemListData){
            for(Items item:list){
                database.mainDao().saveItem(item);
            }
        }

        System.out.println("item data added to DB");
    }

    public void persistDataByCategory(String category){
        try{
            List<Items> list = deleteAllAndGetListByCategory(category);

//            for(Items item:list){
//                database.mainDao().saveItem(item);
//            }

            Toast.makeText(context, "All items of "+category+" deleted successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Items> deleteAllAndGetListByCategory(String category){
        database.mainDao().deleteAllByCategory(category);
        database.mainDao().deleteAllByCategoryAndAddedBy(category, "system");

        switch (category){
            case ItemTypeConstants.Gifts:
                return getGiftsData();
            case ItemTypeConstants.Decoration:
                return getDecorData();
            case ItemTypeConstants.Games:
                return getGamesData();
            case ItemTypeConstants.Songs:
                return getSongsData();
            case ItemTypeConstants.Snacks:
                return getSnacksData();
            case ItemTypeConstants.Beverages:
                return getBeveragesData();
            case ItemTypeConstants.Cutlery:
                return getCutleryData();
            case ItemTypeConstants.Others:
                return getOthersData();
            default:
                return new ArrayList<>();

        }
    }
}
