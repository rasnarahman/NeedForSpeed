package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class gas_logList extends Activity {

    protected static final String ACTIVITY_NAME = "gas_logList";
    Button btnAddFuel, btnDeleteVehicle;
    DatabaseHelper databaseHelper;
    ArrayList<FuelDetail> fuelDetails;
    protected ListView list_view;
    boolean isTab;
    FrameLayout frameLayout;
    gas_logList myActivity;
    FuelDetailAdapter fuelDetailAdapter;
    EditText editTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_gas_log );

        final String currentVehicleVin = getIntent().getStringExtra("CURRENT_VEHICLE_VIN");

        editTotalCost = (EditText) findViewById( R.id.txtTotalCost );

        btnAddFuel = (Button)findViewById( R.id.buttonAddFuel );
        onClickAddFuelButton(currentVehicleVin);

        btnDeleteVehicle = (Button)findViewById( R.id.buttonDeleteVehicle );
        onClickDeleteVehicleButton(currentVehicleVin);

        databaseHelper =  new DatabaseHelper( this);
        fuelDetails = databaseHelper.getFuelDetail(currentVehicleVin);

        setTotalCost(fuelDetails);

        list_view =(ListView) findViewById(R.id.list_view);
        list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        fuelDetailAdapter = new FuelDetailAdapter(this);
        list_view.setAdapter(fuelDetailAdapter);

        frameLayout = (FrameLayout)findViewById(R.id.entryType);
        if(frameLayout == null){ //phone
            Log.i(ACTIVITY_NAME, "frame is not loaded");
            isTab = false;
        }
        else{ //tablet
            Log.i(ACTIVITY_NAME, "frame is loaded");
            isTab = true;
        }
        myActivity = this;

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
                    Fuel_fragment fragment = new Fuel_fragment();
                    fragment.setFuelfragment(myActivity);
                    Bundle bundle = new Bundle();

                    bundle.putString("CURRENT_VEHICLE_VIN",currentVehicleVin);
                    bundle.putString("PURCHSAE_DATE", purchaseDateFinal);
                    bundle.putString("ODOMETER",odometerFinal.toString());
                    bundle.putString("FUEL_PRICE",fuelPriceFinal.toString());
                    bundle.putString("FUEL_AMOUNT",fuelAmountFinal.toString());
                    fragment.setArguments(bundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.entryType, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
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

    public void setTotalCost(ArrayList<FuelDetail> fuelDetails){
        Float totalCost = 0.0f;

        if(fuelDetails.size() != 0) {
            for(FuelDetail fuelDetail: fuelDetails) {
                Float costPerTransaction = fuelDetail.getFuelAmount() * fuelDetail.getFuelPrice();
                totalCost += costPerTransaction;
            }
            editTotalCost.setText("", TextView.BufferType.EDITABLE);
            editTotalCost.setText(totalCost.toString(), TextView.BufferType.EDITABLE);
        }
        else {
            editTotalCost.setText("", TextView.BufferType.EDITABLE);
            editTotalCost.setText("0", TextView.BufferType.EDITABLE);
        }
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 10  && responseCode == 10) {
            Log.i("gas_logList", "Delete fuel info for mobile");
            Bundle bundle = data.getExtras();
            String vin = bundle.getString("CURRENT_VEHICLE_VIN");
            String purchaseDate = bundle.getString("PURCHSAE_DATE");
            Boolean deleted = databaseHelper.removeFuelRecord(vin, purchaseDate);
            if (deleted){
                Toast.makeText( gas_logList.this, "fuel info deleted successfully" , Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText( gas_logList.this, "fuel info delete  failed" , Toast.LENGTH_LONG).show();
            }
            fuelDetails = databaseHelper.getFuelDetail(vin);
            setTotalCost(fuelDetails); //update total cost
            fuelDetailAdapter.notifyDataSetChanged(); //update list
        }
    }


    public void deleteFuelInfoRecord(String vin, String purchaseDate) {
        //DatabaseHelper databaseHelper =  new DatabaseHelper( this);
        Log.i("gas_logList", "Delete fuel info for tablet");
        Boolean deleted = databaseHelper.removeFuelRecord(vin, purchaseDate);
        if (deleted){
            Toast.makeText( gas_logList.this, "fuel info deleted successfully" , Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText( gas_logList.this, "fuel info delete  failed" , Toast.LENGTH_LONG).show();
        }
        fuelDetails = databaseHelper.getFuelDetail(vin);
        setTotalCost(fuelDetails); //update total cost
        fuelDetailAdapter.notifyDataSetChanged(); //update list
    }


    protected void onDestroy(){
        super.onDestroy();
        if(databaseHelper != null ){
            databaseHelper.close();
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


    public void onClickDeleteVehicleButton(final String currentVehicleVin){
        btnDeleteVehicle.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
              Log.i("gas_logList", "Delete vehicle");

              Boolean deletedVehicle = false;
              Boolean deletedFuelRecord = false;

              if(fuelDetails.size() != 0) {
                  deletedFuelRecord = databaseHelper.removeFuelRecordAll(currentVehicleVin);
              }
                else{
                  deletedFuelRecord = true;
              }

              if(deletedFuelRecord) {
                  deletedVehicle = databaseHelper.removeVehicle(currentVehicleVin);
              }

              if (deletedFuelRecord && deletedVehicle){
                  Toast.makeText( gas_logList.this, "Vehicle deleted successfully" , Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(gas_logList.this, MainActivity.class);
                  startActivity( intent );
              }
              else {
                  Toast.makeText( gas_logList.this, "Vehicle delete  failed" , Toast.LENGTH_LONG).show();
              }
              }
          }
        );
    }
}
