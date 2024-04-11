package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        Button login = findViewById(R.id.sign_in_login_btn);
        Button signUp = findViewById(R.id.sign_in_sign_up_btn);
        Button forgetPassword = findViewById(R.id.sign_in_forget_password);
        CheckBox rememberMeCheckBox = findViewById(R.id.sign_up_remember_me);
        TextView em = findViewById(R.id.sign_in_email);
        String email = em.getText().toString();
        TextView pass = findViewById(R.id.sign_in_password);
        String password = pass.getText().toString();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("rememberMe", "");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, Regestiration.class);
                startActivity(i);
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this, Forgetpass.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this,home.class);
                startActivity(i);
            }
        });
    }
}