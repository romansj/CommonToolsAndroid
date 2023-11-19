package com.cherrydev.network;

import android.app.Application;

public class NetworkApp extends Application {
    private static NetworkApp INSTANCE;

    public static NetworkApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }
}
