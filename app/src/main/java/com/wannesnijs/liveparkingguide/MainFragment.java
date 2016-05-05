package com.wannesnijs.liveparkingguide;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {

    ListView lv;
    public MainActivity main = null;

    // Class for location services and JSON requests
    HelperFunctions helper;

    private Timer autoUpdate;
    private double userLatitude, userLongitude;
    private boolean locationAvailable;

    // Interval between json requests
    private int refreshTime = 20000;

    // Comparators for ordering the ArrayList of Parkings: one for alphabetically, one for
    // distance-based
    private Comparator<Parking> nameComparator = new Comparator<Parking>() {
        @Override
        public int compare(Parking p1, Parking p2) {
            return p1.getName().compareTo(p2.getName());
        }
    };
    private Comparator<Parking> distanceComparator = new Comparator<Parking>() {
        @Override
        public int compare(Parking p1, Parking p2) {
            return new Float(p1.getUserDistance()).compareTo(new Float(p2.getUserDistance()));
        }
    };

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();
        Resources res = getResources();

        helper = new HelperFunctions(main, this, res);

        // Start collecting the parking data from the server asynchronously
        new initialRequest().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        try {
            lv = (ListView) getView().findViewById(R.id.listview);
        } catch (NullPointerException e) {
            Log.e("VIEW", e.getMessage());
        }

        // Wait until adapter has been initialized with data from JSON request
        while(main.adapter == null) {}
        orderParkings();
        lv.setAdapter(main.adapter);
    }

    // Sets a timer for a new JSON request, if the app is active
    @Override
    public void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                new updateRequest().execute();
                orderParkings();
                main.updateAdapter();
            }
        }, 0, refreshTime);

    }

    // Deletes the new JSON request timer when going in standby or closing
    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }

    public double getLatitude() {
        return userLatitude;
    }

    public double getLongitude() {
        return userLongitude;
    }

    // Orders ArrayList of parkings by distance to the user if available, alphabetically otherwise
    private void orderParkings() {
        if(locationAvailable) {
            Collections.sort(main.parkings, distanceComparator);
        } else {
            Collections.sort(main.parkings, nameComparator);
        }
    }

    // Sets the new user location
    public void updateLocation(double lat, double lon, boolean locationAvailable) {
        userLatitude = lat;
        userLongitude = lon;
        this.locationAvailable = locationAvailable;
    }

    // Initial collecting of data: requests JSON message from server and initializes the ArrayList
    // of parkings
    class initialRequest extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                boolean result = helper.InitialCast(helper.makeRequest());
                main.dataLoaded = true;
                main.newAdapter();
                return result;
            } catch (JSONException e) {
                Log.e("JSON", e.getMessage());
            }
            return false;
        }
    }

    // Updates the available parking spaces: request JSON message from server and adjusts the
    // available spaces attributes of the parkings
    class updateRequest extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                boolean result = helper.UpdateCast(helper.makeRequest());
                main.dataLoaded = true;
                return result;
            } catch (JSONException e) {
                Log.e("JSON", e.getMessage());
            }
            return false;
        }
    }
}
