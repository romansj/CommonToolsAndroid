package com.cherrydev.common.utils;

import androidx.fragment.app.Fragment;


public class FragmentPropertiesPrinter {
    public void print(Fragment fragment) {
        print("", fragment);
    }


    public void print(String s, Fragment fragment) {
        if (fragment == null) {
            //Timber.e(fragment + " is null.");
            return;
        }

        boolean isAdded = fragment.isAdded();
        boolean isVisible = fragment.isVisible();
        boolean isDetached = fragment.isDetached();
        boolean isRemoving = fragment.isRemoving();
        boolean isInLayout = fragment.isInLayout();
        boolean isViewNull = fragment.getView() == null;


        System.out.println(s + " isAdded " + isAdded +
                " isVisible " + isVisible +
                " isDetached " + isDetached +
                " isRemoving " + isRemoving +
                " isInLayout " + isInLayout +
                " getView == null " + isViewNull);
    }
}
