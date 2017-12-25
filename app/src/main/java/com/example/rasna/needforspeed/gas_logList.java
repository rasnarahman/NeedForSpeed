package com.example.rasna.needforspeed;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class gas_logList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_log );
        String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");
        Log.i("GasLogActivity", "Current Vehicle VIN = " + currentVehicleVin);

    }
}
