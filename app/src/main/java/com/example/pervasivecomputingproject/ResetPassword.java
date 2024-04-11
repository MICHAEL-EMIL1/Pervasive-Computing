package com.example.pervasivecomputingproject;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        Button cont = findViewById(R.id.confirm);
        EditText NewPassword;
        EditText ConfirmPassword;
        NewPassword = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmpassword);


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPassword.this, SignIn.class));
            }
        });
    }
}