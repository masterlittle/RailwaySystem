package com.example.shitij.railway.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.shitij.railway.R;
import com.example.shitij.railway.customviews.EditTextRegularFont;
import com.example.shitij.railway.customviews.TextViewRegularFont;
import com.example.shitij.railway.log.Logging;
import com.example.shitij.railway.model.StationCodes;
import com.example.shitij.railway.model.StationsInCities;
import com.example.shitij.railway.model.TrainRoute;
import com.example.shitij.railway.model.TrainRouteStationDetails;
import com.example.shitij.railway.networking.GsonRequest;
import com.example.shitij.railway.networking.VolleyRequest;
import com.example.shitij.railway.networking.VolleySingleton;
import com.example.shitij.railway.utils.Api;
import com.example.shitij.railway.utils.CommonLibs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityRoute extends AppCompatActivity implements OnMapReadyCallback {

    @Bind(R.id.edit_train_number)
    EditTextRegularFont trainNumber;
    @Bind(R.id.layout_train_number)
    TextInputLayout layoutTrainNumber;
    @Bind(R.id.source_field)
    TextViewRegularFont sourceField;
    @Bind(R.id.destination_field) TextViewRegularFont destinationField;
    @Bind(R.id.distance_field) TextViewRegularFont distanceField;
    private GoogleMap googleMap;

    @OnClick(R.id.fab)
    public void onClickFab() {
        if (trainNumber.getText().toString().trim().length() == 5) {
            postData(this, trainNumber.getText().toString().trim());
        } else {
            layoutTrainNumber.setError("Enter Correct Train Number");
        }
    }

    private ArrayList<TrainRouteStationDetails> trainRoutes;
    private String LOG_TAG = ActivityRoute.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(trainNumber, 0);

        trainNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length() == 5){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(trainNumber.getWindowToken(), 0);
                }
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void postData(final Activity mActivity, final String train) {

        final String verifyUrl = "http://api.railwayapi.com/route/train/" + train + "/apikey/" + CommonLibs.API_KEYS.PUBLIC_KEY + "/";

        GsonRequest<TrainRoute> request = new GsonRequest<>(Request.Method.GET, verifyUrl, TrainRoute.class, null, null,
                new Response.Listener<TrainRoute>() {
                    @Override
                    public void onResponse(@NonNull TrainRoute message) {
                        try {
                            if (message.getResponse_code() != 200) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put(Logging.REQUEST_URL, verifyUrl);
                                map.put(Logging.REQUEST_TYPE, Logging.TYPE_POST);
                                Logging.logError(LOG_TAG, "Unable to get List of Cities", CommonLibs.Priority.VERY_HIGH, map);
                                Snackbar.make(sourceField, "No such train exists", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                            if (message.getResponse_code() == 200) {
                                int c = 0;
                                TrainRouteStationDetails routes[];
                                routes = message.getRoute();
                                trainRoutes = new ArrayList<>(Arrays.asList(routes));
                                String stationName = trainRoutes.get(0).getFullname();
                                String time = trainRoutes.get(0).getSchdep();
                                String text = stationName + " (" + time + ")";
                                sourceField.setText(text);

                                stationName = trainRoutes.get(trainRoutes.size()-1).getFullname();
                                time = trainRoutes.get(trainRoutes.size()-1).getScharr();
                                text = stationName + " (" + time + ")";

                                destinationField.setText(text);

                                String distance = "Distance = " + trainRoutes.get(trainRoutes.size()-1).getDistance() + " kms";
                                distanceField.setText(distance);
                                getDirections(mActivity);
                                layoutTrainNumber.setErrorEnabled(false);
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
                                    Logging.logError(LOG_TAG, "Internet error while importing products", CommonLibs.Priority.LOW);
                                    Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.VERY_HIGH);
                                    Toast.makeText(mActivity, "Internet Error", Toast.LENGTH_SHORT).show();
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

    public void drawPath(String  result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            String status = json.getString("status");
            if (status.equalsIgnoreCase("NOT_FOUND")) {
                Snackbar.make(sourceField, "Sorry, Route could not be found", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                JSONArray routeArray = json.getJSONArray("routes");
                JSONObject routes = routeArray.getJSONObject(0);
                JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                String encodedString = overviewPolylines.getString("points");
                List<LatLng> list = decodePoly(encodedString);
                googleMap.clear();

                googleMap.addMarker(new MarkerOptions().position(list.get(0)));
                googleMap.addMarker(new MarkerOptions().position(list.get(list.size() - 1)));
                Polyline line = googleMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(12)
                                .color(Color.parseColor("#05b1fb"))//Google maps blue color
                                .geodesic(true)
                );

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(list.get(0))
                        .zoom(8)                   // Sets the zoom
                        .bearing(1)                // Sets the orientation of the camera to north
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }
        catch (JSONException e) {

        }
    }

    private void getDirections(Activity mActivity) {
        String url = makeUrl();
        url = url.replaceAll(" ", "%20");
        VolleyRequest request = new VolleyRequest(Request.Method.GET, url, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                drawPath(response);
                Logging.logMessage(LOG_TAG, response, CommonLibs.Priority.LOW);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (!(volleyError instanceof TimeoutError) && !(volleyError instanceof NoConnectionError))
                            Logging.logException(LOG_TAG, volleyError, CommonLibs.Priority.HIGH);
                    }
                });

        VolleySingleton.getInstance(mActivity).addToRequestQueue(request);
    }

    private String makeUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://maps.googleapis.com/maps/api/directions/json");
        stringBuilder.append("?origin=");
        stringBuilder.append(trainRoutes.get(0).getFullname());
        stringBuilder.append("&destination=");
        stringBuilder.append(trainRoutes.get(trainRoutes.size() - 1).getFullname());
//        if (trainRoutes.size() > 2) {
//            stringBuilder.append("&waypoints=");
//            for (int i = 1; i < trainRoutes.size() - 1; i++) {
//                if (i > 1) {
//                    stringBuilder.append("|");
//                }
//                stringBuilder.append(trainRoutes.get(i).getFullname());
//            }
//        }
        stringBuilder.append("&smode=transit");
//        stringBuilder.append("&key=");
//        stringBuilder.append(CommonLibs.API_KEYS.DIRECTIONS_KEY_ANDROID);
        stringBuilder.append("/");
        return stringBuilder.toString();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng sydney = new LatLng(28.6100, 77.2300);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Delhi"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(28.6100, 77.2300))      // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

}
