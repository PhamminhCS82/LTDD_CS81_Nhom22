package com.example.weatherforecast.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "CityFTS.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

}
