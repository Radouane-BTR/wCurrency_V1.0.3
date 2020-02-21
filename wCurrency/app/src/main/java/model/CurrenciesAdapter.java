package model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sessionproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.sessionproject.R.id.textViewCurrency;
import static com.example.sessionproject.R.layout.rowcurrencies;

public class CurrenciesAdapter extends BaseAdapter {

    private Activity activity;
    private List<Currency> rCurrency;
    private LayoutInflater inflater;

    public CurrenciesAdapter(Activity activity, List<Currency> currencies) {
        this.activity = activity;
        this.rCurrency = currencies;
        this.inflater = activity.getLayoutInflater();
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            convertView = inflater.inflate(rowcurrencies, parent, false);

            holder = new ViewHolder();
            holder.flags = convertView.findViewById(R.id.imageViewFlag);
            holder.myCurrency = convertView.findViewById(textViewCurrency);
            holder.check = convertView.findViewById(R.id.imageViewCheck);
            holder.check.setImageResource(R.drawable.check);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        Currency currency = rCurrency.get(position);

        holder.flags.setImageResource(Image.flags[currency.getImg()]);
        holder.myCurrency.setText(currency.toString());

        if (currency.isSelected()) {
            holder.check.setVisibility(View.VISIBLE);
        } else {
            holder.check.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void doFilter(String charText) {
        List<Currency> myFilterlistViewContain = new ArrayList<>();
        for (Currency c: rCurrency) {
            if (c.toString().contains(charText))
                myFilterlistViewContain.add(c);
        }
        updateRecord(myFilterlistViewContain);
    }

    @Override
    public int getCount() {
        return rCurrency.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Currency getItem(int position) {
        return rCurrency.get(position);
    }

    public void updateRecord(List<Currency> currencies) {
        //rCurrency.clear();
        this.rCurrency = currencies;
        rCurrency.addAll(currencies);
        notifyDataSetChanged();
    }

    public void update(List<Currency> myFilterlistViewContain) {
        rCurrency = new ArrayList<>();
        rCurrency.addAll(myFilterlistViewContain);
        notifyDataSetChanged();
    }


    public class ViewHolder {
        ImageView flags;
        TextView myCurrency;
        ImageView check;
    }
}
