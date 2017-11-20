package com.example.rasna.needforspeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Vehicle extends Activity {

    EditText editVin, editMake, editModel, editYear, editTankSize;
    Button btnOk, btnCancel;
    Spinner spFuelType;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vehicle );

        databaseHelper =  new DatabaseHelper( this);

        editVin = (EditText)findViewById( R.id.txtVin );
        editMake = (EditText)findViewById( R.id.txtMake );
        editModel = (EditText)findViewById( R.id.txtModel );
        editYear = (EditText)findViewById( R.id.txtYear );
        editTankSize = (EditText)findViewById( R.id.txtTankSize );
        btnOk = (Button)findViewById( R.id.buttonOK );
        spFuelType = (Spinner)findViewById( R.id.spinnerFuelType );
        clickOkButton();
    }

    public void clickOkButton(){
        btnOk.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String vin = editVin.getText().toString();
                    String make = editMake.getText().toString();
                    String model = editModel.getText().toString();
                    Integer year = Integer.parseInt(editYear.getText().toString());
                    Integer tankSize = Integer.parseInt(editTankSize.getText().toString());
                    String fuelType = spFuelType.getSelectedItem().toString();
                    boolean addedSuccessfully = databaseHelper.addVehicle( vin, make, model, year, fuelType, tankSize );
                    if (addedSuccessfully){
                        Toast.makeText( Vehicle.this, "vehicle added successfully" , Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText( Vehicle.this, "Failed to add vehicle" , Toast.LENGTH_LONG).show();
                    }

                }
            }
        );
    }
}
