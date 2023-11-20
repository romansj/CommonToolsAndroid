package com.cherrydev.dialogs.confirm;

import static com.cherrydev.dialogs.utils.Constants.ARG_MESSAGE;
import static com.cherrydev.dialogs.utils.Constants.ARG_NEGATIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_POSITIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


import com.cherrydev.dialogs.utils.DialogInputListener;

import io.github.romansj.tools.dialogs.R;


public class DialogInputConfirm extends DialogFragment {

    private DialogInputListener listener;


    public static DialogInputConfirm newInstance(String title, String message, String positiveText, String negativeText) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeText);

        DialogInputConfirm fragment = new DialogInputConfirm();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);
        String message = args.getString(ARG_MESSAGE);
        String positive = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        String negative = args.getString(ARG_NEGATIVE_BUTTON_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(title);


        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_confirm, null);
        builder.setView(view);

        EditText editText = view.findViewById(R.id.edit_text);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Enter a number");


        builder.setPositiveButton(positive, (dialogInterface, i) -> {
            if (listener != null)
                listener.onPositiveButtonClicked(editText.getText().toString().trim());
        });
        builder.setNegativeButton(negative, (dialog, which) -> {
            if (listener != null) listener.onNegativeButtonClicked();
        });


        if (!message.isEmpty()) {
            builder.setMessage(message);
        }

        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            editText.postDelayed(() -> {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }, 200);

        });

        return alertDialog;
    }


    public void setListener(DialogInputListener dialogButtonListener) {
        this.listener = dialogButtonListener;
    }


}
