package com.example.rasna.needforspeed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    Spinner spinnerDropDown;
    Button weatherButton;
    Button toolBarButton;
    ArrayList<String> listItems=new ArrayList<String>();
    Toolbar mToolBar;

    ArrayAdapter<String> adapter;

    private static Button btnVehicleAdd;
    String sendBackMsgToOne = "Welcome to my first android app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        dbHelper = new DatabaseHelper( this );
        onClickButtonListener();

        List<String> vechileMakeModelYearStringList = new ArrayList<String>();
        List<Vehicle> vehicleList = dbHelper.getAllVehicles();
        spinnerDropDown = (Spinner) findViewById(R.id.spinnerVehicles);
        vechileMakeModelYearStringList.add("----- Vehicles -----");

        for (Vehicle vehicle: vehicleList) {
            vechileMakeModelYearStringList.add(vehicle.Make + " " + vehicle.Model + " " + vehicle.Year + " (" + vehicle.Vin + ")");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vechileMakeModelYearStringList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDropDown.setAdapter(dataAdapter);

        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(pos != 0 ) {
                    String selectedVehicle = parent.getItemAtPosition(pos).toString();
                    Toast.makeText(parent.getContext(), "Selected Vehicle : " + selectedVehicle, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, gas_logList.class);
                    intent.putExtra("CURRENT_VEHICLE_VIN", extractVinFromVehicleInfo(selectedVehicle));
                    startActivity( intent );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);


        onClickWeather();

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
                Log.i("Toolbar", "Option3 is selected");
                LayoutInflater inflater = this.getLayoutInflater();
                //inflating the layout that contains custom message
                View view = inflater.inflate(R.layout.layout_to_signin,null);
                final TextView customText = (TextView) view.findViewById(R.id.tvInfo);
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                builder1.setView(view).setMessage(R.string.dialog_message);
                builder1.setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendBackMsgToOne = customText.getText().toString();
                    }
                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                            }
                        });

                // Create the AlertDialog object and return it
                android.support.v7.app.AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            case R.id.action_settings:
                Toast.makeText(this.getApplicationContext(), "Version 1.0, by Rasna Rahman.",
                        Toast.LENGTH_LONG).show();
            default:
                break;
            case R.id.action_home:
                Log.i("Toolbar", "Welcome to my first android app");
                Snackbar.make(findViewById(R.id.toolbar),sendBackMsgToOne, Snackbar.LENGTH_LONG).show();
                break;

            case R.id.action_two:
                Log.i("Toolbar", "Option 2 selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));

                builder.setTitle(R.string.pick_color);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;


        }
        return  true;
    }


    private String extractVinFromVehicleInfo(String vehicleInfo) {
        Pattern pattern = Pattern.compile(".*([(].*[)])");
        Matcher matcher = pattern.matcher(vehicleInfo);
        if(matcher.matches()) {
            String vin = matcher.group(1);
            return vin.substring(1, vin.length() - 1);
        }
        else {
            return "";
        }
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

    public void onClickWeather(){

        weatherButton = (Button)findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener(){

                                             @Override
                                             public void onClick(View v){
                                                 Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
                                                 startActivity(intent);

                                             }

                                         }

        );

    }

}
