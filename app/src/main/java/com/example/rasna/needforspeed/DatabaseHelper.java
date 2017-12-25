package com.example.rasna.needforspeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public Cursor getVehicleInfo() {
        return db.rawQuery( "Select * from "+ "Vehicle",null);
    }

    public List<Vehicle> getAllVehicles(){
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        String[] vechileTableColumns = new String[]{
            "VIN", "Make", "Model", "Year", "FuelType", "TankSize"
        };

        //Get vehicle from database
        Cursor cursor = db.query("Vehicle", vechileTableColumns, null, null, null, null, null);
        String count =  Integer.toString(cursor.getCount());
        Log.i("DataBaseHelper", "Total Vehicle in Database= " + count);

        //Create list of 'Vehicle' objects from the retrieved data
        if (cursor.moveToFirst()){
            do{
                String vin = cursor.getString(cursor.getColumnIndex("VIN"));
                String make = cursor.getString(cursor.getColumnIndex("Make"));
                String model = cursor.getString(cursor.getColumnIndex("Model"));
                Integer year = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Year")));
                String fuelType = cursor.getString(cursor.getColumnIndex("FuelType"));
                Integer tankSize = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TankSize")));
                Log.i("DatabaseHelper", "Vehicle Info = " + vin + "- " + make + " - " + model + " - " + year + " - " + fuelType + " - " + tankSize);
                Vehicle vehicle = new Vehicle(vin, make, model, year, tankSize, fuelType);
                vehicleList.add(vehicle);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return vehicleList;
    }



    public boolean insert(String tableName, ContentValues contentValues) {
        long result = db.insert( tableName, null, contentValues);
        return result == -1? false: true;
    }
}
