package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class TemperatureDataAdapter extends ArrayAdapter<TemperatureSetting> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<TemperatureSetting> mSettings;

    public TemperatureDataAdapter(Context context, int resourceId, List<TemperatureSetting> settings) {
        super(context, resourceId, settings);
        mContext = context;
        mLayoutResourceId = resourceId;
        mSettings = settings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
        }

        TemperatureSetting setting = mSettings.get(position);

        TextView tvDay = convertView.findViewById(R.id.tv_day);
        tvDay.setText(setting.mDay);

        TextView tvTime = convertView.findViewById(R.id.tv_time);
        tvTime.setText(String.format("%d:%d", setting.mHr, setting.mMin));

        TextView tvTemp = convertView.findViewById(R.id.tv_temp);
        tvTemp.setText(Math.round(setting.mTemperature) + "\u00B0");

        parent.setBackgroundColor(parent.getResources().getColor(R.color.white));
        if (position % 2 == 0)
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.lightOrange));

        return convertView;
    }
}
