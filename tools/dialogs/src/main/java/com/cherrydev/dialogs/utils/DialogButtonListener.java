package com.cherrydev.dialogs.utils;

public interface DialogButtonListener {
    void onPositiveButtonClicked();

    default void onNegativeButtonClicked() {
    }
}
