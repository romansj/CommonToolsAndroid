package com.cherrydev.dialogs.progress;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cherrydev.commontools.R;


public class DialogLoading extends DialogFragment {

    public static DialogLoading newInstance() {
        return new DialogLoading();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View parentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_loading, null);

        builder
                .setView(parentView)
                .setCancelable(false);

        return builder.create();
    }


}
