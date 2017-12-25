package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class gas_logList extends Activity {
    Button btnAddFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_log );
        String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");

        btnAddFuel = (Button)findViewById( R.id.buttonAddFuel );
        onClickAddFuelButton(currentVehicleVin);

    }


    public void onClickAddFuelButton(final String currentVehicleVin){
        btnAddFuel.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
              Intent intent = new Intent(gas_logList.this, gas_record.class);
              intent.putExtra("CURRENT_VEHICLE_VIN", currentVehicleVin);
              startActivity( intent );
              }
          }
        );
    }
}
