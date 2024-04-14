package com.example.pervasivecomputingproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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
        //EditText password = findViewById(R.id.sign_up_password);
        ImageButton calenderBtn = findViewById(R.id.sign_up_calender_btn);


        EditText passwordEditText = findViewById(R.id.sign_up_password);
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_icon, 0);
                        } else {
                            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_icon, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });






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
                String Password = passwordEditText.getText().toString();
                Button uploadd = findViewById(R.id.upload_pic_btn);
                uploadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                    }
                });

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
        Button uploadd = findViewById(R.id.upload_pic_btn);
        uploadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // You can now use the selectedImageUri to display the image or upload it
            Toast.makeText(this, "Image selected: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
 }
    }
}