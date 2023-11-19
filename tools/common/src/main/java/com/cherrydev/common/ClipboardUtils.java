package com.cherrydev.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils {
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        clipboard.setPrimaryClip(clip);
    }


    public static String getClipboardText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (!clipboardContainsText(clipboardManager)) return "";


        ClipData clip = clipboardManager.getPrimaryClip();
        String strVal = clip.getItemAt(0).getText().toString();
        return strVal;
    }

    public static String getClipboardText(ClipboardManager clipboardManager) {
        if (!clipboardContainsText(clipboardManager)) return "";

        ClipData clip = clipboardManager.getPrimaryClip();
        String strVal = clip.getItemAt(0).getText().toString();
        return strVal;
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