package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Vehicle extends Activity {

    EditText editVin, editMake, editModel, editYear, editTankSize;
    Button btnOk,btnView,btnCancel ;
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
        btnView = (Button)findViewById( R.id.buttonView );
        spFuelType = (Spinner)findViewById( R.id.spinnerFuelType );
        clickOkButton();
        viewAll();
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

    public void viewAll() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = databaseHelper.getVehicleInfo();
                        if (res.getCount() == 0) {
                            showMessage( "Error", "No data found" );
                            return;
                        }


                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {

                            buffer.append( "VIN:  " + res.getString( 0 ) + "\n" );
                            buffer.append( "Make:  " + res.getString( 1 ) + "\n" );
                            buffer.append( "Model:  " + res.getString( 2 ) + "\n" );
                            buffer.append( "Year:  " + res.getString( 3 ) + "\n" );
                            buffer.append( "FuelType:  " + res.getString( 4 ) + "\n" );
                            buffer.append( "TankSize:  " + res.getString( 5 ) + "\n\n" );
                        }

                      showMessage( "Data", buffer.toString() );
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
