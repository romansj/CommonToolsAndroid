package com.cherrydev.navigation;

import android.annotation.SuppressLint;

import androidx.lifecycle.LifecycleObserver;

@SuppressLint("BinaryOperationInTimber")
public class TransactionSafetyHolder implements LifecycleObserver {
    private static boolean isTransactionSafe = true;
    private static boolean isTransactionPending;


    public static void setIsTransactionSafe(boolean safe) {
        //Timber.i("setIsTransactionSafe " + safe);
        isTransactionSafe = safe;
    }

    public static void setIsTransactionPending(boolean pending) {
        isTransactionPending = pending;
    }

    public static boolean isTransactionSafe() {
        return isTransactionSafe;
    }

    public static boolean isTransactionPending() {
        return isTransactionPending;
    }


//    public TransactionSafetyHolder(Lifecycle lifecycle) {
//        lifecycle.addObserver(this);
//    }
//
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    void onResume() {
//        setSafe(true);
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    void onPause() {
//        setSafe(false);
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    void onStop() {
//        setSafe(false);
//    }
//
//
//    void setSafe(boolean b) {
//        isTransactionSafe = b;
//    }
}
