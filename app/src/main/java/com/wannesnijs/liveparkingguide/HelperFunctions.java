package com.wannesnijs.liveparkingguide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

    MainActivity main;
    MainFragment fragment;
    Resources res;

    LocationManager locationManager;
    LocationListener locationListener;

    private String url;
    private boolean isGPSEnabled = false;

    public HelperFunctions(MainActivity main, MainFragment frag, Resources res) {
        this.main = main;
        this.fragment = frag;
        this.res = res;

        url = main.getResources().getString(R.string.url);

        initLocationService(main.context);
    }

    private void initLocationService(final Context context) {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                fragment.updateLocation(location.getLatitude(), location.getLongitude(), isGPSEnabled);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            fragment.updateLocation(0.0, 0.0, false);
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, (long)0, 0, locationListener);
            }
            if(locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                fragment.updateLocation(location.getLatitude(), location.getLongitude(), isGPSEnabled);
            }
        } catch (Exception e) {
            Log.e("LOCATION", e.getMessage());
        }
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
            Log.e("JSON Malformed URL", e.getMessage());
        } catch (IOException e) {
            Log.e("JSON IO", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
        }
        return null;
    }

    public boolean InitialCast(JSONArray jsonArray) throws JSONException{
        if(jsonArray != null) {
            main.parkings = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject parking = jsonArray.getJSONObject(i);
                Parking newParking = new Parking(parking.getString("description"),
                        parking.getDouble("latitude"),
                        parking.getDouble("longitude"),
                        parking.getString("address"), parking.getString("contactInfo"),
                        parking.getInt("totalCapacity"),
                        parking.getJSONObject("parkingStatus").getInt("availableCapacity"),
                        fragment.getLatitude(),
                        fragment.getLongitude());
                System.out.println(newParking.getName() + ": " + newParking.getAvailableCapacity());
                main.parkings.add(newParking);
            }
        }
        return false;
    }

    public boolean UpdateCast(JSONArray jsonArray) throws JSONException{
        if(jsonArray != null) {
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject parking = jsonArray.getJSONObject(i);
                String name = parking.getString("description");
                for(int j = 0; j < main.parkings.size(); j++) {
                    if(name.equals(main.parkings.get(j).getName())) {
                        main.parkings.get(j).updateCapacity(parking.getJSONObject("parkingStatus").getInt("availableCapacity"));
                        System.out.println(main.parkings.get(i).getName() + ": " + main.parkings.get(i).getAvailableCapacity());
                    }
                }
            }
        }
        return false;
    }


}