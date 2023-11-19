package com.cherrydev.navigation.newbase;

import androidx.fragment.app.Fragment;

public interface IDestination {
    boolean isAddToBackstack();

    Fragment initFragment();

    int getID();
}
