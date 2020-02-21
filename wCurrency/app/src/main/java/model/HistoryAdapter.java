package model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sessionproject.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.sessionproject.R.id.mHistory;
import static com.example.sessionproject.R.layout.rowhistory;

public class HistoryAdapter extends ArrayAdapter<History> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public HistoryAdapter(Context context, int resource, ArrayList<History> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView date;
        TextView currencyA;
        TextView currencyB;
    }

    /*
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        Date date = getItem(position).getDateChange();
        Currency c1 = getItem(position).getCurrencyA();
        Currency c2 = getItem(position).getCurrencyB();

        //Create the person object with the information
        History h = new History(date,c1,c2);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.textViewDate);
            holder.currencyA = (TextView) convertView.findViewById(R.id.textViewMonneyA);
            holder.currencyB = (TextView) convertView.findViewById(R.id.textViewMonneyB);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.date.setText((CharSequence) h.getDateChange());
        holder.currencyA.setText((CharSequence) h.getCurrencyA());
        holder.currencyB.setText((CharSequence) h.getCurrencyB());


        return convertView;
    }*/
}
