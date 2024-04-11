package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Led extends AppCompatActivity {
    private boolean isLedOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        ImageButton backButton = findViewById(R.id.arrow_icon);
        Button controlButton = findViewById(R.id.controlButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Led.this,home.class);
                startActivity(i);
            }
        });
    }

    private void sendBooleanValueToCloud(boolean value) {
        // Code to send the boolean value to the cloud for storage
        // This could involve making a network request to a cloud service
        // and storing the boolean value in a database or cloud storage
    }
}