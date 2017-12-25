package com.example.rasna.needforspeed;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class gas_record extends Activity {

    private RadioGroup radioGroupUnitOption;
    private RadioButton selctedRadioButtonUnitOption;
    EditText editOdometer, editFuelPrice, editFuelAmount;
    String purchaseDate = "2017-12-12 12:20:00.000";
    Button saveButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_record );

        databaseHelper =  new DatabaseHelper( this);

        String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");
        //Toast.makeText( gas_record.this, currentVehicleVin , Toast.LENGTH_LONG).show();

        radioGroupUnitOption = (RadioGroup) findViewById(R.id.radioGroupUnit);
        editOdometer = (EditText) findViewById(R.id.txtOdometer);
        editFuelPrice = (EditText) findViewById(R.id.txtFuelPrice);
        editFuelAmount = (EditText)findViewById(R.id.txtFuelAmount);
        saveButton = (Button) findViewById(R.id.SaveButton);

        onClickSaveButton(currentVehicleVin);
    }

    public void onClickSaveButton(final String vin){
        //Save data in db
        saveButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int selectedId = radioGroupUnitOption.getCheckedRadioButtonId();
                        selctedRadioButtonUnitOption = (RadioButton) findViewById(selectedId);
                        String unit = selctedRadioButtonUnitOption.getText().toString();

                        Integer odometer = Integer.parseInt(editOdometer.getText().toString());
                        Float fuelPrice = Float.parseFloat(editFuelPrice.getText().toString());
                        Integer fuelAmount = Integer.parseInt(editFuelAmount.getText().toString());

                        boolean addedSuccessfully = databaseHelper.addFuel(vin, unit, odometer, fuelPrice, fuelAmount, purchaseDate );
                        if (addedSuccessfully){
                            Toast.makeText( gas_record.this, "Fuel added successfully" , Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText( gas_record.this, "Failed to add vehicle" , Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
