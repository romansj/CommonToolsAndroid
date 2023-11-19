package com.cherrydev.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class KeyboardUtils {

    private static KeyboardUtils INSTANCE;
    private Rect rect;

    public static KeyboardUtils get() {
        if (INSTANCE == null) INSTANCE = new KeyboardUtils();
        return INSTANCE;
    }

    private KeyboardUtils() {

    }

    private Rect getRect() {
        if (rect == null) rect = new Rect();
        return rect;
    }


    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity, null);
    }

    public static void hideKeyboard(Activity activity, EditText editText) {
        if (activity == null) return;

        View view = activity.getCurrentFocus();
        if (view == null) return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (editText != null) editText.clearFocus();
    }


    public static void showKeyboard(EditText editText, InputMethodManager imm) {
        editText.requestFocus();
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    public static void showKeyboard(EditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    public enum Mode {
        MOVE_WITH_KEYBOARD(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE),
        DO_NOTHING(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING),
        PAN(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        private final int value;

        Mode(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    private static Mode mode;

    public static void setMode(Activity activity, Mode mode) {
        activity.getWindow().setSoftInputMode(mode.getValue());
        KeyboardUtils.mode = mode;
    }

    //convenience method, as most screens in Notes are fragments
    public static void setMode(Fragment fragment, Mode mode) {
        FragmentActivity activity = fragment.requireActivity();
        setMode(activity, mode);
    }


    public static Mode getMode() {
        return mode;
    }
}
