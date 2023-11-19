package com.cherrydev.common;

import androidx.annotation.Nullable;


//import io.realm.mongodb.AppException;

public class MyResult<T> {
    private T result;
    private Exception error;
    private Throwable throwable;


    private MyResult() {
    }


    public MyResult(@Nullable T result, @Nullable Exception exception) {
        this.result = result;
        this.error = exception;
    }

    private MyResult(@Nullable T result, @Nullable Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    /**
     * Creates a successful request result with no return value.
     */
    public static <T> MyResult<T> success() {
        return new MyResult(null, null);
    }

    /**
     * Creates a successful request result with a return value.
     *
     * @param result the result value.
     */
    public static <T> MyResult<T> withResult(T result) {
        return new MyResult<>(result, null);
    }

    /**
     * Creates a failed request result. The request failed for some reason, either because there
     * was a network error or the Realm Object Server returned an error.
     *
     * @param exception error that occurred.
     * @return
     */
    public static <T> MyResult<T> withError(Exception exception) {
        return new MyResult<>(null, exception);
    }

    public static <T> MyResult<T> withError(T result) {
        return new MyResult<>(result, null);
    }

    public static <T> MyResult<T> withError(Throwable throwable) {
        return new MyResult<>(null, throwable);
    }

    /**
     * Returns whether or not request was successful
     *
     * @return {@code true} if the request was a success, {@code false} if not.
     */
    public boolean isSuccess() {
        return error == null;
    }

    /**
     * Returns the response in case the request was a success.
     *
     * @return the response value in case of a successful request.
     */
    public T get() {
        return result;
    }

    /**
     * Returns the response if the request was a success. If it failed, the default value is
     * returned instead.
     *
     * @return the response value in case of a successful request. If the request failed, the
     * default value is returned instead.
     */
    public T getOrDefault(T defaultValue) {
        return isSuccess() ? result : defaultValue;
    }

    /**
     * If the request was successful the response is returned, otherwise the provided error
     * is thrown.
     *
     * @return the response object in case the request was a success.
     * @throws Exception provided error in case the request failed.
     */
    public T getOrThrow() throws Exception {
        if (isSuccess()) {
            return result;
        } else {
            throw error;
        }
    }

    /**
     * Returns the error in case of a failed request.
     *
     * @return the {@link Exception} in case of a failed request.
     */
    public Exception getError() {
        return error;
    }


    public Throwable getThrowable() {
        return throwable;
    }
}
