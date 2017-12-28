package com.example.rasna.needforspeed;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Fuel_fragment extends Fragment  {

    TextView txtVin, txtOdometer, txtPurchaseDate, txtPrice, txtAmount;
    Button deleteBtn;
    String myMsg;
    int myId;
    long dbID;
    gas_logList gasLogList;
    String vin, odometer, fuelPrice, fuelAmount, purchaseDate;

    public Fuel_fragment() {
        //default constructor
    }

    public static Fuel_fragment newInstance()
    {
        Fuel_fragment myFragment = new Fuel_fragment();
        return myFragment;
    }

    public void setChatWindow(  gas_logList gasLogList){
        this.gasLogList = gasLogList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vin = bundle.getString("CURRENT_VEHICLE_VIN");
            odometer = bundle.getString("ODOMETER");
            purchaseDate = bundle.getString("PURCHSAE_DATE");
            fuelPrice = bundle.getString("FUEL_PRICE");
            fuelAmount = bundle.getString("FUEL_AMOUNT");
        }
    }



    @Override

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_fuel_fragment, container, false);

        txtVin = (TextView) view.findViewById(R.id.txtVin);
        txtVin.setText(vin);

        txtOdometer = (TextView) view.findViewById(R.id.txtOdometer);
        txtOdometer.setText(odometer);

        txtPurchaseDate = (TextView) view.findViewById(R.id.txtPurchaseDate);
        txtPurchaseDate.setText(purchaseDate);

        txtPrice = (TextView) view.findViewById(R.id.txtFuelPrice);
        txtPrice.setText(fuelPrice);

        txtAmount = (TextView) view.findViewById(R.id.txtFuelAmount);
        txtAmount.setText(fuelAmount);

        deleteBtn = (Button) view.findViewById(R.id.deleteFuelRecord);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vin != null){
                    //tablet
                    Toast.makeText( getActivity(), vin , Toast.LENGTH_LONG).show();
                    Toast.makeText( getActivity(), purchaseDate , Toast.LENGTH_LONG).show();
                    gasLogList.deleteFuelInfoRecord(vin, purchaseDate);
                    getActivity().getFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText( getActivity(), "in else block" , Toast.LENGTH_LONG).show();
                    Toast.makeText( getActivity(), purchaseDate , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("deleteMsgId", myId);
                    getActivity().setResult(10, intent);
                    getActivity().finish();
                }
            }

        });

        return view;
    }

}

