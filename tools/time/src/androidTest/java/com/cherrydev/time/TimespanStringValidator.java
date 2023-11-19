package com.cherrydev.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.jupiter.api.Test;

/**
 * Testing CommonTimeUtils getTimeSpanString method which requires Android Context (only because Android's DateFormat needs it to know 12/24h)
 * Differs from Duration tests in that getTimeSpanString returns relative difference in small periods but original date in long periods ...
 * Duration tests get the duration in years, months, days, hours, minutes
 * (Smallest return is one of the units, like '5 minutes' or '2 days', largest is a collection of them all like '22 years, 3 months, 5 days etc...')
 * To convert to and back from millis use https://currentmillis.com/
 */
public class TimespanStringValidator {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getContext();
        assertEquals("com.cherrydev.time.test", appContext.getPackageName());
    }


    private Context getContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }


    @Test
    public void checkTimeSpanJustNow() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622876430000L); //10:00:00, 10:00:30
        assertEquals("just now", timeSpanString);
    }


    @Test
    public void checkTimeSpanJustNowOneMinute() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622876460000L); //10:00:00, 10:01:00
        assertEquals("1 min. ago", timeSpanString);
    }

    @Test
    public void checkTimeSpanJustNowMax() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622876580000L); //10:00:00, 10:03:00
        assertEquals("3 min. ago", timeSpanString);
    }

    @Test
    public void checkTimeSpanOneHour() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622880000000L); //10:00:00, 11:00:00
        assertEquals("1 hr. ago", timeSpanString);
    }

    @Test
    public void checkTimeSpanTwentyThreeHours() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622959200000L); //5th 10:00, 6th 09:00
        assertEquals("23 hr. ago", timeSpanString);
    }

    @Test
    public void checkTimeSpanOneDay() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1622962800000L); //5th 10:00, 6th 10:00
        assertEquals("Yesterday", timeSpanString);
    }

    @Test
    public void checkTimeSpanTwoDays() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1623049200000L); //5th 10:00, 7th 10:00
        assertEquals("June 5", timeSpanString);
    }


    @Test
    public void checkTimeSpanTwoDaysAndSomeHours() {
        Context appContext = getContext();

        String timeSpanString = CommonTimeUtils.Format.getTimeSpanString(appContext, 1622876400000L, 1623067200000L); //5th 10:00, 7th 15:00
        assertEquals("June 5", timeSpanString);
    }


}