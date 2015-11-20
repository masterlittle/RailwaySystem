package com.example.shitij.railway.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.shitij.railway.CustomDialogDate;
import com.example.shitij.railway.R;
import com.example.shitij.railway.customviews.TextViewRegularFont;
import com.example.shitij.railway.interfaces.DialogCallbackInterface;
import com.example.shitij.railway.log.Logging;
import com.example.shitij.railway.model.AllTrainDetails;
import com.example.shitij.railway.model.StationCodes;
import com.example.shitij.railway.model.StationsInCities;
import com.example.shitij.railway.networking.GsonRequest;
import com.example.shitij.railway.networking.VolleySingleton;
import com.example.shitij.railway.utils.CommonLibs;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentTrainBetweenStations extends Fragment implements DialogCallbackInterface {

    private String LOG_TAG = FragmentTrainBetweenStations.class.getSimpleName();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> classAdapter;
    private String selectedClass;
    private String sourceStation;
    private String destinationStation;
    private String date;
    private int sFlag = 0;
    private int dFlag = 0;

    @OnClick(R.id.date_travel_train)
    public void onClickDate(){
            CustomDialogDate dialogDate = new CustomDialogDate();
            dialogDate.show(getFragmentManager(), "DatePicker");
    }

    private boolean validateData() {
        boolean result = true;

        if(sFlag == 0){
            result =false;
            Snackbar.make(dateTrain, "Choose Source Station from the list that will appear", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
        if(dFlag == 0){
            result =false;
            Snackbar.make(dateTrain, "Choose Destination Station from the list that will appear", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
        if(destinationTrain.getText().toString().trim().length() <=0){
            result = false;
        }
        if(sourceTrain.getText().toString().trim().length() <=0){
            result = false;
        }
        if(dateTrain.length() >14){
            result =false;
        }
        return result;
    }

    @Bind(R.id.date_travel_train) TextViewRegularFont dateTrain;
    @Bind(R.id.edit_destination_train) AutoCompleteTextView destinationTrain;
    @Bind(R.id.edit_source_train) AutoCompleteTextView sourceTrain;
    private ArrayList<String> stations;
    private HashMap<String, String> stationCodeMapping = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

//        RefWatcher refWatcher = InventoryApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_activity_train_between_stations, container, false);
        ButterKnife.bind(this,v);

        stations = new ArrayList<>();

        stations.add("Delhi");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, stations);

        destinationTrain.setAdapter(adapter);
        sourceTrain.setAdapter(adapter);

        sourceTrain.setThreshold(3);
        destinationTrain.setThreshold(3);

        sourceTrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sourceStation = stationCodeMapping.get(adapter.getItem(position));
                sFlag = 1;
            }
        });

        destinationTrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                destinationStation = stationCodeMapping.get(adapter.getItem(position));
                dFlag =1;
            }
        });

        sourceTrain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 3) {
                    adapter.clear();
                    postData(getActivity(), s.toString().trim());
                }
            }
        });

        destinationTrain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 3) {
                    adapter.clear();
                    postData(getActivity(), s.toString().trim());
                }
            }
        });

        return v;
    }

    private void postData(final Activity mActivity, String city) {

        final String verifyUrl ="http://api.railwayapi.com/suggest_station/name/" + city + "/apikey/"+ CommonLibs.API_KEYS.PUBLIC_KEY + "/";
        final HashMap<String, String> params = new HashMap<>();

        GsonRequest<StationsInCities> request = new GsonRequest<>(Request.Method.GET, verifyUrl, StationsInCities.class, null, null,
                new Response.Listener<StationsInCities>() {
                    @Override
                    public void onResponse(@NonNull StationsInCities message) {
                        try {
                            if (message == null) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put(Logging.REQUEST_URL, verifyUrl);
                                map.put(Logging.REQUEST_TYPE, Logging.TYPE_POST);
                                Logging.logError(LOG_TAG, "Unable to get List of Cities", CommonLibs.Priority.VERY_HIGH, map);
                                Toast.makeText(mActivity, "Unable to request verification code, please retry", Toast.LENGTH_LONG).show();
                            } else {
                                int c = 0;
                                StationCodes codes[];
                                codes = message.getStations();
                                while(message.getTotal()!=c) {
                                    adapter.add(codes[c].getName() + " - " + codes[c].getCode());
                                    stationCodeMapping.put(codes[c].getName() + " - " + codes[c].getCode(), codes[c].getCode());
                                    c++;
                                }
                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception exception) {
                            Logging.logException(LOG_TAG, exception, CommonLibs.Priority.MEDIUM);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@Nullable VolleyError volleyError) {

                        try {
                            if (volleyError != null) {
                                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                                    //  showAToast("No Internet");
                                    Logging.logError(LOG_TAG, "Internet error", CommonLibs.Priority.LOW);
                                    Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.VERY_HIGH);
                                } else {
                                    Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.VERY_HIGH);
                                }
                            } else {
                                Toast.makeText(mActivity, "Some error occured. Please Try again ", Toast.LENGTH_LONG).show();
                            }
                        } catch (NullPointerException e) {
                        }
                    }


                });
        request.setTag(this);
        request.setRetryPolicy(new DefaultRetryPolicy(CommonLibs.VolleyConstants.DEFAULT_TIMEOUT_MS, CommonLibs.VolleyConstants.DEFAULT_MAX_RETRIES, CommonLibs.VolleyConstants.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mActivity).addToRequestQueue(request);
    }

    private void getTrains(final Activity mActivity) {
        final String verifyUrl ="http://api.railwayapi.com/between/source/" + sourceStation.toLowerCase()+ "/dest/" +destinationStation.toLowerCase()+ "/date/" +date +"/apikey/"+ CommonLibs.API_KEYS.PUBLIC_KEY + "/";
        final HashMap<String, String> params = new HashMap<>();

        GsonRequest<AllTrainDetails> request = new GsonRequest<>(Request.Method.GET, verifyUrl, AllTrainDetails.class, null, null,
                new Response.Listener<AllTrainDetails>() {
                    @Override
                    public void onResponse(@NonNull AllTrainDetails message) {
                        try {
                            if (message.getResponse_code() != 200 && message.getResponse_code()!= 204) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put(Logging.REQUEST_URL, verifyUrl);
                                map.put(Logging.REQUEST_TYPE, Logging.TYPE_POST);
                                Logging.logError(LOG_TAG, "Unable to get List of Cities", CommonLibs.Priority.VERY_HIGH, map);
                            } else if(message.getResponse_code() == 200){
                                FragmentListTrainBetweenStations frag = new FragmentListTrainBetweenStations();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("listOfTrains", message);
                                frag.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.container, frag, "ListTrains").addToBackStack("ListTrains").commit();
                            }
                            else{
                                Snackbar.make(dateTrain, "No Trains run between these stations", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                        } catch (Exception exception) {
                            Logging.logException(LOG_TAG, exception, CommonLibs.Priority.MEDIUM);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@Nullable VolleyError volleyError) {

                        try {
                            if (volleyError != null) {
                                if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                                    //  showAToast("No Internet");
                                    Logging.logError(LOG_TAG, "Internet error ", CommonLibs.Priority.LOW);
                                    Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.VERY_HIGH);
                                    Snackbar.make(dateTrain, "Internet Error", Snackbar.LENGTH_SHORT)
                                            .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(dateTrain, "Error", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.VERY_HIGH);
                                }
                            } else {
                                Toast.makeText(mActivity, "Some error occured. Please Try again ", Toast.LENGTH_LONG).show();
                            }
                        } catch (NullPointerException e) {
                        }
                    }


                });
        request.setTag(this);
        request.setRetryPolicy(new DefaultRetryPolicy(CommonLibs.VolleyConstants.DEFAULT_TIMEOUT_MS, CommonLibs.VolleyConstants.DEFAULT_MAX_RETRIES, CommonLibs.VolleyConstants.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mActivity).addToRequestQueue(request);
    }


    @Override
    public void onDialogPositivePressed(Bundle bundle, String info) {

    }

    @Override
    public void onDialogNegativePressed(Bundle bundle, String info) {

    }

    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        date = dayOfMonth + "-" + (monthOfYear+1);
        dateTrain.setText((dayOfMonth) + " / " + (monthOfYear+1) +" / "+ year);
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(destinationTrain.getWindowToken(), 0);
        InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(sourceTrain.getWindowToken(), 0);
    }

    public void getAllTrains(){
        boolean result = validateData();
        if(result) {
            getTrains(getActivity());
        }
        else{
            Snackbar.make(dateTrain, "Enter the required fields", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
}
