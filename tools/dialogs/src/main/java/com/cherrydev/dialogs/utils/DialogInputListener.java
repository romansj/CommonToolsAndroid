package com.cherrydev.dialogs.utils;

public interface DialogInputListener {
    void onPositiveButtonClicked(String newText);

    default void onNegativeButtonClicked() {
    }
}
