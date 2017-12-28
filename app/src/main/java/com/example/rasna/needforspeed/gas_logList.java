package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class gas_logList extends Activity {
    Button btnAddFuel;
    DatabaseHelper databaseHelper;
    ArrayList<FuelDetail> fuelDetails;
    protected ListView list_view;
    boolean isTab;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_log );
        final String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");

        btnAddFuel = (Button)findViewById( R.id.buttonAddFuel );
        onClickAddFuelButton(currentVehicleVin);

        databaseHelper =  new DatabaseHelper( this);
        fuelDetails = databaseHelper.getFuelDetail(currentVehicleVin);

        list_view =(ListView) findViewById(R.id.list_view);
        list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final FuelDetailAdapter fuelDetailAdapter = new FuelDetailAdapter(this);
        list_view.setAdapter(fuelDetailAdapter);

        frameLayout = (FrameLayout)findViewById(R.id.entryType);
        if(frameLayout == null){ //phone
            isTab = false;
        }
        else{ //tablet
            isTab = true;
        }

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView <?> parentAdapter, View view, int position, long id) {
                Object o = fuelDetailAdapter.getItem(position);
                FuelDetail fuelDetail = (FuelDetail) o;
                final Integer odometerFinal = fuelDetail.getOdometer();
                final Float fuelPriceFinal = fuelDetail.getFuelPrice();
                final Integer fuelAmountFinal = fuelDetail.getFuelAmount();
                final String purchaseDateFinal = fuelDetail.getPurchaseDate();

                if(isTab){
                    // if the app is running on a tablet
                    /*MessageFragment fragment = new MessageFragment();
                    //   fragment.setChatWindow(ownActivity);
                    fragment.setChatWindow(myActivity);
                    Bundle bundle = new Bundle();
                    bundle.putString("chatMsg", s);
                    bundle.putInt("Id",position);
                    //bundle.putLong("dbId",id);
                    fragment.setArguments(bundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.entryType, fragment);
                    ft.addToBackStack(null);
                    ft.commit();*/
                }
                /* sending the activity to the newly created MessageDetails class */
                else{
                    Intent intent = new Intent(getApplicationContext(), Fuel_details.class);
                    intent.putExtra("CURRENT_VEHICLE_VIN",currentVehicleVin);
                    intent.putExtra("PURCHSAE_DATE", purchaseDateFinal);
                    intent.putExtra("ODOMETER",odometerFinal.toString());
                    intent.putExtra("FUEL_PRICE",fuelPriceFinal.toString());
                    intent.putExtra("FUEL_AMOUNT",fuelAmountFinal.toString());
                    startActivityForResult(intent, 10);
                }

            }
        });
    }


    public void deleteFuelInfoRecord(String vin, String purchaseDate) {
        Toast.makeText( gas_logList.this, "we are here" , Toast.LENGTH_LONG).show();
        DatabaseHelper databaseHelper =  new DatabaseHelper( this);
        Boolean deleted = databaseHelper.removeFuelRecord(vin, purchaseDate);
        if (deleted){
            Toast.makeText( gas_logList.this, "fuel info deleted successfully" , Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText( gas_logList.this, "fuel info deleted  failed" , Toast.LENGTH_LONG).show();
        }
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
