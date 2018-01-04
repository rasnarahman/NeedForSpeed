package com.example.rasna.needforspeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 1/3/2018.
 */

public class NavDatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "GlobalDatabase.db";

    static final String NUTRITION_TABLE_NAME = "NUTRITION_INFO";
    public static final String DROP_NUTRITION_TABLE_SQL = "DROP TABLE IF EXISTS " + NUTRITION_TABLE_NAME;
    static final String FOOD_ID = "FOOD_ID";
    static final String FOOD_ITEM_COL_NAME = "FOOD_NAME";
    static final String CALORIES_COL_NAME = "CALORIES";
    static final String CARB_COL_NAME = "CARBOHYDRATE";
    static final String FAT_COL_NAME = "FAT";
    static final String COMMENTS_COL_NAME = "COMMENTS";
    static final String FOOD_DATE_COL_NAME = "DATE";

    public static final String CREATE_NUTRITION_TABLE_SQL
            = "CREATE TABLE " + NUTRITION_TABLE_NAME + " ( "
            + FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOOD_ITEM_COL_NAME + " TEXT, "
            + CALORIES_COL_NAME + " INTEGER, "
            + CARB_COL_NAME + " INTEGER, "
            + FAT_COL_NAME + " INTEGER "
            + COMMENTS_COL_NAME + " TEXT "
            + FOOD_DATE_COL_NAME + " DATETIME DEFAULT CURRENT_TIMESTAMP "

            + " );";

    private static final int DATABASE_VERSION_NUM = 2;

    NavDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NUTRITION_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL(DROP_NUTRITION_TABLE_SQL);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(DROP_NUTRITION_TABLE_SQL);

        onCreate(db);
    }


}


















