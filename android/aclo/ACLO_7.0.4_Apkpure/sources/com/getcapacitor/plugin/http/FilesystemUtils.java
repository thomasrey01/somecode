package com.getcapacitor.plugin.http;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
/* loaded from: classes.dex */
public class FilesystemUtils {
    public static final String DIRECTORY_APPLICATION = "APPLICATION";
    public static final String DIRECTORY_CACHE = "CACHE";
    public static final String DIRECTORY_DATA = "DATA";
    public static final String DIRECTORY_DOCUMENTS = "DOCUMENTS";
    public static final String DIRECTORY_DOWNLOADS = "DOWNLOADS";
    public static final String DIRECTORY_EXTERNAL = "EXTERNAL";
    public static final String DIRECTORY_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";

    public static File getFileObject(Context c, String path, String directory) {
        if (directory == null || path.startsWith("file://")) {
            Uri parse = Uri.parse(path);
            if (parse.getScheme() == null || parse.getScheme().equals("file")) {
                return new File(parse.getPath());
            }
        }
        File directory2 = getDirectory(c, directory);
        if (directory2 == null) {
            return null;
        }
        if (!directory2.exists()) {
            directory2.mkdir();
        }
        return new File(directory2, path);
    }

    public static File getDirectory(Context c, String directory) {
        directory.hashCode();
        char c2 = 65535;
        switch (directory.hashCode()) {
            case -1038134325:
                if (directory.equals(DIRECTORY_EXTERNAL)) {
                    c2 = 0;
                    break;
                }
                break;
            case -587753168:
                if (directory.equals(DIRECTORY_APPLICATION)) {
                    c2 = 1;
                    break;
                }
                break;
            case -564829544:
                if (directory.equals(DIRECTORY_DOCUMENTS)) {
                    c2 = 2;
                    break;
                }
                break;
            case -195667765:
                if (directory.equals(DIRECTORY_DOWNLOADS)) {
                    c2 = 3;
                    break;
                }
                break;
            case 2090922:
                if (directory.equals(DIRECTORY_DATA)) {
                    c2 = 4;
                    break;
                }
                break;
            case 63879010:
                if (directory.equals(DIRECTORY_CACHE)) {
                    c2 = 5;
                    break;
                }
                break;
            case 1013698023:
                if (directory.equals(DIRECTORY_EXTERNAL_STORAGE)) {
                    c2 = 6;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return c.getExternalFilesDir(null);
            case 1:
            case 4:
                return c.getFilesDir();
            case 2:
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            case 3:
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            case 5:
                return c.getCacheDir();
            case 6:
                return Environment.getExternalStorageDirectory();
            default:
                return null;
        }
    }

    public static boolean isPublicDirectory(String directory) {
        return DIRECTORY_DOCUMENTS.equals(directory) || DIRECTORY_DOWNLOADS.equals(directory) || DIRECTORY_EXTERNAL_STORAGE.equals(directory);
    }
}
