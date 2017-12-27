package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class gas_record extends Activity {

    private RadioGroup radioGroupUnitOption;
    private RadioButton selctedRadioButtonUnitOption;
    EditText editOdometer, editFuelPrice, editFuelAmount, editPurchaseDate;
    String purchaseDate = "2017-12-12 12:20:00.000";
    Button saveButton;
    Button DateButton;
    Integer odometer ;
    Float fuelPrice;
    Integer fuelAmount;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_record );

        databaseHelper =  new DatabaseHelper( this);

        String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");
        String selectedDate = getIntent().getStringExtra("Selected_Date");

        radioGroupUnitOption = (RadioGroup) findViewById(R.id.radioGroupUnit);
        editOdometer = (EditText) findViewById(R.id.txtOdometer);
        editFuelPrice = (EditText) findViewById(R.id.txtFuelPrice);
        editFuelAmount = (EditText)findViewById(R.id.txtFuelAmount);
        editPurchaseDate = (EditText) findViewById(R.id.Dated);
        saveButton = (Button) findViewById(R.id.SaveButton);

        if (selectedDate != null && !selectedDate.isEmpty() && !selectedDate.equals("null")){
            editPurchaseDate.setText("", TextView.BufferType.EDITABLE);
            editPurchaseDate.setText(selectedDate, TextView.BufferType.EDITABLE);
        }

        onClickSaveButton(currentVehicleVin);
        onClickDate(currentVehicleVin);
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

                        odometer = Integer.parseInt(editOdometer.getText().toString());
                        fuelPrice = Float.parseFloat(editFuelPrice.getText().toString());
                        fuelAmount = Integer.parseInt(editFuelAmount.getText().toString());
                        purchaseDate = editPurchaseDate.getText().toString();

                        boolean addedSuccessfully = databaseHelper.addFuel(vin, unit, odometer, fuelPrice, fuelAmount, purchaseDate );
                        if (addedSuccessfully){
                            Toast.makeText( gas_record.this, "Fuel added successfully" , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(gas_record.this, gas_logList.class);
                            intent.putExtra("CURRENT_VEHICLE_VIN", vin);
                            startActivity( intent );
                        }
                        else {
                            Toast.makeText( gas_record.this, "Failed to add fuel detail" , Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }


    public void onClickDate(final String vin){

        DateButton = (Button)findViewById(R.id.DateButton);
        DateButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(gas_record.this, TimeActivity.class);
                    intent.putExtra("CURRENT_VEHICLE_VIN", vin);
                    startActivity(intent);
                }
             }
        );

    }
}
