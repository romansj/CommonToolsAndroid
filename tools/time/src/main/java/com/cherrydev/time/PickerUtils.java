package com.cherrydev.time;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class PickerUtils {

    public static void showTimePicker(LocalTime localTime, Context context, CallbackTime callback) {
        showTimePicker(localTime, context, -1, callback);
    }

    public static void showTimePicker(LocalTime localTime, Context context, int style, CallbackTime callback) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        TimePickerDialog.OnTimeSetListener listener = (timePicker, selHour, selMinute) -> {
            callback.onTimeSelected(LocalTime.of(selHour, selMinute, 0));
        };

        TimePickerDialog timePicker;
        if (style == -1)
            timePicker = new TimePickerDialog(context, listener, hour, minute, DateFormat.is24HourFormat(context));
        else
            timePicker = new TimePickerDialog(context, style, listener, hour, minute, DateFormat.is24HourFormat(context));

        timePicker.setCancelable(true);
        timePicker.show();
    }

    public static void showDatePicker(LocalDate localDate, Context context, CallbackDate callback) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();


        DatePickerDialog.OnDateSetListener listener = (datePicker, selYear, selMonth, selDay) -> {
            callback.onDateSelected(LocalDate.of(selYear, selMonth + 1, selDay));
        };


        DatePickerDialog datePicker = new DatePickerDialog(context, listener, year, month - 1, day);
        datePicker.setCancelable(true);
        datePicker.show();
    }


    public interface CallbackTime {
        void onTimeSelected(LocalTime localTime);
    }

    public interface CallbackDate {
        void onDateSelected(LocalDate localDate);
    }
}
