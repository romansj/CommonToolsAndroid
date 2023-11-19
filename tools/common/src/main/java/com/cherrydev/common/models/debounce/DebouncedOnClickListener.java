package com.cherrydev.common.models.debounce;

import android.os.SystemClock;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * A Debounced OnClickListener
 * Rejects clicks that are too close together in time.
 * This class is safe to use as an OnClickListener for multiple views, and will debounce each one separately.
 */

//https://gist.github.com/rfreedman/5573388

public abstract class DebouncedOnClickListener implements View.OnClickListener {

    private final long minimumInterval;
    private Map<View, Long> lastClickMap;

    /**
     * Implement this in your subclass instead of onClick
     *
     * @param v The view that was clicked
     */
    public abstract void onDebouncedClick(View v);

    /**
     * The one and only constructor
     *
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    public DebouncedOnClickListener(long minimumIntervalMsec) {
        this.minimumInterval = minimumIntervalMsec;
        this.lastClickMap = new WeakHashMap<>();
    }

    @Override
    public void onClick(View clickedView) {
        long previousClickTimestamp = lastClickMap.get(clickedView) == null ? -1 : lastClickMap.get(clickedView);
        long currentTimestamp = SystemClock.uptimeMillis();

        lastClickMap.put(clickedView, currentTimestamp);
        if (previousClickTimestamp == -1 || (currentTimestamp - previousClickTimestamp > minimumInterval)) {
            onDebouncedClick(clickedView);
        }
    }
}