package com.cherrydev.common.models;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//https://stackoverflow.com/questions/51919865/disappearing-fab-icon-on-navigation-fragment-change
//https://issuetracker.google.com/issues/117476935
public class FabulousFab extends FloatingActionButton {

    private Matrix matrix;

    public FabulousFab(Context context) {
        super(context);
    }

    public FabulousFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FabulousFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        matrix = getImageMatrix();
    }


    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setImageMatrix(matrix);
    }


    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        setImageMatrix(matrix);
    }
}
