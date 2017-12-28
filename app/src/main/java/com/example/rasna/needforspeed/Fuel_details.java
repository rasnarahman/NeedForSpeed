package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Fuel_details extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_details);


        final String currentVehicleVin = this.getIntent().getStringExtra("CURRENT_VEHICLE_VIN");
        final String purchaseDate = this.getIntent().getStringExtra("PURCHSAE_DATE");
        final String odometer = this.getIntent().getStringExtra("ODOMETER");
        final String fuelPrice = this.getIntent().getStringExtra("FUEL_PRICE");
        final String fuelAmount = this.getIntent().getStringExtra("FUEL_AMOUNT");

        /*Toast.makeText(getBaseContext(), currentVehicleVin, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), purchaseDate, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), odometer, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), fuelPrice, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), fuelAmount, Toast.LENGTH_SHORT).show();*/

        Fuel_fragment fragment = new Fuel_fragment();
        Bundle bundle = new Bundle();
        bundle.putString("CURRENT_VEHICLE_VIN", currentVehicleVin);
        bundle.putString("PURCHSAE_DATE", purchaseDate);
        bundle.putString("ODOMETER", odometer);
        bundle.putString("FUEL_PRICE", fuelPrice);
        bundle.putString("FUEL_AMOUNT", fuelAmount);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }
}
