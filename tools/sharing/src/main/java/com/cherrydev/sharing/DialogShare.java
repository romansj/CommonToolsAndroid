package com.cherrydev.sharing;

import static com.cherrydev.sharing.AppListHelper.copyToClipboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DialogShare extends BottomSheetDialogFragment implements ShareAdapter.ItemClickListener {

    public static final String uploadActivity = "com.google.android.apps.docs.shareitem.UploadMenuActivity";
    public static final String copyActivity = "com.google.android.apps.docs.drive.clipboard.SendTextToClipboardActivity";
    public static final String packageDocs = "com.google.android.apps.docs";

    public static final String notesCopyActivity = "com.jromans.notes.tools.IntentActivity";
    public static final String notesPackage = "com.jromans.notes";

    private ShareClickListener listener;
    private String text;
    private Intent intent;
    private ShareAdapter shareAdapter;

    public static DialogShare newInstance(String text, ShareClickListener listener) {
        DialogShare fragment = new DialogShare();
        fragment.setText(text);
        fragment.setListener(listener);
        return fragment;
    }

    public static DialogShare newInstance(String text) {
        return newInstance(text, null);
    }


    public void setListener(ShareClickListener listener) {
        this.listener = listener;
    }

    public void setText(String text) {
        this.text = text;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Activity context = getActivity();


        View sheetView = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);


        if (savedInstanceState != null) text = savedInstanceState.getString("TEXT");

        mListSwitcher = sheetView.findViewById(R.id.switcher);


        intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");


        shareAdapter = new ShareAdapter(new ArrayList<>(), intent);
        shareAdapter.setHasStableIds(true);
        shareAdapter.setmIntent(intent);
        shareAdapter.setClickListener(this);

        ShareViewModel model = new ViewModelProvider(getActivity()).get(ShareViewModel.class);
        model.getApps(requireContext(), intent).observe(this, dialogItems -> {
            shareAdapter.setmData(dialogItems);
            showItems(dialogItems);
        });

        //model.setApps(AppListHelper.getInstance().getApps(context));


        RecyclerView rv = sheetView.findViewById(R.id.rv_share);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(context, 4));
        rv.setAdapter(shareAdapter);

        showItems(new ArrayList<>());

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(sheetView);


        sheetView.findViewById(R.id.bttnClose).setOnClickListener(v -> dialog.dismiss());

        return dialog;


    }

    ViewSwitcher mListSwitcher;

    void showItems(List<DialogItemShare> items) {
        if (items.size() > 0) {
            if (R.id.rv_share == mListSwitcher.getNextView().getId()) {
                mListSwitcher.showNext();
            }
        } else if (R.id.text_empty == mListSwitcher.getNextView().getId()) {
            mListSwitcher.showNext();
        }
    }


    public static final String KEY_NOTE_ID = "KEY_NOTE_ID";

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("TEXT", text);
        super.onSaveInstanceState(outState);
    }

    //implementing rv adapter's interface, calling dialogfrag's click listener (interface)
    @Override
    public void onShareRVItemClick(View view, int position) {

        //ResolveInfo info = ShareUtilsNew.getActivities().get(position);

        //in case the user uninstalls and app and then clicks it in a share sheet
        // apps are not reloaded each time (performance vs android default sheet) and there is no listener for app (un)install events - unnecessary permission
        try {
            DialogItemShare item = shareAdapter.getItem(position);
            switch (item.getActivityName()) {
                case uploadActivity:
                    intent.setComponent(new ComponentName(packageDocs, uploadActivity));
                    startActivity(intent);
                    break;
                case copyActivity:
                    intent.setPackage(item.getPackageName());
                    intent.setComponent(new ComponentName(packageDocs, copyActivity));
                    startActivity(intent);
                    break;

                case copyToClipboard:
                    copyToClipboard();
                    break;

                default:
                    intent.setPackage(item.getPackageName());
                    startActivity(intent);
                    break;
            }

        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), requireContext().getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
        }


        dismiss();

        if (listener != null) listener.onShareItemClick(view, position);
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getContext(), requireContext().getString(R.string.copy_to_clipboard_success), Toast.LENGTH_SHORT).show();
    }

    public interface ShareClickListener {
        void onShareItemClick(View view, int position);
    }
}
