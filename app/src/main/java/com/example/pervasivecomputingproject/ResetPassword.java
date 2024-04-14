package com.example.pervasivecomputingproject;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        Button cont = findViewById(R.id.confirm);
        EditText newPassword = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmpassword);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NewPassword = newPassword.getText().toString();
                String ConfirmPassword = confirmPassword.getText().toString();
                if (NewPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing NewPassword.", Toast.LENGTH_SHORT).show();
                }else if (ConfirmPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing Confirm NewPassword.", Toast.LENGTH_SHORT).show();
                }else if (!NewPassword.equals(ConfirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }else {
                    // Input is valid, proceed to home page
                    //Toast.makeText(getApplicationContext(), "Password is equal " + NewPassword , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ResetPassword.this, SignIn.class);
                    startActivity(i);
                    finish(); // Optionally finish SignIn activity to prevent going back when pressing back button
                }
            }
        });
    }
}