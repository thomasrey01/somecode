package com.google.zxing.client.android.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/* loaded from: classes.dex */
final class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "barcode_scanner_history.db";
    private static final int DB_VERSION = 5;
    static final String DETAILS_COL = "details";
    static final String DISPLAY_COL = "display";
    static final String FORMAT_COL = "format";
    static final String ID_COL = "id";
    static final String TABLE_NAME = "history";
    static final String TEXT_COL = "text";
    static final String TIMESTAMP_COL = "timestamp";

    /* JADX INFO: Access modifiers changed from: package-private */
    public DBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 5);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE history (id INTEGER PRIMARY KEY, text TEXT, format TEXT, display TEXT, timestamp INTEGER, details TEXT);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS history");
        onCreate(sQLiteDatabase);
    }
}
