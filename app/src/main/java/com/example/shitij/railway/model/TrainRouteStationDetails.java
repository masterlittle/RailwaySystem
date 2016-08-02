package com.example.shitij.railway.model;

/**
 * Created by Shitij on 19/11/15.
 */
public class TrainRouteStationDetails {

    private int no;
    private int distance;
    private int day;
    private int halt;
    private int route;
    private double lat;
    private double lng;
    private String code;
    private String fullname;
    private String state;
    private String schdep;
    private String scharr;

    public String getState() {
        return state;
    }

    public int getDay() {
        return day;
    }

    public int getHalt() {
        return halt;
    }


    public int getDistance() {
        return distance;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getNo() {
        return no;
    }

    public String getCode() {
        return code;
    }

    public int getRoute() {
        return route;
    }

    public String getFullname() {
        return fullname;
    }

    public String getScharr() {
        return scharr;
    }

    public String getSchdep() {
        return schdep;
    }
}
