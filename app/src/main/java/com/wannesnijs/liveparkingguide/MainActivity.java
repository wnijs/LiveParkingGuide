package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Context context;

    ListView lv;
    ParkingAdapter adapter;
    public MainActivity main = null;
    public ArrayList<Parking> parkings = new ArrayList<Parking>();
    HelperFunctions helper;
    boolean dataLoaded = false;
    private Timer autoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;
        context = this;
        Resources res = getResources();

        helper = new HelperFunctions(main, getResources());
        new initialRequest().execute();
        while(!dataLoaded) {}
        lv = (ListView) findViewById(R.id.listview);
        adapter = new ParkingAdapter(main, parkings, getResources());
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new updateRequest().execute();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 0, 4000);
    }

    @Override
    public void onPause() {
        autoUpdate.cancel();
        super.onPause();
    }

    public void onItemClick(int mPosition) {
        Parking temp = parkings.get(mPosition);
        System.out.println(temp.getName() + " clicked");
        //TODO: fragment opstarten
    }

    class initialRequest extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                boolean result = helper.InitialCast(helper.makeRequest());
                dataLoaded = true;
                return result;
            } catch (JSONException e) {
                Log.e("JSON",e.getMessage());
            }
            return false;
        }
    }

    class updateRequest extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                boolean result = helper.UpdateCast(helper.makeRequest());
                dataLoaded = true;
                return result;
            } catch (JSONException e) {
                Log.e("JSON",e.getMessage());
            }
            return false;
        }
    }
}
