package com.cherrydev.time;

import java.util.Date;

public class TimeFormatHelper {

    private static TimeFormatHelper INSTANCE;

    public static TimeFormatHelper getInstance() {
        if (INSTANCE == null) INSTANCE = new TimeFormatHelper();
        return INSTANCE;
    }

    private TimeFormatHelper() {

    }


    private String lastDateTime = "";
    private long lastTime = -1;

    public String getDateTime(Date date) {
        if (lastTime != -1 && lastTime == date.getTime()) {
            return lastDateTime;
        }

        //  Timber.d("lastTime == newTime " + (lastTime == date.getTime()));

        lastDateTime = CommonTimeUtils.Format.formatDateTime(date);
        lastTime = date.getTime();


        return lastDateTime;
    }
}
