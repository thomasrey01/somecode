package com.capacitorjs.plugins.app;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import androidx.activity.OnBackPressedCallback;
import com.getcapacitor.App;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.PluginResult;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
@CapacitorPlugin(name = "App")
/* loaded from: classes.dex */
public class AppPlugin extends Plugin {
    private static final String EVENT_BACK_BUTTON = "backButton";
    private static final String EVENT_RESTORED_RESULT = "appRestoredResult";
    private static final String EVENT_STATE_CHANGE = "appStateChange";
    private static final String EVENT_URL_OPEN = "appUrlOpen";

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.bridge.getApp().setStatusChangeListener(new App.AppStatusChangeListener() { // from class: com.capacitorjs.plugins.app.-$$Lambda$AppPlugin$C9U7V8b5SRbrcOULNf4wtgut9ZM
            @Override // com.getcapacitor.App.AppStatusChangeListener
            public final void onAppStatusChanged(Boolean bool) {
                AppPlugin.this.lambda$load$0$AppPlugin(bool);
            }
        });
        this.bridge.getApp().setAppRestoredListener(new App.AppRestoredListener() { // from class: com.capacitorjs.plugins.app.-$$Lambda$AppPlugin$q707joBOlkekwiROknukP_Oqzoc
            @Override // com.getcapacitor.App.AppRestoredListener
            public final void onAppRestored(PluginResult pluginResult) {
                AppPlugin.this.lambda$load$1$AppPlugin(pluginResult);
            }
        });
        getActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) { // from class: com.capacitorjs.plugins.app.AppPlugin.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                if (!AppPlugin.this.hasListeners(AppPlugin.EVENT_BACK_BUTTON)) {
                    if (AppPlugin.this.bridge.getWebView().canGoBack()) {
                        AppPlugin.this.bridge.getWebView().goBack();
                        return;
                    }
                    return;
                }
                JSObject jSObject = new JSObject();
                jSObject.put("canGoBack", AppPlugin.this.bridge.getWebView().canGoBack());
                AppPlugin.this.notifyListeners(AppPlugin.EVENT_BACK_BUTTON, jSObject, true);
                AppPlugin.this.bridge.triggerJSEvent("backbutton", "document");
            }
        });
    }

    public /* synthetic */ void lambda$load$0$AppPlugin(Boolean bool) {
        String logTag = getLogTag();
        Logger.debug(logTag, "Firing change: " + bool);
        JSObject jSObject = new JSObject();
        jSObject.put("isActive", (Object) bool);
        notifyListeners(EVENT_STATE_CHANGE, jSObject, false);
    }

    public /* synthetic */ void lambda$load$1$AppPlugin(PluginResult pluginResult) {
        Logger.debug(getLogTag(), "Firing restored result");
        notifyListeners(EVENT_RESTORED_RESULT, pluginResult.getWrappedResult(), true);
    }

    @PluginMethod
    public void exitApp(PluginCall call) {
        unsetAppListeners();
        getBridge().getActivity().finish();
    }

    @PluginMethod
    public void getInfo(PluginCall call) {
        JSObject jSObject = new JSObject();
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            ApplicationInfo applicationInfo = getContext().getApplicationInfo();
            int i = applicationInfo.labelRes;
            jSObject.put(AppMeasurementSdk.ConditionalUserProperty.NAME, i == 0 ? applicationInfo.nonLocalizedLabel.toString() : getContext().getString(i));
            jSObject.put("id", packageInfo.packageName);
            if (Build.VERSION.SDK_INT >= 28) {
                jSObject.put("build", Long.toString(packageInfo.getLongVersionCode()));
            } else {
                jSObject.put("build", Integer.toString(packageInfo.versionCode));
            }
            jSObject.put("version", packageInfo.versionName);
            call.resolve(jSObject);
        } catch (Exception unused) {
            call.reject("Unable to get App Info");
        }
    }

    @PluginMethod
    public void getLaunchUrl(PluginCall call) {
        Uri intentUri = this.bridge.getIntentUri();
        if (intentUri != null) {
            JSObject jSObject = new JSObject();
            jSObject.put(ImagesContract.URL, intentUri.toString());
            call.resolve(jSObject);
            return;
        }
        call.resolve();
    }

    @PluginMethod
    public void getState(PluginCall call) {
        JSObject jSObject = new JSObject();
        jSObject.put("isActive", this.bridge.getApp().isActive());
        call.resolve(jSObject);
    }

    @PluginMethod
    public void minimizeApp(PluginCall call) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        getActivity().startActivity(intent);
        call.resolve();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);
        String action = intent.getAction();
        Uri data = intent.getData();
        if (!"android.intent.action.VIEW".equals(action) || data == null) {
            return;
        }
        JSObject jSObject = new JSObject();
        jSObject.put(ImagesContract.URL, data.toString());
        notifyListeners(EVENT_URL_OPEN, jSObject, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnDestroy() {
        unsetAppListeners();
    }

    private void unsetAppListeners() {
        this.bridge.getApp().setStatusChangeListener(null);
        this.bridge.getApp().setAppRestoredListener(null);
    }
}
