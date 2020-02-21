package jsonUtil;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import model.Currency;

public class utilCurrencies {

    public static ArrayList<Currency> parseJsonObject(String json) throws JSONException {

        ArrayList<Currency> listCurrencies = new ArrayList<>();

        JSONObject currencies = new JSONObject(json);
        JSONObject obj = currencies.getJSONObject("results");
        int img=0;
        Currency c;
        for (Iterator key=obj.keys();key.hasNext();) {
            JSONObject test = (JSONObject) obj.get((String) key.next());
             c = new Currency();

            c.setCurrencyName(test.getString("currencyName"));
            if (test.has("currencySymbol"))
                c.setCurrencySymbol(test.getString("currencySymbol"));
            else
                c.setCurrencySymbol("null");

            if (test.has("id"))
                c.setId(test.getString("id"));
            else
                c.setId("null");

            c.setImg(img);
            c.setSelected(false);
            listCurrencies.add(c);
            img++;
        }
        return listCurrencies;
    }
}
