package com.cherrydev.dialogs.progress;

import static com.cherrydev.dialogs.utils.Constants.ARG_INDETERMINATE;
import static com.cherrydev.dialogs.utils.Constants.ARG_MAX;
import static com.cherrydev.dialogs.utils.Constants.ARG_MESSAGE;
import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.transition.TransitionManager;

import com.cherrydev.commontools.R;
import com.transitionseverywhere.Crossfade;

public class DialogProgress extends DialogFragment {


    public static final long MEDIUM = 300;

    public static DialogProgress newInstance(int max, String title, String description, boolean indeterminate) {

        Bundle args = new Bundle();
        args.putInt(ARG_MAX, max);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, description);
        args.putBoolean(ARG_INDETERMINATE, indeterminate);
        DialogProgress fragment = new DialogProgress();
        fragment.setArguments(args);
        return fragment;
    }


    private ProgressBar progressBar;
    private int max;
    private TextView tvDescription;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);
        boolean indeterminate = args.getBoolean(ARG_INDETERMINATE, false);
        max = args.getInt(ARG_MAX);


        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null);
        tvDescription = view.findViewById(R.id.tv_description);

        progressBar = view.findViewById(R.id.progress_bar);
        if (!indeterminate) progressBar.setMax(max);
        progressBar.setIndeterminate(indeterminate);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        if (!message.isEmpty()) tvDescription.setText(message);


        view.findViewById(R.id.button_close).setOnClickListener(view1 -> dismiss());


        builder.setTitle(title);
        builder.setView(view);


        //builder.setCancelable(false); // doesn't work
        AlertDialog alertDialog = builder.create();
        //alertDialog.setCancelable(false); // doesn't work
        setCancelable(false); // does work!
        return alertDialog;
    }


    public void setProgress(float progress) {
        if (progressBar == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            progressBar.setProgress((int) progress, true);
        else progressBar.setProgress((int) progress);
    }

    public void setDescription(String s) {
        if (tvDescription == null) return;

        TransitionManager.beginDelayedTransition((ViewGroup) tvDescription.getParent(),
                new Crossfade().setDuration(MEDIUM).setInterpolator(new DecelerateInterpolator()));

        tvDescription.setText(s);
    }
}
