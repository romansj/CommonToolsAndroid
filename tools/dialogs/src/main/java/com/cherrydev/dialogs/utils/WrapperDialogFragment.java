package com.cherrydev.dialogs.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.cherrydev.commontools.R;
import com.cherrydev.commontools.databinding.FragmentWrapperBinding;


public class WrapperDialogFragment extends DialogFragment {


    private FragmentWrapperBinding binding;
    private Fragment fragmentToWrap;


    public static WrapperDialogFragment newInstance(Fragment fragmentToWrap) {
        WrapperDialogFragment fragment = new WrapperDialogFragment();
        fragment.setFragmentToWrap(fragmentToWrap);
        return fragment;
    }


    private void setFragmentToWrap(Fragment fragmentToWrap) {
        this.fragmentToWrap = fragmentToWrap;
    }

    LinearLayout dialogView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = FragmentWrapperBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext(), com.google.android.material.R.style.MaterialAlertDialog_MaterialComponents);
        dialogView = binding.getRoot();
        dialogBuilder.setView(dialogView);

        return dialogBuilder.create();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return dialogView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_view_dialog, fragmentToWrap).commit();
    }
}
