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
    HelperFunctions helper;
    private Timer autoUpdate;
    private Comparator<Parking> comparator = new Comparator<Parking>() {
        @Override
        public int compare(Parking p1, Parking p2) {
            return p1.getName().compareTo(p2.getName());
        }
    };

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();
        Resources res = getResources();

        helper = new HelperFunctions(main, res);
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
        while(main.adapter == null) {}
        orderParkings();
        lv.setAdapter(main.adapter);
    }

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
        }, 0, 4000);

    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }

    private void orderParkings() {
        //TODO: if (location available) order by distance; else order alphabetically
        Collections.sort(main.parkings, comparator);
    }

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
