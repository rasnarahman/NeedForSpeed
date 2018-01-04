package com.example.rasna.needforspeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Admin on 1/3/2018.
 */

public class NavdeepMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final Toolbar m_toolbar = findViewById(R.id.m_toolbar);
        setSupportActionBar(m_toolbar);

        setButtonListeners();
    }

    private void setButtonListeners() {

        ImageView nutritionButton = findViewById(R.id.m_ivFood);
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavdeepMain.this, NavNutriTrackAct.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.m_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()){



            case R.id.menu_food:
                startActivity(new Intent(NavdeepMain.this, NavNutriTrackAct.class));
                break;




        }
        return true;
    }
}












