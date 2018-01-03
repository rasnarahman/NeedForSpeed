package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TimeActivity extends Activity {

    Button OkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_time );
        onClickSaveDate();
    }

    public void onClickSaveDate(){
        //  String purchaseDate = "2017-12-12 12:20:00.000";
        final String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");

        OkButton = (Button)findViewById(R.id.saveTime);

        OkButton.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v){
                  DatePicker datePicker;
                  TimePicker timePicker;
                  datePicker = (DatePicker) findViewById(R.id.datePicker);
                  timePicker=(TimePicker) findViewById(R.id.timePicker);
                  int   day  = datePicker.getDayOfMonth();
                  int   month= datePicker.getMonth() + 1;
                  int   year = datePicker.getYear();
                  int   hour = timePicker.getCurrentHour();
                  int   minute= timePicker.getCurrentMinute();
                  final String dateTime = year+"-"+month+"-"+day+" "+hour+":"+minute+":00.000";

                  Intent intent = new Intent(TimeActivity.this, gas_record.class);
                  intent.putExtra("Selected_Date", dateTime);
                  intent.putExtra("CURRENT_VEHICLE_VIN", currentVehicleVin);
                  startActivity(intent);
              }
          }
        );
    }

}
