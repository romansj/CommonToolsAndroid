package com.cherrydev.dialogs.utils;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;


public class DialogUtils {

    //could create a class to hold all open dialogs and wouldn't need to hardcode fragments in hideDialogs() method. just loop through open dialogs.

    public void hideDialogs(FragmentManager fm, List<DialogFragment> fragments) {

        for (DialogFragment dialogFragment : fragments) {
            if (isDialogVisible(dialogFragment)) {
                dialogFragment.dismiss();
            }
        }

    }


    private boolean isDialogVisible(DialogFragment dialogFragment) {
        return dialogFragment != null
                && dialogFragment.getDialog() != null
                && dialogFragment.getDialog().isShowing()
                && !dialogFragment.isRemoving();
    }

}
