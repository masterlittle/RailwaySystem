package com.example.shitij.railway.model;

/**
 * Created by Shitij on 19/11/15.
 */
public class TrainRouteDetails {
    private String name;
    private TrainClasses classes[];
    private TrainDaysActive days[];
    private String number;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public TrainClasses[] getClasses() {
        return classes;
    }

    public TrainDaysActive[] getDays() {
        return days;
    }
}
