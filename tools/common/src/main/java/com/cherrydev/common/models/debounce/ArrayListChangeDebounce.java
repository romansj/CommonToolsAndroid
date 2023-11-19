package com.cherrydev.common.models.debounce;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.List;

public class ArrayListChangeDebounce<T> implements LifecycleObserver {

    //https://gist.github.com/demixdn/1267fa215824e2d5111e5321a7184721

    private final Handler debounceHandler;
    private DebounceCallback<T> debounceCallback;
    private Runnable debounceWorker;
    private int delayMillis;


    ArrayListChangeDebounce() {
        this.debounceHandler = new Handler(Looper.getMainLooper());
        this.debounceWorker = new DebounceRunnable(new ArrayList<>(), null);
    }


    void setLifecycle(LifecycleOwner viewLifecycleOwner) {
        Lifecycle lifecycle = viewLifecycleOwner.getLifecycle();
        lifecycle.addObserver(this);
    }

    void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public void post(List<T> arrayList) {
        debounceHandler.removeCallbacks(debounceWorker);
        debounceWorker = new DebounceRunnable(arrayList, debounceCallback);
        debounceHandler.postDelayed(debounceWorker, delayMillis);
    }

    public void setCallback(DebounceCallback<T> debounceCallback) {
        this.debounceCallback = debounceCallback;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unwatch() {
        debounceHandler.removeCallbacks(debounceWorker);
    }


    private class DebounceRunnable implements Runnable {

        private final List<T> arrayList;
        private final DebounceCallback<T> debounceCallback;

        DebounceRunnable(List<T> arrayList, DebounceCallback<T> debounceCallback) {
            this.arrayList = arrayList;
            this.debounceCallback = debounceCallback;
        }

        @Override
        public void run() {
            if (debounceCallback != null) {
                debounceCallback.onFinished(arrayList);
            }
        }
    }

    public interface DebounceCallback<T> {
        void onFinished(@NonNull List<T> arrayList);
    }


}