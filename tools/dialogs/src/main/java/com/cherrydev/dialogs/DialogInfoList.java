package com.cherrydev.dialogs;

import static com.cherrydev.dialogs.utils.Constants.ARG_LINK;
import static com.cherrydev.dialogs.utils.Constants.ARG_LIST;
import static com.cherrydev.dialogs.utils.Constants.ARG_MESSAGE;
import static com.cherrydev.dialogs.utils.Constants.ARG_NEGATIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_POSITIVE_BUTTON_TEXT;
import static com.cherrydev.dialogs.utils.Constants.ARG_TITLE;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cherrydev.commontools.databinding.DialogInfoListBinding;
import com.cherrydev.dialogs.utils.DialogButtonListener;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class DialogInfoList extends DialogFragment {

    private DialogButtonListener listener;


    public static DialogInfoList newInstance(String title, String message, List<String> list, String positiveText, String negativeText) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeText);

        args.putStringArrayList(ARG_LIST, new ArrayList<>(list));

        DialogInfoList fragment = new DialogInfoList();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        String title = args.getString(ARG_TITLE, "");
        String message = args.getString(ARG_MESSAGE, "");
        String positive = args.getString(ARG_POSITIVE_BUTTON_TEXT, "");
        String negative = args.getString(ARG_NEGATIVE_BUTTON_TEXT, "");
        List<String> list = args.getStringArrayList(ARG_LIST);
        String link = args.getString(ARG_LINK, "%link%");


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setTitle(title);
        if (!positive.isEmpty()) {
            builder.setPositiveButton(positive, (dialogInterface, i) -> {
                if (listener != null) listener.onPositiveButtonClicked();
            });
        }
        if (!negative.isEmpty()) {
            builder.setNegativeButton(negative, (dialog, which) -> {
                if (listener != null) listener.onNegativeButtonClicked();
            });
        }


        DialogInfoListBinding binding = DialogInfoListBinding.inflate(LayoutInflater.from(requireContext()));
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new UsedDataAdapter(list));
        if (!message.isEmpty()) binding.tvDescription.setText(message);

        binding.tvLink.setOnClickListener(v -> {
            openBrowser(link);
        });


        builder.setView(binding.getRoot());
        builder.setTitle(title);
        builder.setPositiveButton(positive, null);
        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;
    }


    private void openBrowser(String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
