package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Context context;

    HelperFunctions helper;

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
            helper = new HelperFunctions(context);
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
