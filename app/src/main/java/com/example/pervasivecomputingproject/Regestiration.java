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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Regestiration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestiration);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        DBhelper dbHelper = new DBhelper(this);

        EditText username = findViewById(R.id.sign_up_username);
        EditText email = findViewById(R.id.sign_up_email);
        EditText name = findViewById(R.id.name);
        EditText birthdate = findViewById(R.id.sign_up_birthdate);
        EditText confpassword = findViewById(R.id._password);
        EditText passwordEditText = findViewById(R.id.sign_up_password);
        ImageButton calenderBtn = findViewById(R.id.sign_up_calender_btn);

        // Set password visibility toggle
        confpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confpassword.getRight() - confpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        confpassword.setInputType(confpassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ?
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        return true;
                    }
                }
                return false;
            }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        passwordEditText.setInputType(passwordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ?
                                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD :
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
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
                String Confpassword = confpassword.getText().toString();

                if (Name.equals("") || Username.equals("") || Email.equals("") || Birthdate.equals("") || Password.equals("") || Confpassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(Confpassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(Regestiration.this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    if (firebaseUser != null) {
                                        String userId = firebaseUser.getUid();
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("name", Name);
                                        userData.put("username", Username);
                                        userData.put("email", Email);
                                        userData.put("birthdate", Birthdate);

                                        // Add more fields as needed
                                        mDatabase.child(userId).setValue(userData);

                                        // Store in SQLite database
                                        boolean inserted = dbHelper.insertUserData(userId, Name, Username, Email, Password, Birthdate);
                                        if (inserted) {
                                            Toast.makeText(Regestiration.this, "User data saved in SQLite database.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Regestiration.this, "Failed to save user data in SQLite database.", Toast.LENGTH_SHORT).show();
                                        }

                                        Toast.makeText(Regestiration.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Regestiration.this, home.class);
                                        startActivity(i);
                                        finish();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Regestiration.this, "Authentication failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Regestiration.this, SignIn.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Toast.makeText(this, "Image selected: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}