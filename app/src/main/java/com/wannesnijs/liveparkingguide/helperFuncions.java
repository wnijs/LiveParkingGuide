package com.wannesnijs.liveparkingguide;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wannesnijs on 3/05/16.
 */
public class HelperFuncions {

    Context context;

    private String url;

    public HelperFuncions (Context context) {
        this.context = context;
        url = context.getResources().getString(R.string.url);
    }

    public JSONArray makeRequest() {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String stream;
                while((stream = bufferedReader.readLine()) != null) {
                    builder.append(stream);
                }
                Log.d("JSON","JSON: " + builder.toString());
                return new JSONArray(builder.toString());
            }
        } catch(MalformedURLException e) {
            Log.e("JSON", e.getMessage());
        } catch (IOException e) {
            Log.e("JSON", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSON", e.getMessage());
        }
        return null;
    }

    public boolean separateJSON(JSONArray jsonObject) throws JSONException{
        if(jsonObject != null) {

        }
        return false;
    }

    public void updateScreen() {

    }
}
