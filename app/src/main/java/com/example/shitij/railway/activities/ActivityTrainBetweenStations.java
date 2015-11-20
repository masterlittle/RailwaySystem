package com.example.shitij.railway.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;

import com.example.shitij.railway.R;
import com.example.shitij.railway.fragments.FragmentTrainBetweenStations;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityTrainBetweenStations extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @OnClick(R.id.fab)
    public void onClickFab(){
        FragmentTrainBetweenStations fragmentTrainBetweenStations = (FragmentTrainBetweenStations)getFragmentManager().findFragmentByTag("FragmentTrainStations");
        if(fragmentTrainBetweenStations != null){
            fragmentTrainBetweenStations.getAllTrains();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_stations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        FragmentTrainBetweenStations fragmentTrainBetweenStations = new FragmentTrainBetweenStations();
        getFragmentManager().beginTransaction().replace(R.id.container, fragmentTrainBetweenStations, "FragmentTrainStations").commit();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        FragmentTrainBetweenStations instance = (FragmentTrainBetweenStations)getFragmentManager().findFragmentByTag("FragmentTrainStations");
        instance.setDate(year, monthOfYear,dayOfMonth);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStackImmediate();
        }
        else {
            super.onBackPressed();
        }
    }
}
