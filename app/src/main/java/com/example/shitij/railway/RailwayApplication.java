package com.example.shitij.railway;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Shitij on 17/11/15.
 */
public class RailwayApplication extends Application {
    private static Typeface typefaceIconFont;
    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-67733512-1";

    public RailwayApplication() {
        super();
    }

    @Override
    public void onCreate() {
        typefaceIconFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/android.ttf");
        //Initialize Parse
        // Enable Crash Reporting
//        Fabric.with(this, new Crashlytics());
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
    }

    public static Typeface getTypefaceIconFont() {
        return typefaceIconFont;
    }
}

