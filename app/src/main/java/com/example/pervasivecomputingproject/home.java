package com.example.pervasivecomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Recyclerview.ActionsListAdapter;

public class home extends AppCompatActivity {

    private ArrayList<Actions> actionsList;
    private ActionsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionsList = DataUtils.getActionsData(this);

        adapter = new ActionsListAdapter(actionsList, this::onItemClick);

        RecyclerView recyclerView = findViewById(R.id.actionsrecyc);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuuu, menu);
        MenuItem searchItem = menu.findItem(R.id.seeeearch);
        if (searchItem != null) {
            View actionView = searchItem.getActionView();
            if (actionView instanceof SearchView) {
                SearchView searchView = (SearchView) actionView;
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.filter(newText);
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

        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_log_file) {
            Intent intent = new Intent(this, LogFileActivity.class);
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

    private void filterr(String text) {
        ArrayList<Actions> filteredList = new ArrayList<>();
        for (Actions item : actionsList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.setList(filteredList);
    }


    public void onItemClick(int position) {
        Actions clickedAction = actionsList.get(position);
        Class<?> activityClass = clickedAction.getActivityClass();
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
