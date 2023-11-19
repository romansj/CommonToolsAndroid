package com.cherrydev.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IntentUtils {
    /**
     * Shows activity chooser to send an email. E.g. user has Outlook and Gmail apps installed, both will show and user can select preferred choice.
     *
     * @param context        Need to launch Activity and get PackageManager
     * @param recipientEmail Mandatory
     * @param subject        Mandatory
     * @param message        Optional - you can provide message text or let the user enter the whole message
     */
    public static void sendEmail(@NonNull Context context, @NonNull String recipientEmail, @NonNull String subject, @Nullable String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, recipientEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (message != null) intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No email app installed.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * {@code message} defaults to {@link null}.
     *
     * @see {@link IntentUtils.sendEmail(Context,String,String,String)}
     */
    public static void sendEmail(@NonNull Context context, @NonNull String recipientEmail, @NonNull String subject) {
        sendEmail(context, recipientEmail, subject, null);
    }
}
