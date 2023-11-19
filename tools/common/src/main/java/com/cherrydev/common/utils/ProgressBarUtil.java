package com.cherrydev.common.utils;

import android.os.Build;
import android.widget.ProgressBar;

public class ProgressBarUtil {
    public static void setProgress(ProgressBar progressBar, int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true);
        } else {
            progressBar.setProgress(progress);
        }
    }
}
