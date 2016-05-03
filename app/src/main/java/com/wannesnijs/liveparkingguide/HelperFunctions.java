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
public class HelperFunctions {

    Context context;

    private String url;

    private ArrayList<Parking> parkings = new ArrayList<>();

    public HelperFunctions(Context context) {
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

                //JSONObject response = new JSONObject(builder.toString());
                //JSONArray parkingsArray = response.getJSONArray("");


                return new JSONArray(builder.toString());
            }
        } catch(MalformedURLException e) {
            Log.e("JSON Malformed URL", e.getMessage());
        } catch (IOException e) {
            Log.e("JSON IO", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
        }
        return null;
    }

    public boolean separateJSON(JSONArray jsonArray) throws JSONException{
        if(jsonArray != null) {
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject parking = jsonArray.getJSONObject(i);
                //String parkingName = parking.toString();
                //System.out.println(parkingName);
                Parking newParking = new Parking(parking.getString("name"),
                        parking.getString("address"), parking.getString("contactInfo"),
                        parking.getInt("totalCapacity"),
                        parking.getJSONObject("parkingStatus").getInt("availableCapacity"));
                System.out.println(newParking.getName() + ": " + newParking.getAvailableCapacity());
                parkings.add(newParking);
            }
        }
        return false;
    }

    public void updateScreen() {

    }
}
