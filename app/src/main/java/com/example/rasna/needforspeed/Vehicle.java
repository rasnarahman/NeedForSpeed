package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Vehicle extends Activity {

    EditText editVin, editMake, editModel, editYear, editTankSize;
    Button btnOk,btnCancel ;
    Spinner spFuelType;
    DatabaseHelper databaseHelper;

    String Vin;
    String Make;
    String Model;
    Integer Year;
    Integer TankSize;
    String FuelType;

    public Vehicle() {}

    public Vehicle(String vin, String make, String model, Integer year, Integer tankSize, String fuelType) {
        Vin = vin;
        Make = make;
        Model = model;
        Year = year;
        TankSize = tankSize;
        FuelType = fuelType;
    }

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
        onClickButtonToGoHome();
    }

    public boolean isVechileNew(){
        boolean newVehicle = true;
        String vin = editVin.getText().toString();
        List<Vehicle> currentVehiclesInDb = databaseHelper.getAllVehicles();

        for(Vehicle vehicle : currentVehiclesInDb) {
           if(vehicle.Vin == vin) {
               newVehicle = false;
           }
        }

        return newVehicle;
    }

    public void clickOkButton(){
        if(!isVechileNew()) {
            Toast.makeText( Vehicle.this, "Error: The vehicle is alre1dy in database. Please check the  VIN number", Toast.LENGTH_LONG).show();
            return;
        }

        //Save data in db
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
                    boolean addedSuccessfully = databaseHelper.addVehicle(vin, make, model, year, fuelType, tankSize );
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

    public void onClickButtonToGoHome(){
        Button homeButton = (Button)findViewById(R.id.buttonHome);
        homeButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(Vehicle.this, MainActivity.class);
               startActivity( intent );
            }
            }
        );
    }


    public void showMessage(String Title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( getTitle() );
        builder.setMessage( Message );
        builder.show();
    }
}
