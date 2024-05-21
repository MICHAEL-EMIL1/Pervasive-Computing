package com.example.pervasivecomputingproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Temperature extends AppCompatActivity {

    private TextView temperatureTextView;

    private DatabaseReference temperatureRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temperatureTextView = findViewById(R.id.temperatureTextView);

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        temperatureRef = database.getReference("sensors").child("temperature");

        // Listen for initial temperature value
        listenForTemperatureUpdates();

        Button refreshButton = findViewById(R.id.refreshButtton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manually refresh temperature value
                listenForTemperatureUpdates();
            }
        });
    }

    private void listenForTemperatureUpdates() {
        // Read temperature value once
        temperatureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String temperature = dataSnapshot.getValue(String.class);
                    if (temperature != null) {
                        // Update UI to display the temperature value
                        temperatureTextView.setText("Current Temperature: " + temperature + "°C");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(Temperature.this, "Failed to read temperature value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTemperature(String newTemperature) {
        // Update temperature value in Firebase
        temperatureRef.setValue(newTemperature)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Temperature.this, "Temperature updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Temperature.this, "Failed to update temperature", Toast.LENGTH_SHORT).show();
                });
    }
}
