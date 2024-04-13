package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile extends AppCompatActivity {
    // private ImageView userProfileImage;
    private TextView userNameTextView;
    private Button logoutButton;

    private  Button Arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Arrow = findViewById(R.id.arrow_icon);
        //  userProfileImage = findViewById(R.id.male_user_ek1);
        userNameTextView = findViewById(R.id.rewan_wael);
        logoutButton = findViewById(R.id.Logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,SignIn.class);
                startActivity(i);
            }
        });
        Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, home.class);
                startActivity(i);
            }
        });
    }
}