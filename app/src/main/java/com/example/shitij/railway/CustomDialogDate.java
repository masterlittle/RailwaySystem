package com.example.shitij.railway;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.shitij.railway.activities.ActivityRoute;
import com.example.shitij.railway.activities.ActivityTrainBetweenStations;
import com.example.shitij.railway.interfaces.DialogCallbackInterface;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Shitij on 18/11/15.
 */
public class CustomDialogDate extends DialogFragment {

    private DialogCallbackInterface listener;
    @Bind(R.id.date_travel) DatePicker datePicker;
    @OnClick(R.id.button_submit)
    public void onClickSubmit(){

    }

    @OnClick(R.id.button_cancel)
    public void onClickCancel(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (ActivityTrainBetweenStations)getActivity(), year, month, day);

    }
}
