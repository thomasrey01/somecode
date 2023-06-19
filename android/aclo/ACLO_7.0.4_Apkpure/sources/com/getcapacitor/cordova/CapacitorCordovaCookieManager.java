package com.getcapacitor.cordova;

import android.webkit.CookieManager;
import android.webkit.WebView;
import org.apache.cordova.ICordovaCookieManager;
/* loaded from: classes.dex */
class CapacitorCordovaCookieManager implements ICordovaCookieManager {
    private final CookieManager cookieManager;
    protected final WebView webView;

    public CapacitorCordovaCookieManager(WebView webview) {
        this.webView = webview;
        CookieManager cookieManager = CookieManager.getInstance();
        this.cookieManager = cookieManager;
        CookieManager.setAcceptFileSchemeCookies(true);
        cookieManager.setAcceptThirdPartyCookies(webview, true);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void setCookiesEnabled(boolean accept) {
        this.cookieManager.setAcceptCookie(accept);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void setCookie(final String url, final String value) {
        this.cookieManager.setCookie(url, value);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public String getCookie(final String url) {
        return this.cookieManager.getCookie(url);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void clearCookies() {
        this.cookieManager.removeAllCookie();
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void flush() {
        this.cookieManager.flush();
    }
}
