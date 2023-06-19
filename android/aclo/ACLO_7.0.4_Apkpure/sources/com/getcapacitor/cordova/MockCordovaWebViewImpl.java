package com.getcapacitor.cordova;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.getcapacitor.cordova.MockCordovaWebViewImpl;
import java.util.List;
import java.util.Map;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaResourceApi;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.ICordovaCookieManager;
import org.apache.cordova.NativeToJsMessageQueue;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
/* loaded from: classes.dex */
public class MockCordovaWebViewImpl implements CordovaWebView {
    private Context context;
    private CapacitorCordovaCookieManager cookieManager;
    private CordovaInterface cordova;
    private boolean hasPausedEver;
    private NativeToJsMessageQueue nativeToJsMessageQueue;
    private PluginManager pluginManager;
    private CordovaPreferences preferences;
    private CordovaResourceApi resourceApi;
    private WebView webView;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$triggerDocumentEvent$1(String str) {
    }

    @Override // org.apache.cordova.CordovaWebView
    public boolean backHistory() {
        return false;
    }

    @Override // org.apache.cordova.CordovaWebView
    public boolean canGoBack() {
        return false;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void clearCache() {
    }

    @Override // org.apache.cordova.CordovaWebView
    public void clearCache(boolean b) {
    }

    @Override // org.apache.cordova.CordovaWebView
    public void clearHistory() {
    }

    @Override // org.apache.cordova.CordovaWebView
    public CordovaWebViewEngine getEngine() {
        return null;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void hideCustomView() {
    }

    @Override // org.apache.cordova.CordovaWebView
    public boolean isButtonPlumbedToJs(int keyCode) {
        return false;
    }

    @Override // org.apache.cordova.CordovaWebView
    public boolean isCustomViewShowing() {
        return false;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void setButtonPlumbedToJs(int keyCode, boolean override) {
    }

    @Override // org.apache.cordova.CordovaWebView
    public void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
    }

    @Override // org.apache.cordova.CordovaWebView
    public void showWebPage(String url, boolean openExternal, boolean clearHistory, Map<String, Object> params) {
    }

    @Override // org.apache.cordova.CordovaWebView
    public void stopLoading() {
    }

    public MockCordovaWebViewImpl(Context context) {
        this.context = context;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void init(CordovaInterface cordova, List<PluginEntry> pluginEntries, CordovaPreferences preferences) {
        this.cordova = cordova;
        this.preferences = preferences;
        PluginManager pluginManager = new PluginManager(this, cordova, pluginEntries);
        this.pluginManager = pluginManager;
        this.resourceApi = new CordovaResourceApi(this.context, pluginManager);
        this.pluginManager.init();
    }

    public void init(CordovaInterface cordova, List<PluginEntry> pluginEntries, CordovaPreferences preferences, WebView webView) {
        this.cordova = cordova;
        this.webView = webView;
        this.preferences = preferences;
        PluginManager pluginManager = new PluginManager(this, cordova, pluginEntries);
        this.pluginManager = pluginManager;
        this.resourceApi = new CordovaResourceApi(this.context, pluginManager);
        NativeToJsMessageQueue nativeToJsMessageQueue = new NativeToJsMessageQueue();
        this.nativeToJsMessageQueue = nativeToJsMessageQueue;
        nativeToJsMessageQueue.addBridgeMode(new CapacitorEvalBridgeMode(webView, this.cordova));
        this.nativeToJsMessageQueue.setBridgeMode(0);
        this.cookieManager = new CapacitorCordovaCookieManager(webView);
        this.pluginManager.init();
    }

    /* loaded from: classes.dex */
    public static class CapacitorEvalBridgeMode extends NativeToJsMessageQueue.BridgeMode {
        private final CordovaInterface cordova;
        private final WebView webView;

        public CapacitorEvalBridgeMode(WebView webView, CordovaInterface cordova) {
            this.webView = webView;
            this.cordova = cordova;
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void onNativeToJsMessageAvailable(final NativeToJsMessageQueue queue) {
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: com.getcapacitor.cordova.-$$Lambda$MockCordovaWebViewImpl$CapacitorEvalBridgeMode$dsH1z3YU9EN0upNubIbxcFkTBDc
                @Override // java.lang.Runnable
                public final void run() {
                    MockCordovaWebViewImpl.CapacitorEvalBridgeMode.this.lambda$onNativeToJsMessageAvailable$0$MockCordovaWebViewImpl$CapacitorEvalBridgeMode(queue);
                }
            });
        }

        public /* synthetic */ void lambda$onNativeToJsMessageAvailable$0$MockCordovaWebViewImpl$CapacitorEvalBridgeMode(NativeToJsMessageQueue nativeToJsMessageQueue) {
            String popAndEncodeAsJs = nativeToJsMessageQueue.popAndEncodeAsJs();
            if (popAndEncodeAsJs != null) {
                this.webView.evaluateJavascript(popAndEncodeAsJs, null);
            }
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public boolean isInitialized() {
        return this.cordova != null;
    }

    @Override // org.apache.cordova.CordovaWebView
    public View getView() {
        return this.webView;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void loadUrlIntoView(String url, boolean recreatePlugins) {
        if (url.equals("about:blank") || url.startsWith("javascript:")) {
            this.webView.loadUrl(url);
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void handlePause(boolean keepRunning) {
        if (isInitialized()) {
            this.hasPausedEver = true;
            this.pluginManager.onPause(keepRunning);
            triggerDocumentEvent("pause");
            if (keepRunning) {
                return;
            }
            setPaused(true);
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void onNewIntent(Intent intent) {
        PluginManager pluginManager = this.pluginManager;
        if (pluginManager != null) {
            pluginManager.onNewIntent(intent);
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void handleResume(boolean keepRunning) {
        if (isInitialized()) {
            setPaused(false);
            this.pluginManager.onResume(keepRunning);
            if (this.hasPausedEver) {
                triggerDocumentEvent("resume");
            }
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void handleStart() {
        if (isInitialized()) {
            this.pluginManager.onStart();
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void handleStop() {
        if (isInitialized()) {
            this.pluginManager.onStop();
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void handleDestroy() {
        if (isInitialized()) {
            this.pluginManager.onDestroy();
        }
    }

    @Override // org.apache.cordova.CordovaWebView
    public void sendJavascript(String statememt) {
        this.nativeToJsMessageQueue.addJavaScript(statememt);
    }

    public void eval(final String js, final ValueCallback<String> callback) {
        new Handler(this.context.getMainLooper()).post(new Runnable() { // from class: com.getcapacitor.cordova.-$$Lambda$MockCordovaWebViewImpl$3BLNvjdgewq5fvNTQrfrs8pTTuI
            @Override // java.lang.Runnable
            public final void run() {
                MockCordovaWebViewImpl.this.lambda$eval$0$MockCordovaWebViewImpl(js, callback);
            }
        });
    }

    public /* synthetic */ void lambda$eval$0$MockCordovaWebViewImpl(String str, ValueCallback valueCallback) {
        this.webView.evaluateJavascript(str, valueCallback);
    }

    public void triggerDocumentEvent(final String eventName) {
        eval("window.Capacitor.triggerEvent('" + eventName + "', 'document');", new ValueCallback() { // from class: com.getcapacitor.cordova.-$$Lambda$MockCordovaWebViewImpl$VtW_1RL4aNZQSZweEhKanb87-q4
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                MockCordovaWebViewImpl.lambda$triggerDocumentEvent$1((String) obj);
            }
        });
    }

    @Override // org.apache.cordova.CordovaWebView
    public CordovaResourceApi getResourceApi() {
        return this.resourceApi;
    }

    @Override // org.apache.cordova.CordovaWebView
    public void sendPluginResult(PluginResult cr, String callbackId) {
        this.nativeToJsMessageQueue.addPluginResult(cr, callbackId);
    }

    @Override // org.apache.cordova.CordovaWebView
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override // org.apache.cordova.CordovaWebView
    public CordovaPreferences getPreferences() {
        return this.preferences;
    }

    @Override // org.apache.cordova.CordovaWebView
    public ICordovaCookieManager getCookieManager() {
        return this.cookieManager;
    }

    @Override // org.apache.cordova.CordovaWebView
    public String getUrl() {
        return this.webView.getUrl();
    }

    @Override // org.apache.cordova.CordovaWebView
    public Context getContext() {
        return this.webView.getContext();
    }

    @Override // org.apache.cordova.CordovaWebView
    public void loadUrl(String url) {
        loadUrlIntoView(url, true);
    }

    @Override // org.apache.cordova.CordovaWebView
    public Object postMessage(String id, Object data) {
        return this.pluginManager.postMessage(id, data);
    }

    public void setPaused(boolean value) {
        if (value) {
            this.webView.onPause();
            this.webView.pauseTimers();
            return;
        }
        this.webView.onResume();
        this.webView.resumeTimers();
    }
}
