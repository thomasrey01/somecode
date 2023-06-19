package com.getcapacitor;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
/* loaded from: classes.dex */
public class MessageHandler {
    private Bridge bridge;
    private org.apache.cordova.PluginManager cordovaPluginManager;
    private WebView webView;

    public MessageHandler(Bridge bridge, WebView webView, org.apache.cordova.PluginManager cordovaPluginManager) {
        this.bridge = bridge;
        this.webView = webView;
        this.cordovaPluginManager = cordovaPluginManager;
        webView.addJavascriptInterface(this, "androidBridge");
    }

    @JavascriptInterface
    public void postMessage(String jsonStr) {
        try {
            JSObject jSObject = new JSObject(jsonStr);
            String string = jSObject.getString("type");
            boolean z = true;
            boolean z2 = string != null;
            boolean z3 = z2 && string.equals("cordova");
            if (!z2 || !string.equals("js.error")) {
                z = false;
            }
            String string2 = jSObject.getString("callbackId");
            if (z3) {
                String string3 = jSObject.getString(NotificationCompat.CATEGORY_SERVICE);
                String string4 = jSObject.getString("action");
                String string5 = jSObject.getString("actionArgs");
                Logger.verbose(Logger.tags("Plugin"), "To native (Cordova plugin): callbackId: " + string2 + ", service: " + string3 + ", action: " + string4 + ", actionArgs: " + string5);
                callCordovaPluginMethod(string2, string3, string4, string5);
            } else if (z) {
                Logger.error("JavaScript Error: " + jsonStr);
            } else {
                String string6 = jSObject.getString("pluginId");
                String string7 = jSObject.getString("methodName");
                JSObject jSObject2 = jSObject.getJSObject("options", new JSObject());
                Logger.verbose(Logger.tags("Plugin"), "To native (Capacitor plugin): callbackId: " + string2 + ", pluginId: " + string6 + ", methodName: " + string7);
                callPluginMethod(string2, string6, string7, jSObject2);
            }
        } catch (Exception e) {
            Logger.error("Post message error:", e);
        }
    }

    public void sendResponseMessage(PluginCall call, PluginResult successResult, PluginResult errorResult) {
        try {
            PluginResult pluginResult = new PluginResult();
            pluginResult.put("save", call.isKeptAlive());
            pluginResult.put("callbackId", call.getCallbackId());
            pluginResult.put("pluginId", call.getPluginId());
            pluginResult.put("methodName", call.getMethodName());
            if (errorResult != null) {
                pluginResult.put(FirebaseAnalytics.Param.SUCCESS, false);
                pluginResult.put("error", errorResult);
                Logger.debug("Sending plugin error: " + pluginResult.toString());
            } else {
                pluginResult.put(FirebaseAnalytics.Param.SUCCESS, true);
                if (successResult != null) {
                    pluginResult.put("data", successResult);
                }
            }
            if (!call.getCallbackId().equals(PluginCall.CALLBACK_ID_DANGLING)) {
                final String str = "window.Capacitor.fromNative(" + pluginResult.toString() + ")";
                final WebView webView = this.webView;
                webView.post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$MessageHandler$4AznmtxFHPC33QQV_3QddN2u6c4
                    @Override // java.lang.Runnable
                    public final void run() {
                        webView.evaluateJavascript(str, null);
                    }
                });
            } else {
                this.bridge.getApp().fireRestoredResult(pluginResult);
            }
        } catch (Exception e) {
            Logger.error("sendResponseMessage: error: " + e);
        }
        if (call.isKeptAlive()) {
            return;
        }
        call.release(this.bridge);
    }

    private void callPluginMethod(String callbackId, String pluginId, String methodName, JSObject methodData) {
        this.bridge.callPluginMethod(pluginId, methodName, new PluginCall(this, pluginId, callbackId, methodName, methodData));
    }

    private void callCordovaPluginMethod(String callbackId, String service, String action, String actionArgs) {
        this.cordovaPluginManager.exec(service, action, callbackId, actionArgs);
    }
}
