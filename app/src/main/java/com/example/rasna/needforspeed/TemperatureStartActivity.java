package com.example.rasna.needforspeed;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TemperatureStartActivity extends AppCompatActivity {
    public enum LOC { BEDROOM, BASEMENT, HALLWAY, KITCHEN }

    private ListView mListView;
    private Integer location;
    private Integer settingId;

    Button btnBasement;
    Button btnBedroom;
    Button btnKitchen;
    Button btnHallway;

    EditText etDay;
    EditText etHr;
    EditText etMin;
    EditText etTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ht_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.htAbout:
                showAboutInfo();
                return true;
            case R.id.nHome:
                setMainView();
                return true;
            case R.id.htEng:
                loadLanguage("en");
                return true;
            case R.id.nFr:
                loadLanguage("fr");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadLanguage(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setMainView();
    }

    private void setMainView() {
        setContentView(R.layout.ht_activity_start);
        setTitle(R.string.htAppName);
        addClickListener();
    }

    View.OnClickListener handler = new View.OnClickListener(){
        public void onClick(View v) {
            if (v == btnHallway) {
                setListContentView(LOC.HALLWAY.ordinal());
            } else if (v == btnBasement) {
                setListContentView(LOC.BASEMENT.ordinal());
            } else if (v == btnBedroom) {
                setListContentView(LOC.BEDROOM.ordinal());
            } else {
                setListContentView(LOC.KITCHEN.ordinal());
            }
        }
    };

    private void addClickListener() {
        btnBasement = findViewById(R.id.btnBasement);
        btnBedroom = findViewById(R.id.btnBedroom);
        btnKitchen = findViewById(R.id.btnKitchen);
        btnHallway = findViewById(R.id.btnHallway);

        btnBasement.setOnClickListener(handler);
        btnBedroom.setOnClickListener(handler);
        btnKitchen.setOnClickListener(handler);
        btnHallway.setOnClickListener(handler);
    }

    private void setListContentView(Integer location) {
        this.location = location;
        setContentView(R.layout.ht_list_view);
        setTitle(getLocationName());

        mListView = findViewById(R.id.lv_settings);
        View footer = getLayoutInflater().inflate(R.layout.ht_progress, null);
        mListView.addFooterView(footer);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TemperatureSetting setting = (TemperatureSetting)mListView.getItemAtPosition(position);
                setDetailsContentView(setting);
            }
        });

        final ProgressBar progressBar = findViewById(R.id.pbProgress);
        final TextView tvProgress = findViewById(R.id.tvProgress);
        HomeThermostatGetInfoTask getInfoTask = new HomeThermostatGetInfoTask(this, mListView, progressBar, tvProgress);
        getInfoTask.execute(location.toString());
        showSnackbar();
    }

    private void showSnackbar() {
        String msg = "SETTING(S) FOR " + getLocationName();
        Snackbar.make(mListView, msg, Snackbar.LENGTH_LONG).show();
    }

    private String getLocationName() {
        String locationName;

        if(location == 0) {
            locationName = "BEDROOM";
        } else if (location == 1) {
            locationName = "BASEMENT";
        } else if (location == 2) {
            locationName = "HALLWAY";
        } else {
            locationName = "KITCHEN";
        }
        return locationName;
    }

    private void setDetailsContentView(TemperatureSetting setting) {
        setContentView(R.layout.ht_details);

        etDay = findViewById(R.id.etDay);
        etHr = findViewById(R.id.etHr);
        etMin = findViewById(R.id.etMin);
        etTemp = findViewById(R.id.etTemp);

        etDay.setText(setting.mDay);
        etHr.setText(String.valueOf(setting.mHr));
        etMin.setText(String.valueOf(setting.mMin));
        etTemp.setText(String.valueOf(setting.mTemperature));

        location = setting.mLocation;
        settingId = setting.mId;
    }

    public void addSetting(View view) {
        String day = etDay.getText().toString();
        Integer hour = Integer.parseInt(etHr.getText().toString());
        Integer min = Integer.parseInt(etMin.getText().toString());
        Integer temp = Integer.parseInt(etTemp.getText().toString());

        HomeThermostatBackgroundTask backgroundTask = new HomeThermostatBackgroundTask(this);
        backgroundTask.execute("addInfo", "0", location.toString(),
                day, hour.toString(),
                min.toString(), temp.toString());

        setListContentView(location);
    }

    public void updateSetting(View view) {
        String day = etDay.getText().toString();
        Integer hour = Integer.parseInt(etHr.getText().toString());
        Integer min = Integer.parseInt(etMin.getText().toString());
        Integer temp = Integer.parseInt(etTemp.getText().toString());

        HomeThermostatBackgroundTask backgroundTask = new HomeThermostatBackgroundTask(this);
        backgroundTask.execute("editInfo", settingId.toString(), location.toString(),
                day, hour.toString(),
                min.toString(), temp.toString());

        setListContentView(location);
    }

    public void deleteSetting(View view) {
        HomeThermostatBackgroundTask backgroundTask = new HomeThermostatBackgroundTask(this);
        backgroundTask.execute("deleteInfo", settingId.toString());
        setMainView();
    }

    private void showAboutInfo() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(this.getString(R.string.htAppName));
        dialog.setContentView(R.layout.ht_custom_dialog);
        dialog.show();

        Button btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
