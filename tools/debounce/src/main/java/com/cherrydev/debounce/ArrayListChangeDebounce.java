package com.cherrydev.debounce;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;

public class ArrayListChangeDebounce<T> implements LifecycleObserver {

    //https://gist.github.com/demixdn/1267fa215824e2d5111e5321a7184721

    private final Handler debounceHandler;
    private DebounceCallback<T> debounceCallback;
    private Runnable debounceWorker;
    private int delayMillis;


    private ArrayListChangeDebounce() {
        this.debounceHandler = new Handler(Looper.getMainLooper());
        this.debounceWorker = new DebounceRunnable<T>(new ArrayList<>(), null);
    }


    public static class Builder<T> {

        private int delay;
        private LifecycleOwner lifecycleOwner;
        private DebounceCallback<T> debounceCallback;

        public Builder<T> setDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public Builder<T> setLifecycle(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
            return this;
        }

        public Builder<T> watch(DebounceCallback<T> debounceCallback) {
            this.debounceCallback = debounceCallback;
            return this;
        }

        public ArrayListChangeDebounce<T> build() {
            ArrayListChangeDebounce<T> changeDebounce = new ArrayListChangeDebounce<T>();
            changeDebounce.setCallback(debounceCallback);
            changeDebounce.setDelayMillis(delay);
            changeDebounce.setLifecycle(lifecycleOwner);
            return changeDebounce;
        }
    }

    private void setLifecycle(LifecycleOwner viewLifecycleOwner) {
        Lifecycle lifecycle = viewLifecycleOwner.getLifecycle();
        lifecycle.addObserver(this);
    }

    private void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public void post(ArrayList<T> arrayList) {
        debounceHandler.removeCallbacks(debounceWorker);
        debounceWorker = new DebounceRunnable<T>(arrayList, debounceCallback);
        debounceHandler.postDelayed(debounceWorker, delayMillis);
    }

    public void setCallback(DebounceCallback<T> debounceCallback) {
        this.debounceCallback = debounceCallback;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unwatch() {
        debounceHandler.removeCallbacks(debounceWorker);
    }


    private static class DebounceRunnable<T> implements Runnable {

        private final ArrayList<T> arrayList;
        private final DebounceCallback<T> debounceCallback;

        DebounceRunnable(ArrayList<T> arrayList, DebounceCallback<T> debounceCallback) {
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
        void onFinished(@NonNull ArrayList<T> arrayList);
    }


}