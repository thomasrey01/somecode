package org.apache.cordova.engine;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebView;
import org.apache.cordova.ICordovaCookieManager;
/* loaded from: classes.dex */
class SystemCookieManager implements ICordovaCookieManager {
    private final CookieManager cookieManager;
    protected final WebView webView;

    public SystemCookieManager(WebView webView) {
        this.webView = webView;
        CookieManager cookieManager = CookieManager.getInstance();
        this.cookieManager = cookieManager;
        CookieManager.setAcceptFileSchemeCookies(true);
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void setCookiesEnabled(boolean z) {
        this.cookieManager.setAcceptCookie(z);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void setCookie(String str, String str2) {
        this.cookieManager.setCookie(str, str2);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public String getCookie(String str) {
        return this.cookieManager.getCookie(str);
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void clearCookies() {
        this.cookieManager.removeAllCookie();
    }

    @Override // org.apache.cordova.ICordovaCookieManager
    public void flush() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.cookieManager.flush();
        }
    }
}
