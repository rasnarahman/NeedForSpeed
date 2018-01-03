package com.example.rasna.needforspeed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Vehicle extends AppCompatActivity {

    EditText editVin, editMake, editModel, editYear, editTankSize;
    Button btnOk,btnCancel ;
    Spinner spFuelType;
    DatabaseHelper databaseHelper;
    Toolbar mToolBar;

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

        mToolBar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(mToolBar);

        clickOkButton();
        onClickButtonToGoHome();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                Toast.makeText(this.getApplicationContext(), "Version 1.0, by Rasna Rahman.",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.action_home:
                Intent intent = new Intent(Vehicle.this, MainActivity.class);
                startActivity( intent );
                break;
        }

        return  true;
    }

    public boolean isVechileNew(String newvin){
        boolean newVehicle = true;
        List<Vehicle> currentVehiclesInDb = databaseHelper.getAllVehicles();
        for(Vehicle vehicle : currentVehiclesInDb) {
           if(vehicle.Vin.equalsIgnoreCase(newvin)) {
               newVehicle = false;
               break;
           }
        }

        return newVehicle;
    }

    public void clickOkButton(){
        //Save data in db
        btnOk.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String vin = editVin.getText().toString();
                    if(!isVechileNew(vin)) {
                        Toast.makeText( Vehicle.this, "Error: The vehicle is already in database. Please check the  VIN number", Toast.LENGTH_LONG).show();
                        return;
                    }
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
