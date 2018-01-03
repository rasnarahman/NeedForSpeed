package com.example.rasna.needforspeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity {

    Button RasnaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_welcome );
        onClickRasna();
    }





    public void onClickRasna(){
        RasnaButton = (Button)findViewById(R.id.RasnaButton);
        RasnaButton.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View v){
                                                 Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                                 startActivity( intent );
                                             }
                                         }
        );
    }



}
