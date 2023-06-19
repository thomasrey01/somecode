package com.getcapacitor.plugin.http;

import android.util.Log;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.CapConfig;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.plugin.http.HttpRequestHandler;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
@CapacitorPlugin(name = "Http", permissions = {@Permission(alias = "HttpWrite", strings = {"android.permission.WRITE_EXTERNAL_STORAGE"}), @Permission(alias = "HttpRead", strings = {"android.permission.WRITE_EXTERNAL_STORAGE"})})
/* loaded from: classes.dex */
public class Http extends Plugin {
    public static final int HTTP_REQUEST_DOWNLOAD_WRITE_PERMISSIONS = 9022;
    public static final int HTTP_REQUEST_UPLOAD_READ_PERMISSIONS = 9023;
    CapConfig capConfig;
    CapacitorCookieManager cookieManager;

    private String getServerUrl(PluginCall call) {
        String string = call.getString(ImagesContract.URL, "");
        if (getUri(string) == null) {
            call.reject("Invalid URL. Check that \"server\" is passed in correctly");
            return "";
        }
        return string;
    }

    private URI getUri(String url) {
        try {
            return new URI(url);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean isStoragePermissionGranted(PluginCall call, String permission) {
        if (hasPermission(permission)) {
            String logTag = getLogTag();
            Log.v(logTag, "Permission '" + permission + "' is granted");
            return true;
        }
        String logTag2 = getLogTag();
        Log.v(logTag2, "Permission '" + permission + "' denied. Asking user for it.");
        requestPermissions(call);
        return false;
    }

    private void http(final PluginCall call, final String httpMethod) {
        new Thread(new Runnable() { // from class: com.getcapacitor.plugin.http.Http.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    call.resolve(HttpRequestHandler.request(call, httpMethod));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    call.reject(e.getClass().getSimpleName(), e);
                }
            }
        }).start();
    }

    @Override // com.getcapacitor.Plugin
    public void load() {
        CapacitorCookieManager capacitorCookieManager = new CapacitorCookieManager(null, CookiePolicy.ACCEPT_ALL);
        this.cookieManager = capacitorCookieManager;
        CookieHandler.setDefault(capacitorCookieManager);
        this.capConfig = getBridge().getConfig();
    }

    @PluginMethod
    public void request(final PluginCall call) {
        http(call, null);
    }

    @PluginMethod
    public void get(final PluginCall call) {
        http(call, ShareTarget.METHOD_GET);
    }

    @PluginMethod
    public void post(final PluginCall call) {
        http(call, ShareTarget.METHOD_POST);
    }

    @PluginMethod
    public void put(final PluginCall call) {
        http(call, "PUT");
    }

    @PluginMethod
    public void patch(final PluginCall call) {
        http(call, "PATCH");
    }

    @PluginMethod
    public void del(final PluginCall call) {
        http(call, "DELETE");
    }

