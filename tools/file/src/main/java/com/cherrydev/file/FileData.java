package com.cherrydev.file;


public class FileData {
    final String fileName;
    final String mimeType;
    final long lastModified;
    final float size;

    public FileData(String fileName, String mimeType, long lastModified, float size) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.lastModified = lastModified;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getLastModified() {
        return lastModified;
    }

    public float getSize() {
        return size;
    }
}
