package com.wannesnijs.liveparkingguide;

import android.location.Location;

/**
 * Created by wannesnijs on 3/05/16.
 */
public class Parking {
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private String contact;
    private int totalCapacity;
    private int availableCapacity;
    private float userDistance;

    public Parking(String name, double latitude, double longitude, String address, String contact, int totalCapacity, int availableCapacity, double userLatitude, double userLongitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.contact = contact;
        this.totalCapacity = totalCapacity;
        this.availableCapacity = availableCapacity;
        float[] distance = new float[3];
        Location.distanceBetween(latitude, longitude, userLatitude, userLongitude, distance);
        userDistance = distance[0];
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public float getUserDistance() {
        return userDistance;
    }

    public void updateCapacity (int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}
