package com.example.rasna.needforspeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rasna on 2017-11-19.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "NeedForSpeed.db";
    public static final Integer DATABASE_VERSION = 4;
    public static SQLiteDatabase db;
    public static ContentValues contentValues;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE Vehicle ( VIN TEXT NOT NULL, Make TEXT, Model TEXT, Year INTEGER, FuelType TEXT, TankSize INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE Vehicle" );
        onCreate(db);
    }

    public boolean addVehicle( String vin, String make, String model, Integer year, String fuelType, Integer tankSize){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues( );

        contentValues.put( "VIN", vin );
        contentValues.put( "Make", make );
        contentValues.put( "Model", model );
        contentValues.put( "Year", year );
        contentValues.put( "FuelType", fuelType );
        contentValues.put( "TankSize", tankSize );
        return insert( "Vehicle", contentValues);
    }

    public Cursor getVehicleInfo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "Select * from "+ "Vehicle",null);
        return res;
    }



    public boolean insert(String tableName, ContentValues contentValues) {
        long result = db.insert( tableName, null, contentValues);
        return result == -1? false: true;
    }
}
