package com.wannesnijs.liveparkingguide;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wannesnijs on 3/05/16.
 */
public class ParkingAdapter extends BaseAdapter implements DialogInterface.OnClickListener {

    private Activity activity;
    private ArrayList<Parking> data;
    private static LayoutInflater inflater = null;
    private Resources res;
    private Context context;
    Parking temp = null;

    public ParkingAdapter(Activity a, ArrayList<Parking> d, Resources resLocal, Context c) {
        activity = a;
        data = d;
        res = resLocal;
        context = c;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() <= 0) return 1;
        return data.size();
    }

    @Override
    public Parking getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView spaces;
    }

    // Returns the view for the list item at a given position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView == null) {
            // Inflate list_item.xml file for each row
            vi = inflater.inflate(R.layout.list_item, null);

            // View Holder Object to contain list_item.xml file elements
            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.list_name);
            holder.spaces = (TextView) vi.findViewById(R.id.list_spaces);

            // Set holder with LayoutInflater
            vi.setTag(holder);
        } else holder = (ViewHolder) vi.getTag();

        if(data.size() <= 0) {
            holder.name.setText(res.getString(R.string.no_name));
        } else {
            // Get each Model object from Arraylist
            temp = null;
            temp = data.get(position);

            // Set Model values in Holder elements
            holder.name.setText(temp.getName());
            holder.spaces.setText("" + temp.getAvailableCapacity());

            // Determine the color of the number, based on the number of available spaces
            int color;
            if(temp.getAvailableCapacity() >= 50) color = ContextCompat.getColor(context, R.color.green);
            else if(temp.getAvailableCapacity() >= 10) color = ContextCompat.getColor(context, R.color.yellow);
            else if(temp.getAvailableCapacity() > 0) color = ContextCompat.getColor(context, R.color.orange);
            else color = ContextCompat.getColor(context, R.color.red);
            holder.spaces.setTextColor(color);

            // Set Item Click Listener for LayoutInflater for each row
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.v("ParkingAdapter", "Row clicked");
    }

    // Detects when user clicks an item, passes to the onItemClick()-method of the main activity
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MainActivity sct = (MainActivity) activity;

            sct.onItemClick(mPosition);
        }
    }
}
