package com.cherrydev.commontools;

import android.app.Application;

import timber.log.Timber;

public class MyApplication extends Application {
    private static MyApplication INSTANCE;

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}
