package com.wannesnijs.liveparkingguide;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    public Resources res;
    Parking temp = null;
    int i = 0;

    public ParkingAdapter(Activity a, ArrayList<Parking> d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView == null) {
            // Inflate list_item.xml file for each row
            vi = inflater.inflate(R.layout.list_item, null);

            // View Holder Object to contain list_item.xml file elements
            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.textViewName);
            holder.spaces = (TextView) vi.findViewById(R.id.textViewSpaces);

            // Set holder with LayoutInflater
            vi.setTag(holder);
        } else holder = (ViewHolder) vi.getTag();

        if(data.size() <= 0) {
            holder.name.setText("No name");
        } else {
            // Get each Model object from Arraylist
            temp = null;
            temp = data.get(position);

            // Set Model values in Holder elements
            holder.name.setText(temp.getName());
            holder.spaces.setText("" + temp.getAvailableCapacity());

            // Set Item Click Listener for LayoutInflater for each row

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.v("ParkingAdapter", "Row clicked");
    }

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
