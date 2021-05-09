package com.example.weatherforecast.databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weatherforecast.model.City;
import com.example.weatherforecast.model.Coord;

import java.util.ArrayList;

public class DBAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DBAccess instance;
    Cursor cursor = null;

    private DBAccess(Context context){
        this.openHelper = new DBHelper(context);
    }
    public static DBAccess getInstance(Context context){
        if(instance == null){
            instance = new DBAccess(context);
        }
        return instance;
    }
    //open database
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }
    //close database
    public void close(){
        if(database != null){
            this.database.close();
        }
    }
    //Lấy danh sách thành phố
    public ArrayList<City> getCityList(String name){
        cursor = database.rawQuery("select name, country from city where name match '%" + name + "%'", new String[]{});
        ArrayList<City> ct = new ArrayList<>();
        while(cursor.moveToNext()) {
            City city = new City(cursor.getString(0), cursor.getString(1));
            ct.add(city);
        }
        return ct;
    }
    //Lấy tọa độ theo tên thành phố
    public Coord getCoordByCityName(String name){
        cursor = database.rawQuery("select [coord.lon], [coord.lat] from city where name match '" + name + "'", null);
        Coord coord = new Coord();
        while(cursor.moveToNext())
            coord = new Coord(cursor.getDouble(0), cursor.getDouble(1));
        return coord;
    }
}
