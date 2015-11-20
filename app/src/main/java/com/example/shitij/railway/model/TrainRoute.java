package com.example.shitij.railway.model;

/**
 * Created by Shitij on 19/11/15.
 */
public class TrainRoute {
    private int response_code;
    private TrainRouteStationDetails[] route;
    private TrainRouteDetails train;

    public int getResponse_code() {
        return response_code;
    }

    public TrainRouteStationDetails[] getRoute() {
        return route;
    }

    public TrainRouteDetails getTrain() {
        return train;
    }
}
