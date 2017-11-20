package com.example.rasna.needforspeed;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        dbHelper = new DatabaseHelper( this );
    }
}
