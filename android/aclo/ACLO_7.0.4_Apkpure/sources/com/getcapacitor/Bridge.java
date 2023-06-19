package com.getcapacitor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.getcapacitor.android.R;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.cordova.MockCordovaInterfaceImpl;
import com.getcapacitor.cordova.MockCordovaWebViewImpl;
import com.getcapacitor.util.HostMask;
import com.getcapacitor.util.PermissionHelper;
import com.getcapacitor.util.WebColor;
import java.io.File;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginEntry;
import org.json.JSONException;
/* loaded from: classes.dex */
public class Bridge {
    private static final String BUNDLE_LAST_PLUGIN_CALL_METHOD_NAME_KEY = "capacitorLastActivityPluginMethod";
    private static final String BUNDLE_LAST_PLUGIN_ID_KEY = "capacitorLastActivityPluginId";
    private static final String BUNDLE_PLUGIN_CALL_BUNDLE_KEY = "capacitorLastPluginCallBundle";
    private static final String BUNDLE_PLUGIN_CALL_OPTIONS_SAVED_KEY = "capacitorLastPluginCallOptions";
    public static final String CAPACITOR_CONTENT_START = "/_capacitor_content_";
    public static final String CAPACITOR_FILE_START = "/_capacitor_file_";
    public static final String CAPACITOR_HTTPS_SCHEME = "https";
    public static final String CAPACITOR_HTTP_SCHEME = "http";
    public static final String DEFAULT_WEB_ASSET_DIR = "public";
    private static final String LAST_BINARY_VERSION_CODE = "lastBinaryVersionCode";
    private static final String LAST_BINARY_VERSION_NAME = "lastBinaryVersionName";
    private static final String PERMISSION_PREFS_NAME = "PluginPermStates";
    private static final String PREFS_NAME = "CapacitorSettings";
    private App app;
    private HostMask appAllowNavigationMask;
    private String appUrl;
    private String appUrlConfig;
    private CapConfig config;
    private final AppCompatActivity context;
    public final MockCordovaInterfaceImpl cordovaInterface;
    private CordovaWebView cordovaWebView;
    private final Fragment fragment;
    private final HandlerThread handlerThread;
    private final List<Class<? extends Plugin>> initialPlugins;
    private Uri intentUri;
    private WebViewLocalServer localServer;
    private String localUrl;
    private final MessageHandler msgHandler;
    private PluginCall pluginCallForLastActivity;
    private Map<String, PluginHandle> plugins;
    private CordovaPreferences preferences;
    private RouteProcessor routeProcessor;
    private Map<String, PluginCall> savedCalls;
    private Map<String, LinkedList<String>> savedPermissionCallIds;
    private Handler taskHandler;
    private final WebView webView;
    private BridgeWebViewClient webViewClient;
    private List<WebViewListener> webViewListeners;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$triggerJSEvent$2(String str) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$triggerJSEvent$3(String str) {
    }

    @Deprecated
    public Bridge(AppCompatActivity context, WebView webView, List<Class<? extends Plugin>> initialPlugins, MockCordovaInterfaceImpl cordovaInterface, org.apache.cordova.PluginManager pluginManager, CordovaPreferences preferences, CapConfig config) {
        this(context, null, webView, initialPlugins, cordovaInterface, pluginManager, preferences, config);
    }

    private Bridge(AppCompatActivity context, Fragment fragment, WebView webView, List<Class<? extends Plugin>> initialPlugins, MockCordovaInterfaceImpl cordovaInterface, org.apache.cordova.PluginManager pluginManager, CordovaPreferences preferences, CapConfig config) {
        HandlerThread handlerThread = new HandlerThread("CapacitorPlugins");
        this.handlerThread = handlerThread;
        this.taskHandler = null;
        this.plugins = new HashMap();
        this.savedCalls = new HashMap();
        this.savedPermissionCallIds = new HashMap();
        this.webViewListeners = new ArrayList();
        this.app = new App();
        this.context = context;
        this.fragment = fragment;
        this.webView = webView;
        this.webViewClient = new BridgeWebViewClient(this);
        this.initialPlugins = initialPlugins;
        this.cordovaInterface = cordovaInterface;
        this.preferences = preferences;
        handlerThread.start();
        this.taskHandler = new Handler(handlerThread.getLooper());
        config = config == null ? CapConfig.loadDefault(getActivity()) : config;
        this.config = config;
        Logger.init(config);
        initWebView();
        this.msgHandler = new MessageHandler(this, webView, pluginManager);
        this.intentUri = context.getIntent().getData();
        registerAllPlugins();
        loadWebView();
    }

