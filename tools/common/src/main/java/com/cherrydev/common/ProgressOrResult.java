package com.cherrydev.common;

public final class ProgressOrResult<T> {
    public final int progress;
    public final T result;

    public ProgressOrResult(int progress, T result) {
        this.progress = progress;
        this.result = result;
    }
}
