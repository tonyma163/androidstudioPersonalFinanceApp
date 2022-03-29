package com.example.personalfinanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myfinance.db";
    private static final String DB_TABLE_TRANSACTION = "transactions";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_AMOUNT = "amount";

    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create table
    public static final String DB_CREATE = "create table "+
            DB_TABLE_TRANSACTION+" ("+KEY_ID+" integer primary key autoincrement, "+
            KEY_DATE+" text not null, "+KEY_CATEGORY+" text not null, "+KEY_AMOUNT+" int);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE_TRANSACTION);
        onCreate(db);
    }

    private SQLiteDatabase db;

    public boolean insertData(String date, String category, int amount) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_CATEGORY, category);
        contentValues.put(KEY_AMOUNT, amount);

        Log.d("DEBUG", "date: "+date+"name:"+category+" number:"+amount);

        long result = db.insert(DB_TABLE_TRANSACTION, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from "+DB_TABLE_TRANSACTION, null);
        return result;
    }

}
