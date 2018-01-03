package com.example.rasna.needforspeed;

import android.app.Dialog;
import android.content.Intent;
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

public class ActivityTracking extends AppCompatActivity {
        public enum LOC { SWIMMING, RUNNING, BIKING, WALKING, SKATING }
    //String TAG = "activity_tracking.xml";
    Button swimmingButton;
    Button runningButton;
    Button bikingButton;
    Button walkingButton;
    Button skatingButton;

    EditText etDay;
    EditText etHr;
    EditText etMin;
    EditText etComment;

    private Integer activity;
    private ListView activityListView;
    private Integer settingId;

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
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.htAbout:
                showAboutInfo();
                return true;
            case R.id.htHome:
                setMainView();
                return true;
            case R.id.htEng:
                loadLanguage("en");
                return true;
            case R.id.htFr:
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
        setContentView(R.layout.activity_tracking);
        //setTitle(R.string.);
        addClickListener();
    }
    View.OnClickListener handler = new View.OnClickListener(){
        public void onClick(View v) {
            if (v == swimmingButton) {
                setListContentView(LOC.SWIMMING.ordinal());
            } else if (v == runningButton) {
                setListContentView(LOC.RUNNING.ordinal());
            } else if (v == bikingButton) {
                setListContentView(LOC.BIKING.ordinal());
            } else if (v == walkingButton){
                setListContentView(LOC.WALKING.ordinal());
            }else {
                setListContentView(LOC.WALKING.ordinal());
            }
        }
    };

    private void addClickListener() {
        swimmingButton = findViewById( R.id.swimming);
        runningButton = (Button)findViewById( R.id.running);
        bikingButton = (Button)findViewById( R.id.biking);
        walkingButton = (Button)findViewById( R.id.walking);
        skatingButton = (Button)findViewById( R.id.skating);

        swimmingButton.setOnClickListener(handler);
        runningButton.setOnClickListener(handler);
        bikingButton.setOnClickListener(handler);
        walkingButton.setOnClickListener(handler);
        skatingButton.setOnClickListener(handler);
    }

    private void setListContentView(Integer activity) {
        this.activity = activity;
        setContentView(R.layout.activity_tracking_listview);
        setTitle(getActivityName());

        activityListView = findViewById(R.id.commentsList);
        View footer = getLayoutInflater().inflate(R.layout.activity_progress, null);
        activityListView.addFooterView(footer);

        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivitySetting setting = (ActivitySetting)activityListView.getItemAtPosition(position);
                setDetailsContentView(setting);
            }
        });
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView progress = findViewById(R.id.progress);
        ActivityTracking2 getInfoTask = new ActivityTracking2(this, activityListView, progressBar, progress);
        getInfoTask.execute(activity.toString());
        //showSnackbar();
    }
    private void showSnackbar() {
        String msg = "SETTING(S) FOR " + getActivityName();
        Snackbar.make(activityListView, msg, Snackbar.LENGTH_LONG).show();
    }

    private String getActivityName() {
        String activityName;

        if(activity == 0) {
            activityName = "SWIMMING";
        } else if (activity == 1) {
            activityName = "RUNNING";
        } else if (activity == 2) {
            activityName = "BIKING";
        }else if (activity == 3) {
            activityName = "WALKING";
        } else {
            activityName = "SKATING";
        }
        return activityName;
    }

    private void setDetailsContentView(ActivitySetting setting) {
        setContentView(R.layout.activity_tracking_page2);

        etDay = findViewById(R.id.etDay);
        etHr = findViewById(R.id.etHr);
        etMin = findViewById(R.id.etMin);
        etComment = findViewById(R.id.etcomment);


        etDay.setText(setting.iday);
        etHr.setText(String.valueOf(setting.ihour));
        etMin.setText(String.valueOf(setting.imin));
        etComment.setText(setting.icomment);

        activity = setting.iActivity;
        settingId = setting.iId;
    }
    public void addSetting(View view) {
        String day = etDay.getText().toString();
        Integer hour = Integer.parseInt(etHr.getText().toString());
        Integer min = Integer.parseInt(etMin.getText().toString());
        String comment = etComment.getText().toString();

        ActivityTracking3 tracking2 = new ActivityTracking3(this);
        tracking2.execute("addInfo", "0", activity.toString(),
                day, hour.toString(),
                min.toString(), comment);

        setListContentView(activity);
    }

    public void updateSetting(View view) {
        String day = etDay.getText().toString();
        Integer hour = Integer.parseInt(etHr.getText().toString());
        Integer min = Integer.parseInt(etMin.getText().toString());
        String comment = etComment.getText().toString();

        ActivityTracking3 backgroundTask = new ActivityTracking3(this);
        backgroundTask.execute("editInfo", settingId.toString(), activity.toString(),
                day, hour.toString(),
                min.toString(), comment);

        setListContentView(activity);
    }

    public void deleteSetting(View view) {
        ActivityTracking3 backgroundTask = new ActivityTracking3(this);
        backgroundTask.execute("deleteInfo", settingId.toString());
        setMainView();
    }

    private void showAboutInfo() {

        final Dialog dialog = new Dialog(this);
        //dialog.setTitle(this.getString(R.string.htAppName));
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
