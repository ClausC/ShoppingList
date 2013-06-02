package com.clausc.shoppinglist.database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clausc.shoppinglist.dao.ItemDao;
import com.clausc.shoppinglist.model.Item;

public class ItemTable {
  public static final String TABLE_ITEM = "Item";
  public static final String COLUMN_ID = "Id";
  public static final String COLUMN_NAME = "Name";
  public static final String COLUMN_AMOUNT = "Amount";

  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table " 
      + TABLE_ITEM
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_NAME + " text not null, " 
      + COLUMN_AMOUNT + " integer null" 
      + ");";
  
  public void onCreate(SQLiteDatabase db) {
  	Log.w(ItemTable.class.getName(), "onCreate");
  	db.execSQL(DATABASE_CREATE);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
  	Log.w(ItemTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
    onCreate(db);	  
  }
 	
}

