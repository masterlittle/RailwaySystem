package com.example.shitij.railway.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.shitij.railway.log.Logging;
import com.example.shitij.railway.utils.CommonLibs;

/**
 * Created by amandeep on 12/10/14.
 */
public class InternetConnectivity
{
    private static final String LOG_TAG = InternetConnectivity.class.getSimpleName();
    private static InternetConnectivity instance = new InternetConnectivity();
    private boolean connected = false;

    public static InternetConnectivity getInstance()
    {
        return instance;
    }

    public boolean isOnline(Context context)
    {
        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        }
        catch (Exception exception)
        {
            Logging.logException(LOG_TAG, exception, CommonLibs.Priority.LOW);
        }
        return connected;
    }

}
