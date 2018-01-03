package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ActivityDataAdapter extends ArrayAdapter<ActivitySetting> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<ActivitySetting> mSettings;

    public ActivityDataAdapter(Context context, int resourceId, List<ActivitySetting> settings) {
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

        ActivitySetting setting = mSettings.get(position);

        TextView tvDay = convertView.findViewById(R.id.tv_day);
        tvDay.setText(setting.iday);

        TextView tvTime = convertView.findViewById(R.id.tv_time);
        tvTime.setText(String.format("%d:%d", setting.ihour, setting.imin));

        TextView tvComment = convertView.findViewById(R.id.tv_comment);
        tvComment.setText(setting.icomment);



        /*parent.setBackgroundColor(parent.getResources().getColor(R.color.white));
        if (position % 2 == 0)
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.LightOrange));*/

        return convertView;
    }
}
