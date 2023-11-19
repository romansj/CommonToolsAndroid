package com.cherrydev.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;


public class DurationStringValidator {

    @Test
    public void doesNotReturnSecondsForSeconds() {
        Assertions.assertEquals("now", CommonTimeUtils.Format.formatDuration(1, Calendar.SECOND));
    }

    @Test
    public void doesNotReturnSecondsForMinuteAndSeconds() {
        Assertions.assertEquals("1 minute", CommonTimeUtils.Format.formatDuration(62, Calendar.SECOND));
    }

    @Test
    public void returnsMinutes() {
        Assertions.assertEquals("2 minutes", CommonTimeUtils.Format.formatDuration(120, Calendar.SECOND));
    }

    @Test
    public void returnsHour() {
        Assertions.assertEquals("1 hour", CommonTimeUtils.Format.formatDuration(3600, Calendar.SECOND));
    }

    @Test
    public void doesNotReturnSecondsForHourAndMinuteAndSeconds() {
        Assertions.assertEquals("1 hour and 1 minute", CommonTimeUtils.Format.formatDuration(3662, Calendar.SECOND));
    }


    @Test
    public void returnsSameResultWithMillisAsWithSeconds() {
        Assertions.assertEquals(CommonTimeUtils.Format.formatDuration(30000, Calendar.MILLISECOND), CommonTimeUtils.Format.formatDuration(30, Calendar.SECOND)); //seconds
        Assertions.assertEquals(CommonTimeUtils.Format.formatDuration(60000, Calendar.MILLISECOND), CommonTimeUtils.Format.formatDuration(60, Calendar.SECOND)); //minute
        Assertions.assertEquals(CommonTimeUtils.Format.formatDuration(3600000, Calendar.MILLISECOND), CommonTimeUtils.Format.formatDuration(3600, Calendar.SECOND)); //hour
        Assertions.assertEquals(CommonTimeUtils.Format.formatDuration(86400000, Calendar.MILLISECOND), CommonTimeUtils.Format.formatDuration(86400, Calendar.SECOND)); //day
    }
}
