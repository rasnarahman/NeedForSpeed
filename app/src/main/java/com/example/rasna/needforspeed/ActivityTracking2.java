package com.example.rasna.needforspeed;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityTracking2 extends AsyncTask<String, Integer, ArrayList<ActivitySetting>> {
    //String TAG = "activity_tracking_page2.xml";
    private static final String TAG = "ActivityTracking";
    Context context;
     private ProgressBar progressBar;
     private TextView progress;
     private List<ActivitySetting> settings;
     private ListView listView;
    private ActivityDataAdapter dataAdapter;

    ActivityTracking2(Context context, ListView listView, ProgressBar progressBar, TextView progress) {
        this.context = context;
        this.listView = listView;
        this.progressBar = progressBar;
        this.progress = progress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        settings = new ArrayList<>();
        dataAdapter = new ActivityDataAdapter(context, R.layout.list_row, settings);
        listView.setAdapter(dataAdapter);

        // show progress information
        progress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        progress.setText("");
        progressBar.setProgress(0);
    }

    @Override
    protected ArrayList<ActivitySetting> doInBackground(String... params) {
        Integer activity = Integer.parseInt(params[0]);

        Log.i(TAG, "Retrieving list for location: " + activity);
        ActivityDBHelper dbHelper = new ActivityDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<ActivitySetting> activitySettings = dbHelper.getAllSettings(db, activity);
        db.close();

        this.settings.clear();
        int size = activitySettings.size();
        progressBar.setMax(size);
        for(int i = 0; i < size; i++){
            this.settings.add(activitySettings.get(i));
            publishProgress(i+1, size);
            // put a delay for 1 second for demo purpose
            SystemClock.sleep(1000);
        }
        return activitySettings;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progress.setText("Loading " + values[0] + "/" + values[1]);
        progressBar.setProgress(values[0]);
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(ArrayList<ActivitySetting> settings) {
        super.onPostExecute(settings);
        progress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }




}


