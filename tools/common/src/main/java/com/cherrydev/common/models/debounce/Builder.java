package com.cherrydev.common.models.debounce;

import androidx.lifecycle.LifecycleOwner;

public class Builder<T> {

    private int delay;
    private LifecycleOwner lifecycleOwner;
    private ArrayListChangeDebounce.DebounceCallback<T> debounceCallback;

    public Builder<T> setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public Builder<T> setLifecycle(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        return this;
    }

    public Builder<T> watch(ArrayListChangeDebounce.DebounceCallback<T> debounceCallback) {
        this.debounceCallback = debounceCallback;
        return this;
    }

    public ArrayListChangeDebounce<T> build() {
        ArrayListChangeDebounce<T> changeDebounce = new ArrayListChangeDebounce();
        changeDebounce.setCallback(debounceCallback);
        changeDebounce.setDelayMillis(delay);
        changeDebounce.setLifecycle(lifecycleOwner);
        return changeDebounce;
    }
}
