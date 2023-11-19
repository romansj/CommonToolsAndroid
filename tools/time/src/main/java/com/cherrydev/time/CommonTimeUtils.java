package com.cherrydev.time;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class CommonTimeUtils {

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;

    private static DateFormat formatDate = DateFormat.getDateInstance(DateFormat.SHORT);
    private static DateFormat formatTime = DateFormat.getTimeInstance(DateFormat.SHORT);
    private static DateFormat formatDateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

    private static final String justNow = "just now";


    private CommonTimeUtils() {

    }

    public static class Format {
        private static String lastString;
        private static long lastDifference = -1;

        public static String getTimeSpanString(Context context, boolean friendly, long oldTime, long newTime) {
            long difference = newTime - oldTime;


            if (lastDifference != -1) {
                long newMinusOld = difference - lastDifference;
                if (newMinusOld < 0) newMinusOld *= -1;

                boolean sThanMin = newMinusOld < MINUTE;

                if (lastDifference == difference || sThanMin) {
                    if (sThanMin) lastDifference = difference;
                    return lastString;
                }
            }


            lastDifference = difference;


            String timeString;

            if (friendly) {
                if (difference < MINUTE) timeString = justNow;
                else if (difference > DAY)
                    timeString = DateUtils.formatDateTime(context, oldTime, DateUtils.FORMAT_SHOW_DATE);
                else
                    timeString = DateUtils.getRelativeTimeSpanString(oldTime, newTime, 0L, DateUtils.FORMAT_ABBREV_ALL).toString();

            } else {
                timeString = TimeFormatHelper.getInstance().getDateTime(new Date(oldTime));
            }

            lastString = timeString;
            return timeString;
        }


        public static String getTimeSpanString(Context context, long oldTime, long newTime) {
            return getTimeSpanString(context, true, oldTime, newTime);
        }


        public String formatTime(long time) {
            return formatTime.format(new Date(time));
        }


        public static String formatDateTime(long time) {
            return formatDateTime.format(new Date(time));
        }


        public static String formatDateTime(Date date) {
            return formatDateTime.format(date);
        }


        public static String formatDate(long time) {
            return formatDate.format(time);
        }


        public static String getEndsInString(long diff) {
            if (formatDuration(diff, Calendar.MILLISECOND).equals("now"))
                return "Ends in less than a minute";

            return "Ends in " + formatDuration(diff, Calendar.MILLISECOND);
        }

        public static String getDurationString(long diff) {
            if (formatDuration(diff, Calendar.MILLISECOND).equals("now"))
                return "Less than a minute";

            return formatDuration(diff, Calendar.MILLISECOND);
        }

        public static String formatDateTime(Context context, long time) {
            return DateUtils.formatDateTime(context, time, DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);
        }


        public static String toFormattedTimeString(long time, Context context) {
            LocalTime localTime = Convert.toLocalTime(time);
            return toFormattedTimeString(localTime, context);
        }

        public static String toFormattedTimeString(LocalTime localTime, Context context) {
            boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(context);
            Locale locale = is24HourFormat ? Locale.UK : Locale.US;

            String formatted = localTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale));
            return formatted;
        }


        public static String toFormattedDateTimeString(LocalDateTime localTime, Context context) {
            return toFormattedDateTimeString(localTime, android.text.format.DateFormat.is24HourFormat(context));
        }

        public static String toFormattedDateTimeString(LocalDateTime localTime, boolean is24HourFormat) {
            Locale locale = is24HourFormat ? Locale.UK : Locale.US;

            String formatted = localTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale));
            return formatted;
        }


        //modified from https://ao.ms/human-readable-duration-format-in-java/
        private static String formatDuration(long seconds) {
            return seconds < 60 ?
                    "now"
                    :
                    Arrays.stream(
                                    new String[]{
                                            formatTime("year", (seconds / 31536000)),
                                            formatTime("day", (seconds / 86400) % 365),
                                            formatTime("hour", (seconds / 3600) % 24),
                                            formatTime("minute", (seconds / 60) % 60)
                                    })
                            .filter(e -> !e.equals(""))
                            .collect(Collectors.joining(", "))
                            .replaceAll(", (?!.+,)", " and ");
        }


        /**
         * @param value Value to format. Value is interpreted by second parameter type
         * @param type  Specify Calendar {@link java.util.Calendar.MILLISECOND} or {@link java.util.Calendar.SECOND}
         * @return Duration formatted as e.g. 1 year and 1 month and 1 day and 1 hour and 1 minute
         */
        public static String formatDuration(long value, int type) {
            String s;

            switch (type) {
                default:
                case Calendar.MILLISECOND:
                    long millis = value / 1000;
                    s = formatDuration(millis);
                    break;

                case Calendar.SECOND:
                    s = formatDuration(value);
                    break;
            }

            return s;
        }


        private static String formatTime(String s, long time) {
            return time == 0 ? "" : time + " " + s + (time == 1 ? "" : "s");
        }
    }


    public static class Convert {
        public static LocalDate toLocalDate(Date date) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            return res;
        }

        public static LocalTime toLocalTime(Date time) {
            Instant instant = Instant.ofEpochMilli(time.getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            return res;
        }


        public static LocalDateTime toLocalDateTime(long millis) {
            Instant instant = Instant.ofEpochMilli(millis);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime;
        }

        public static LocalDateTime toLocalDateTime(Date date) {
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime;
        }


        public static LocalTime toLocalTime(long time) {
            return toLocalTime(new Date(time));
        }


        public static Date toDate(LocalDate ld) {
            Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }


        public static Date toDate(LocalDate ld, LocalTime lt) {
            Instant instant = lt.atDate(ld).atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }

        public static Date toDate(LocalDateTime ldt) {
            Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }

        public static long toMillis(LocalDateTime ldt) {
            long milli = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            return milli;
        }
    }


    public static LocalDate adjustDate(LocalTime itemTime, LocalTime timeNow) {
        int compareResult = itemTime.compareTo(timeNow);
        //System.out.println("compare " + compareResult);

        //selected item is <before> current time
        LocalDate localDate = LocalDate.now();
        if (compareResult < 0) {
            //add a day (in this case 'next time' is a day from 'now')
            localDate = LocalDate.now().plusDays(1);
        }
        return localDate;
    }

    public static LocalDate adjustDate(LocalDate dateToAdjust, LocalTime itemTime, LocalTime timeNow) {
        int compareResult = itemTime.compareTo(timeNow);

        //selected item is <before> current time
        if (compareResult < 0) {
            //add a day (in this case 'next time' is a day from 'now')
            dateToAdjust = dateToAdjust.plusDays(1);
        }
        return dateToAdjust;
    }

    /**
     * date when to start:
     * 1 current <= start (thus <= end) = today
     * 2 current = start (thus <= end) = today
     * 3 current > start (but  <= end) = today
     * 4 current > start (and  >= end) = tomorrow
     * <p>
     * or in simpler way same thing:
     * before start = 1
     * between start and end = 2,3
     * after end = 4
     *
     * @param startTime event start time
     * @param endTime   event end time (cannot be equal to startTime)
     * @return Returns the start date for the event, either today because event time is upcoming or end time is upcoming, or tomorrow, because end time has passed.
     */
    public static LocalDate getStartDate(LocalTime startTime, LocalTime endTime, LocalTime timeNow, LocalDate dateNow) {
        int compareResultStart = timeNow.compareTo(startTime); //1
        int compareResultEnd = timeNow.compareTo(endTime); //-1
        int compareResultEndStart = endTime.compareTo(startTime); //1

        // started yesterday e.g. at 10PM
        if (compareResultStart < 1 && compareResultEnd < 1 && compareResultEndStart < 1)
            return dateNow.minusDays(1);

        //start time is now (0) or in future (-1) AKA before
        if (compareResultStart < 1) return dateNow; // start <= now
        if (compareResultEnd < 1) return dateNow; //between = end <= now
        if (compareResultEndStart < 1) return dateNow;
        else return dateNow.plusDays(1);  //after
    }


}
