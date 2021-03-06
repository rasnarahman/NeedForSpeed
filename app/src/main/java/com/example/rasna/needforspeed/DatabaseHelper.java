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
    public static final Integer DATABASE_VERSION = 5;
    public static SQLiteDatabase db;
    public static ContentValues contentValues;
    private Context currentContext;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        currentContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE Vehicle ( VIN TEXT NOT NULL, Make TEXT, Model TEXT, Year INTEGER, FuelType TEXT, TankSize INTEGER)" );
        db.execSQL( "CREATE TABLE FuelInfo ( VIN TEXT NOT NULL, Unit TEXT, Odometer INTEGER, FuelPrice REAL, FuelAmount INTEGER, PurchaseDate TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE Vehicle" );
        db.execSQL( "DROP TABLE FuelInfo" );
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

    public boolean addFuel( String vin, String unit, Integer odometer, float fuelPrice, Integer fuelAmount, String purchaseDate){
        //Toast.makeText(currentContext, "Adding fuel", Toast.LENGTH_LONG).show();

        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues( );

        contentValues.put( "VIN", vin );
        contentValues.put( "Unit", unit );
        contentValues.put( "Odometer", odometer );
        contentValues.put( "FuelPrice", fuelPrice );
        contentValues.put( "FuelAmount", fuelAmount );
        contentValues.put( "PurchaseDate", purchaseDate );
        return insert( "FuelInfo", contentValues);
    }

    public ArrayList<FuelDetail> getFuelDetail(String vin) {
        String[] fuelTableColumns = new String[]{
                "VIN", "Unit", "Odometer", "FuelPrice", "FuelAmount", "PurchaseDate"
        };
        ArrayList<FuelDetail> fuelDetailList = new ArrayList<FuelDetail>();

        Cursor cursor = db.query("FuelInfo", fuelTableColumns, "VIN=?", new String[] { vin }, null, null, null, null);

        if (cursor.moveToFirst()){
            do{
                String unit = cursor.getString(cursor.getColumnIndex("Unit"));
                Integer odometer = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Odometer")));
                Float fuelPrice = Float.parseFloat(cursor.getString(cursor.getColumnIndex("FuelPrice")));
                Integer fuelAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("FuelAmount")));
                String purchaseDate = cursor.getString(cursor.getColumnIndex("PurchaseDate"));
                Log.i("DatabaseHelper", "Fuel Info = " + vin + "- " + unit + " - " + odometer + " - " + fuelPrice + " - " + fuelAmount + " - " + purchaseDate);
                FuelDetail fuelDetail = new FuelDetail(vin, unit, odometer, fuelPrice, fuelAmount, purchaseDate);
                fuelDetailList.add(fuelDetail);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return fuelDetailList;

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

    public Boolean removeFuelRecord(String vin, String date){
        Log.i("DatabaseHelper", "Delete fuel purchase date: " + date);
        Log.i("DatabaseHelper", "Delete fuel vin: " + vin);

        int deletedRecrod =  db.delete("FuelInfo", "VIN=? AND PurchaseDate=?",new String [] { vin, date});

        Log.i("Deleted ","Delete result: " + Integer.toString(deletedRecrod));

        return (deletedRecrod == 0) ? false : true;
    }


    public Boolean removeVehicle(String vin){
        Log.i("DatabaseHelper", "Delete vehicle vin: " + vin);

        int deletedRecrod =  db.delete("Vehicle", "VIN=?",new String [] { vin});

        Log.i("Deleted ","Delete result: " + Integer.toString(deletedRecrod));

        return (deletedRecrod == 0) ? false : true;
    }


    public Boolean removeFuelRecordAll(String vin){
        Log.i("DatabaseHelper", "Delete fuel vin: " + vin);

        int deletedRecrod =  db.delete("FuelInfo", "VIN=?",new String [] { vin});

        Log.i("Deleted ","Delete result: " + Integer.toString(deletedRecrod));

        return (deletedRecrod == 0) ? false : true;
    }

}
