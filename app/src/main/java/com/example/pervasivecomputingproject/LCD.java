package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LCD extends AppCompatActivity {

    private EditText messageEditText;
    private Button sendButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcd);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToFirebase();
            }
        });
    }

    private void sendMessageToFirebase() {
        String messageText = messageEditText.getText().toString().trim();

        // Check if the message is not empty
        if (!messageText.isEmpty()) {
            // Define a unique key for saving the message entry
            String messageKey = "message";

            // Create a map to store message data along with metadata
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("message", messageText);
            messageMap.put("timestamp", getCurrentDateTime()); // Save current timestamp
            messageMap.put("addedBy", firebaseAuth.getCurrentUser().getUid()); // Save current user ID

            // Save the message data along with metadata to Firebase under the "messages" parent
            databaseReference.child("sensors").child(messageKey).setValue(messageMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(LCD.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                        messageEditText.setText(""); // Clear EditText after sending message
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(LCD.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Display a toast message if the message is empty
            Toast.makeText(LCD.this, "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentDateTime() {
        // Get current date and time in a desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
