package com.example.shitij.railway.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shitij.railway.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentRoute extends Fragment {

    public FragmentRoute() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_route, container, false);
    }
}
