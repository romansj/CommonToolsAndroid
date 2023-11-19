package com.cherrydev.sharing;

import android.graphics.drawable.Drawable;

public class DialogItemShare extends DialogItem {

    private String packageName;
    private String activityName;

    public DialogItemShare(String name, Drawable drawable, String packageName, String activityName) {
        super(name, drawable);
        this.packageName = packageName;
        this.activityName = activityName;
    }


    public String getPackageName() {
        return packageName;
    }

    public String getActivityName() {
        return activityName;
    }
}