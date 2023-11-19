package com.cherrydev.commontools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cherrydev.time.CommonTimeUtils;

import java.time.LocalTime;
import java.util.Calendar;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var duration = CommonTimeUtils.Format.formatDuration(3660, Calendar.SECOND);
        Timber.d("duration " + duration);


        var endsInString = CommonTimeUtils.Format.getEndsInString(6660000);
        Timber.d("endsInString " + endsInString);


        var timeSpanString = CommonTimeUtils.Format.getTimeSpanString(this, System.currentTimeMillis() - 1000000, System.currentTimeMillis());
        Timber.d("timeSpanString " + timeSpanString);


        var formattedTimeString = CommonTimeUtils.Format.toFormattedTimeString(LocalTime.now(), this);
        Timber.d("formattedTimeString " + formattedTimeString);


        var fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, FragmentDialogDemo.newInstance()).commit();
    }
}