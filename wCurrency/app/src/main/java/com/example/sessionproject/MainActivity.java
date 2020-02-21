package com.example.sessionproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.math3.util.Precision;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

import Data.DBAdapter;
import apiServices.getResponses;
import model.Currency;
import model.History;
import model.Image;

public class MainActivity extends AppCompatActivity {

    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;

    private final String URL = "http://api.currconv.com/api/v7/convert?q=Money&compact=ultra&apiKey=";
    private final String API_KEY = "806a854ddb8b420cbf02e68f9ac73b9f";

    BottomNavigationView bottomNavigationView;
    FloatingActionButton addCurrency;
    Intent currenciesIntent, moneyBintent, historyIntent, intentShareApp;

    Currency moneyA, moneyB;

    TextView txtMoneyA, txtMoneyB;
    EditText editMoneyA, editMoneyB;
    ImageView flagMoneyA, flagMoneyB, reverseIcon, imgViewEqual;
    Boolean isReversed = true;

    private Handler handler;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetwork();

        moneyA = new Currency();
        moneyB = new Currency();

        moneyA.setId("CAD");
        moneyA.setImg(R.drawable.canada);

        moneyB.setId("USD");
        moneyB.setImg(R.drawable.usa);

        setWidget();
        setListener();


        currenciesIntent = getIntent();
        if (currenciesIntent.hasExtra("currencyImg")) {
            if (currenciesIntent.getStringExtra("money").equals("A")) {
                moneyA.setId(currenciesIntent.getStringExtra("currencyId"));
                moneyA.setImg(Image.flags[currenciesIntent.getIntExtra("currencyImg", 0)]);
                moneyB =  currenciesIntent.getParcelableExtra("moneyB");
            } else if (currenciesIntent.getStringExtra("money").equals("B")){
                moneyB.setId(currenciesIntent.getStringExtra("currencyId"));
                moneyB.setImg(Image.flags[currenciesIntent.getIntExtra("currencyImg", 0)]);
                moneyA =  currenciesIntent.getParcelableExtra("moneyA");
            }
        }
        loadActivity(moneyA.getImg(), moneyB.getImg());
        doConvert();
        getResult();

        dbAdapter = new DBAdapter(MainActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_convert, menu);
        return true;
    }

    @SuppressLint("HandlerLeak")
    private void doConvert() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (!editMoneyA.getText().toString().matches("")) {
                        String resultat = msg.getData().getString("REPONSE");
                        JSONObject taux = new JSONObject(resultat);
                        double obj = taux.getDouble(txtMoneyA.getText() + "_" + txtMoneyB.getText());
                        double  get= Precision.round(
                                (Double.parseDouble(String.valueOf(editMoneyA.getText())) * obj),
                                4, BigDecimal.ROUND_DOWN);
                        editMoneyB.setText(String.valueOf(get));
                    }else{
                        editMoneyB.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private void setListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mHistory:
                        historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                        startActivity(historyIntent);
                        break;
                }
                return true;
            }
        });

        addCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenciesIntent = new Intent(MainActivity.this, CurrenciesActivity.class);
                startActivity(currenciesIntent);
            }
        });

        reverseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMoneys();
                getResult();
            }
        });

        imgViewEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMoneyA.setText(String.valueOf(1));
                editMoneyA.setSelection(editMoneyA.getText().length());
            }
        });

        editMoneyA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        flagMoneyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenciesIntent = new Intent(MainActivity.this, CurrenciesActivity.class);
                currenciesIntent.putExtra("money", "A");
                currenciesIntent.putExtra("oldMoneyB", moneyB);
                startActivity(currenciesIntent); // intent explicite (pas de bagage)
            }
        });

        flagMoneyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenciesIntent = new Intent(MainActivity.this, CurrenciesActivity.class);
                currenciesIntent.putExtra("money", "B");
                currenciesIntent.putExtra("oldMoneyA", moneyA);
                startActivity(currenciesIntent); // intent explicite (pas de bagage)
            }
        });
    }

    private void getResult() {
        getResponses task = new getResponses(handler);
        String newUrl = URL.replace("Money", txtMoneyA.getText() + "_" + txtMoneyB.getText());
        task.execute(newUrl + API_KEY);
    }


    private void setWidget() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        addCurrency = findViewById(R.id.fabAdd);

        txtMoneyA = findViewById(R.id.txtMoneyA);
        editMoneyA = findViewById(R.id.editMoneyA);
        txtMoneyB = findViewById(R.id.txtMoneyB);
        editMoneyB = findViewById(R.id.editMoneyB);

        flagMoneyA = findViewById(R.id.flagMoneyA);
        flagMoneyB = findViewById(R.id.flagMoneyB);
        reverseIcon = findViewById(R.id.imgViewSwap);
        imgViewEqual = findViewById(R.id.imgViewEqual);

        reverseIcon.setImageResource(R.drawable.swap);
        imgViewEqual.setImageResource(R.drawable.delete);
        editMoneyA.setText(String.valueOf(1));

    }

    private void loadActivity(int drawableA, int drawableB) {
        txtMoneyA.setText(moneyA.getId());
        flagMoneyA.setImageResource(drawableA);
        txtMoneyB.setText(moneyB.getId());
        flagMoneyB.setImageResource(drawableB);
    }

    private void switchMoneys() {
        if (!isReversed) {
            txtMoneyA.setText(moneyB.getId());
            flagMoneyA.setImageResource(moneyB.getImg());
            txtMoneyB.setText(moneyA.getId());
            flagMoneyB.setImageResource(moneyA.getImg());
            editMoneyA.setSelection(editMoneyA.getText().length());
            isReversed = true;
        } else {
            loadActivity(moneyA.getImg(), moneyB.getImg());
            editMoneyA.setSelection(editMoneyA.getText().length());
            isReversed = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_shareApp) {
            intentShareApp = new Intent(Intent.ACTION_SEND);
            intentShareApp.setType("text/plain");
            intentShareApp.putExtra(Intent.EXTRA_SUBJECT,"wCurrency");
            intentShareApp.putExtra(Intent.EXTRA_TEXT,"link to my app playStore");
            startActivity(intentShareApp);
        } else if ((item.getItemId() == R.id.action_shareRates)) {
            intentShareApp = new Intent(Intent.ACTION_SEND);
            intentShareApp.setType("text/plain");
            intentShareApp.putExtra(Intent.EXTRA_SUBJECT,"wCurrency");
            intentShareApp.putExtra(Intent.EXTRA_TEXT,editMoneyA.getText() + " " + txtMoneyA.getText()
                                                + " = " +  editMoneyB.getText()+ " " + txtMoneyB.getText());
            startActivity(intentShareApp);
        } else if ((item.getItemId() == R.id.action_save)) {
            History h  = new History();
            h.setCurrencyNameA(moneyA.getId());
            h.setCurrencyNameB(moneyB.getId());
            h.setValueCurrencyA(Float.parseFloat(editMoneyA.getText().toString()));
            h.setValueCurrencyB(Float.parseFloat(editMoneyB.getText().toString()));
           // h.getDateChange();
            dbAdapter.insertHistory(h);
            //dbAdapter.insertHistory(new History(moneyA.getCurrencyName(),moneyB.getCurrencyName(),
            //            Float.valueOf(editMoneyA.getText().toString()),Float.valueOf(editMoneyB.getText().toString())));
            Toast.makeText(MainActivity.this,"Ajout avec succes", Toast.LENGTH_LONG ).show();
            //dbAdapter.
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkNetwork(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }
}
