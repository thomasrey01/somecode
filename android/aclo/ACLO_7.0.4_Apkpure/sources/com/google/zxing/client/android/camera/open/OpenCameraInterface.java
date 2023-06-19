package com.google.zxing.client.android.camera.open;

import android.hardware.Camera;
import android.util.Log;
/* loaded from: classes.dex */
public final class OpenCameraInterface {
    public static final int NO_REQUESTED_CAMERA = -1;
    private static final String TAG = "com.google.zxing.client.android.camera.open.OpenCameraInterface";

    private OpenCameraInterface() {
    }

    public static OpenCamera open(int i) {
        int i2;
        Camera.CameraInfo cameraInfo;
        Camera open;
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0) {
            Log.w(TAG, "No cameras!");
            return null;
        }
        if (numberOfCameras == 1) {
            i = 0;
        }
        boolean z = i >= 0;
        if (!z) {
            i2 = 0;
            while (true) {
                if (i2 >= numberOfCameras) {
                    cameraInfo = null;
                    break;
                }
                cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i2, cameraInfo);
                if (CameraFacing.values()[cameraInfo.facing] == CameraFacing.BACK) {
                    break;
                }
                i2++;
            }
        } else {
            Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo2);
            cameraInfo = cameraInfo2;
            i2 = i;
        }
        if (i2 < numberOfCameras) {
            Log.i(TAG, "Opening camera #" + i2);
            open = Camera.open(i2);
        } else if (z) {
            Log.w(TAG, "Requested camera does not exist: " + i);
            open = null;
        } else {
            Log.i(TAG, "No camera facing " + CameraFacing.BACK + "; returning camera #0");
            open = Camera.open(0);
            cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(0, cameraInfo);
        }
        if (open == null) {
            return null;
        }
        return new OpenCamera(i2, open, CameraFacing.values()[cameraInfo.facing], cameraInfo.orientation);
    }
}
