package model;


import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.awt.font.*;

public class History {
    private String dateChange = getCurrentDate().toString();
    private String currencyNameA, currencyNameB;
    private float valueCurrencyA,valueCurrencyB;

    public History() {
    }

    public History(String currencyNameA, String currencyNameB, float valueCurrencyA, float valueCurrencyB) {
        this.currencyNameA = currencyNameA;
        this.currencyNameB = currencyNameB;
        this.valueCurrencyA = valueCurrencyA;
        this.valueCurrencyB = valueCurrencyB;
    }

    public History(String dateChange, String currencyNameA, String currencyNameB, float valueCurrencyA, float valueCurrencyB) {
        this.dateChange = dateChange;
        this.currencyNameA = currencyNameA;
        this.currencyNameB = currencyNameB;
        this.valueCurrencyA = valueCurrencyA;
        this.valueCurrencyB = valueCurrencyB;
    }

    public String getDateChange() {
        return dateChange;
    }

    public String getCurrencyNameA() {
        return currencyNameA;
    }

    public void setCurrencyNameA(String currencyNameA) {
        this.currencyNameA = currencyNameA;
    }

    public String getCurrencyNameB() {
        return currencyNameB;
    }

    public void setCurrencyNameB(String currencyNameB) {
        this.currencyNameB = currencyNameB;
    }

    public float getValueCurrencyA() {
        return valueCurrencyA;
    }

    public void setValueCurrencyA(float valueCurrencyA) {
        this.valueCurrencyA = valueCurrencyA;
    }

    public float getValueCurrencyB() {
        return valueCurrencyB;
    }

    public void setValueCurrencyB(float valueCurrencyB) {
        this.valueCurrencyB = valueCurrencyB;
    }

    public static String getCurrentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return formatter.format(today);
    }

    @Override
    public String toString() {
        SpannableString ss = new SpannableString(dateChange);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        ss.setSpan(bold,0,4, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
       // Log.v("My Spannable : ", ss);
        return  dateChange+'\n' +
                currencyNameA + " : "  + valueCurrencyA +'\n' +
                currencyNameB + " : " + valueCurrencyB ;
    }
}
