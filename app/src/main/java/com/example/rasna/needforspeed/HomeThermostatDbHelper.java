package com.example.rasna.needforspeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class HomeThermostatDbHelper extends SQLiteOpenHelper {
    private static final String DB_PATH = "/data/data/com.example.rasna.needforspeed/databases/HomeThermostat.db";
    private static final String DB_NAME = "HomeThermostat.db";
    private static final Integer DB_VERSION = 1;
    private static final String TAG = "HomeThermostat";
    private static final String TABLE_NAME = "thermostatInfo";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                                            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "LOC INTEGER NOT NULL," +
                                            "DAY TEXT NOT NULL," +
                                            "HOUR INTEGER NOT NULL," +
                                            "MIN INTEGER NOT NULL," +
                                            "TEMP INTEGER NOT NULL)";

    public HomeThermostatDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(TAG, "Database created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        addDefaultSettings(db);
        Log.i(TAG, "Database table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Log.i(TAG, "Database upgraded.");
    }

    private void addDefaultSettings(SQLiteDatabase db) {
        addInfo(db, 0, "FRI", 10, 30, 20);
        addInfo(db, 0, "SAT", 11, 45, 22);
        addInfo(db, 0, "SUN", 12, 15, 23);

        addInfo(db, 1, "MON", 13, 15, 23);
        addInfo(db, 1, "TUE", 15, 30, 21);
        addInfo(db, 1, "WED", 17, 50, 24);

        addInfo(db, 2, "THR", 13, 15, 20);
        addInfo(db, 2, "FRI", 18, 35, 24);
        addInfo(db, 2, "SAT", 19, 25, 23);

        addInfo(db, 3, "SUN", 18, 25, 22);
        addInfo(db, 3, "MON", 12, 55, 21);
        addInfo(db, 3, "TUE", 16, 25, 25);
    }

    public boolean addInfo(SQLiteDatabase db, Integer location, String day, Integer hour, Integer min, Integer temperature){
        Log.i(TAG, "Attempt to add entry: {" + location + "," + day + "," + hour + "," + min + "," + temperature + "}");
        ContentValues contentValues = new ContentValues();
        contentValues.put("LOC", location);
        contentValues.put( "DAY", day);
        contentValues.put( "HOUR", hour);
        contentValues.put( "MIN", min);
        contentValues.put( "TEMP", temperature);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1 ? true : false;
    }

    public int updateInfo(SQLiteDatabase db, Integer id, Integer location, String day, Integer hour, Integer min, Integer temperature){
        Log.i(TAG, "Attempt to update entry: {" + location + "," + day + "," + hour + "," + min + "," + temperature + "}");
        String whereClause = "Id=?";
        String[] whereArgs = new String[] {Integer.toString(id)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("LOC", location);
        contentValues.put( "DAY", day);
        contentValues.put( "HOUR", hour);
        contentValues.put( "MIN", min);
        contentValues.put( "TEMP", temperature);
        int numOfRowsAffected = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        return numOfRowsAffected;
    }

    public int deleteInfo(SQLiteDatabase db, Integer id){
        Log.i(TAG, "Attempt to delete entry: {Id:" + id + "}");
        String whereClause = "Id=?";
        String[] whereArgs = new String[] {Integer.toString(id)};

        int numOfRowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        return numOfRowsAffected;
    }

    public ArrayList<TemperatureSetting> getAllSettings(SQLiteDatabase db, Integer location) {
        Log.i(TAG, "Attempt to query for: {location:" + location + "}");
        String[] columns = new String[] {"Id", "LOC", "DAY", "HOUR", "MIN", "TEMP"};
        String whereClause = "LOC=?";
        String[] whereArgs = new String[] {Integer.toString(location)};

        ArrayList<TemperatureSetting> settings = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, columns,
                whereClause, whereArgs,
                null, null, null);

        TemperatureSetting setting;
        if (cursor.moveToFirst()) {
            do {
                setting = new TemperatureSetting(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5));
                settings.add(setting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return settings;
    }
}

