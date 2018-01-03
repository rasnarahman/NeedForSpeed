package com.example.rasna.needforspeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ActivityTracking3 extends AsyncTask<String, Void, String> {

    private static final String TAG = "ActivityTracking";
    Context context;
    Integer id, iactivity, hr, min;
    String day, comment;

    ActivityTracking3(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String action = params[0];
        id = Integer.parseInt(params[1]);

        if(!action.endsWith("deleteInfo")) {
            iactivity = Integer.parseInt(params[2]);
            day = params[3];
            hr = Integer.parseInt(params[4]);
            min = Integer.parseInt(params[5]);
            comment = params[6];
        }
        Log.i(TAG, "Background Task: " + action);
        SQLiteDatabase db;
        ActivityDBHelper dbHelper = new ActivityDBHelper(context);
        switch(action) {
            case "addInfo":
                db = dbHelper.getWritableDatabase();
                boolean added = dbHelper.addInfo(db, iactivity, day, hr, min, comment);
                db.close();
                return added ? "One record added." : "Failed to add record.";
            case "editInfo":
                db = dbHelper.getWritableDatabase();
                int affectedRows = dbHelper.updateInfo(db, id, iactivity, day, hr, min, comment);
                db.close();
                return affectedRows + " row(s) updated.";
            case "deleteInfo":
                db = dbHelper.getWritableDatabase();
                int deletedRows = dbHelper.deleteInfo(db, id);
                db.close();
                return deletedRows + " row(s) deleted.";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
