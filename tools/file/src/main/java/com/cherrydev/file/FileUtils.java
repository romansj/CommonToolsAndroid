package com.cherrydev.file;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.text.format.Formatter;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Objects;

public class FileUtils {
    private static final String PRIMARY_VOLUME_NAME = "primary";

    @Nullable
    public static String getFullPathFromTreeUri(@Nullable final Uri treeUri, Context con) {
        if (treeUri == null) return null;
        String volumePath = getVolumePath(getVolumeIdFromTreeUri(treeUri), con);
        if (volumePath == null) return File.separator;
        if (volumePath.endsWith(File.separator))
            volumePath = volumePath.substring(0, volumePath.length() - 1);

        String documentPath = getDocumentPathFromTreeUri(treeUri);
        if (documentPath.endsWith(File.separator))
            documentPath = documentPath.substring(0, documentPath.length() - 1);

        if (documentPath.length() > 0) {
            if (documentPath.startsWith(File.separator))
                return volumePath + documentPath;
            else
                return volumePath + File.separator + documentPath;
        } else return volumePath;
    }


    @SuppressLint("ObsoleteSdkInt")
    private static String getVolumePath(final String volumeId, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        try {
            StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getUuid = storageVolumeClazz.getMethod("getUuid");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isPrimary = storageVolumeClazz.getMethod("isPrimary");
            Object result = getVolumeList.invoke(mStorageManager);

            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String uuid = (String) getUuid.invoke(storageVolumeElement);
                Boolean primary = (Boolean) isPrimary.invoke(storageVolumeElement);

                // primary volume?
                if (primary && PRIMARY_VOLUME_NAME.equals(volumeId))
                    return (String) getPath.invoke(storageVolumeElement);

                // other volumes?
                if (uuid != null && uuid.equals(volumeId))
                    return (String) getPath.invoke(storageVolumeElement);
            }
            // not found.
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getVolumeIdFromTreeUri(final Uri treeUri) {
        final String docId = DocumentsContract.getTreeDocumentId(treeUri);
        final String[] split = docId.split(":");
        if (split.length > 0) return split[0];
        else return null;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getDocumentPathFromTreeUri(final Uri treeUri) {
        final String docId = DocumentsContract.getTreeDocumentId(treeUri);
        final String[] split = docId.split(":");
        if ((split.length >= 2) && (split[1] != null)) return split[1];
        else return File.separator;
    }


    //https://stackoverflow.com/a/36514843/4673960
    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }


    public static FileData getFileData(ContentResolver contentResolver, Uri uri) {
//            StringBuilder builder = new StringBuilder();
//            String[] columnNames = cursor.getColumnNames();
//            for (String cn : columnNames) builder.append(cn).append(", ");
//            Timber.d(builder.toString());


        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        cursor.moveToFirst();
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        int mimeIndex = cursor.getColumnIndex("mime_type");
        int modifiedIndex = cursor.getColumnIndex("last_modified");

        String name = cursor.getString(nameIndex);
        String mime = cursor.getString(mimeIndex);

        //float fileSizeInKB = -1f;
        //if (sizeIndex != -1) fileSizeInKB = cursor.getLong(sizeIndex) / 1000f;
        float fileSize = cursor.getLong(sizeIndex);

        long lastModified = -1;
        if (sizeIndex != -1) lastModified = cursor.getLong(modifiedIndex);


        cursor.close();

        return new FileData(name, mime, lastModified, fileSize);
    }


    public static String readTextFromUri(ContentResolver contentResolver, Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = contentResolver.openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }


    public static void writeTextToFile(ContentResolver contentResolver, Uri uri, String s) {
        try {
            //InputStream in = contentResolver.openInputStream(uri);

            OutputStream out = contentResolver.openOutputStream(uri);


            out.write(s.getBytes());

            out.close();
            //in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Format {
        public static String formatFileSize(Context context, long size) {
            String formatted = Formatter.formatShortFileSize(context, size);
            return formatted;
        }

        public static String formatFileSize(Context context, float size) {
            return formatFileSize(context, (long) size);
        }
    }
}
