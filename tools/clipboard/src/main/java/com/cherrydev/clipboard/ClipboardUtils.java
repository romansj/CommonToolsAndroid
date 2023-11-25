package com.cherrydev.clipboard;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.cherrydev.common.MimeTypes;

public class ClipboardUtils {
    /**
     * Copies given String to the clipboard
     *
     * @param text : text to copy
     */
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Returns empty string if nothing is stored on the clipboard or if it's not text.
     *
     * @return Text stored in the clipboard.
     */
    @NonNull
    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String textToReturn = "";

        //nothing in the clipboard
        if (!clipboard.hasPrimaryClip()) return textToReturn;

        ClipData primaryClip = clipboard.getPrimaryClip();
        if (primaryClip == null) return textToReturn; //null check necessitated by Android
        if (!primaryClip.getDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))
            return textToReturn; //not text

        // can still be null, check and return or empty
        CharSequence clipItem = primaryClip.getItemAt(0).getText();
        return clipItem == null ? textToReturn : clipItem.toString();
    }

    public static String getClipboardText(ClipboardManager clipboardManager) {
        if (!clipboardContainsText(clipboardManager)) return "";

        ClipData clip = clipboardManager.getPrimaryClip();
        CharSequence clipItem = clip.getItemAt(0).getText();
        return clipItem == null ? "" : clipItem.toString();
    }

    public static boolean clipboardContainsText(ClipboardManager clipboardManager) {
        if (!(clipboardManager.hasPrimaryClip())) {
            return (false);
        } else if (!(clipboardManager.getPrimaryClipDescription().hasMimeType(MimeTypes.Text.PLAIN))) {
            // This disables the paste menu item, since the clipboard has data but it is not plain text
            return (false);
        } else {
            // This enables the paste menu item, since the clipboard contains plain text.
            return (true);
        }
    }
}