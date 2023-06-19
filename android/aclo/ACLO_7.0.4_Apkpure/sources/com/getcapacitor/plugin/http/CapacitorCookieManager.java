package com.getcapacitor.plugin.http;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/* loaded from: classes.dex */
public class CapacitorCookieManager extends CookieManager {
    private final android.webkit.CookieManager webkitCookieManager;

    public CapacitorCookieManager() {
        this(null, null);
    }

    public CapacitorCookieManager(CookieStore store, CookiePolicy policy) {
        super(store, policy);
        this.webkitCookieManager = android.webkit.CookieManager.getInstance();
    }

    public String getCookieString(String url) {
        return this.webkitCookieManager.getCookie(url);
    }

    public HttpCookie getCookie(String url, String key) {
        HttpCookie[] cookies;
        for (HttpCookie httpCookie : getCookies(url)) {
            if (httpCookie.getName().equals(key)) {
                return httpCookie;
            }
        }
        return null;
    }

    public HttpCookie[] getCookies(String url) {
        try {
            ArrayList arrayList = new ArrayList();
            String cookieString = getCookieString(url);
            if (cookieString != null) {
                for (String str : cookieString.split(";")) {
                    HttpCookie httpCookie = HttpCookie.parse(str).get(0);
                    httpCookie.setValue(httpCookie.getValue());
                    arrayList.add(httpCookie);
                }
            }
            return (HttpCookie[]) arrayList.toArray(new HttpCookie[arrayList.size()]);
        } catch (Exception unused) {
            return new HttpCookie[0];
        }
    }

    public void setCookie(String url, String value) {
        this.webkitCookieManager.setCookie(url, value);
        flush();
    }

    public void setCookie(String url, String key, String value) {
        setCookie(url, key + "=" + value);
    }

    public void removeAllCookies() {
        this.webkitCookieManager.removeAllCookies(null);
        flush();
    }

    public void flush() {
        this.webkitCookieManager.flush();
    }

    @Override // java.net.CookieManager, java.net.CookieHandler
    public void put(URI uri, Map<String, List<String>> responseHeaders) {
        if (uri == null || responseHeaders == null) {
            return;
        }
        String uri2 = uri.toString();
        for (String str : responseHeaders.keySet()) {
            if (str != null && (str.equalsIgnoreCase("Set-Cookie2") || str.equalsIgnoreCase("Set-Cookie"))) {
                List<String> list = responseHeaders.get(str);
                Objects.requireNonNull(list);
                for (String str2 : list) {
                    setCookie(uri2, str2);
                }
            }
        }
    }

    @Override // java.net.CookieManager, java.net.CookieHandler
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) {
        if (uri == null || requestHeaders == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        String uri2 = uri.toString();
        HashMap hashMap = new HashMap();
        String cookieString = getCookieString(uri2);
        if (cookieString != null) {
            hashMap.put("Cookie", Collections.singletonList(cookieString));
        }
        return hashMap;
    }

    @Override // java.net.CookieManager
    public CookieStore getCookieStore() {
        throw new UnsupportedOperationException();
    }
}
