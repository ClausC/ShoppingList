package com.clausc.shoppinglist.database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.clausc.shoppinglist.dao.ItemDao;
import com.clausc.shoppinglist.model.Item;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "ShoppingList.db";
  private static final int DATABASE_VERSION = 1;

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override	
  public void onCreate(SQLiteDatabase database) {
  	Log.w(this.getClass().getName(), "onCreate");
  	new ItemTable().onCreate(database);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  	Log.w(this.getClass().getName(), "onUpgrade. From version: " + oldVersion + " , To: " + newVersion );
  	new ItemTable().onUpgrade(db, oldVersion, newVersion);
  }


}