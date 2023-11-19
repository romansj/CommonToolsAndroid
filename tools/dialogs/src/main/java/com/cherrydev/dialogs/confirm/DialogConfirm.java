package com.cherrydev.dialogs.confirm;

import static com.cherrydev.dialogs.utils.Constants.ARG_CANCELABLE;
import static com.cherrydev.dialogs.utils.Constants.ARG_MESSAGE;
import static com.cherrydev.dialogs.utils.Constants.ARG_NEGATIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_POSITIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_STYLE;
import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;
import static com.cherrydev.dialogs.utils.StringUtils.isNullOrEmpty;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.cherrydev.dialogs.utils.DialogButtonListener;

public class DialogConfirm extends DialogFragment {

    private DialogButtonListener listener;

    public static DialogConfirm newInstance(String title, String message, String positiveText, String negativeText) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeText);

        DialogConfirm fragment = new DialogConfirm();
        fragment.setArguments(args);
        return fragment;
    }


    public static DialogConfirm newInstance(@NonNull String title, String description, @NonNull String positiveText, @NonNull String negativeText, int style) {
        return newInstance(title, description, positiveText, negativeText, style, true);
    }


    public static DialogConfirm newInstance(@NonNull String title, @NonNull String positiveText, @NonNull String negativeText) {
        return newInstance(title, null, positiveText, negativeText, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert, true);
    }

    public static DialogConfirm newInstance(@NonNull String title, String description, @NonNull String positiveText, @NonNull String negativeText, int style, boolean cancelable) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, description);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeText);
        args.putInt(ARG_STYLE, style);
        args.putBoolean(ARG_CANCELABLE, cancelable);

        DialogConfirm fragment = new DialogConfirm();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int style = args.getInt(ARG_STYLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), style);

        String title = args.getString(ARG_TITLE);
        String descr = args.getString(ARG_MESSAGE);
        String yes = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        String no = args.getString(ARG_NEGATIVE_BUTTON_TEXT);

        if (!isNullOrEmpty(title)) builder.setTitle(title);
        if (!isNullOrEmpty(descr)) builder.setMessage(descr);
        if (!isNullOrEmpty(yes))
            builder.setPositiveButton(yes, (dialog, which) -> listener.onPositiveButtonClicked());
        if (!isNullOrEmpty(no))
            builder.setNegativeButton(no, (dialog, which) -> listener.onNegativeButtonClicked());

        boolean cancelable = args.getBoolean(ARG_CANCELABLE);
        builder.setCancelable(cancelable);

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    public void setListener(DialogButtonListener dialogButtonListener) {
        this.listener = dialogButtonListener;
    }


}
