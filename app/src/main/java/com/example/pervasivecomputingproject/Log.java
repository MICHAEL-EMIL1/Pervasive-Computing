package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pervasivecomputingproject.home;

public class Log extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        ImageButton back_arrow = findViewById(R.id.back);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Log.this, home.class));
            }
        });

        // Find the TableLayout in the layout
        TableLayout tableLayout = findViewById(R.id.tableLayout);



        // You can continue adding more rows as needed
    }

    // Helper method to add a TableRow with TextViews to the TableLayout
    private void addRow(TableLayout tableLayout, String activity, String action, String time) {
        TableRow row = new TableRow(this);

        // Create TextViews for each column
        TextView activityTextView = createTextView(activity);
        TextView actionTextView = createTextView(action);
        TextView timeTextView = createTextView(time);

        // Add TextViews to the TableRow
        row.addView(activityTextView);
        row.addView(actionTextView);
        row.addView(timeTextView);

        // Add the TableRow to the TableLayout
        tableLayout.addView(row);
    }

    // Helper method to create a TextView with common properties
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(17);
        return textView;
    }
}