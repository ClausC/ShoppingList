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

  public Item save(String item) {
    ContentValues values = new ContentValues();
    values.put(ItemTable.COLUMN_NAME, item);
    long insertId = database.insert(ItemTable.TABLE_ITEM, null,values);
    Cursor cursor = database.query(ItemTable.TABLE_ITEM,allColumns, ItemTable.COLUMN_ID + " = " + insertId, null,null, null, null);
    cursor.moveToFirst();
    Item newItem = cursorToItem(cursor);
    cursor.close();
    return newItem;
  }

  public static void save(SQLiteDatabase db, String item) {
    ContentValues values = new ContentValues();
    values.put(ItemTable.COLUMN_NAME, item);
    long insertId = db.insert(ItemTable.TABLE_ITEM, null,values);
  }

  public void delete(Item item) {
    int id = item.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(ItemTable.TABLE_ITEM, ItemTable.COLUMN_ID + " = " + id, null);
  }

  public List<Item> getAll() {
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

  private Item cursorToItem(Cursor cursor) {
    Item item = new Item();
    item.setId(cursor.getInt(0));
    item.setName(cursor.getString(1));
    return item ;
  }
} 	











