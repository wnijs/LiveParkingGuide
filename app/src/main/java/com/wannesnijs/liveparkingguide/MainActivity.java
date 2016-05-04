package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;

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
    }

    public void newAdapter() {
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
                if(adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