    public App getApp() {
        return this.app;
    }

    private void loadWebView() {
        String string;
        this.appUrlConfig = getServerUrl();
        String[] allowNavigation = this.config.getAllowNavigation();
        ArrayList arrayList = new ArrayList();
        if (allowNavigation != null) {
            arrayList.addAll(Arrays.asList(allowNavigation));
        }
        this.appAllowNavigationMask = HostMask.Parser.parse(allowNavigation);
        String host = getHost();
        arrayList.add(host);
        String scheme = getScheme();
        String str = scheme + "://" + host;
        this.localUrl = str;
        if (this.appUrlConfig != null) {
            try {
                arrayList.add(new URL(this.appUrlConfig).getAuthority());
            } catch (Exception unused) {
            }
            String str2 = this.appUrlConfig;
            this.localUrl = str2;
            this.appUrl = str2;
        } else {
            this.appUrl = str;
            if (!scheme.equals(CAPACITOR_HTTP_SCHEME) && !scheme.equals(CAPACITOR_HTTPS_SCHEME)) {
                this.appUrl += "/";
            }
        }
        String startPath = this.config.getStartPath();
        if (startPath != null && !startPath.trim().isEmpty()) {
            this.appUrl += startPath;
        }
        WebViewLocalServer webViewLocalServer = new WebViewLocalServer(this.context, this, getJSInjector(), arrayList, this.config.isHTML5Mode());
        this.localServer = webViewLocalServer;
        webViewLocalServer.hostAssets(DEFAULT_WEB_ASSET_DIR);
        Logger.debug("Loading app at " + this.appUrl);
        this.webView.setWebChromeClient(new BridgeWebChromeClient(this));
        this.webView.setWebViewClient(this.webViewClient);
        if (!isDeployDisabled() && !isNewBinary() && (string = getContext().getSharedPreferences(com.getcapacitor.plugin.WebView.WEBVIEW_PREFS_NAME, 0).getString(com.getcapacitor.plugin.WebView.CAP_SERVER_PATH, null)) != null && !string.isEmpty() && new File(string).exists()) {
            setServerBasePath(string);
        }
        this.webView.loadUrl(this.appUrl);
    }

    public boolean launchIntent(Uri url) {
        Boolean shouldOverrideLoad;
        for (Map.Entry<String, PluginHandle> entry : this.plugins.entrySet()) {
            Plugin pluginHandle = entry.getValue().getInstance();
            if (pluginHandle != null && (shouldOverrideLoad = pluginHandle.shouldOverrideLoad(url)) != null) {
                return shouldOverrideLoad.booleanValue();
            }
        }
        if (url.toString().contains(this.appUrl) || this.appAllowNavigationMask.matches(url.getHost())) {
            return false;
        }
        try {
            getContext().startActivity(new Intent("android.intent.action.VIEW", url));
            return true;
        } catch (ActivityNotFoundException unused) {
            return true;
        }
    }

