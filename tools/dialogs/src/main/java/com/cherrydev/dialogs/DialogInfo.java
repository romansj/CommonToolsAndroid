package com.cherrydev.dialogs;

import static com.cherrydev.dialogs.utils.Constants.ARG_LIST;
import static com.cherrydev.dialogs.utils.Constants.ARG_NEGATIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_POSITIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DialogInfo extends DialogFragment {


    public static DialogInfo newInstance(String title, List<Pair<String, String>> list, String positiveText, String negativeText) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putStringArrayList(ARG_LIST, new ArrayList<>(
                list.stream()
                        .map(stringStringPair -> stringStringPair.first + "\n" + stringStringPair.second)
                        .collect(Collectors.toList())
        ));
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeText);

        DialogInfo fragment = new DialogInfo();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE);


        var list = args.getStringArrayList(ARG_LIST);
        String positive = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        String negative = args.getString(ARG_NEGATIVE_BUTTON_TEXT);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_info, null);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new UsedDataAdapter(list));

        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(title);


        builder.setView(view);
        builder.setTitle(title);
        builder.setPositiveButton(positive, null);

        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;
    }
}
