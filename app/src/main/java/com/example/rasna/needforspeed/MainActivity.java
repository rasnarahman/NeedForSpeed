package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    DatabaseHelper dbHelper;
    Spinner spinnerDropDown;
    ArrayList<String> listItems=new ArrayList<String>();

    ArrayAdapter<String> adapter;

    private static Button btnVehicleAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        dbHelper = new DatabaseHelper( this );
        onClickButtonListener();

        List<String> vechileMakeModelYearStringList = new ArrayList<String>();
        List<Vehicle> vehicleList = dbHelper.getAllVehicles();
        spinnerDropDown = (Spinner) findViewById(R.id.spinnerVehicles);

        for (Vehicle vehicle: vehicleList) {
            vechileMakeModelYearStringList.add(vehicle.Make + " - " + vehicle.Model + " - " + vehicle.Year);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vechileMakeModelYearStringList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDropDown.setAdapter(dataAdapter);
    }

    private void setListAdapter(ArrayAdapter<String> adapter) {
    }


    public void onClickButtonListener(){
        btnVehicleAdd = (Button)findViewById( R.id.buttonVehicleAdd );
        btnVehicleAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( MainActivity.this,Vehicle.class );
                      startActivity( intent );

                    }
                }
        );
    }



}
