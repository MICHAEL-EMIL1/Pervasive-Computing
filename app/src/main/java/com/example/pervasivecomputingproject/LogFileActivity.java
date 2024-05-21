package com.example.pervasivecomputingproject;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogFileActivity extends AppCompatActivity {

    private DatabaseReference sensorsRef;

    private TextView ledStatusValueTextView;
    private TextView messageAddedByValueTextView;
    private TextView messageContentValueTextView;
    private TextView messageTimestampValueTextView;
    private TextView passwordAddedByValueTextView;
    private TextView passwordValueTextView;
    private TextView passwordTimestampValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        sensorsRef = database.getReference("sensors");

        // Initialize TextViews
        ledStatusValueTextView = findViewById(R.id.ledStatusValueTextView);
        messageAddedByValueTextView = findViewById(R.id.messageAddedByValueTextView);
        messageContentValueTextView = findViewById(R.id.messageContentValueTextView);
        messageTimestampValueTextView = findViewById(R.id.messageTimestampValueTextView);
        passwordAddedByValueTextView = findViewById(R.id.passwordAddedByValueTextView);
        passwordValueTextView = findViewById(R.id.passwordValueTextView);
        passwordTimestampValueTextView = findViewById(R.id.passwordTimestampValueTextView);

        // Read data from Firebase
        readDataFromFirebase();
    }

    private void readDataFromFirebase() {
        sensorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate over each child node under "sensors"
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    switch (key) {
                        case "led-status":
                            updateTextView(ledStatusValueTextView, childSnapshot.getValue());

                            break;
                        case "message":
                            updateMessageViews(childSnapshot);
                            break;
                        case "password":
                            updatePasswordViews(childSnapshot);
                            break;
                        default:
                            // Handle unrecognized data if needed
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("Failed to read data from Firebase");
            }
        });
    }

    private void updateTextView(TextView textView, Object value) {
        if (value!= null) {
            if (value instanceof Boolean) {
                // Convert Boolean to String
                textView.setText(value.toString());
            } else {
                // For other types, just set the value directly
                textView.setText(value.toString());
            }
        } else {
            textView.setText("N/A");
            showToast("Data not found in Firebase");
        }
    }


    private void updateMessageViews(DataSnapshot messageSnapshot) {
        String addedBy = messageSnapshot.child("addedBy").getValue(String.class);
        String messageContent = messageSnapshot.child("message").getValue(String.class);
        String timestamp = messageSnapshot.child("timestamp").getValue(String.class);

        if (addedBy!= null && messageContent!= null && timestamp!= null) {
            // Fetch the user profile using the addedBy user ID
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(addedBy);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                    // Extract the username from the user profile
                    String username = userSnapshot.child("username").getValue(String.class);

                    // Update the TextViews
                    updateTextView(messageAddedByValueTextView, username);
                    updateTextView(messageContentValueTextView, messageContent);
                    updateTextView(messageTimestampValueTextView, timestamp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to fetch user profile");
                }
            });
        } else {
            // Handle null values gracefully
            showToast("Message data fields are null in Firebase");
        }
    }




    private void updatePasswordViews(DataSnapshot passwordSnapshot) {
        String addedBy = passwordSnapshot.child("addedBy").getValue(String.class);
        String passwordValue = passwordSnapshot.child("password").getValue(String.class);
        String timestamp = passwordSnapshot.child("timestamp").getValue(String.class);

        if (addedBy!= null && passwordValue!= null && timestamp!= null) {
            // Fetch the user profile using the addedBy user ID
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(addedBy);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                    // Extract the username from the user profile
                    String username = userSnapshot.child("username").getValue(String.class);

                    // Update the TextViews
                    updateTextView(passwordAddedByValueTextView, username);
                    updateTextView(passwordValueTextView, passwordValue);
                    updateTextView(passwordTimestampValueTextView, timestamp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to fetch user profile");
                }
            });
        } else {
            // Handle null values gracefully
            showToast("Password data fields are null in Firebase");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
