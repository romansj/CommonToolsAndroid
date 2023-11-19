package com.cherrydev.sharing;

import static com.cherrydev.sharing.DialogShare.copyActivity;
import static com.cherrydev.sharing.DialogShare.notesPackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListHelper {

    private static AppListHelper appListHelper;

    public static AppListHelper getInstance() {
        if (appListHelper == null) appListHelper = new AppListHelper();
        return appListHelper;
    }


    private List<DialogItemShare> appsList;
    public static final String copyToClipboard = "com.jromans.notes.copyToClipboard";

    public List<DialogItemShare> getApps(Context context, Intent intent) {

        appsList = new ArrayList<>();

        PackageManager pm = context.getPackageManager();
        //Intent mIntent = createExampleIntent();


        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);

        for (ResolveInfo info : activities) {
            appsList.add(new DialogItemShare(
                    info.loadLabel(pm).toString(), info.loadIcon(pm),
                    info.activityInfo.packageName, info.activityInfo.name));
        }

        if (!copyActivityExists(activities)) {
            appsList.add(new DialogItemShare(context.getString(R.string.copy_to_clipboard), context.getDrawable(R.drawable.content_copy), notesPackage, copyToClipboard));
        }


        //alphabetical list of apps
        Collections.sort(appsList, (a, b) -> a.app.compareTo(b.app));


        return appsList;
    }

    private boolean copyActivityExists(List<ResolveInfo> activities) {
        for (ResolveInfo ri : activities) {
            if (ri.activityInfo.name.equals(copyActivity)) {
                return true;
            }
        }
        return false;
    }


    @NotNull
    private Intent createExampleIntent() {
        Intent mIntent = new Intent();
        mIntent.setAction(Intent.ACTION_SEND);
        mIntent.setType("text/plain");
        return mIntent;
    }

}