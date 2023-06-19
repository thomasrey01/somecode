package com.google.zxing.client.android.history;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.PreferencesActivity;
import com.google.zxing.client.android.result.ResultHandler;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kotlin.text.Typography;
/* loaded from: classes.dex */
public final class HistoryManager {
    private static final String[] COLUMNS = {"text", "display", "format", "timestamp", "details"};
    private static final String[] COUNT_COLUMN = {"COUNT(1)"};
    private static final String[] ID_COL_PROJECTION = {"id"};
    private static final String[] ID_DETAIL_COL_PROJECTION = {"id", "details"};
    private static final int MAX_ITEMS = 2000;
    private static final String TAG = "HistoryManager";
    private final Activity activity;
    private final boolean enableHistory;

    public HistoryManager(Activity activity) {
        this.activity = activity;
        this.enableHistory = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(PreferencesActivity.KEY_ENABLE_HISTORY, true);
    }

    public boolean hasHistoryItems() {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getReadableDatabase();
            try {
                cursor = sQLiteDatabase.query("history", COUNT_COLUMN, null, null, null, null, null);
                cursor.moveToFirst();
                boolean z = cursor.getInt(0) > 0;
                close(cursor, sQLiteDatabase);
                return z;
            } catch (Throwable th) {
                th = th;
                close(cursor, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    public List<HistoryItem> buildHistoryItems() {
        SQLiteDatabase sQLiteDatabase;
        DBHelper dBHelper = new DBHelper(this.activity);
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            sQLiteDatabase = dBHelper.getReadableDatabase();
            try {
                cursor = sQLiteDatabase.query("history", COLUMNS, null, null, null, null, "timestamp DESC");
                while (cursor.moveToNext()) {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    String string3 = cursor.getString(2);
                    arrayList.add(new HistoryItem(new Result(string, null, null, BarcodeFormat.valueOf(string3), cursor.getLong(3)), string2, cursor.getString(4)));
                }
                close(cursor, sQLiteDatabase);
                return arrayList;
            } catch (Throwable th) {
                th = th;
                close(cursor, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    public HistoryItem buildHistoryItem(int i) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getReadableDatabase();
            try {
                cursor = sQLiteDatabase.query("history", COLUMNS, null, null, null, null, "timestamp DESC");
                cursor.move(i + 1);
                String string = cursor.getString(0);
                String string2 = cursor.getString(1);
                String string3 = cursor.getString(2);
                HistoryItem historyItem = new HistoryItem(new Result(string, null, null, BarcodeFormat.valueOf(string3), cursor.getLong(3)), string2, cursor.getString(4));
                close(cursor, sQLiteDatabase);
                return historyItem;
            } catch (Throwable th) {
                th = th;
                close(cursor, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    public void deleteHistoryItem(int i) {
        SQLiteDatabase sQLiteDatabase;
        Cursor query;
        Cursor cursor = null;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                query = sQLiteDatabase.query("history", ID_COL_PROJECTION, null, null, null, null, "timestamp DESC");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
        try {
            query.move(i + 1);
            sQLiteDatabase.delete("history", "id=" + query.getString(0), null);
            close(query, sQLiteDatabase);
        } catch (Throwable th3) {
            th = th3;
            cursor = query;
            close(cursor, sQLiteDatabase);
            throw th;
        }
    }

    public void addHistoryItem(Result result, ResultHandler resultHandler) {
        SQLiteDatabase sQLiteDatabase;
        if (!this.activity.getIntent().getBooleanExtra(Intents.Scan.SAVE_HISTORY, true) || resultHandler.areContentsSecure() || !this.enableHistory) {
            return;
        }
        if (!PreferenceManager.getDefaultSharedPreferences(this.activity).getBoolean(PreferencesActivity.KEY_REMEMBER_DUPLICATES, false)) {
            deletePrevious(result.getText());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("text", result.getText());
        contentValues.put("format", result.getBarcodeFormat().toString());
        contentValues.put("display", resultHandler.getDisplayContents().toString());
        contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                sQLiteDatabase.insert("history", "timestamp", contentValues);
                close(null, sQLiteDatabase);
            } catch (Throwable th) {
                th = th;
                close(null, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    public void addHistoryItemDetails(String str, String str2) {
        SQLiteDatabase sQLiteDatabase;
        String str3;
        String str4;
        Cursor cursor = null;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                Cursor query = sQLiteDatabase.query("history", ID_DETAIL_COL_PROJECTION, "text=?", new String[]{str}, null, null, "timestamp DESC", "1");
                try {
                    if (query.moveToNext()) {
                        str3 = query.getString(0);
                        str4 = query.getString(1);
                    } else {
                        str3 = null;
                        str4 = null;
                    }
                    if (str3 != null) {
                        if (str4 != null) {
                            if (str4.contains(str2)) {
                                str2 = null;
                            } else {
                                str2 = str4 + " : " + str2;
                            }
                        }
                        if (str2 != null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("details", str2);
                            sQLiteDatabase.update("history", contentValues, "id=?", new String[]{str3});
                        }
                    }
                    close(query, sQLiteDatabase);
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    close(cursor, sQLiteDatabase);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = null;
        }
    }

    private void deletePrevious(String str) {
        SQLiteDatabase sQLiteDatabase;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                sQLiteDatabase.delete("history", "text=?", new String[]{str});
                close(null, sQLiteDatabase);
            } catch (Throwable th) {
                th = th;
                close(null, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    public void trimHistory() {
        Cursor cursor;
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        SQLiteException e;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
        } catch (SQLiteException e2) {
            cursor = null;
            e = e2;
            sQLiteDatabase = null;
        } catch (Throwable th2) {
            cursor = null;
            th = th2;
            sQLiteDatabase = null;
        }
        try {
            cursor = sQLiteDatabase.query("history", ID_COL_PROJECTION, null, null, null, null, "timestamp DESC");
            try {
                try {
                    cursor.move(MAX_ITEMS);
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(0);
                        Log.i(TAG, "Deleting scan history ID " + string);
                        sQLiteDatabase.delete("history", "id=" + string, null);
                    }
                } catch (SQLiteException e3) {
                    e = e3;
                    Log.w(TAG, e);
                    close(cursor, sQLiteDatabase);
                }
            } catch (Throwable th3) {
                th = th3;
                close(cursor, sQLiteDatabase);
                throw th;
            }
        } catch (SQLiteException e4) {
            cursor = null;
            e = e4;
        } catch (Throwable th4) {
            cursor = null;
            th = th4;
            close(cursor, sQLiteDatabase);
            throw th;
        }
        close(cursor, sQLiteDatabase);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CharSequence buildHistory() {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                cursor = sQLiteDatabase.query("history", COLUMNS, null, null, null, null, "timestamp DESC");
                DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(2, 2);
                StringBuilder sb = new StringBuilder(1000);
                while (cursor.moveToNext()) {
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(cursor.getString(0)));
                    sb.append("\",");
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(cursor.getString(1)));
                    sb.append("\",");
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(cursor.getString(2)));
                    sb.append("\",");
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(cursor.getString(3)));
                    sb.append("\",");
                    long j = cursor.getLong(3);
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(dateTimeInstance.format(new Date(j))));
                    sb.append("\",");
                    sb.append(Typography.quote);
                    sb.append(massageHistoryField(cursor.getString(4)));
                    sb.append("\"\r\n");
                }
                close(cursor, sQLiteDatabase);
                return sb;
            } catch (Throwable th) {
                th = th;
                close(cursor, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearHistory() {
        SQLiteDatabase sQLiteDatabase;
        try {
            sQLiteDatabase = new DBHelper(this.activity).getWritableDatabase();
            try {
                sQLiteDatabase.delete("history", null, null);
                close(null, sQLiteDatabase);
            } catch (Throwable th) {
                th = th;
                close(null, sQLiteDatabase);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.net.Uri saveHistory(java.lang.String r6) {
        /*
            java.io.File r0 = new java.io.File
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r2 = "BarcodeScanner"
            r0.<init>(r1, r2)
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "History"
            r1.<init>(r0, r2)
            boolean r0 = r1.exists()
            r2 = 0
            if (r0 != 0) goto L36
            boolean r0 = r1.mkdirs()
            if (r0 != 0) goto L36
            java.lang.String r6 = com.google.zxing.client.android.history.HistoryManager.TAG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "Couldn't make dir "
            r0.append(r3)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r6, r0)
            return r2
        L36:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "history-"
            r3.append(r4)
            long r4 = java.lang.System.currentTimeMillis()
            r3.append(r4)
            java.lang.String r4 = ".csv"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r1, r3)
            java.io.OutputStreamWriter r1 = new java.io.OutputStreamWriter     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
            java.lang.String r4 = "UTF-8"
            java.nio.charset.Charset r4 = java.nio.charset.Charset.forName(r4)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
            r1.<init>(r3, r4)     // Catch: java.lang.Throwable -> L87 java.io.IOException -> L89
            r1.write(r6)     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            r6.<init>()     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            java.lang.String r3 = "file://"
            r6.append(r3)     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            java.lang.String r3 = r0.getAbsolutePath()     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            r6.append(r3)     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            java.lang.String r6 = r6.toString()     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch: java.io.IOException -> L85 java.lang.Throwable -> Laf
            r1.close()     // Catch: java.io.IOException -> L84
        L84:
            return r6
        L85:
            r6 = move-exception
            goto L8b
        L87:
            r6 = move-exception
            goto Lb1
        L89:
            r6 = move-exception
            r1 = r2
        L8b:
            java.lang.String r3 = com.google.zxing.client.android.history.HistoryManager.TAG     // Catch: java.lang.Throwable -> Laf
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf
            r4.<init>()     // Catch: java.lang.Throwable -> Laf
            java.lang.String r5 = "Couldn't access file "
            r4.append(r5)     // Catch: java.lang.Throwable -> Laf
            r4.append(r0)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r0 = " due to "
            r4.append(r0)     // Catch: java.lang.Throwable -> Laf
            r4.append(r6)     // Catch: java.lang.Throwable -> Laf
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> Laf
            android.util.Log.w(r3, r6)     // Catch: java.lang.Throwable -> Laf
            if (r1 == 0) goto Lae
            r1.close()     // Catch: java.io.IOException -> Lae
        Lae:
            return r2
        Laf:
            r6 = move-exception
            r2 = r1
        Lb1:
            if (r2 == 0) goto Lb6
            r2.close()     // Catch: java.io.IOException -> Lb6
        Lb6:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.android.history.HistoryManager.saveHistory(java.lang.String):android.net.Uri");
    }

    private static String massageHistoryField(String str) {
        return str == null ? "" : str.replace("\"", "\"\"");
    }

    private static void close(Cursor cursor, SQLiteDatabase sQLiteDatabase) {
        if (cursor != null) {
            cursor.close();
        }
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
    }
}
