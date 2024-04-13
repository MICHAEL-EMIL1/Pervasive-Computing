package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Keypad extends AppCompatActivity {
    private Button arrowIcon;
    private EditText passwordEditText;
    private Button unlockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_keypad);
        arrowIcon = findViewById(R.id.arrow_icon);
        passwordEditText = findViewById(R.id.passwordEditText);
        unlockButton = findViewById(R.id.unlockButton);

        // Set up click listeners for the UI components
        arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intee = new Intent(Keypad.this, home.class);
                startActivity(intee);
            }
        });


    }
}