    @PluginMethod
    public void downloadFile(final PluginCall call) {
        try {
            this.bridge.saveCall(call);
            if (!FilesystemUtils.isPublicDirectory(call.getString("fileDirectory", FilesystemUtils.DIRECTORY_DOCUMENTS)) || isStoragePermissionGranted(call, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                call.release(this.bridge);
                HttpRequestHandler.ProgressEmitter progressEmitter = new HttpRequestHandler.ProgressEmitter() { // from class: com.getcapacitor.plugin.http.Http.2
                    @Override // com.getcapacitor.plugin.http.HttpRequestHandler.ProgressEmitter
                    public void emit(Integer bytes, Integer contentLength) {
                    }
                };
                if (call.getBoolean(NotificationCompat.CATEGORY_PROGRESS, false).booleanValue()) {
                    progressEmitter = new HttpRequestHandler.ProgressEmitter() { // from class: com.getcapacitor.plugin.http.Http.3
                        @Override // com.getcapacitor.plugin.http.HttpRequestHandler.ProgressEmitter
                        public void emit(final Integer bytes, final Integer contentLength) {
                            JSObject jSObject = new JSObject();
                            jSObject.put("type", "DOWNLOAD");
                            jSObject.put(ImagesContract.URL, call.getString(ImagesContract.URL));
                            jSObject.put("bytes", (Object) bytes);
                            jSObject.put("contentLength", (Object) contentLength);
                            Http.this.notifyListeners(NotificationCompat.CATEGORY_PROGRESS, jSObject);
                        }
                    };
                }
                call.resolve(HttpRequestHandler.downloadFile(call, getContext(), progressEmitter));
            }
        } catch (MalformedURLException e) {
            call.reject("Invalid URL", e);
        } catch (IOException e2) {
            call.reject("IO Error", e2);
        } catch (Exception e3) {
            call.reject("Error", e3);
        }
    }

    @PluginMethod
    public void uploadFile(PluginCall call) {
        try {
            String string = call.getString("fileDirectory", FilesystemUtils.DIRECTORY_DOCUMENTS);
            this.bridge.saveCall(call);
            if (!FilesystemUtils.isPublicDirectory(string) || isStoragePermissionGranted(call, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                call.release(this.bridge);
                call.resolve(HttpRequestHandler.uploadFile(call, getContext()));
            }
        } catch (Exception e) {
            call.reject("Error", e);
        }
    }

    @PluginMethod
    public void setCookie(PluginCall call) {
        String string = call.getString("key");
        String string2 = call.getString("value");
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        this.cookieManager.setCookie(serverUrl, string, string2);
        call.resolve();
    }

    @PluginMethod
    public void getCookiesMap(PluginCall call) {
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie[] cookies = this.cookieManager.getCookies(serverUrl);
        JSObject jSObject = new JSObject();
        for (HttpCookie httpCookie : cookies) {
            jSObject.put(httpCookie.getName(), httpCookie.getValue());
        }
        call.resolve(jSObject);
    }

    @PluginMethod
    public void getCookies(PluginCall call) {
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie[] cookies = this.cookieManager.getCookies(serverUrl);
        JSArray jSArray = new JSArray();
        for (HttpCookie httpCookie : cookies) {
            JSObject jSObject = new JSObject();
            jSObject.put("key", httpCookie.getName());
            jSObject.put("value", httpCookie.getValue());
            jSArray.put(jSObject);
        }
        JSObject jSObject2 = new JSObject();
        jSObject2.put("cookies", (Object) jSArray);
        call.resolve(jSObject2);
    }

    @PluginMethod
    public void getCookie(PluginCall call) {
        String string = call.getString("key");
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie cookie = this.cookieManager.getCookie(serverUrl, string);
        JSObject jSObject = new JSObject();
        jSObject.put("key", string);
        if (cookie != null) {
            jSObject.put("value", cookie.getValue());
        } else {
            jSObject.put("value", "");
        }
        call.resolve(jSObject);
    }

    @PluginMethod
    public void deleteCookie(PluginCall call) {
        String string = call.getString("key");
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        CapacitorCookieManager capacitorCookieManager = this.cookieManager;
        capacitorCookieManager.setCookie(serverUrl, string + "=; Expires=Wed, 31 Dec 2000 23:59:59 GMT");
        call.resolve();
    }

    @PluginMethod
    public void clearCookies(PluginCall call) {
        HttpCookie[] cookies;
        String serverUrl = getServerUrl(call);
        if (serverUrl.isEmpty()) {
            return;
        }
        for (HttpCookie httpCookie : this.cookieManager.getCookies(serverUrl)) {
            this.cookieManager.setCookie(serverUrl, httpCookie.getName() + "=; Expires=Wed, 31 Dec 2000 23:59:59 GMT");
        }
        call.resolve();
    }

    @PluginMethod
    public void clearAllCookies(PluginCall call) {
        this.cookieManager.removeAllCookies();
        call.resolve();
    }
}
