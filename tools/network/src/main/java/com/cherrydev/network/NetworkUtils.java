package com.cherrydev.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtils {

    public static boolean hasNetworkConnection() {
        boolean hasConnectedWifi = false;
        boolean hasConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) NetworkApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    hasConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    hasConnectedMobile = true;
        }
        return hasConnectedWifi || hasConnectedMobile;
    }
}
