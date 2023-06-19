package com.getcapacitor;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/* loaded from: classes.dex */
public class FileUtils {
    private static String CapacitorFileScheme = "/_capacitor_file_";

    /* loaded from: classes.dex */
    public enum Type {
        IMAGE("image");
        
        private String type;

        Type(String type) {
            this.type = type;
        }
    }

    public static String getPortablePath(Context c, String host, Uri u) {
        String fileUrlForUri = getFileUrlForUri(c, u);
        if (fileUrlForUri.startsWith("file://")) {
            fileUrlForUri = fileUrlForUri.replace("file://", "");
        }
        return host + Bridge.CAPACITOR_FILE_START + fileUrlForUri;
    }

    public static String getFileUrlForUri(final Context context, final Uri uri) {
        String[] split;
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                if ("primary".equalsIgnoreCase(documentId.split(":")[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                int indexOf = documentId.indexOf(58, 1);
                String substring = documentId.substring(0, indexOf);
                String substring2 = documentId.substring(indexOf + 1);
                String pathToNonPrimaryVolume = getPathToNonPrimaryVolume(context, substring);
                if (pathToNonPrimaryVolume != null) {
                    String str = pathToNonPrimaryVolume + "/" + substring2;
                    File file = new File(str);
                    if (file.exists() && file.canRead()) {
                        return str;
                    }
                    return null;
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str2 = split2[0];
                    if ("image".equals(str2)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str2)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str2)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
                }
            }
        } else if (FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
            return isGooglePhotosUri(uri) ? uri.getLastPathSegment() : getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String readFile(AssetManager assetManager, String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine + "\n");
                } else {
                    String sb2 = sb.toString();
                    bufferedReader.close();
                    return sb2;
                }
            }
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        r7 = null;
        String string = null;
        Cursor cursor2 = null;
        try {
            try {
                Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            string = query.getString(query.getColumnIndexOrThrow("_data"));
                        }
                    } catch (IllegalArgumentException unused) {
                        cursor = query;
                        String copyFilePath = getCopyFilePath(uri, context);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return copyFilePath;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = query;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
                return string == null ? getCopyFilePath(uri, context) : string;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IllegalArgumentException unused2) {
        }
    }

    private static String getCopyFilePath(Uri uri, Context context) {
        Cursor query = context.getContentResolver().query(uri, null, null, null, null);
        int columnIndex = query.getColumnIndex("_display_name");
        query.moveToFirst();
        File file = new File(context.getFilesDir(), query.getString(columnIndex));
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[Math.min(openInputStream.available(), 1048576)];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            openInputStream.close();
            fileOutputStream.close();
            if (query != null) {
                query.close();
            }
            return file.getPath();
        } catch (Exception unused) {
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th) {
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static String getPathToNonPrimaryVolume(Context context, String tag) {
        String absolutePath;
        int indexOf;
        File[] externalCacheDirs = context.getExternalCacheDirs();
        if (externalCacheDirs != null) {
            for (File file : externalCacheDirs) {
                if (file != null && (absolutePath = file.getAbsolutePath()) != null && (indexOf = absolutePath.indexOf(tag)) != -1) {
                    return absolutePath.substring(0, indexOf) + tag;
                }
            }
            return null;
        }
        return null;
    }
}
