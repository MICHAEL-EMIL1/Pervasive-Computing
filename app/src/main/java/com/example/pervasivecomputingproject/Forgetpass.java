package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Forgetpass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpass);
        EditText email = findViewById(R.id.forget_password_email);
        Button sendEmail=findViewById(R.id.forget_password) ;
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                if (Email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing Email.", Toast.LENGTH_SHORT).show();
                } else {
                    // Input is valid, proceed to home page
                    Intent i = new Intent(Forgetpass.this, ResetPassword.class);
                    startActivity(i);
                    finish(); // Optionally finish SignIn activity to prevent going back when pressing back button
                }
            }
        });

    }
}