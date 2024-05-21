package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    // private ImageView userProfileImage;
    private TextView userNameTextView;
    private Button logoutButton;

    private  Button Arrow;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Arrow = findViewById(R.id.arrow_icon);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        db = new DBhelper(this);
        // userProfileImage = findViewById(R.id.male_user_ek1);
        userNameTextView = findViewById(R.id.rewan_wael);
        logoutButton = findViewById(R.id.Logout);
        // Load user data from Firebase
        loadUserData();

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
    private void loadUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("username").getValue(String.class);
                        //  String email = dataSnapshot.child("email").getValue(String.class);

                        // Set the retrieved data to the respective TextViews
                        userNameTextView.setText(name);
                    } else {
                        Toast.makeText(Profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Profile.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(Profile.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}