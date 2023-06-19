package com.google.zxing.client.android;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class DecodeThread extends Thread {
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";
    private final CaptureActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Map<DecodeHintType, Object> hints;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DecodeThread(CaptureActivity captureActivity, Collection<BarcodeFormat> collection, Map<DecodeHintType, ?> map, String str, ResultPointCallback resultPointCallback) {
        this.activity = captureActivity;
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        this.hints = enumMap;
        if (map != null) {
            enumMap.putAll(map);
        }
        if (collection == null || collection.isEmpty()) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(captureActivity);
            collection = EnumSet.noneOf(BarcodeFormat.class);
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_1D_PRODUCT, true)) {
                collection.addAll(DecodeFormatManager.PRODUCT_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_1D_INDUSTRIAL, true)) {
                collection.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_QR, true)) {
                collection.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_DATA_MATRIX, true)) {
                collection.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_AZTEC, false)) {
                collection.addAll(DecodeFormatManager.AZTEC_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean(PreferencesActivity.KEY_DECODE_PDF417, false)) {
                collection.addAll(DecodeFormatManager.PDF417_FORMATS);
            }
        }
        enumMap.put((EnumMap) DecodeHintType.POSSIBLE_FORMATS, (DecodeHintType) collection);
        if (str != null) {
            enumMap.put((EnumMap) DecodeHintType.CHARACTER_SET, (DecodeHintType) str);
        }
        enumMap.put((EnumMap) DecodeHintType.NEED_RESULT_POINT_CALLBACK, (DecodeHintType) resultPointCallback);
        Log.i("DecodeThread", "Hints: " + enumMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException unused) {
        }
        return this.handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.activity, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
