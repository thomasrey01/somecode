package com.getcapacitor.plugin;

import android.content.SharedPreferences;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
@CapacitorPlugin
/* loaded from: classes.dex */
public class WebView extends Plugin {
    public static final String CAP_SERVER_PATH = "serverBasePath";
    public static final String WEBVIEW_PREFS_NAME = "CapWebViewSettings";

    @PluginMethod
    public void setServerBasePath(PluginCall call) {
        this.bridge.setServerBasePath(call.getString("path"));
        call.resolve();
    }

    @PluginMethod
    public void getServerBasePath(PluginCall call) {
        String serverBasePath = this.bridge.getServerBasePath();
        JSObject jSObject = new JSObject();
        jSObject.put("path", serverBasePath);
        call.resolve(jSObject);
    }

    @PluginMethod
    public void persistServerBasePath(PluginCall call) {
        String serverBasePath = this.bridge.getServerBasePath();
        SharedPreferences.Editor edit = getContext().getSharedPreferences(WEBVIEW_PREFS_NAME, 0).edit();
        edit.putString(CAP_SERVER_PATH, serverBasePath);
        edit.apply();
        call.resolve();
    }
}
