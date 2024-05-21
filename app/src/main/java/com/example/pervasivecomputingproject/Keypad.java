package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Keypad extends AppCompatActivity {

    private EditText passwordEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        passwordEditText = findViewById(R.id.passwordEditText);
        submitButton = findViewById(R.id.unlockButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPassword();
            }
        });
    }

    private void submitPassword() {
        String password = passwordEditText.getText().toString().trim();

        // Regular expression to check if the password contains only digits and has at least 4 characters
        if (password.matches("\\d{4,}")) {
            // Define the key for saving password under the "sensors" parent
            String passwordKey = "password";

            // Create a map to store password, timestamp, and user info
            Map<String, Object> passwordMap = new HashMap<>();
            passwordMap.put("password", password);
            passwordMap.put("timestamp", getCurrentDateTime()); // Save current timestamp
            passwordMap.put("addedBy", firebaseAuth.getCurrentUser().getUid());// Save current user ID


            // Save the password data along with metadata to Firebase under the "sensors" parent
            databaseReference.child("sensors").child(passwordKey).setValue(passwordMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Keypad.this, "Password saved successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Keypad.this, "Failed to save password", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Display a toast message indicating the password requirements
            Toast.makeText(Keypad.this, "Password must contain only numbers and be at least 4 characters long", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentDateTime() {
        // Get current date and time in a desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}