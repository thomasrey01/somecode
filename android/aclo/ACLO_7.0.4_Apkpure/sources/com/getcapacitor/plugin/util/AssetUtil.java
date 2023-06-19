package com.getcapacitor.plugin.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
/* loaded from: classes.dex */
public final class AssetUtil {
    public static final int RESOURCE_ID_ZERO_VALUE = 0;
    private static final String STORAGE_FOLDER = "/capacitorassets";
    private final Context context;

    private AssetUtil(Context context) {
        this.context = context;
    }

    public static AssetUtil getInstance(Context context) {
        return new AssetUtil(context);
    }

    public Uri parse(String path) {
        if (path == null || path.isEmpty()) {
            return Uri.EMPTY;
        }
        if (path.startsWith("res:")) {
            return getUriForResourcePath(path);
        }
        if (path.startsWith("file:///")) {
            return getUriFromPath(path);
        }
        if (path.startsWith("file://")) {
            return getUriFromAsset(path);
        }
        if (path.startsWith(Bridge.CAPACITOR_HTTP_SCHEME)) {
            return getUriFromRemote(path);
        }
        if (path.startsWith("content://")) {
            return Uri.parse(path);
        }
        return Uri.EMPTY;
    }

    private Uri getUriFromPath(String path) {
        File file = new File(path.replaceFirst("file://", "").replaceFirst("\\?.*$", ""));
        if (!file.exists()) {
            Logger.error("File not found: " + file.getAbsolutePath());
            return Uri.EMPTY;
        }
        return getUriFromFile(file);
    }

    private Uri getUriFromAsset(String path) {
        String replaceFirst = path.replaceFirst("file:/", "www").replaceFirst("\\?.*$", "");
        File tmpFile = getTmpFile(replaceFirst.substring(replaceFirst.lastIndexOf(47) + 1));
        if (tmpFile == null) {
            return Uri.EMPTY;
        }
        try {
            copyFile(this.context.getAssets().open(replaceFirst), new FileOutputStream(tmpFile));
            return getUriFromFile(tmpFile);
        } catch (Exception unused) {
            Logger.error("File not found: assets/" + replaceFirst);
            return Uri.EMPTY;
        }
    }

    private Uri getUriForResourcePath(String path) {
        Resources resources = this.context.getResources();
        String replaceFirst = path.replaceFirst("res://", "");
        int resId = getResId(replaceFirst);
        if (resId == 0) {
            Logger.error("File not found: " + replaceFirst);
            return Uri.EMPTY;
        }
        return new Uri.Builder().scheme("android.resource").authority(resources.getResourcePackageName(resId)).appendPath(resources.getResourceTypeName(resId)).appendPath(resources.getResourceEntryName(resId)).build();
    }

    private Uri getUriFromRemote(String path) {
        File tmpFile = getTmpFile();
        if (tmpFile == null) {
            return Uri.EMPTY;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            httpURLConnection.setRequestProperty("Connection", "close");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            copyFile(httpURLConnection.getInputStream(), new FileOutputStream(tmpFile));
            return getUriFromFile(tmpFile);
        } catch (FileNotFoundException e) {
            Logger.error(Logger.tags("Asset"), "Failed to create new File from HTTP Content", e);
            return Uri.EMPTY;
        } catch (MalformedURLException e2) {
            Logger.error(Logger.tags("Asset"), "Incorrect URL", e2);
            return Uri.EMPTY;
        } catch (IOException e3) {
            Logger.error(Logger.tags("Asset"), "No Input can be created from http Stream", e3);
            return Uri.EMPTY;
        }
    }

    private void copyFile(InputStream in, FileOutputStream out) {
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = in.read(bArr);
                if (read != -1) {
                    out.write(bArr, 0, read);
                } else {
                    out.flush();
                    out.close();
                    return;
                }
            } catch (Exception e) {
                Logger.error("Error copying", e);
                return;
            }
        }
    }

    public int getResId(String resPath) {
        int resId = getResId(this.context.getResources(), resPath);
        return resId == 0 ? getResId(Resources.getSystem(), resPath) : resId;
    }

    private int getResId(Resources res, String resPath) {
        String pkgName = getPkgName(res);
        String baseName = getBaseName(resPath);
        int identifier = res.getIdentifier(baseName, "mipmap", pkgName);
        if (identifier == 0) {
            identifier = res.getIdentifier(baseName, "drawable", pkgName);
        }
        return identifier == 0 ? res.getIdentifier(baseName, "raw", pkgName) : identifier;
    }

    public Bitmap getIconFromUri(Uri uri) throws IOException {
        return BitmapFactory.decodeStream(this.context.getContentResolver().openInputStream(uri));
    }

    private String getBaseName(String resPath) {
        String substring = resPath.contains("/") ? resPath.substring(resPath.lastIndexOf(47) + 1) : resPath;
        return resPath.contains(".") ? substring.substring(0, substring.lastIndexOf(46)) : substring;
    }

    private File getTmpFile() {
        return getTmpFile(UUID.randomUUID().toString());
    }

    private File getTmpFile(String name) {
        File externalCacheDir = this.context.getExternalCacheDir();
        if (externalCacheDir == null) {
            externalCacheDir = this.context.getCacheDir();
        }
        if (externalCacheDir == null) {
            Logger.error(Logger.tags("Asset"), "Missing cache dir", null);
            return null;
        }
        String str = externalCacheDir.toString() + STORAGE_FOLDER;
        new File(str).mkdir();
        return new File(str, name);
    }

    private Uri getUriFromFile(File file) {
        try {
            return FileProvider.getUriForFile(this.context, this.context.getPackageName() + ".provider", file);
        } catch (IllegalArgumentException e) {
            Logger.error("File not supported by provider", e);
            return Uri.EMPTY;
        }
    }

    private String getPkgName(Resources res) {
        return res == Resources.getSystem() ? "android" : this.context.getPackageName();
    }

    public static int getResourceID(Context context, String resourceName, String dir) {
        return context.getResources().getIdentifier(resourceName, dir, context.getPackageName());
    }

    public static String getResourceBaseName(String resPath) {
        if (resPath == null) {
            return null;
        }
        if (resPath.contains("/")) {
            return resPath.substring(resPath.lastIndexOf(47) + 1);
        }
        return resPath.contains(".") ? resPath.substring(0, resPath.lastIndexOf(46)) : resPath;
    }
}