    private boolean isNewBinary() {
        String str;
        String str2;
        PackageInfo packageInfo;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(com.getcapacitor.plugin.WebView.WEBVIEW_PREFS_NAME, 0);
        String string = sharedPreferences.getString(LAST_BINARY_VERSION_CODE, null);
        String string2 = sharedPreferences.getString(LAST_BINARY_VERSION_NAME, null);
        try {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            str = Integer.toString(packageInfo.versionCode);
        } catch (Exception e) {
            e = e;
            str = "";
        }
        try {
            str2 = packageInfo.versionName;
        } catch (Exception e2) {
            e = e2;
            Logger.error("Unable to get package info", e);
            str2 = "";
            if (str.equals(string)) {
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(LAST_BINARY_VERSION_CODE, str);
            edit.putString(LAST_BINARY_VERSION_NAME, str2);
            edit.putString(com.getcapacitor.plugin.WebView.CAP_SERVER_PATH, "");
            edit.apply();
            return true;
        }
        if (str.equals(string) || !str2.equals(string2)) {
            SharedPreferences.Editor edit2 = sharedPreferences.edit();
            edit2.putString(LAST_BINARY_VERSION_CODE, str);
            edit2.putString(LAST_BINARY_VERSION_NAME, str2);
            edit2.putString(com.getcapacitor.plugin.WebView.CAP_SERVER_PATH, "");
            edit2.apply();
            return true;
        }
        return false;
    }

    public boolean isDeployDisabled() {
        return this.preferences.getBoolean("DisableDeploy", false);
    }

    public boolean shouldKeepRunning() {
        return this.preferences.getBoolean("KeepRunning", true);
    }

    public void handleAppUrlLoadError(Exception ex) {
        if (ex instanceof SocketTimeoutException) {
            Logger.error("Unable to load app. Ensure the server is running at " + this.appUrl + ", or modify the appUrl setting in capacitor.config.json (make sure to npx cap copy after to commit changes).", ex);
        }
    }

    public boolean isDevMode() {
        return (getActivity().getApplicationInfo().flags & 2) != 0;
    }

    protected void setCordovaWebView(CordovaWebView cordovaWebView) {
        this.cordovaWebView = cordovaWebView;
    }

    public Context getContext() {
        return this.context;
    }

    public AppCompatActivity getActivity() {
        return this.context;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public WebView getWebView() {
        return this.webView;
    }

    public Uri getIntentUri() {
        return this.intentUri;
    }

    public String getScheme() {
        return this.config.getAndroidScheme();
    }

    public String getHost() {
        return this.config.getHostname();
    }

    public String getServerUrl() {
        return this.config.getServerUrl();
    }

    public CapConfig getConfig() {
        return this.config;
    }

    public void reset() {
        this.savedCalls = new HashMap();
    }

    private void initWebView() {
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (this.config.isMixedContentAllowed()) {
            settings.setMixedContentMode(0);
        }
        String appendedUserAgentString = this.config.getAppendedUserAgentString();
        if (appendedUserAgentString != null) {
            String userAgentString = settings.getUserAgentString();
            settings.setUserAgentString(userAgentString + " " + appendedUserAgentString);
        }
        String overriddenUserAgentString = this.config.getOverriddenUserAgentString();
        if (overriddenUserAgentString != null) {
            settings.setUserAgentString(overriddenUserAgentString);
        }
        String backgroundColor = this.config.getBackgroundColor();
        if (backgroundColor != null) {
            try {
                this.webView.setBackgroundColor(WebColor.parseColor(backgroundColor));
            } catch (IllegalArgumentException unused) {
                Logger.debug("WebView background color not applied");
            }
        }
        if (this.config.isInitialFocus()) {
            this.webView.requestFocusFromTouch();
        }
        WebView.setWebContentsDebuggingEnabled(this.config.isWebContentsDebuggingEnabled());
    }

    private void registerAllPlugins() {
        registerPlugin(com.getcapacitor.plugin.WebView.class);
        for (Class<? extends Plugin> cls : this.initialPlugins) {
            registerPlugin(cls);
        }
    }

    public void registerPlugins(Class<? extends Plugin>[] pluginClasses) {
        for (Class<? extends Plugin> cls : pluginClasses) {
            registerPlugin(cls);
        }
    }

    public void registerPlugin(Class<? extends Plugin> pluginClass) {
        String name;
        CapacitorPlugin capacitorPlugin = (CapacitorPlugin) pluginClass.getAnnotation(CapacitorPlugin.class);
        if (capacitorPlugin == null) {
            NativePlugin nativePlugin = (NativePlugin) pluginClass.getAnnotation(NativePlugin.class);
            if (nativePlugin == null) {
                Logger.error("Plugin doesn't have the @CapacitorPlugin annotation. Please add it");
                return;
            }
            name = nativePlugin.name();
        } else {
            name = capacitorPlugin.name();
        }
        String simpleName = pluginClass.getSimpleName();
        if (name.equals("")) {
            name = simpleName;
        }
        Logger.debug("Registering plugin: " + name);
        try {
            this.plugins.put(name, new PluginHandle(this, pluginClass));
        } catch (InvalidPluginException unused) {
            Logger.error("NativePlugin " + pluginClass.getName() + " is invalid. Ensure the @CapacitorPlugin annotation exists on the plugin class and the class extends Plugin");
        } catch (PluginLoadException e) {
            Logger.error("NativePlugin " + pluginClass.getName() + " failed to load", e);
        }
    }

    public PluginHandle getPlugin(String pluginId) {
        return this.plugins.get(pluginId);
    }

    @Deprecated
    public PluginHandle getPluginWithRequestCode(int requestCode) {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            CapacitorPlugin pluginAnnotation = pluginHandle.getPluginAnnotation();
            int i = 0;
            if (pluginAnnotation == null) {
                NativePlugin legacyPluginAnnotation = pluginHandle.getLegacyPluginAnnotation();
                if (legacyPluginAnnotation == null) {
                    continue;
                } else if (legacyPluginAnnotation.permissionRequestCode() == requestCode) {
                    return pluginHandle;
                } else {
                    int[] requestCodes = legacyPluginAnnotation.requestCodes();
                    int length = requestCodes.length;
                    while (i < length) {
                        if (requestCodes[i] == requestCode) {
                            return pluginHandle;
                        }
                        i++;
                    }
                    continue;
                }
            } else {
                int[] requestCodes2 = pluginAnnotation.requestCodes();
                int length2 = requestCodes2.length;
                while (i < length2) {
                    if (requestCodes2[i] == requestCode) {
                        return pluginHandle;
                    }
                    i++;
                }
                continue;
            }
        }
        return null;
    }

    public void callPluginMethod(String pluginId, final String methodName, final PluginCall call) {
        try {
            final PluginHandle plugin = getPlugin(pluginId);
            if (plugin == null) {
                Logger.error("unable to find plugin : " + pluginId);
                call.errorCallback("unable to find plugin : " + pluginId);
                return;
            }
            Logger.verbose("callback: " + call.getCallbackId() + ", pluginId: " + plugin.getId() + ", methodName: " + methodName + ", methodData: " + call.getData().toString());
            this.taskHandler.post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$Bridge$25SFHybyAQk7zS27hTVXh2p8tmw
                @Override // java.lang.Runnable
                public final void run() {
                    Bridge.this.lambda$callPluginMethod$0$Bridge(plugin, methodName, call);
                }
            });
        } catch (Exception e) {
            String tags = Logger.tags("callPluginMethod");
            Logger.error(tags, "error : " + e, null);
            call.errorCallback(e.toString());
        }
    }

    public /* synthetic */ void lambda$callPluginMethod$0$Bridge(PluginHandle pluginHandle, String str, PluginCall pluginCall) {
        try {
            pluginHandle.invoke(str, pluginCall);
            if (pluginCall.isKeptAlive()) {
                saveCall(pluginCall);
            }
        } catch (InvalidPluginMethodException e) {
            e = e;
            Logger.error("Unable to execute plugin method", e);
        } catch (PluginLoadException e2) {
            e = e2;
            Logger.error("Unable to execute plugin method", e);
        } catch (Exception e3) {
            Logger.error("Serious error executing plugin", e3);
            throw new RuntimeException(e3);
        }
    }

    public void eval(final String js, final ValueCallback<String> callback) {
        new Handler(this.context.getMainLooper()).post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$Bridge$T_8fxf_C2PYatLZjYDpV3mPlcrA
            @Override // java.lang.Runnable
            public final void run() {
                Bridge.this.lambda$eval$1$Bridge(js, callback);
            }
        });
    }

    public /* synthetic */ void lambda$eval$1$Bridge(String str, ValueCallback valueCallback) {
        this.webView.evaluateJavascript(str, valueCallback);
    }

    public void logToJs(final String message, final String level) {
        eval("window.Capacitor.logJs(\"" + message + "\", \"" + level + "\")", null);
    }

    public void logToJs(final String message) {
        logToJs(message, "log");
    }

    public void triggerJSEvent(final String eventName, final String target) {
        eval("window.Capacitor.triggerEvent(\"" + eventName + "\", \"" + target + "\")", new ValueCallback() { // from class: com.getcapacitor.-$$Lambda$Bridge$4Y4R2L3E316T0W7A8Dp4fZYlBLg
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                Bridge.lambda$triggerJSEvent$2((String) obj);
            }
        });
    }

    public void triggerJSEvent(final String eventName, final String target, final String data) {
        eval("window.Capacitor.triggerEvent(\"" + eventName + "\", \"" + target + "\", " + data + ")", new ValueCallback() { // from class: com.getcapacitor.-$$Lambda$Bridge$RpkAXeS2Es8Cx3v09sdOCv8DJd4
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                Bridge.lambda$triggerJSEvent$3((String) obj);
            }
        });
    }

    public void triggerWindowJSEvent(final String eventName) {
        triggerJSEvent(eventName, "window");
    }

    public void triggerWindowJSEvent(final String eventName, final String data) {
        triggerJSEvent(eventName, "window", data);
    }

    public void triggerDocumentJSEvent(final String eventName) {
        triggerJSEvent(eventName, "document");
    }

    public void triggerDocumentJSEvent(final String eventName, final String data) {
        triggerJSEvent(eventName, "document", data);
    }

    public void execute(Runnable runnable) {
        this.taskHandler.post(runnable);
    }

    public void executeOnMainThread(Runnable runnable) {
        new Handler(this.context.getMainLooper()).post(runnable);
    }

    public void saveCall(PluginCall call) {
        this.savedCalls.put(call.getCallbackId(), call);
    }

    public PluginCall getSavedCall(String callbackId) {
        if (callbackId == null) {
            return null;
        }
        return this.savedCalls.get(callbackId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PluginCall getPluginCallForLastActivity() {
        PluginCall pluginCall = this.pluginCallForLastActivity;
        this.pluginCallForLastActivity = null;
        return pluginCall;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPluginCallForLastActivity(PluginCall pluginCallForLastActivity) {
        this.pluginCallForLastActivity = pluginCallForLastActivity;
    }

    public void releaseCall(PluginCall call) {
        releaseCall(call.getCallbackId());
    }

    public void releaseCall(String callbackId) {
        this.savedCalls.remove(callbackId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PluginCall getPermissionCall(String pluginId) {
        LinkedList<String> linkedList = this.savedPermissionCallIds.get(pluginId);
        return getSavedCall(linkedList != null ? linkedList.poll() : null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void savePermissionCall(PluginCall call) {
        if (call != null) {
            if (!this.savedPermissionCallIds.containsKey(call.getPluginId())) {
                this.savedPermissionCallIds.put(call.getPluginId(), new LinkedList<>());
            }
            this.savedPermissionCallIds.get(call.getPluginId()).add(call.getCallbackId());
            saveCall(call);
        }
    }

    public <I, O> ActivityResultLauncher<I> registerForActivityResult(final ActivityResultContract<I, O> contract, final ActivityResultCallback<O> callback) {
        Fragment fragment = this.fragment;
        if (fragment != null) {
            return fragment.registerForActivityResult(contract, callback);
        }
        return this.context.registerForActivityResult(contract, callback);
    }

    private JSInjector getJSInjector() {
        try {
            String globalJS = JSExport.getGlobalJS(this.context, this.config.isLoggingEnabled(), isDevMode());
            String bridgeJS = JSExport.getBridgeJS(this.context);
            String pluginJS = JSExport.getPluginJS(this.plugins.values());
            String cordovaJS = JSExport.getCordovaJS(this.context);
            String cordovaPluginJS = JSExport.getCordovaPluginJS(this.context);
            String cordovaPluginsFileJS = JSExport.getCordovaPluginsFileJS(this.context);
            return new JSInjector(globalJS, bridgeJS, pluginJS, cordovaJS, cordovaPluginJS, cordovaPluginsFileJS, "window.WEBVIEW_SERVER_URL = '" + this.localUrl + "';");
        } catch (Exception e) {
            Logger.error("Unable to export Capacitor JS. App will not function!", e);
            return null;
        }
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        String string = savedInstanceState.getString(BUNDLE_LAST_PLUGIN_ID_KEY);
        String string2 = savedInstanceState.getString(BUNDLE_LAST_PLUGIN_CALL_METHOD_NAME_KEY);
        String string3 = savedInstanceState.getString(BUNDLE_PLUGIN_CALL_OPTIONS_SAVED_KEY);
        if (string != null) {
            if (string3 != null) {
                try {
                    this.pluginCallForLastActivity = new PluginCall(this.msgHandler, string, PluginCall.CALLBACK_ID_DANGLING, string2, new JSObject(string3));
                } catch (JSONException e) {
                    Logger.error("Unable to restore plugin call, unable to parse persisted JSON object", e);
                }
            }
            Bundle bundle = savedInstanceState.getBundle(BUNDLE_PLUGIN_CALL_BUNDLE_KEY);
            PluginHandle plugin = getPlugin(string);
            if (bundle != null && plugin != null) {
                plugin.getInstance().restoreState(bundle);
            } else {
                Logger.error("Unable to restore last plugin call");
            }
        }
    }

    public void saveInstanceState(Bundle outState) {
        PluginHandle plugin;
        Logger.debug("Saving instance state!");
        PluginCall pluginCall = this.pluginCallForLastActivity;
        if (pluginCall == null || (plugin = getPlugin(pluginCall.getPluginId())) == null) {
            return;
        }
        Bundle saveInstanceState = plugin.getInstance().saveInstanceState();
        if (saveInstanceState != null) {
            outState.putString(BUNDLE_LAST_PLUGIN_ID_KEY, pluginCall.getPluginId());
            outState.putString(BUNDLE_LAST_PLUGIN_CALL_METHOD_NAME_KEY, pluginCall.getMethodName());
            outState.putString(BUNDLE_PLUGIN_CALL_OPTIONS_SAVED_KEY, pluginCall.getData().toString());
            outState.putBundle(BUNDLE_PLUGIN_CALL_BUNDLE_KEY, saveInstanceState);
            return;
        }
        Logger.error("Couldn't save last " + pluginCall.getPluginId() + "'s Plugin " + pluginCall.getMethodName() + " call");
    }

    @Deprecated
    public void startActivityForPluginWithResult(PluginCall call, Intent intent, int requestCode) {
        Logger.debug("Starting activity for result");
        this.pluginCallForLastActivity = call;
        getActivity().startActivityForResult(intent, requestCode);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PluginHandle pluginWithRequestCode = getPluginWithRequestCode(requestCode);
        if (pluginWithRequestCode == null) {
            Logger.debug("Unable to find a Capacitor plugin to handle permission requestCode, trying Cordova plugins " + requestCode);
            try {
                return this.cordovaInterface.handlePermissionResult(requestCode, permissions, grantResults);
            } catch (JSONException e) {
                Logger.debug("Error on Cordova plugin permissions request " + e.getMessage());
                return false;
            }
        } else if (pluginWithRequestCode.getPluginAnnotation() == null) {
            pluginWithRequestCode.getInstance().handleRequestPermissionsResult(requestCode, permissions, grantResults);
            return true;
        } else {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean validatePermissions(Plugin plugin, PluginCall savedCall, Map<String, Boolean> permissions) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PERMISSION_PREFS_NAME, 0);
        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().booleanValue()) {
                if (sharedPreferences.getString(key, null) != null) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.remove(key);
                    edit.apply();
                }
            } else {
                SharedPreferences.Editor edit2 = sharedPreferences.edit();
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), key)) {
                    edit2.putString(key, PermissionState.PROMPT_WITH_RATIONALE.toString());
                } else {
                    edit2.putString(key, PermissionState.DENIED.toString());
                }
                edit2.apply();
            }
        }
        String[] strArr = (String[]) permissions.keySet().toArray(new String[0]);
        if (PermissionHelper.hasDefinedPermissions(getContext(), strArr)) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Missing the following permissions in AndroidManifest.xml:\n");
        for (String str : PermissionHelper.getUndefinedPermissions(getContext(), strArr)) {
            sb.append(str + "\n");
        }
        savedCall.reject(sb.toString());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Map<String, PermissionState> getPermissionStates(Plugin plugin) {
        Permission[] permissions;
        String[] strings;
        PermissionState byState;
        HashMap hashMap = new HashMap();
        for (Permission permission : plugin.getPluginHandle().getPluginAnnotation().permissions()) {
            if (permission.strings().length == 0 || (permission.strings().length == 1 && permission.strings()[0].isEmpty())) {
                String alias = permission.alias();
                if (!alias.isEmpty() && ((PermissionState) hashMap.get(alias)) == null) {
                    hashMap.put(alias, PermissionState.GRANTED);
                }
            } else {
                for (String str : permission.strings()) {
                    String alias2 = permission.alias().isEmpty() ? str : permission.alias();
                    if (ActivityCompat.checkSelfPermission(getContext(), str) == 0) {
                        byState = PermissionState.GRANTED;
                    } else {
                        PermissionState permissionState = PermissionState.PROMPT;
                        String string = getContext().getSharedPreferences(PERMISSION_PREFS_NAME, 0).getString(str, null);
                        byState = string != null ? PermissionState.byState(string) : permissionState;
                    }
                    PermissionState permissionState2 = (PermissionState) hashMap.get(alias2);
                    if (permissionState2 == null || permissionState2 == PermissionState.GRANTED) {
                        hashMap.put(alias2, byState);
                    }
                }
            }
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        PluginHandle pluginWithRequestCode = getPluginWithRequestCode(requestCode);
        if (pluginWithRequestCode == null || pluginWithRequestCode.getInstance() == null) {
            Logger.debug("Unable to find a Capacitor plugin to handle requestCode, trying Cordova plugins " + requestCode);
            return this.cordovaInterface.onActivityResult(requestCode, resultCode, data);
        }
        if (pluginWithRequestCode.getInstance().getSavedCall() == null && this.pluginCallForLastActivity != null) {
            pluginWithRequestCode.getInstance().saveCall(this.pluginCallForLastActivity);
        }
        pluginWithRequestCode.getInstance().handleOnActivityResult(requestCode, resultCode, data);
        this.pluginCallForLastActivity = null;
        return true;
    }

    public void onNewIntent(Intent intent) {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnNewIntent(intent);
        }
        CordovaWebView cordovaWebView = this.cordovaWebView;
        if (cordovaWebView != null) {
            cordovaWebView.onNewIntent(intent);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnConfigurationChanged(newConfig);
        }
    }

    public void onRestart() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnRestart();
        }
    }

    public void onStart() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnStart();
        }
        CordovaWebView cordovaWebView = this.cordovaWebView;
        if (cordovaWebView != null) {
            cordovaWebView.handleStart();
        }
    }

    public void onResume() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnResume();
        }
        CordovaWebView cordovaWebView = this.cordovaWebView;
        if (cordovaWebView != null) {
            cordovaWebView.handleResume(shouldKeepRunning());
        }
    }

    public void onPause() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnPause();
        }
        if (this.cordovaWebView != null) {
            this.cordovaWebView.handlePause(shouldKeepRunning() || this.cordovaInterface.getActivityResultCallback() != null);
        }
    }

    public void onStop() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnStop();
        }
        CordovaWebView cordovaWebView = this.cordovaWebView;
        if (cordovaWebView != null) {
            cordovaWebView.handleStop();
        }
    }

    public void onDestroy() {
        for (PluginHandle pluginHandle : this.plugins.values()) {
            pluginHandle.getInstance().handleOnDestroy();
        }
        this.handlerThread.quitSafely();
        CordovaWebView cordovaWebView = this.cordovaWebView;
        if (cordovaWebView != null) {
            cordovaWebView.handleDestroy();
        }
    }

    public void onDetachedFromWindow() {
        this.webView.removeAllViews();
        this.webView.destroy();
    }

    public String getServerBasePath() {
        return this.localServer.getBasePath();
    }

    public void setServerBasePath(String path) {
        this.localServer.hostFiles(path);
        this.webView.post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$Bridge$c0zSQw4vY0-e62tPAxvmVM0SOsI
            @Override // java.lang.Runnable
            public final void run() {
                Bridge.this.lambda$setServerBasePath$4$Bridge();
            }
        });
    }

    public /* synthetic */ void lambda$setServerBasePath$4$Bridge() {
        this.webView.loadUrl(this.appUrl);
    }

    public void setServerAssetPath(String path) {
        this.localServer.hostAssets(path);
        this.webView.post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$Bridge$GaikhWsy2fJ6D0hE1oLfsL6LkEk
            @Override // java.lang.Runnable
            public final void run() {
                Bridge.this.lambda$setServerAssetPath$5$Bridge();
            }
        });
    }

    public /* synthetic */ void lambda$setServerAssetPath$5$Bridge() {
        this.webView.loadUrl(this.appUrl);
    }

    public /* synthetic */ void lambda$reload$6$Bridge() {
        this.webView.loadUrl(this.appUrl);
    }

    public void reload() {
        this.webView.post(new Runnable() { // from class: com.getcapacitor.-$$Lambda$Bridge$nwF8IXSs54XmzuPjYWYn62JnE2w
            @Override // java.lang.Runnable
            public final void run() {
                Bridge.this.lambda$reload$6$Bridge();
            }
        });
    }

    public String getLocalUrl() {
        return this.localUrl;
    }

    public WebViewLocalServer getLocalServer() {
        return this.localServer;
    }

    public HostMask getAppAllowNavigationMask() {
        return this.appAllowNavigationMask;
    }

    public BridgeWebViewClient getWebViewClient() {
        return this.webViewClient;
    }

    public void setWebViewClient(BridgeWebViewClient client) {
        this.webViewClient = client;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<WebViewListener> getWebViewListeners() {
        return this.webViewListeners;
    }

    void setWebViewListeners(List<WebViewListener> webViewListeners) {
        this.webViewListeners = webViewListeners;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RouteProcessor getRouteProcessor() {
        return this.routeProcessor;
    }

    void setRouteProcessor(RouteProcessor routeProcessor) {
        this.routeProcessor = routeProcessor;
    }

    public void addWebViewListener(WebViewListener webViewListener) {
        this.webViewListeners.add(webViewListener);
    }

    public void removeWebViewListener(WebViewListener webViewListener) {
        this.webViewListeners.remove(webViewListener);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private AppCompatActivity activity;
        private Fragment fragment;
        private RouteProcessor routeProcessor;
        private Bundle instanceState = null;
        private CapConfig config = null;
        private List<Class<? extends Plugin>> plugins = new ArrayList();
        private final List<WebViewListener> webViewListeners = new ArrayList();

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
        }

        public Builder(Fragment fragment) {
            this.activity = (AppCompatActivity) fragment.getActivity();
            this.fragment = fragment;
        }

        public Builder setInstanceState(Bundle instanceState) {
            this.instanceState = instanceState;
            return this;
        }

        public Builder setConfig(CapConfig config) {
            this.config = config;
            return this;
        }

        public Builder setPlugins(List<Class<? extends Plugin>> plugins) {
            this.plugins = plugins;
            return this;
        }

        public Builder addPlugin(Class<? extends Plugin> plugin) {
            this.plugins.add(plugin);
            return this;
        }

        public Builder addPlugins(List<Class<? extends Plugin>> plugins) {
            for (Class<? extends Plugin> cls : plugins) {
                addPlugin(cls);
            }
            return this;
        }

        public Builder addWebViewListener(WebViewListener webViewListener) {
            this.webViewListeners.add(webViewListener);
            return this;
        }

        public Builder addWebViewListeners(List<WebViewListener> webViewListeners) {
            for (WebViewListener webViewListener : webViewListeners) {
                addWebViewListener(webViewListener);
            }
            return this;
        }

        public Builder setRouteProcessor(RouteProcessor routeProcessor) {
            this.routeProcessor = routeProcessor;
            return this;
        }

        public Bridge create() {
            ConfigXmlParser configXmlParser = new ConfigXmlParser();
            configXmlParser.parse(this.activity.getApplicationContext());
            CordovaPreferences preferences = configXmlParser.getPreferences();
            preferences.setPreferencesBundle(this.activity.getIntent().getExtras());
            ArrayList<PluginEntry> pluginEntries = configXmlParser.getPluginEntries();
            MockCordovaInterfaceImpl mockCordovaInterfaceImpl = new MockCordovaInterfaceImpl(this.activity);
            Bundle bundle = this.instanceState;
            if (bundle != null) {
                mockCordovaInterfaceImpl.restoreInstanceState(bundle);
            }
            Fragment fragment = this.fragment;
            WebView webView = (WebView) (fragment != null ? fragment.getView().findViewById(R.id.webview) : this.activity.findViewById(R.id.webview));
            MockCordovaWebViewImpl mockCordovaWebViewImpl = new MockCordovaWebViewImpl(this.activity.getApplicationContext());
            mockCordovaWebViewImpl.init(mockCordovaInterfaceImpl, pluginEntries, preferences, webView);
            org.apache.cordova.PluginManager pluginManager = mockCordovaWebViewImpl.getPluginManager();
            mockCordovaInterfaceImpl.onCordovaInit(pluginManager);
            Bridge bridge = new Bridge(this.activity, this.fragment, webView, this.plugins, mockCordovaInterfaceImpl, pluginManager, preferences, this.config);
            bridge.setCordovaWebView(mockCordovaWebViewImpl);
            bridge.setWebViewListeners(this.webViewListeners);
            bridge.setRouteProcessor(this.routeProcessor);
            Bundle bundle2 = this.instanceState;
            if (bundle2 != null) {
                bridge.restoreInstanceState(bundle2);
            }
            return bridge;
        }
    }
}
