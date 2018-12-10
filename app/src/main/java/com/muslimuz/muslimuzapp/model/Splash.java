package com.muslimuz.muslimuzapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.muslimuz.muslimuzapp.activities.BerandaActivity;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, BerandaActivity.class);
        startActivity(intent);
        finish();
    }
}