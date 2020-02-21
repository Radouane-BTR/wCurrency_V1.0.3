package com.example.sessionproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import apiServices.getResponses;
import jsonUtil.utilCurrencies;
import model.CurrenciesAdapter;
import model.Currency;


public class CurrenciesActivity extends AppCompatActivity {

    private final String URL = "http://api.currconv.com/api/v7/currencies?apiKey=";
    private final String API_KEY = "806a854ddb8b420cbf02e68f9ac73b9f";

    Intent currenciesIntent, convertIntent;


    SearchView searchView;
    ListView listeViewCurrencies;

    List<Currency> mylistViewContain,myFiltredlistViewContain;
    boolean idIsSorted=false;


    CurrenciesAdapter myAdapter;

    boolean isSelected;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        mylistViewContain = new ArrayList<>();
        myFiltredlistViewContain = new ArrayList<>();

        setWidget();
        setListener();

        getResponses task = new getResponses(handler);
        task.execute(URL + API_KEY);

        isSelected = false;

    }

    private void setWidget() {
        searchView = findViewById(R.id.searchViewCurriences);
        listeViewCurrencies = findViewById(R.id.listViewCurrencies);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String resultat = msg.getData().getString("REPONSE");
            try {
                setCurrencies(utilCurrencies.parseJsonObject(resultat));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void setListener() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myFiltredlistViewContain = new ArrayList<>();
                for (Currency c : mylistViewContain) {
                    if (c.toString().toLowerCase().contains(newText.toLowerCase())) {
                        myFiltredlistViewContain.add(c);
                    }
                }
                //listeViewCurrencies.setAdapter(null);
                myAdapter.update(myFiltredlistViewContain);
                return false;
            }
        });

        listeViewCurrencies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Currency selectedItem = (Currency) parent.getItemAtPosition(position);

                if (!selectedItem.isSelected()) {

                    deselectAll();
                    selectedItem.setSelected(true);
                    selectedPosition = position;
                    mylistViewContain.set(position, selectedItem);

                    currenciesIntent = new Intent(getApplicationContext(), MainActivity.class);
                    currenciesIntent.putExtra("currencyId", selectedItem.getId());
                    currenciesIntent.putExtra("currencyImg", selectedItem.getImg());

                    convertIntent = getIntent();
                    if (convertIntent.getStringExtra("money").equals("A")) {
                        currenciesIntent.putExtra("money", "A");
                        Currency myParcelableObject = convertIntent.getParcelableExtra("oldMoneyB");
                        currenciesIntent.putExtra("moneyB", myParcelableObject);
                    } else if (convertIntent.getStringExtra("money").equals("B")) {
                        currenciesIntent.putExtra("money", "B");
                        Currency myParcelableObject = convertIntent.getParcelableExtra("oldMoneyA");
                        currenciesIntent.putExtra("moneyA", myParcelableObject);
                    }
                } else {
                    selectedItem.setSelected(false);
                    selectedPosition = -1;
                }
                if(!myFiltredlistViewContain.isEmpty())
                    myAdapter.update(myFiltredlistViewContain);
                else
                    myAdapter.update(mylistViewContain);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_currencies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_done) {
            if (checkItemSelected()) {
                startActivity(currenciesIntent);
            }
        } else if ((item.getItemId() == R.id.action_sortById)) {
            if (!idIsSorted){
                sortById(mylistViewContain);
                idIsSorted = true;
                myAdapter.updateRecord(mylistViewContain);
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private void sortById(List<Currency> mylistViewContain) {
        Collections.sort(mylistViewContain, new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getId().compareTo(c2.getId());
            }
        });
    }

    private void sortByName(List<Currency> mylistViewContain) {
        Collections.sort(mylistViewContain, new Comparator<Currency>() {
            public int compare(Currency c1, Currency c2) {
                return c1.getCurrencyName().compareTo(c2.getCurrencyName());
            }
        });
    }

    private void setCurrencies(ArrayList<Currency> listCurrencies) {
        mylistViewContain = new ArrayList<>(listCurrencies);
        sortByName(mylistViewContain);
        myAdapter = new CurrenciesAdapter(this, mylistViewContain);
        listeViewCurrencies.setAdapter(myAdapter);
    }

    private void deselectAll() {
        for (Currency c : mylistViewContain) {
            if (c.isSelected()) {
                c.setSelected(false);
            }
        }
    }

    private boolean checkItemSelected() {
        boolean state = false;
        for (Currency c : mylistViewContain) {
            if (c.isSelected())
                state = true;
        }
        return state;
    }

}
