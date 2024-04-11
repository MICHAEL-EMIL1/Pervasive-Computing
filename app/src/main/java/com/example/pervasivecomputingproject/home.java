package com.example.pervasivecomputingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Recyclerview.ActionsListAdapter;

public class home extends AppCompatActivity {
    private ArrayList<Actions> actionsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // ImageButton profile = findViewById(R.id.profile);
        ImageButton log_file = findViewById(R.id.log_file);
        ImageButton serch = findViewById(R.id._search_btn);
        ImageButton logout = findViewById(R.id.log_out);
        ImageButton profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, Profile.class));
            }
        });


        log_file.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, Log.class));
            }
        });


        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, Search.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, SignIn.class));
            }
        });



        actionsList = DataUtils.getActionsData(this);

        ActionsListAdapter adapter = new ActionsListAdapter(this::onItemClick);
        adapter.setList(actionsList);

        RecyclerView recyclerView = findViewById(R.id.actionsrecyc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onItemClick(int position) {
        // Start the corresponding activity based on the clicked position
        Actions clickedAction = actionsList.get(position);
        Class<?> activityClass = clickedAction.getActivityClass();
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}