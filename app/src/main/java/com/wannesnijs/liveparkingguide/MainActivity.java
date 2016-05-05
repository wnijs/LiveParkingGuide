package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;

    ParkingAdapter adapter;
    public MainActivity main = null;
    public ArrayList<Parking> parkings = new ArrayList<>();
    boolean dataLoaded = false;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;
        context = this;
        fragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
    }

    public void newAdapter() {
        adapter = new ParkingAdapter(main, parkings, getResources());
    }

    public void onItemClick(int mPosition) {
        Parking temp = parkings.get(mPosition);
        System.out.println(temp.getName() + " clicked");
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("position", mPosition);
        detailFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
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
