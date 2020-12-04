package com.example.mobileproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Table Name:
    public static final String TABLE_NAME = "NOTES";

    //Table Columns:
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String DESC = "description";

    //Database information:
    static final String DB_NAME = "NOTES.DB";
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table "
            + TABLE_NAME + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE
            + " TEXT NOT NULL, " + DESC + " TEXT);";

    //Constructor
    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
