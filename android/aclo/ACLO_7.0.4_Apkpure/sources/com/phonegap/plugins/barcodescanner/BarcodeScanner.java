package com.phonegap.plugins.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.encode.EncodeActivity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class BarcodeScanner extends CordovaPlugin {
    private static final String CANCELLED = "cancelled";
    private static final String DATA = "data";
    private static final String DISABLE_BEEP = "disableSuccessBeep";
    private static final String EMAIL_TYPE = "EMAIL_TYPE";
    private static final String ENCODE = "encode";
    private static final String FORMAT = "format";
    private static final String FORMATS = "formats";
    private static final String LOG_TAG = "BarcodeScanner";
    private static final String ORIENTATION = "orientation";
    private static final String PHONE_TYPE = "PHONE_TYPE";
    private static final String PREFER_FRONTCAMERA = "preferFrontCamera";
    private static final String PROMPT = "prompt";
    public static final int REQUEST_CODE = 47740;
    private static final String RESULTDISPLAY_DURATION = "resultDisplayDuration";
    private static final String SAVE_HISTORY = "saveHistory";
    private static final String SCAN = "scan";
    private static final String SHOW_FLIP_CAMERA_BUTTON = "showFlipCameraButton";
    private static final String SHOW_TORCH_BUTTON = "showTorchButton";
    private static final String SMS_TYPE = "SMS_TYPE";
    private static final String TEXT = "text";
    private static final String TEXT_TYPE = "TEXT_TYPE";
    private static final String TORCH_ON = "torchOn";
    private static final String TYPE = "type";
    private CallbackContext callbackContext;
    private String[] permissions = {"android.permission.CAMERA"};
    private JSONArray requestArgs;

    @Override // org.apache.cordova.CordovaPlugin
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
        this.requestArgs = args;
        if (action.equals(ENCODE)) {
            JSONObject optJSONObject = args.optJSONObject(0);
            if (optJSONObject != null) {
                String optString = optJSONObject.optString(TYPE);
                String optString2 = optJSONObject.optString(DATA);
                if (optString == null) {
                    optString = "TEXT_TYPE";
                }
                if (optString2 == null) {
                    callbackContext.error("User did not specify data to encode");
                    return true;
                }
                encode(optString, optString2);
            } else {
                callbackContext.error("User did not specify data to encode");
                return true;
            }
        } else if (!action.equals(SCAN)) {
            return false;
        } else {
            if (!hasPermisssion()) {
                requestPermissions(0);
            } else {
                scan(args);
            }
        }
        return true;
    }

    public void scan(final JSONArray args) {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.phonegap.plugins.barcodescanner.BarcodeScanner.1
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent(this.cordova.getActivity().getBaseContext(), CaptureActivity.class);
                intent.setAction(Intents.Scan.ACTION);
                intent.addCategory("android.intent.category.DEFAULT");
                if (args.length() > 0) {
                    for (int i = 0; i < args.length(); i++) {
                        try {
                            JSONObject jSONObject = args.getJSONObject(i);
                            JSONArray names = jSONObject.names();
                            for (int i2 = 0; i2 < names.length(); i2++) {
                                try {
                                    String string = names.getString(i2);
                                    Object obj = jSONObject.get(string);
                                    if (obj instanceof Integer) {
                                        intent.putExtra(string, (Integer) obj);
                                    } else if (obj instanceof String) {
                                        intent.putExtra(string, (String) obj);
                                    }
                                } catch (JSONException e) {
                                    Log.i("CordovaLog", e.getLocalizedMessage());
                                }
                            }
                            intent.putExtra(Intents.Scan.CAMERA_ID, jSONObject.optBoolean(BarcodeScanner.PREFER_FRONTCAMERA, false) ? 1 : 0);
                            intent.putExtra(Intents.Scan.SHOW_FLIP_CAMERA_BUTTON, jSONObject.optBoolean(BarcodeScanner.SHOW_FLIP_CAMERA_BUTTON, false));
                            intent.putExtra(Intents.Scan.SHOW_TORCH_BUTTON, jSONObject.optBoolean(BarcodeScanner.SHOW_TORCH_BUTTON, false));
                            intent.putExtra(Intents.Scan.TORCH_ON, jSONObject.optBoolean(BarcodeScanner.TORCH_ON, false));
                            intent.putExtra(Intents.Scan.SAVE_HISTORY, jSONObject.optBoolean(BarcodeScanner.SAVE_HISTORY, false));
                            intent.putExtra(Intents.Scan.BEEP_ON_SCAN, !jSONObject.optBoolean(BarcodeScanner.DISABLE_BEEP, false));
                            if (jSONObject.has(BarcodeScanner.RESULTDISPLAY_DURATION)) {
                                intent.putExtra(Intents.Scan.RESULT_DISPLAY_DURATION_MS, "" + jSONObject.optLong(BarcodeScanner.RESULTDISPLAY_DURATION));
                            }
                            if (jSONObject.has(BarcodeScanner.FORMATS)) {
                                intent.putExtra(Intents.Scan.FORMATS, jSONObject.optString(BarcodeScanner.FORMATS));
                            }
                            if (jSONObject.has(BarcodeScanner.PROMPT)) {
                                intent.putExtra(Intents.Scan.PROMPT_MESSAGE, jSONObject.optString(BarcodeScanner.PROMPT));
                            }
                            if (jSONObject.has(BarcodeScanner.ORIENTATION)) {
                                intent.putExtra(Intents.Scan.ORIENTATION_LOCK, jSONObject.optString(BarcodeScanner.ORIENTATION));
                            }
                        } catch (JSONException e2) {
                            Log.i("CordovaLog", e2.getLocalizedMessage());
                        }
                    }
                }
                intent.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());
                this.cordova.startActivityForResult(this, intent, BarcodeScanner.REQUEST_CODE);
            }
        });
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        CallbackContext callbackContext;
        if (requestCode != 47740 || (callbackContext = this.callbackContext) == null) {
            return;
        }
        if (resultCode == -1) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TEXT, intent.getStringExtra(Intents.Scan.RESULT));
                jSONObject.put(FORMAT, intent.getStringExtra(Intents.Scan.RESULT_FORMAT));
                jSONObject.put(CANCELLED, false);
            } catch (JSONException unused) {
                Log.d(LOG_TAG, "This should never happen");
            }
            this.callbackContext.success(jSONObject);
        } else if (resultCode == 0) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(TEXT, "");
                jSONObject2.put(FORMAT, "");
                jSONObject2.put(CANCELLED, true);
            } catch (JSONException unused2) {
                Log.d(LOG_TAG, "This should never happen");
            }
            this.callbackContext.success(jSONObject2);
        } else {
            callbackContext.error("Unexpected error");
        }
    }

    public void encode(String type, String data) {
        Intent intent = new Intent(this.cordova.getActivity().getBaseContext(), EncodeActivity.class);
        intent.setAction(Intents.Encode.ACTION);
        intent.putExtra(Intents.Encode.TYPE, type);
        intent.putExtra(Intents.Encode.DATA, data);
        intent.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());
        this.cordova.getActivity().startActivity(intent);
    }

    @Override // org.apache.cordova.CordovaPlugin
    public boolean hasPermisssion() {
        for (String str : this.permissions) {
            if (!PermissionHelper.hasPermission(this, str)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, this.permissions);
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        for (int i : grantResults) {
            if (i == -1) {
                Log.d(LOG_TAG, "Permission Denied!");
                this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION));
                return;
            }
        }
        if (requestCode != 0) {
            return;
        }
        scan(this.requestArgs);
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }
}
