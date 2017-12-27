package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import java.util.ArrayList;
import java.util.List;

public class gas_logList extends Activity {
    Button btnAddFuel;
    DatabaseHelper databaseHelper;
    ArrayList<FuelDetail> fuelDetails;
    protected ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_log );
        String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");

        btnAddFuel = (Button)findViewById( R.id.buttonAddFuel );
        onClickAddFuelButton(currentVehicleVin);

        databaseHelper =  new DatabaseHelper( this);
        fuelDetails = databaseHelper.getFuelDetail(currentVehicleVin);

        list_view =(ListView) findViewById(R.id.list_view);
        final FuelDetailAdapter fuelDetailAdapter = new FuelDetailAdapter(this);
        list_view.setAdapter(fuelDetailAdapter);
    }

    private class FuelDetailAdapter extends ArrayAdapter<FuelDetail> {

        public FuelDetailAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return fuelDetails.size();
        }

        public FuelDetail getItem(int position) {
            return fuelDetails.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = gas_logList.this.getLayoutInflater();

            View result = null;

            result = inflater.inflate(R.layout.automobile_row, null);

            FuelDetail fuelDetail = getItem(position);
            String date= fuelDetail.getPurchaseDate();
            String fuelPrice = Float.toString(fuelDetail.getFuelPrice());
            Integer odometer = fuelDetail.getOdometer();
            Integer fuelAmount = fuelDetail.getFuelAmount();


            TextView d = result.findViewById(R.id.txtDate);
            d.setText(String.valueOf(date) ); // get the string at position
            TextView l = result.findViewById(R.id.txtOdometer);
            l.setText(String.valueOf(odometer)); // get the string at position
            TextView p = result.findViewById(R.id.txtFuelPrice);
            p.setText(String.valueOf(fuelPrice)); // get the string at position
            TextView k = result.findViewById(R.id.txtFuelAmount);
            k.setText(String.valueOf(fuelAmount)); // get the string at position

            Log.i("Automobile", "Create view");

            return result;
        }
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
