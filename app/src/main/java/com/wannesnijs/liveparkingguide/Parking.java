package com.wannesnijs.liveparkingguide;

/**
 * Created by wannesnijs on 3/05/16.
 */
public class Parking {
    private String name;
    private String address;
    private String contact;
    private int totalCapacity;
    private int availableCapacity;

    public Parking (String name, String address, String contact, int totalCapacity, int availableCapacity) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.totalCapacity = totalCapacity;
        this.availableCapacity = availableCapacity;
    }

    public String getName() {
        return name;
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

    public void updateCapacity (int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}
