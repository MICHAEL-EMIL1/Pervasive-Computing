package com.example.pervasivecomputingproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Regestiration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regestiration);
        EditText username = findViewById(R.id.sign_up_username);
        EditText email = findViewById(R.id.sign_up_email);
        EditText name = findViewById(R.id.name);
        EditText birthdate = findViewById(R.id.sign_up_birthdate);
        EditText password = findViewById(R.id.sign_up_password);
        ImageButton calenderBtn = findViewById(R.id.sign_up_calender_btn);
        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Regestiration.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;

                        String date = month + "/" + day + "/" + year;
                        birthdate.setText(date);
                    }
                },
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        Button SignUpButton = findViewById(R.id.sign_up_sign_up_btn);
        Button toSignIn = findViewById(R.id.sign_up_sign_in_btn);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Name = name.getText().toString();
                String Birthdate = birthdate.getText().toString();
                String Password = password.getText().toString();


                if (Username.equals("") || Email.equals("") || Birthdate.equals("") || Password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing fields.", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Regestiration.this, home.class));
            }
        });

        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Regestiration.this, SignIn.class));
            }
        });
    }
}