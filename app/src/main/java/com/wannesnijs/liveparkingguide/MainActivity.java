package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Context context;

    HelperFuncions helper;

    ArrayList parkings;

    public static String[] parkingNames = {"Kouter", "Zuid"};
    public static int[] parkingSpaces = {25, 201};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        lv = (ListView) findViewById(R.id.listview);
        //lv.setAdapter(new CustomAdapter(this, parkingNames, parkingSpaces));

        new JsonRequestTask().execute();
    }

    class JsonRequestTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            helper = new HelperFuncions(context);
            try {
                return helper.separateJSON(helper.makeRequest());
            } catch (JSONException e) {
                Log.e("JSON",e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if(b) {
                helper.updateScreen();
            }
        }
    }
}
