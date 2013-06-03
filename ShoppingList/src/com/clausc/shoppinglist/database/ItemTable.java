package com.clausc.shoppinglist.database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clausc.shoppinglist.dao.ItemDao;

public class ItemTable {
  public static final String TABLE_ITEM = "Item";
  public static final String COLUMN_ID = "Id";
  public static final String COLUMN_NAME = "Name";
  public static final String COLUMN_AMOUNT = "Amount";

  private static List<String> stockItems = new ArrayList<String>(Arrays.asList("Agurk","Tomat","Salat", 
  		"Mælk", "Rugbrød", "Kærgården", "Havregryn", "Rosiner", "Ris", "Pasta", "Kartofler", 
  		"Remoulade", "Ketchup", "Mayonnaise", "Spareribs", "Makrel i tomat", "Laks", "Mel", "Gær",
  		"Frikadeller", "Sushi", "Hakket svinekød", "Hakket oksekød", "Salt", "Peber", "Oregano", 
  		"Grøntsager frost", "Peberfrugt", "Løg", "Hvidløg", "Hvidkål", "Rødløg", "Blegselleri", "Champignon", "Plasticposer")); 
  
  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table " 
      + TABLE_ITEM
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_NAME + " text not null, " 
      + COLUMN_AMOUNT + " integer null" 
      + ");";
  
  private void initDefaultItems(SQLiteDatabase db){
  	for(String item : stockItems){
  		ItemDao.save(db, item);
  		Log.w(this.getClass().getName(), "Saving item to DB: " + item);
  	}
  }
  
  public void onCreate(SQLiteDatabase db) {
  	Log.w(ItemTable.class.getName(), "onCreate");
  	db.execSQL(DATABASE_CREATE);
  	Log.w(ItemTable.class.getName(), "prepopulate defaultItems");
  	initDefaultItems(db);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
  	Log.w(ItemTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
    onCreate(db);	  
  }
 	
}

