package com.example.datossesion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_close );

        finishAffinity(); System.exit(0); finish ();
    }
}