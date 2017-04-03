package com.firstapp.pois;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ActiveInternetConnection {
	
	public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
     
     
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        } 
        return TYPE_NOT_CONNECTED;
    }
     
    public static String getConnectivityStatusString(Context context) {
        int conn = ActiveInternetConnection.getConnectivityStatus(context);
        String status = null;
        if (conn == ActiveInternetConnection.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == ActiveInternetConnection.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == ActiveInternetConnection.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}


