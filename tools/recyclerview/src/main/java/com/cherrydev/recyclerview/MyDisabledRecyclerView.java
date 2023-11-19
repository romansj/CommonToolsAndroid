package com.cherrydev.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

//Disable nested recyclerview focus
//https://stackoverflow.com/a/37155806/4673960
public class MyDisabledRecyclerView extends RecyclerView {
    public MyDisabledRecyclerView(Context context) {
        super(context);
    }

    public MyDisabledRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDisabledRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}