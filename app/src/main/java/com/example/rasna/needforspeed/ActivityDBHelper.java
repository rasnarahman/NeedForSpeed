package com.example.rasna.needforspeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ActivityDBHelper extends SQLiteOpenHelper {
    private static final String DB_PATH = "/data/data/com.example.rasna.needforspeed/databases/ActivityTracking.db";
    private static final String DB_NAME = "ActivityTracking.db";
    private static final Integer DB_VERSION = 1;
    private static final String TAG = "ActivityTracking";
    private static final String TABLE_NAME = "activityInfo";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ACTIVITY INTEGER NOT NULL," +
            "DAY TEXT NOT NULL," +
            "HOUR INTEGER NOT NULL," +
            "MIN INTEGER NOT NULL," +
            "COMMENT TEXT NOT NULL)";

    public ActivityDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(TAG, "Database created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        addDefaultSettings(db);
        Log.i(TAG, "Database table created.");
    }

    private void addDefaultSettings(SQLiteDatabase db) {
        addInfo(db, 0, "FRI", 1, 30, "It was easy");
        addInfo(db, 0, "SAT", 1, 30, "It was easy");
        addInfo(db, 1, "SAT", 1, 45, "It was easy");
        addInfo(db, 2, "SUN", 1, 15, "It was hard");
        addInfo(db, 2, "MON", 1, 15, "It was easy");
        addInfo(db, 3, "TUE", 1, 30, "It was ok");
        addInfo(db, 3, "WED", 1, 50, "It was easy");
        addInfo(db, 4, "THR", 1, 15, "It was hard");
        addInfo(db, 4, "FRI", 1, 35, "It was easy");
    }

    public boolean addInfo(SQLiteDatabase db, Integer activity, String day, Integer hour, Integer min, String comment) {
        Log.i(TAG, "Attempt to add entry: {" + activity + "," + day + "," + hour + "," + min + "," + comment + "}");
        ContentValues contentValues = new ContentValues();
        contentValues.put("ACTIVITY", activity);
        contentValues.put( "DAY", day);
        contentValues.put( "HOUR", hour);
        contentValues.put( "MIN", min);
        contentValues.put( "COMMENT", comment);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1 ? true : false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Log.i(TAG, "Database upgraded.");
    }

    public int updateInfo(SQLiteDatabase db, Integer id, Integer activity, String day, Integer hour, Integer min, String comment){
        Log.i(TAG, "Trying to update entry: {" + activity + "," + day + "," + hour + "," + min + "," + comment + "}");
        String whereClause = "Id=?";
        String[] whereArgs = new String[] {Integer.toString(id)};

        ContentValues contentValues = new ContentValues();
        contentValues.put("ACTIVITY", activity);
        contentValues.put( "DAY", day);
        contentValues.put( "HOUR", hour);
        contentValues.put( "MIN", min);
        contentValues.put( "COMMENT", comment);
        int numOfRowsAffected = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        return numOfRowsAffected;
    }

    public int deleteInfo(SQLiteDatabase db, Integer id){
        Log.i(TAG, "Trying to delete entry: {Id:" + id + "}");
        String whereClause = "Id=?";
        String[] whereArgs = new String[] {Integer.toString(id)};

        int numOfRowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        return numOfRowsAffected;
    }

    public ArrayList<ActivitySetting> getAllSettings(SQLiteDatabase db, Integer activity) {
        Log.i(TAG, "Attempt to query for: {activity:" + activity + "}");
        String[] columns = new String[] {"Id", "ACTIVITY", "DAY", "HOUR", "MIN", "COMMENT"};
        String whereClause = "ACTIVITY=?";
        String[] whereArgs = new String[] {Integer.toString(activity)};

        ArrayList<ActivitySetting> settings = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, columns,
                whereClause, whereArgs,
                null, null, null);

        ActivitySetting setting;
        if (cursor.moveToFirst()) {
            do {
                setting = new ActivitySetting(cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(1));
                settings.add(setting);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return settings;
    }
}
