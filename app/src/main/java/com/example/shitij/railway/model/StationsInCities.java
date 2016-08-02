package com.example.shitij.railway.model;

/**
 * Created by Shitij on 18/11/15.
 */
public class StationsInCities {
    private int total;
    private StationCodes[] station;
    private String responseCode;

    public StationCodes[] getStations() {
        return station;
    }

    public int getTotal() {
        return total;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
