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

        // Initialize the adapter with the full list of actions
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, actionNames);
        list.setAdapter(adapter);

        // Set click listener for ListView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                String selectedItem = actionNames.get(position);
                // Example: You can handle the item click as needed, e.g., show a Toast
                Toast.makeText(Search.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
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