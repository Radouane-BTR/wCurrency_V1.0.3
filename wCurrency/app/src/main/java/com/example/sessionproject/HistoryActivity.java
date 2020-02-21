package com.example.sessionproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Data.DBAdapter;
import Data.RegistreHistory;
import model.History;

public class HistoryActivity extends AppCompatActivity {

    private DBAdapter dbAdapter;
    private ListView lvHistory;
    private ArrayAdapter<History> adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setWidgets();
        setListners();
        dbAdapter = new DBAdapter(getApplicationContext());
        lvHistory.setAdapter(adapter);
        loadList();
    }

    private void setListners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mConvert:
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainIntent);
                        break;
                }
                return true;
            }
        });
    }

    private void setWidgets() {
        lvHistory = findViewById(R.id.listViewHistoryChange);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

    }

    private void loadList() {
        Cursor cursor = dbAdapter.getHistories();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, RegistreHistory.getListe());
        lvHistory.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_history, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            dbAdapter.deleteTable("history");
            RegistreHistory.clearListe();
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, RegistreHistory.getListe());
            lvHistory.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }
}
