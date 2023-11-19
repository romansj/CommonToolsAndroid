package com.cherrydev.common.utils;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;


public class AnimationHelper {

    public class Durations {
        /**
         * 800
         */
        public static final int VERY_LONG = 800;
        /**
         * 500
         */
        public static final int LONG = 500;
        /**
         * 350
         */
        public static final int MEDIUM = 350;
        /**
         * 250
         */
        public static final int SHORT = 250;
        /**
         * 100
         */
        public static final int MINIMUM = 100;


    }

    private static int DURATION_SHORT = Durations.MINIMUM * 2;
    private static int DURATION_LONG = Durations.MINIMUM * 4;

    private static Handler handler = new Handler();

    public AnimationHelper() {

    }


    public static void hide(ViewGroup viewGroup) {
        if (viewGroup == null) return;


        Transition transition = new Fade();
        transition.setDuration(DURATION_SHORT);
        transition.addTarget(viewGroup);

        TransitionManager.beginDelayedTransition(viewGroup, transition);
        viewGroup.setVisibility(View.INVISIBLE);
    }


    public static void show(ViewGroup view) {
        if (view == null) return;


        handler.postDelayed(() -> {
            Transition transition2 = new Fade();
            transition2.setDuration(DURATION_SHORT);
            transition2.addTarget(view);

            TransitionManager.beginDelayedTransition(view, transition2);
            view.setVisibility(View.VISIBLE);

        }, DURATION_LONG);
    }


    public static void hideRV(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(null);
        hide(recyclerView);
    }


    public static void showRV(RecyclerView recyclerView, T animator) {
        handler.postDelayed(() -> {
            show(recyclerView);
            recyclerView.setItemAnimator(animator);
        }, DURATION_LONG);
    }

    abstract static class T extends RecyclerView.ItemAnimator {

    }
}
