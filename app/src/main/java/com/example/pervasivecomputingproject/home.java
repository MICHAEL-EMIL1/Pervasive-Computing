package com.example.pervasivecomputingproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
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
    private ArrayList<Actions> actionsListFull; // This will hold the full list of actions
    ActionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionsList = DataUtils.getActionsData(this);
        actionsListFull = new ArrayList<>(actionsList); // Initialize the full list

        adapter = new ActionsListAdapter(actionsList, this::onItemClick);
        adapter.setList(actionsList);

        RecyclerView recyclerView = findViewById(R.id.actionsrecyc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuuu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            View actionView = searchItem.getActionView();
            if (actionView != null && actionView instanceof SearchView) {
                SearchView searchView = (SearchView) actionView;
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText); // Use the custom filter
                        return true;
                    }
                });
            }
        }
        return true;
    }


    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Log.d("MenuSelection", "Item selected: " + item.getItemId());

        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_log_file) {
            Intent intent = new Intent(this, Log_File.class);
            startActivity(intent);
            return true;

        }
        if (id == R.id.action_log_out) {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }



    public void onItemClick(int position) {
        // Start the corresponding activity based on the clicked position
        Actions clickedAction = actionsList.get(position);
        Class<?> activityClass = clickedAction.getActivityClass();
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
    public void filterActionsByQuery(String newText) {
        ArrayList<Actions> filteredList = filterActions(actionsList, newText);
        adapter.setList(filteredList);
    }

    private ArrayList<Actions> filterActions(ArrayList<Actions> actions, String query) {
        ArrayList<Actions> filteredList = new ArrayList<>();
        for (Actions action : actions) {
            if (action.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(action);
            }
        }
        return filteredList;
    }
}
