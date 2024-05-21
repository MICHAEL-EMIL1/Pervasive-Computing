package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Led extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Button controlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button backButton = findViewById(R.id.arrow_icon);
        controlButton = findViewById(R.id.controlButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Led.this, home.class));
            }
        });

        // Set an initial listener to retrieve and update button text based on LED status
        setLedStatusListener();

        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the LED state and send the action to Firebase
                toggleLedStatus();
            }
        });
    }

    private void setLedStatusListener() {
        databaseReference.child("sensors").child("led-status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean isLedOn = dataSnapshot.getValue(Boolean.class);
                    updateButton(isLedOn);
                } else {
                    // Handle if the data does not exist
                    Toast.makeText(Led.this, "LED status not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any database error
                Toast.makeText(Led.this, "Failed to retrieve LED status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleLedStatus() {
        // Toggle the current LED status in Firebase
        databaseReference.child("sensors").child("led-status").setValue(controlButton.getText().toString().equalsIgnoreCase("Turn On"));
    }

    private void updateButton(boolean isLedOn) {
        if (isLedOn) {
            controlButton.setText("Turn Off");
        } else {
            controlButton.setText("Turn On");
        }
    }
}
