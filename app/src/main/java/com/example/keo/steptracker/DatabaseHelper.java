package com.example.keo.steptracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //database name and version
    public static final String DATABASE_NAME = "stepTracker.db";
    public static final int DATABASE_VERSION = 1;

    //user table (profile)
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_Surname = "surname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_WEIGHT_GOAL = "weight_goal";
    public static final String COLUMN_STEPS_GOAL = "steps_goal";

    //history table
    public static final String TABLE_HISTORY = "history";
    public static final String HISTORY_ID = "logID";
    public static final String HISTORY_DATE = "logDate";
    public static final String HISTORY_WEIGHT = "weight";
    //calls weight and id as a foreign key

    //STATS table
    public static final String TABLE_STATS = "stats";
    public static final String STATS_ID = "statsID";
    public static final String STATS_STEPS= "steps";
    public static final String STATS_CALORIES= "calories";
    public static final String STATS_DISTANCE = "distance";
    public static final String STATS_ACTIVE_TIME = "active_time";

    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;

    private static final String CREATE_TABLE_USER  = "Create table " + TABLE_NAME + " (id integer primary key AUTOINCREMENT,"
            + "name text,surname text, email text, dob text,password text, weight text, height text, weight_goal text, steps_goal text)";


    private static final String CREATE_TABLE_HISTORY = "Create table " + TABLE_HISTORY + "(" + HISTORY_ID + " integer primary key AUTOINCREMENT,"
            + "logDate text, weight text)";



    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //EXECUTES the table creation queries
        db.execSQL(CREATE_TABLE_USER); //USER table
        db.execSQL(CREATE_TABLE_HISTORY); //history table
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //drops the table
        String query1 = "DROP TABLE IF EXISTS " + TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS " + TABLE_HISTORY;

        db.execSQL(query1);
        db.execSQL(query2);
        this.onCreate(db);
    }

    public Cursor getWeightLog()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME  + " WHERE id =" + Account.getId(), null);
        return data;
    }
}
