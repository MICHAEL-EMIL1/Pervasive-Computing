package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpass extends AppCompatActivity {
    FirebaseAuth mAuth;

    String Email; // Class-level variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        EditText email = findViewById(R.id.forget_password_email);
        Button sendEmail = findViewById(R.id.forget_password);
        mAuth = FirebaseAuth.getInstance();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString(); // Use the class-level variable
                if (Email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing Email.", Toast.LENGTH_SHORT).show();
                } else {
                    Resetpassword();
                }
            }
        });
    }

    private void Resetpassword() {
        mAuth.sendPasswordResetEmail(Email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Forgetpass.this, "Email sent", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Forgetpass.this, SignIn.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Forgetpass.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
           });
}
}