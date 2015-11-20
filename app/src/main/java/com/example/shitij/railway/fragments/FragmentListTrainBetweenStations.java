package com.example.shitij.railway.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import com.example.shitij.railway.ExpandableTrainAdapter;
import com.example.shitij.railway.R;
import com.example.shitij.railway.log.Logging;
import com.example.shitij.railway.model.AllTrainDetails;
import com.example.shitij.railway.model.TrainDetails;
import com.example.shitij.railway.utils.CommonLibs;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListTrainBetweenStations extends Fragment {

    @Bind(R.id.listTrains) ExpandableListView listTrains;
    private AllTrainDetails allTrainDetails;
    private ArrayList<TrainDetails> trains;

    public FragmentListTrainBetweenStations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragement_list_train_between_stations, container, false);
        ButterKnife.bind(this,v);

        if(getArguments().size() != 0){
             allTrainDetails = getArguments().getParcelable("listOfTrains");
            trains = new ArrayList<>(Arrays.asList(allTrainDetails.getTrain()));
            Logging.logMessage("List Of Trains", trains.get(0).getName(), CommonLibs.Priority.MEDIUM);
            ExpandableTrainAdapter adapter = new ExpandableTrainAdapter(getActivity(), trains);
            listTrains.setAdapter(adapter);
        }
        return v;
    }


}
