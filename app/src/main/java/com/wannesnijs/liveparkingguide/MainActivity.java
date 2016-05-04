package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
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
    public ArrayList<Parking> parkings = new ArrayList<>();
    boolean dataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;
        context = this;
        while(!dataLoaded) {}
        adapter = new ParkingAdapter(main, parkings, getResources());
    }

    public void onItemClick(int mPosition) {
        Parking temp = parkings.get(mPosition);
        System.out.println(temp.getName() + " clicked");
        //TODO: fragment opstarten
    }

    public void updateAdapter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
