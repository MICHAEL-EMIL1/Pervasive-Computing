package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText search;
    ListView list;
    ImageButton Back;
    ArrayList<String> actionNames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.search_edit_text);
        list = findViewById(R.id.search_list_view);
        Back = findViewById(R.id.search_back_btn);

        // Initialize the list of action names
        actionNames = new ArrayList<>();
        actionNames.add("Temperature");
        actionNames.add("LCD");
        actionNames.add("Led");
        actionNames.add("Keypad");


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, actionNames);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                String selectedItem = actionNames.get(position);

                Intent intent;
                switch (selectedItem) {
                    case "Temperature":
                        intent = new Intent(Search.this, Temperature.class);
                        break;
                    case "LCD":
                        intent = new Intent(Search.this, LCD.class);
                        break;
                    case "Led":
                        intent = new Intent(Search.this, Led.class);
                        break;
                    case "Keypad":
                        intent = new Intent(Search.this, Keypad.class);
                        break;
                    default:
                        // Default case if no match is found
                        Toast.makeText(Search.this, "Unknown item selected", Toast.LENGTH_SHORT).show();
                        return; // Exit the method if no matching activity is found
                }
                startActivity(intent);
            }
        });

        // Set click listener for Back button
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Search.this, home.class));
            }
        });

        // Add a TextWatcher to the search EditText to filter the list as the user types
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (String item : actionNames) {
            if (item.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}