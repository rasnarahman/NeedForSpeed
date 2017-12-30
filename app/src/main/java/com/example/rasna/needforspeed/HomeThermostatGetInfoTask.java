package com.example.rasna.needforspeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandita on 2017-12-24.
 */

public class HomeThermostatGetInfoTask extends AsyncTask<String, Integer, ArrayList<TemperatureSetting>> {
    private static final String TAG = "HomeThermostat";
    Context context;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private List<TemperatureSetting> settings;
    private ListView listView;
    private TemperatureDataAdapter dataAdapter;


    HomeThermostatGetInfoTask(Context context, ListView listView, ProgressBar progressBar, TextView tvProgress) {
        this.context = context;
        this.listView = listView;
        this.progressBar = progressBar;
        this.tvProgress = tvProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        settings = new ArrayList<>();
        dataAdapter = new TemperatureDataAdapter(context, R.layout.ht_list_row, settings);
        listView.setAdapter(dataAdapter);

        // show progress information
        tvProgress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        tvProgress.setText("");
        progressBar.setProgress(0);
    }

    @Override
    protected ArrayList<TemperatureSetting> doInBackground(String... params) {
        Integer location = Integer.parseInt(params[0]);

        Log.i(TAG, "Retrieving list for location: " + location);
        HomeThermostatDbHelper dbHelper = new HomeThermostatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<TemperatureSetting> tempSettings = dbHelper.getAllSettings(db, location);
        db.close();

        this.settings.clear();
        int size = tempSettings.size();
        progressBar.setMax(size);
        for(int i = 0; i < size; i++){
            this.settings.add(tempSettings.get(i));
            publishProgress(i+1, size);
            // put a delay for 1 second for demo purpose
            SystemClock.sleep(1000);
        }
        return tempSettings;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tvProgress.setText("Loading " + values[0] + "/" + values[1]);
        progressBar.setProgress(values[0]);
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(ArrayList<TemperatureSetting> settings) {
        super.onPostExecute(settings);
        tvProgress.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
}
