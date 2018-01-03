package com.example.rasna.needforspeed;

import java.net.Inet4Address;
import java.util.Date;

/**
 * Created by rasna on 2017-12-25.
 */

public class FuelDetail {
    String Vin;
    String Unit;
    Integer Odometer;
    Float FuelPrice;
    Integer FuelAmount;
    String PurchaseDate;

    public FuelDetail(String vin, String unit, Integer odometer, Float fuelPrice, Integer fuelAmount, String date) {
        Vin = vin;
        Unit = unit;
        Odometer = odometer;
        FuelPrice = fuelPrice;
        FuelAmount = fuelAmount;
        PurchaseDate = date;
    }

    public Integer getOdometer() {
        return Odometer;
    }

    public Float getFuelPrice() {
        return FuelPrice;
    }

    public Integer getFuelAmount() {
        return  FuelAmount;
    }

    public String getPurchaseDate() {
        return  PurchaseDate;
    }
}
