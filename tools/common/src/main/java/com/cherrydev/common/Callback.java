package com.cherrydev.common;


public interface Callback<T> {
    /**
     * Returns the result of the request when available.
     *
     * @param result the request response.
     */
    void onResult(MyResult<T> result);
}
