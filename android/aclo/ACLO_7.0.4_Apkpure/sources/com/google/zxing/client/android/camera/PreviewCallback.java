package com.google.zxing.client.android.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
/* loaded from: classes.dex */
final class PreviewCallback implements Camera.PreviewCallback {
    private static final String TAG = "PreviewCallback";
    private final CameraConfigurationManager configManager;
    private Handler previewHandler;
    private int previewMessage;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PreviewCallback(CameraConfigurationManager cameraConfigurationManager) {
        this.configManager = cameraConfigurationManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setHandler(Handler handler, int i) {
        this.previewHandler = handler;
        this.previewMessage = i;
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        Point cameraResolution = this.configManager.getCameraResolution();
        Handler handler = this.previewHandler;
        if (cameraResolution != null && handler != null) {
            handler.obtainMessage(this.previewMessage, cameraResolution.x, cameraResolution.y, bArr).sendToTarget();
            this.previewHandler = null;
            return;
        }
        Log.d(TAG, "Got preview callback, but no handler or resolution available");
    }
}
