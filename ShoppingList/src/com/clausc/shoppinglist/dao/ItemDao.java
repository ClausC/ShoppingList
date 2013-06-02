package com.clausc.shoppinglist.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clausc.shoppinglist.database.ItemTable;
import com.clausc.shoppinglist.database.MySQLiteHelper;
import com.clausc.shoppinglist.model.Item;

public class ItemDao {

	public List<Item> getItems(){
		return new ArrayList<Item>();
	}
  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { ItemTable.COLUMN_ID,ItemTable.COLUMN_NAME, ItemTable.COLUMN_AMOUNT };
  private static List<String> stockItems = new ArrayList<String>(Arrays.asList("Agurk","Tomat","Salat", 
  		"Mælk", "Rugbrød", "Kærgården", "Havregryn", "Rosiner", "Ris", "Pasta", "Kartofler", 
  		"Remoulade", "Ketchup", "Mayonnaise", "Spareribs", "Makrel i tomat", "Laks", "Mel", "Gær",
  		"Frikadeller", "Sushi", "Hakket svinekød", "Hakket oksekød", "Salt", "Peber", "Oregano", 
  		"Grøntsager frost", "Peberfrugt", "Løg", "Hvidløg", "Hvidkål", "Rødløg", "Blegselleri", "Champignon", "Plasticposer")); 

  public ItemDao(Context context) {
    dbHelper = new MySQLiteHelper(context);
    open();
  }

  private void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Item createItem(String item) {
    ContentValues values = new ContentValues();
    values.put(ItemTable.COLUMN_NAME, item);
    long insertId = database.insert(ItemTable.TABLE_ITEM, null,values);
    Cursor cursor = database.query(ItemTable.TABLE_ITEM,allColumns, ItemTable.COLUMN_ID + " = " + insertId, null,null, null, null);
    cursor.moveToFirst();
    Item newItem = cursorToItem(cursor);
    cursor.close();
    return newItem;
  }

  public void deleteItem(Item item) {
    int id = item.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(ItemTable.TABLE_ITEM, ItemTable.COLUMN_ID + " = " + id, null);
  }

  private List<Item> findItems() {
    List<Item> items = new ArrayList<Item>();
    Cursor cursor = database.query(ItemTable.TABLE_ITEM,allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Item item = cursorToItem(cursor);
      items.add(item);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return items;

  }

  private void initDefaultItems(){
  	for(String item : stockItems){
  		createItem(item);
  		Log.w(this.getClass().getName(), "Saving item to DB: " + item);
  	}
  	Log.w(this.getClass().getName(), "Checking items exists:");
  	List<Item> items = getAllItems();
  	for(Item item : items){
  		Log.w(this.getClass().getName(), "   Item: " + item.getName());
  	}
  }

  public List<Item> getAllItems() {
  	List<Item> tmp = findItems();
  	if(tmp == null || tmp.size() == 0){
  		initDefaultItems();
  		tmp = findItems();
  	}
  	return tmp;
  }

  private Item cursorToItem(Cursor cursor) {
    Item item = new Item();
    item.setId(cursor.getInt(0));
    item.setName(cursor.getString(1));
    return item ;
  }
} 	
