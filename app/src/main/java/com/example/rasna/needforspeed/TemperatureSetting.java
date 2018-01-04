package com.example.rasna.needforspeed;

public class TemperatureSetting {
    public Integer mId;
    public String mDay;
    public Integer mHr;
    public Integer mMin;
    public Integer mTemperature;
    public Integer mLocation;

    public TemperatureSetting(Integer id, Integer location, String day, Integer hr, Integer min, Integer temperature) {
        mId = id;
        mDay = day;
        mHr = hr;
        mMin = min;
        mTemperature = temperature;
        mLocation = location;
    }
}
