package com.cherrydev.common.utils;

import android.annotation.SuppressLint;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

@SuppressLint("RestrictedApi")
public class PopupHelper {


    private int menuRes;
    private View v;
    private MenuBuilder.Callback callback;

    /**
     * Set view from which the popup menu will pop up
     */
    public PopupHelper setView(View view) {
        this.v = view;
        return this;
    }

    public PopupHelper setMenu(int menuResource) {
        this.menuRes = menuResource;
        return this;
    }

    @SuppressLint("RestrictedApi")
    public PopupHelper setCallback(MenuBuilder.Callback callback) {
        this.callback = callback;
        return this;
    }

    MenuPopupHelper optionsMenu;
    MenuBuilder menuBuilder;

    public MenuPopupHelper build() {
        //https://stackoverflow.com/a/40839543/4673960

        menuBuilder = new MenuBuilder(v.getContext());
        MenuInflater inflater = new MenuInflater(v.getContext());
        inflater.inflate(menuRes, menuBuilder);
        optionsMenu = new MenuPopupHelper(v.getContext(), menuBuilder, v);
        optionsMenu.setForceShowIcon(true);

        // Set Item Click Listener
        menuBuilder.setCallback(callback);


        return optionsMenu;
    }


    public void dismiss() {
        if (optionsMenu != null) optionsMenu.dismiss();
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
