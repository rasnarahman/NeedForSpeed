package com.example.rasna.needforspeed;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Admin on 1/3/2018.
 */

public class NavdeepNutritionFragment extends Fragment {

    private Activity myActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String id = getArguments().getString("id");
        final String name = getArguments().getString("item");
        final String calories = getArguments().getString("calories");
        final String carbs = getArguments().getString("carbs");
        final String fat = getArguments().getString("fat");
        final String comment = getArguments().getString("comment");

        View view = inflater.inflate(R.layout.navdeep_nutrition_details, container, false);

        TextView detailsName = view.findViewById(R.id.nutrition_detail_name_value);
        TextView detailsCalories = view.findViewById(R.id.nutrition_detail_calories_value);
        TextView detailsCarbs = view.findViewById(R.id.nutrition_detail_carbs_value);
        TextView detailsFat = view.findViewById(R.id.nutrition_detail_fat_value);
        TextView detailsComment = view.findViewById(R.id.nutrition_detail_comment_value);

        Button btnEdit = view.findViewById(R.id.nutrition_detail_edit_button);
        Button btnClose = view.findViewById(R.id.nutrition_detail_cancel);
        Button btnDelete = view.findViewById(R.id.nutrition_detail_delete);

        detailsName.setText(name);
        detailsCalories.setText(calories);
        detailsCarbs.setText(carbs);
        detailsFat.setText(fat);
        detailsComment.setText(comment);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.getFragmentManager().beginTransaction().remove(NavdeepNutritionFragment.this).commit();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myActivity.getFragmentManager().beginTransaction().remove(NavdeepNutritionFragment.this).commit();
                ((NavNutriTrackAct) myActivity).editItem(Integer.parseInt(id));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myActivity.getFragmentManager().beginTransaction().remove(NavdeepNutritionFragment.this).commit();
                ((NavNutriTrackAct) myActivity).deleteItem(Integer.parseInt(id));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.myActivity = activity;
    }



}
