package com.cherrydev.dialogs.confirm;


import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cherrydev.dialogs.utils.DialogInputListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import io.github.romansj.tools.dialogs.databinding.DialogBottomInputConfirmBinding;

//https://guides.codepath.com/android/using-dialogfragment
public class DialogBottomInputConfirm extends BottomSheetDialogFragment {

    private DialogBottomInputConfirmBinding binding;
    private DialogInputListener listener;


    public static DialogBottomInputConfirm newInstance(String title, DialogInputListener listener) {
        DialogBottomInputConfirm fragment = new DialogBottomInputConfirm();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);


        fragment.setArguments(args);
        fragment.setListener(listener);

        return fragment;
    }

    private void setListener(DialogInputListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);


        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        binding = DialogBottomInputConfirmBinding.inflate(LayoutInflater.from(requireActivity()));
        View parentView = binding.getRoot();
        bottomSheetDialog.setContentView(parentView);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            View view = requireActivity().getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });

        EditText editText = binding.inputEditText;
        bottomSheetDialog.setOnShowListener(dialog -> {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });

        MaterialButton buttonSave = binding.buttonSave;


        editText.setText(title);
        editText.setSelection(editText.length());


        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                buttonSave.performClick();
                return true;
            }
            return false;
        });

        buttonSave.setOnClickListener(v1 -> {
            listener.onPositiveButtonClicked(editText.getText().toString().trim());
            dismiss();
        });

        bottomSheetDialog.show();


        return bottomSheetDialog;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NotNull final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //so the activity views don't move
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        //so the dialog is expanded. otherwise keyboard covers it
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.setOnShowListener(dialogInterface -> {


            new Handler().postDelayed(() -> {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }, 250);


        });
    }
}