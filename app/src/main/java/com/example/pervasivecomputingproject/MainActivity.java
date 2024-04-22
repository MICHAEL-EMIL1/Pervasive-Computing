package com.example.pervasivecomputingproject;

import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();




        handler.postDelayed(new Runnable() {
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(new Intent(MainActivity.this, Regestiration.class));
            }
        }, 6000);
    }
}