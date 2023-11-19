package com.cherrydev.commontools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.cherrydev.commontools.databinding.FragmentDialogDemoBinding;
import com.cherrydev.dialogs.DialogInfo;
import com.cherrydev.dialogs.DialogInfoList;
import com.cherrydev.dialogs.confirm.DialogBottomInputConfirm;
import com.cherrydev.dialogs.confirm.DialogInputConfirm;
import com.cherrydev.dialogs.progress.DialogLoading;
import com.cherrydev.dialogs.progress.DialogProgress;

import java.util.List;


public class FragmentDialogDemo extends Fragment {

    FragmentDialogDemoBinding binding;

    public static FragmentDialogDemo newInstance() {
        FragmentDialogDemo fragment = new FragmentDialogDemo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDialogDemoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View frView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(frView, savedInstanceState);

        binding.btnInputConfirm.setOnClickListener(v -> {
            DialogInputConfirm.newInstance("Dialog title", "Message text", "Yes", "No").show(getChildFragmentManager(), null);
        });

        binding.btnInputConfirmBottom.setOnClickListener(v -> {
            DialogBottomInputConfirm.newInstance("Dialog title", null).show(getChildFragmentManager(), null);
        });

        binding.btnProgress.setOnClickListener(v -> {
            DialogProgress.newInstance(100, "Dialog title", "Message text", true).show(getChildFragmentManager(), null);
        });

        binding.btnLoading.setOnClickListener(v -> {
            DialogLoading.newInstance().show(getChildFragmentManager(), null);
        });

        binding.btnInfo.setOnClickListener(v -> {
            DialogInfo.newInstance("", List.of(
                            new Pair<>("Item0", "1"),
                            new Pair<>("Item1", "2"),
                            new Pair<>("Item2", "3")
                    )
                    , "Yes", "No").show(getChildFragmentManager(), null);
        });


        binding.btnInfoList.setOnClickListener(v -> {
            DialogInfoList.newInstance("", "", List.of(
                            "Item0", "1",
                            "Item1", "2",
                            "Item2", "3"
                    )
                    , "Yes", "No").show(getChildFragmentManager(), null);
        });

    }
}