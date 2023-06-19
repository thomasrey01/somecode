package org.apache.cordova.inappbrowser;

import android.os.Message;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
/* loaded from: classes.dex */
public class InAppChromeClient extends WebChromeClient {
    private String LOG_TAG = "InAppChromeClient";
    private long MAX_QUOTA = 104857600;
    private CordovaWebView webView;

    public InAppChromeClient(CordovaWebView webView) {
        this.webView = webView;
    }

    @Override // android.webkit.WebChromeClient
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
        LOG.d(this.LOG_TAG, "onExceededDatabaseQuota estimatedSize: %d  currentQuota: %d  totalUsedQuota: %d", Long.valueOf(estimatedSize), Long.valueOf(currentQuota), Long.valueOf(totalUsedQuota));
        quotaUpdater.updateQuota(this.MAX_QUOTA);
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        callback.invoke(origin, true, false);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        PluginResult pluginResult;
        if (defaultValue == null || !defaultValue.startsWith("gap")) {
            return false;
        }
        if (defaultValue.startsWith("gap-iab://")) {
            String substring = defaultValue.substring(10);
            if (substring.matches("^InAppBrowser[0-9]{1,10}$")) {
                if (message == null || message.length() == 0) {
                    pluginResult = new PluginResult(PluginResult.Status.OK, new JSONArray());
                } else {
                    try {
                        pluginResult = new PluginResult(PluginResult.Status.OK, new JSONArray(message));
                    } catch (JSONException e) {
                        pluginResult = new PluginResult(PluginResult.Status.JSON_EXCEPTION, e.getMessage());
                    }
                }
                this.webView.sendPluginResult(pluginResult, substring);
                result.confirm("");
                return true;
            }
            String str = this.LOG_TAG;
            LOG.w(str, "InAppBrowser callback called with invalid callbackId : " + substring);
            result.cancel();
            return true;
        }
        String str2 = this.LOG_TAG;
        LOG.w(str2, "InAppBrowser does not support Cordova API calls: " + url + " " + defaultValue);
        result.cancel();
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(final WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebViewClient webViewClient = new WebViewClient() { // from class: org.apache.cordova.inappbrowser.InAppChromeClient.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view2, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view2, String url) {
                view.loadUrl(url);
                return true;
            }
        };
        WebView webView = new WebView(view.getContext());
        webView.setWebViewClient(webViewClient);
        ((WebView.WebViewTransport) resultMsg.obj).setWebView(webView);
        resultMsg.sendToTarget();
        return true;
    }
}
