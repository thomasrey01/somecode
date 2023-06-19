package org.apache.cordova.inappbrowser;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.getcapacitor.Bridge;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.Config;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class InAppBrowser extends CordovaPlugin {
    private static final String BEFORELOAD = "beforeload";
    private static final String CLEAR_ALL_CACHE = "clearcache";
    private static final String CLEAR_SESSION_CACHE = "clearsessioncache";
    private static final String EXIT_EVENT = "exit";
    private static final int FILECHOOSER_REQUESTCODE = 1;
    private static final String FOOTER = "footer";
    private static final String FULLSCREEN = "fullscreen";
    private static final String HARDWARE_BACK_BUTTON = "hardwareback";
    private static final String HIDDEN = "hidden";
    private static final String HIDE_NAVIGATION = "hidenavigationbuttons";
    private static final String HIDE_URL = "hideurlbar";
    private static final String LEFT_TO_RIGHT = "lefttoright";
    private static final String LOAD_ERROR_EVENT = "loaderror";
    private static final String LOAD_START_EVENT = "loadstart";
    private static final String LOAD_STOP_EVENT = "loadstop";
    private static final String LOCATION = "location";
    protected static final String LOG_TAG = "InAppBrowser";
    private static final String MEDIA_PLAYBACK_REQUIRES_USER_ACTION = "mediaPlaybackRequiresUserAction";
    private static final String MESSAGE_EVENT = "message";
    private static final String NULL = "null";
    private static final String SELF = "_self";
    private static final String SHOULD_PAUSE = "shouldPauseOnSuspend";
    private static final String SYSTEM = "_system";
    private static final String USER_WIDE_VIEW_PORT = "useWideViewPort";
    private static final String ZOOM = "zoom";
    private String[] allowedSchemes;
    private CallbackContext callbackContext;
    private InAppBrowserClient currentClient;
    private InAppBrowserDialog dialog;
    private EditText edittext;
    private WebView inAppWebView;
    private ValueCallback<Uri[]> mUploadCallback;
    private static final Boolean DEFAULT_HARDWARE_BACK = true;
    private static final String CLOSE_BUTTON_CAPTION = "closebuttoncaption";
    private static final String TOOLBAR_COLOR = "toolbarcolor";
    private static final String NAVIGATION_COLOR = "navigationbuttoncolor";
    private static final String CLOSE_BUTTON_COLOR = "closebuttoncolor";
    private static final String FOOTER_COLOR = "footercolor";
    private static final List customizableOptions = Arrays.asList(CLOSE_BUTTON_CAPTION, TOOLBAR_COLOR, NAVIGATION_COLOR, CLOSE_BUTTON_COLOR, FOOTER_COLOR);
    private boolean showLocationBar = true;
    private boolean showZoomControls = true;
    private boolean openWindowHidden = false;
    private boolean clearAllCache = false;
    private boolean clearSessionCache = false;
    private boolean hadwareBackButton = true;
    private boolean mediaPlaybackRequiresUserGesture = false;
    private boolean shouldPauseInAppBrowser = false;
    private boolean useWideViewPort = true;
    private String closeButtonCaption = "";
    private String closeButtonColor = "";
    private boolean leftToRight = false;
    private int toolbarColor = -3355444;
    private boolean hideNavigationButtons = false;
    private String navigationButtonColor = "";
    private boolean hideUrlBar = false;
    private boolean showFooter = false;
    private String footerColor = "";
    private String beforeload = "";
    private boolean fullscreen = true;

    /* JADX INFO: Access modifiers changed from: private */
    public InAppBrowser getInAppBrowser() {
        return this;
    }

    @Override // org.apache.cordova.CordovaPlugin
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.callbackContext = callbackContext;
            final String string = args.getString(0);
            String optString = args.optString(1);
            if (optString == null || optString.equals("") || optString.equals(NULL)) {
                optString = SELF;
            }
            final String str = optString;
            final HashMap<String, String> parseFeature = parseFeature(args.optString(2));
            LOG.d(LOG_TAG, "target = " + str);
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.1
                @Override // java.lang.Runnable
                public void run() {
                    String showWebPage;
                    if (InAppBrowser.SELF.equals(str)) {
                        LOG.d(InAppBrowser.LOG_TAG, "in self");
                        Boolean bool = string.startsWith("javascript:") ? true : null;
                        if (bool == null) {
                            try {
                                bool = (Boolean) Config.class.getMethod("isUrlWhiteListed", String.class).invoke(null, string);
                            } catch (IllegalAccessException e) {
                                LOG.d(InAppBrowser.LOG_TAG, e.getLocalizedMessage());
                            } catch (NoSuchMethodException e2) {
                                LOG.d(InAppBrowser.LOG_TAG, e2.getLocalizedMessage());
                            } catch (InvocationTargetException e3) {
                                LOG.d(InAppBrowser.LOG_TAG, e3.getLocalizedMessage());
                            }
                        }
                        if (bool == null) {
                            try {
                                PluginManager pluginManager = (PluginManager) InAppBrowser.this.webView.getClass().getMethod("getPluginManager", new Class[0]).invoke(InAppBrowser.this.webView, new Object[0]);
                                bool = (Boolean) pluginManager.getClass().getMethod("shouldAllowNavigation", String.class).invoke(pluginManager, string);
                            } catch (IllegalAccessException e4) {
                                LOG.d(InAppBrowser.LOG_TAG, e4.getLocalizedMessage());
                            } catch (NoSuchMethodException e5) {
                                LOG.d(InAppBrowser.LOG_TAG, e5.getLocalizedMessage());
                            } catch (InvocationTargetException e6) {
                                LOG.d(InAppBrowser.LOG_TAG, e6.getLocalizedMessage());
                            }
                        }
                        if (Boolean.TRUE.equals(bool)) {
                            LOG.d(InAppBrowser.LOG_TAG, "loading in webview");
                            InAppBrowser.this.webView.loadUrl(string);
                        } else if (string.startsWith("tel:")) {
                            try {
                                LOG.d(InAppBrowser.LOG_TAG, "loading in dialer");
                                Intent intent = new Intent("android.intent.action.DIAL");
                                intent.setData(Uri.parse(string));
                                InAppBrowser.this.cordova.getActivity().startActivity(intent);
                            } catch (ActivityNotFoundException e7) {
                                LOG.e(InAppBrowser.LOG_TAG, "Error dialing " + string + ": " + e7.toString());
                            }
                        } else {
                            LOG.d(InAppBrowser.LOG_TAG, "loading in InAppBrowser");
                            showWebPage = InAppBrowser.this.showWebPage(string, parseFeature);
                        }
                        showWebPage = "";
                    } else if (InAppBrowser.SYSTEM.equals(str)) {
                        LOG.d(InAppBrowser.LOG_TAG, "in system");
                        showWebPage = InAppBrowser.this.openExternal(string);
                    } else {
                        LOG.d(InAppBrowser.LOG_TAG, "in blank");
                        showWebPage = InAppBrowser.this.showWebPage(string, parseFeature);
                    }
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, showWebPage);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);
                }
            });
        } else if (action.equals("close")) {
            closeDialog();
        } else if (action.equals("loadAfterBeforeload")) {
            if (this.beforeload == null) {
                LOG.e(LOG_TAG, "unexpected loadAfterBeforeload called without feature beforeload=yes");
            }
            final String string2 = args.getString(0);
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.2
                @Override // java.lang.Runnable
                public void run() {
                    if (Build.VERSION.SDK_INT < 26) {
                        InAppBrowser.this.currentClient.waitForBeforeload = false;
                        InAppBrowser.this.inAppWebView.setWebViewClient(InAppBrowser.this.currentClient);
                    } else {
                        ((InAppBrowserClient) InAppBrowser.this.inAppWebView.getWebViewClient()).waitForBeforeload = false;
                    }
                    InAppBrowser.this.inAppWebView.loadUrl(string2);
                }
            });
        } else if (action.equals("injectScriptCode")) {
            injectDeferredObject(args.getString(0), args.getBoolean(1) ? String.format("(function(){prompt(JSON.stringify([eval(%%s)]), 'gap-iab://%s')})()", callbackContext.getCallbackId()) : null);
        } else if (action.equals("injectScriptFile")) {
            injectDeferredObject(args.getString(0), args.getBoolean(1) ? String.format("(function(d) { var c = d.createElement('script'); c.src = %%s; c.onload = function() { prompt('', 'gap-iab://%s'); }; d.body.appendChild(c); })(document)", callbackContext.getCallbackId()) : "(function(d) { var c = d.createElement('script'); c.src = %s; d.body.appendChild(c); })(document)");
        } else if (action.equals("injectStyleCode")) {
            injectDeferredObject(args.getString(0), args.getBoolean(1) ? String.format("(function(d) { var c = d.createElement('style'); c.innerHTML = %%s; d.body.appendChild(c); prompt('', 'gap-iab://%s');})(document)", callbackContext.getCallbackId()) : "(function(d) { var c = d.createElement('style'); c.innerHTML = %s; d.body.appendChild(c); })(document)");
        } else if (action.equals("injectStyleFile")) {
            injectDeferredObject(args.getString(0), args.getBoolean(1) ? String.format("(function(d) { var c = d.createElement('link'); c.rel='stylesheet'; c.type='text/css'; c.href = %%s; d.head.appendChild(c); prompt('', 'gap-iab://%s');})(document)", callbackContext.getCallbackId()) : "(function(d) { var c = d.createElement('link'); c.rel='stylesheet'; c.type='text/css'; c.href = %s; d.head.appendChild(c); })(document)");
        } else if (action.equals("show")) {
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.3
                @Override // java.lang.Runnable
                public void run() {
                    if (InAppBrowser.this.dialog == null || InAppBrowser.this.cordova.getActivity().isFinishing()) {
                        return;
                    }
                    InAppBrowser.this.dialog.show();
                }
            });
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
            pluginResult.setKeepCallback(true);
            this.callbackContext.sendPluginResult(pluginResult);
        } else if (!action.equals("hide")) {
            return false;
        } else {
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.4
                @Override // java.lang.Runnable
                public void run() {
                    if (InAppBrowser.this.dialog == null || InAppBrowser.this.cordova.getActivity().isFinishing()) {
                        return;
                    }
                    InAppBrowser.this.dialog.hide();
                }
            });
            PluginResult pluginResult2 = new PluginResult(PluginResult.Status.OK);
            pluginResult2.setKeepCallback(true);
            this.callbackContext.sendPluginResult(pluginResult2);
        }
        return true;
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onReset() {
        closeDialog();
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onPause(boolean multitasking) {
        if (this.shouldPauseInAppBrowser) {
            this.inAppWebView.onPause();
        }
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onResume(boolean multitasking) {
        if (this.shouldPauseInAppBrowser) {
            this.inAppWebView.onResume();
        }
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onDestroy() {
        closeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectDeferredObject(final String source, String jsWrapper) {
        if (this.inAppWebView != null) {
            if (jsWrapper != null) {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(source);
                String jSONArray2 = jSONArray.toString();
                source = String.format(jsWrapper, jSONArray2.substring(1, jSONArray2.length() - 1));
            }
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.5
                @Override // java.lang.Runnable
                public void run() {
                    InAppBrowser.this.inAppWebView.evaluateJavascript(source, null);
                }
            });
            return;
        }
        LOG.d(LOG_TAG, "Can't inject code into the system browser");
    }

    private HashMap<String, String> parseFeature(String optString) {
        if (optString.equals(NULL)) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        StringTokenizer stringTokenizer = new StringTokenizer(optString, ",");
        while (stringTokenizer.hasMoreElements()) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "=");
            if (stringTokenizer2.hasMoreElements()) {
                String nextToken = stringTokenizer2.nextToken();
                String nextToken2 = stringTokenizer2.nextToken();
                if (!customizableOptions.contains(nextToken) && !nextToken2.equals("yes") && !nextToken2.equals("no")) {
                    nextToken2 = "yes";
                }
                hashMap.put(nextToken, nextToken2);
            }
        }
        return hashMap;
    }

    public String openExternal(String url) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            Uri parse = Uri.parse(url);
            if ("file".equals(parse.getScheme())) {
                intent.setDataAndType(parse, this.webView.getResourceApi().getMimeType(parse));
            } else {
                intent.setData(parse);
            }
            intent.putExtra("com.android.browser.application_id", this.cordova.getActivity().getPackageName());
            openExternalExcludeCurrentApp(intent);
            return "";
        } catch (RuntimeException e) {
            LOG.d(LOG_TAG, "InAppBrowser: Error loading url " + url + ":" + e.toString());
            return e.toString();
        }
    }

    private void openExternalExcludeCurrentApp(Intent intent) {
        String packageName = this.cordova.getActivity().getPackageName();
        List<ResolveInfo> queryIntentActivities = this.cordova.getActivity().getPackageManager().queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (packageName.equals(resolveInfo.activityInfo.packageName)) {
                z = true;
            } else {
                Intent intent2 = (Intent) intent.clone();
                intent2.setPackage(resolveInfo.activityInfo.packageName);
                arrayList.add(intent2);
            }
        }
        if (!z || arrayList.size() == 0) {
            this.cordova.getActivity().startActivity(intent);
        } else if (arrayList.size() == 1) {
            this.cordova.getActivity().startActivity((Intent) arrayList.get(0));
        } else if (arrayList.size() > 0) {
            Intent createChooser = Intent.createChooser((Intent) arrayList.remove(arrayList.size() - 1), null);
            createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
            this.cordova.getActivity().startActivity(createChooser);
        }
    }

    public void closeDialog() {
        this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.6
            @Override // java.lang.Runnable
            public void run() {
                WebView webView = InAppBrowser.this.inAppWebView;
                if (webView == null) {
                    return;
                }
                webView.setWebViewClient(new WebViewClient() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.6.1
                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView view, String url) {
                        if (InAppBrowser.this.dialog == null || InAppBrowser.this.cordova.getActivity().isFinishing()) {
                            return;
                        }
                        InAppBrowser.this.dialog.dismiss();
                        InAppBrowser.this.dialog = null;
                    }
                });
                webView.loadUrl("about:blank");
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", InAppBrowser.EXIT_EVENT);
                    InAppBrowser.this.sendUpdate(jSONObject, false);
                } catch (JSONException unused) {
                    LOG.d(InAppBrowser.LOG_TAG, "Should never happen");
                }
            }
        });
    }

    public void goBack() {
        if (this.inAppWebView.canGoBack()) {
            this.inAppWebView.goBack();
        }
    }

    public boolean canGoBack() {
        return this.inAppWebView.canGoBack();
    }

    public boolean hardwareBack() {
        return this.hadwareBackButton;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goForward() {
        if (this.inAppWebView.canGoForward()) {
            this.inAppWebView.goForward();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void navigate(String url) {
        ((InputMethodManager) this.cordova.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.edittext.getWindowToken(), 0);
        if (!url.startsWith(Bridge.CAPACITOR_HTTP_SCHEME) && !url.startsWith("file:")) {
            WebView webView = this.inAppWebView;
            webView.loadUrl("http://" + url);
        } else {
            this.inAppWebView.loadUrl(url);
        }
        this.inAppWebView.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getShowLocationBar() {
        return this.showLocationBar;
    }

    public String showWebPage(final String url, HashMap<String, String> features) {
        boolean z = true;
        this.showLocationBar = true;
        this.showZoomControls = true;
        this.openWindowHidden = false;
        this.mediaPlaybackRequiresUserGesture = false;
        if (features != null) {
            String str = features.get("location");
            if (str != null) {
                this.showLocationBar = str.equals("yes");
            }
            if (this.showLocationBar) {
                String str2 = features.get(HIDE_NAVIGATION);
                String str3 = features.get(HIDE_URL);
                if (str2 != null) {
                    this.hideNavigationButtons = str2.equals("yes");
                }
                if (str3 != null) {
                    this.hideUrlBar = str3.equals("yes");
                }
            }
            String str4 = features.get(ZOOM);
            if (str4 != null) {
                this.showZoomControls = str4.equals("yes");
            }
            String str5 = features.get(HIDDEN);
            if (str5 != null) {
                this.openWindowHidden = str5.equals("yes");
            }
            String str6 = features.get(HARDWARE_BACK_BUTTON);
            if (str6 != null) {
                this.hadwareBackButton = str6.equals("yes");
            } else {
                this.hadwareBackButton = DEFAULT_HARDWARE_BACK.booleanValue();
            }
            String str7 = features.get(MEDIA_PLAYBACK_REQUIRES_USER_ACTION);
            if (str7 != null) {
                this.mediaPlaybackRequiresUserGesture = str7.equals("yes");
            }
            String str8 = features.get(CLEAR_ALL_CACHE);
            if (str8 != null) {
                this.clearAllCache = str8.equals("yes");
            } else {
                String str9 = features.get(CLEAR_SESSION_CACHE);
                if (str9 != null) {
                    this.clearSessionCache = str9.equals("yes");
                }
            }
            String str10 = features.get(SHOULD_PAUSE);
            if (str10 != null) {
                this.shouldPauseInAppBrowser = str10.equals("yes");
            }
            String str11 = features.get(USER_WIDE_VIEW_PORT);
            if (str11 != null) {
                this.useWideViewPort = str11.equals("yes");
            }
            String str12 = features.get(CLOSE_BUTTON_CAPTION);
            if (str12 != null) {
                this.closeButtonCaption = str12;
            }
            String str13 = features.get(CLOSE_BUTTON_COLOR);
            if (str13 != null) {
                this.closeButtonColor = str13;
            }
            String str14 = features.get(LEFT_TO_RIGHT);
            if (str14 == null || !str14.equals("yes")) {
                z = false;
            }
            this.leftToRight = z;
            String str15 = features.get(TOOLBAR_COLOR);
            if (str15 != null) {
                this.toolbarColor = Color.parseColor(str15);
            }
            String str16 = features.get(NAVIGATION_COLOR);
            if (str16 != null) {
                this.navigationButtonColor = str16;
            }
            String str17 = features.get(FOOTER);
            if (str17 != null) {
                this.showFooter = str17.equals("yes");
            }
            String str18 = features.get(FOOTER_COLOR);
            if (str18 != null) {
                this.footerColor = str18;
            }
            if (features.get(BEFORELOAD) != null) {
                this.beforeload = features.get(BEFORELOAD);
            }
            String str19 = features.get(FULLSCREEN);
            if (str19 != null) {
                this.fullscreen = str19.equals("yes");
            }
        }
        final CordovaWebView cordovaWebView = this.webView;
        this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7
            private int dpToPixels(int dipValue) {
                return (int) TypedValue.applyDimension(1, dipValue, InAppBrowser.this.cordova.getActivity().getResources().getDisplayMetrics());
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v7, types: [android.view.View] */
            /* JADX WARN: Type inference failed for: r0v8, types: [android.widget.TextView] */
            private View createCloseButton(int id) {
                ImageButton imageButton;
                Resources resources = InAppBrowser.this.cordova.getActivity().getResources();
                if (InAppBrowser.this.closeButtonCaption != "") {
                    ?? textView = new TextView(InAppBrowser.this.cordova.getActivity());
                    textView.setText(InAppBrowser.this.closeButtonCaption);
                    textView.setTextSize(20.0f);
                    if (InAppBrowser.this.closeButtonColor != "") {
                        textView.setTextColor(Color.parseColor(InAppBrowser.this.closeButtonColor));
                    }
                    textView.setGravity(16);
                    textView.setPadding(dpToPixels(10), 0, dpToPixels(10), 0);
                    imageButton = textView;
                } else {
                    ImageButton imageButton2 = new ImageButton(InAppBrowser.this.cordova.getActivity());
                    Drawable drawable = resources.getDrawable(resources.getIdentifier("ic_action_remove", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                    if (InAppBrowser.this.closeButtonColor != "") {
                        imageButton2.setColorFilter(Color.parseColor(InAppBrowser.this.closeButtonColor));
                    }
                    imageButton2.setImageDrawable(drawable);
                    imageButton2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageButton2.getAdjustViewBounds();
                    imageButton = imageButton2;
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -1);
                if (InAppBrowser.this.leftToRight) {
                    layoutParams.addRule(9);
                } else {
                    layoutParams.addRule(11);
                }
                imageButton.setLayoutParams(layoutParams);
                imageButton.setBackground(null);
                imageButton.setContentDescription("Close Button");
                imageButton.setId(Integer.valueOf(id).intValue());
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        InAppBrowser.this.closeDialog();
                    }
                });
                return imageButton;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (InAppBrowser.this.dialog != null) {
                    InAppBrowser.this.dialog.dismiss();
                }
                InAppBrowser.this.dialog = new InAppBrowserDialog(InAppBrowser.this.cordova.getActivity(), 16973830);
                InAppBrowser.this.dialog.getWindow().getAttributes().windowAnimations = 16973826;
                InAppBrowser.this.dialog.requestWindowFeature(1);
                if (InAppBrowser.this.fullscreen) {
                    InAppBrowser.this.dialog.getWindow().setFlags(1024, 1024);
                }
                InAppBrowser.this.dialog.setCancelable(true);
                InAppBrowser.this.dialog.setInAppBroswer(InAppBrowser.this.getInAppBrowser());
                LinearLayout linearLayout = new LinearLayout(InAppBrowser.this.cordova.getActivity());
                linearLayout.setOrientation(1);
                RelativeLayout relativeLayout = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                relativeLayout.setBackgroundColor(InAppBrowser.this.toolbarColor);
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, dpToPixels(44)));
                relativeLayout.setPadding(dpToPixels(2), dpToPixels(2), dpToPixels(2), dpToPixels(2));
                if (InAppBrowser.this.leftToRight) {
                    relativeLayout.setHorizontalGravity(3);
                } else {
                    relativeLayout.setHorizontalGravity(5);
                }
                relativeLayout.setVerticalGravity(48);
                RelativeLayout relativeLayout2 = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                if (InAppBrowser.this.leftToRight) {
                    layoutParams.addRule(11);
                } else {
                    layoutParams.addRule(9);
                }
                relativeLayout2.setLayoutParams(layoutParams);
                relativeLayout2.setHorizontalGravity(3);
                relativeLayout2.setVerticalGravity(16);
                relativeLayout2.setId((InAppBrowser.this.leftToRight ? 5 : 1).intValue());
                ImageButton imageButton = new ImageButton(InAppBrowser.this.cordova.getActivity());
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -1);
                layoutParams2.addRule(5);
                imageButton.setLayoutParams(layoutParams2);
                imageButton.setContentDescription("Back Button");
                Integer num = 2;
                imageButton.setId(num.intValue());
                Resources resources = InAppBrowser.this.cordova.getActivity().getResources();
                Drawable drawable = resources.getDrawable(resources.getIdentifier("ic_action_previous_item", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                if (InAppBrowser.this.navigationButtonColor != "") {
                    imageButton.setColorFilter(Color.parseColor(InAppBrowser.this.navigationButtonColor));
                }
                imageButton.setBackground(null);
                imageButton.setImageDrawable(drawable);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButton.setPadding(0, dpToPixels(10), 0, dpToPixels(10));
                imageButton.getAdjustViewBounds();
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        InAppBrowser.this.goBack();
                    }
                });
                ImageButton imageButton2 = new ImageButton(InAppBrowser.this.cordova.getActivity());
                RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -1);
                layoutParams3.addRule(1, 2);
                imageButton2.setLayoutParams(layoutParams3);
                imageButton2.setContentDescription("Forward Button");
                Integer num2 = 3;
                imageButton2.setId(num2.intValue());
                Drawable drawable2 = resources.getDrawable(resources.getIdentifier("ic_action_next_item", "drawable", InAppBrowser.this.cordova.getActivity().getPackageName()));
                if (InAppBrowser.this.navigationButtonColor != "") {
                    imageButton2.setColorFilter(Color.parseColor(InAppBrowser.this.navigationButtonColor));
                }
                imageButton2.setBackground(null);
                imageButton2.setImageDrawable(drawable2);
                imageButton2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButton2.setPadding(0, dpToPixels(10), 0, dpToPixels(10));
                imageButton2.getAdjustViewBounds();
                imageButton2.setOnClickListener(new View.OnClickListener() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        InAppBrowser.this.goForward();
                    }
                });
                InAppBrowser.this.edittext = new EditText(InAppBrowser.this.cordova.getActivity());
                RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams4.addRule(1, 1);
                layoutParams4.addRule(0, 5);
                InAppBrowser.this.edittext.setLayoutParams(layoutParams4);
                Integer num3 = 4;
                InAppBrowser.this.edittext.setId(num3.intValue());
                InAppBrowser.this.edittext.setSingleLine(true);
                InAppBrowser.this.edittext.setText(url);
                InAppBrowser.this.edittext.setInputType(16);
                InAppBrowser.this.edittext.setImeOptions(2);
                InAppBrowser.this.edittext.setInputType(0);
                InAppBrowser.this.edittext.setOnKeyListener(new View.OnKeyListener() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.4
                    @Override // android.view.View.OnKeyListener
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == 0 && keyCode == 66) {
                            InAppBrowser.this.navigate(InAppBrowser.this.edittext.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
                relativeLayout.addView(createCloseButton(InAppBrowser.this.leftToRight ? 1 : 5));
                RelativeLayout relativeLayout3 = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                relativeLayout3.setBackgroundColor(InAppBrowser.this.footerColor != "" ? Color.parseColor(InAppBrowser.this.footerColor) : -3355444);
                RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-1, dpToPixels(44));
                layoutParams5.addRule(12, -1);
                relativeLayout3.setLayoutParams(layoutParams5);
                if (InAppBrowser.this.closeButtonCaption != "") {
                    relativeLayout3.setPadding(dpToPixels(8), dpToPixels(8), dpToPixels(8), dpToPixels(8));
                }
                relativeLayout3.setHorizontalGravity(3);
                relativeLayout3.setVerticalGravity(80);
                relativeLayout3.addView(createCloseButton(7));
                InAppBrowser.this.inAppWebView = new WebView(InAppBrowser.this.cordova.getActivity());
                InAppBrowser.this.inAppWebView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                Integer num4 = 6;
                InAppBrowser.this.inAppWebView.setId(num4.intValue());
                InAppBrowser.this.inAppWebView.setWebChromeClient(new InAppChromeClient(cordovaWebView) { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.5
                    @Override // android.webkit.WebChromeClient
                    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        LOG.d(InAppBrowser.LOG_TAG, "File Chooser 5.0+");
                        if (InAppBrowser.this.mUploadCallback != null) {
                            InAppBrowser.this.mUploadCallback.onReceiveValue(null);
                        }
                        InAppBrowser.this.mUploadCallback = filePathCallback;
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.addCategory("android.intent.category.OPENABLE");
                        intent.setType("*/*");
                        InAppBrowser.this.cordova.startActivityForResult(InAppBrowser.this, Intent.createChooser(intent, "Select File"), 1);
                        return true;
                    }
                });
                InAppBrowser inAppBrowser = InAppBrowser.this;
                InAppBrowser inAppBrowser2 = InAppBrowser.this;
                inAppBrowser.currentClient = new InAppBrowserClient(cordovaWebView, inAppBrowser2.edittext, InAppBrowser.this.beforeload);
                InAppBrowser.this.inAppWebView.setWebViewClient(InAppBrowser.this.currentClient);
                WebSettings settings = InAppBrowser.this.inAppWebView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setBuiltInZoomControls(InAppBrowser.this.showZoomControls);
                settings.setPluginState(WebSettings.PluginState.ON);
                settings.setMediaPlaybackRequiresUserGesture(InAppBrowser.this.mediaPlaybackRequiresUserGesture);
                InAppBrowser.this.inAppWebView.addJavascriptInterface(new Object() { // from class: org.apache.cordova.inappbrowser.InAppBrowser.7.1JsObject
                    @JavascriptInterface
                    public void postMessage(String data) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("type", InAppBrowser.MESSAGE_EVENT);
                            jSONObject.put("data", new JSONObject(data));
                            InAppBrowser.this.sendUpdate(jSONObject, true);
                        } catch (JSONException unused) {
                            LOG.e(InAppBrowser.LOG_TAG, "data object passed to postMessage has caused a JSON error.");
                        }
                    }
                }, "cordova_iab");
                String string = InAppBrowser.this.preferences.getString("OverrideUserAgent", null);
                String string2 = InAppBrowser.this.preferences.getString("AppendUserAgent", null);
                if (string != null) {
                    settings.setUserAgentString(string);
                }
                if (string2 != null) {
                    settings.setUserAgentString(settings.getUserAgentString() + string2);
                }
                Bundle extras = InAppBrowser.this.cordova.getActivity().getIntent().getExtras();
                if (extras == null ? true : extras.getBoolean("InAppBrowserStorageEnabled", true)) {
                    settings.setDatabasePath(InAppBrowser.this.cordova.getActivity().getApplicationContext().getDir("inAppBrowserDB", 0).getPath());
                    settings.setDatabaseEnabled(true);
                }
                settings.setDomStorageEnabled(true);
                if (!InAppBrowser.this.clearAllCache) {
                    if (InAppBrowser.this.clearSessionCache) {
                        CookieManager.getInstance().removeSessionCookie();
                    }
                } else {
                    CookieManager.getInstance().removeAllCookie();
                }
                CookieManager.getInstance().setAcceptThirdPartyCookies(InAppBrowser.this.inAppWebView, true);
                InAppBrowser.this.inAppWebView.loadUrl(url);
                Integer num5 = 6;
                InAppBrowser.this.inAppWebView.setId(num5.intValue());
                InAppBrowser.this.inAppWebView.getSettings().setLoadWithOverviewMode(true);
                InAppBrowser.this.inAppWebView.getSettings().setUseWideViewPort(InAppBrowser.this.useWideViewPort);
                InAppBrowser.this.inAppWebView.getSettings().setSupportMultipleWindows(true);
                InAppBrowser.this.inAppWebView.requestFocus();
                InAppBrowser.this.inAppWebView.requestFocusFromTouch();
                relativeLayout2.addView(imageButton);
                relativeLayout2.addView(imageButton2);
                if (!InAppBrowser.this.hideNavigationButtons) {
                    relativeLayout.addView(relativeLayout2);
                }
                if (!InAppBrowser.this.hideUrlBar) {
                    relativeLayout.addView(InAppBrowser.this.edittext);
                }
                if (InAppBrowser.this.getShowLocationBar()) {
                    linearLayout.addView(relativeLayout);
                }
                RelativeLayout relativeLayout4 = new RelativeLayout(InAppBrowser.this.cordova.getActivity());
                relativeLayout4.addView(InAppBrowser.this.inAppWebView);
                linearLayout.addView(relativeLayout4);
                if (InAppBrowser.this.showFooter) {
                    relativeLayout4.addView(relativeLayout3);
                }
                WindowManager.LayoutParams layoutParams6 = new WindowManager.LayoutParams();
                layoutParams6.copyFrom(InAppBrowser.this.dialog.getWindow().getAttributes());
                layoutParams6.width = -1;
                layoutParams6.height = -1;
                if (InAppBrowser.this.dialog != null) {
                    InAppBrowser.this.dialog.setContentView(linearLayout);
                    InAppBrowser.this.dialog.show();
                    InAppBrowser.this.dialog.getWindow().setAttributes(layoutParams6);
                }
                if (!InAppBrowser.this.openWindowHidden || InAppBrowser.this.dialog == null) {
                    return;
                }
                InAppBrowser.this.dialog.hide();
            }
        });
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdate(JSONObject obj, boolean keepCallback) {
        sendUpdate(obj, keepCallback, PluginResult.Status.OK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdate(JSONObject obj, boolean keepCallback, PluginResult.Status status) {
        if (this.callbackContext != null) {
            PluginResult pluginResult = new PluginResult(status, obj);
            pluginResult.setKeepCallback(keepCallback);
            this.callbackContext.sendPluginResult(pluginResult);
            if (keepCallback) {
                return;
            }
            this.callbackContext = null;
        }
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ValueCallback<Uri[]> valueCallback;
        LOG.d(LOG_TAG, "onActivityResult");
        if (requestCode != 1 || (valueCallback = this.mUploadCallback) == null) {
            super.onActivityResult(requestCode, resultCode, intent);
            return;
        }
        valueCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
        this.mUploadCallback = null;
    }

    /* loaded from: classes.dex */
    public class InAppBrowserClient extends WebViewClient {
        String beforeload;
        EditText edittext;
        boolean waitForBeforeload;
        CordovaWebView webView;

        public WebResourceResponse shouldInterceptRequest(String url, WebResourceResponse response, String method) {
            return response;
        }

        public InAppBrowserClient(CordovaWebView webView, EditText mEditText, String beforeload) {
            this.webView = webView;
            this.edittext = mEditText;
            this.beforeload = beforeload;
            this.waitForBeforeload = beforeload != null;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return shouldOverrideUrlLoading(url, (String) null);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
            return shouldOverrideUrlLoading(request.getUrl().toString(), request.getMethod());
        }

        /* JADX WARN: Removed duplicated region for block: B:93:0x0247  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean shouldOverrideUrlLoading(java.lang.String r12, java.lang.String r13) {
            /*
                Method dump skipped, instructions count: 586
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.cordova.inappbrowser.InAppBrowser.InAppBrowserClient.shouldOverrideUrlLoading(java.lang.String, java.lang.String):boolean");
        }

        private boolean sendBeforeLoad(String url, String method) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", InAppBrowser.BEFORELOAD);
                jSONObject.put(ImagesContract.URL, url);
                if (method != null) {
                    jSONObject.put(FirebaseAnalytics.Param.METHOD, method);
                }
                InAppBrowser.this.sendUpdate(jSONObject, true);
                return true;
            } catch (JSONException unused) {
                LOG.e(InAppBrowser.LOG_TAG, "URI passed in has caused a JSON error.");
                return false;
            }
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(request.getUrl().toString(), super.shouldInterceptRequest(view, request), request.getMethod());
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!url.startsWith("http:") && !url.startsWith("https:") && !url.startsWith("file:")) {
                LOG.e(InAppBrowser.LOG_TAG, "Possible Uncaught/Unknown URI");
                url = "http://" + url;
            }
            if (!url.equals(this.edittext.getText().toString())) {
                this.edittext.setText(url);
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", InAppBrowser.LOAD_START_EVENT);
                jSONObject.put(ImagesContract.URL, url);
                InAppBrowser.this.sendUpdate(jSONObject, true);
            } catch (JSONException unused) {
                LOG.e(InAppBrowser.LOG_TAG, "URI passed in has caused a JSON error.");
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            InAppBrowser.this.injectDeferredObject("window.webkit={messageHandlers:{cordova_iab:cordova_iab}}", null);
            CookieManager.getInstance().flush();
            view.clearFocus();
            view.requestFocus();
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", InAppBrowser.LOAD_STOP_EVENT);
                jSONObject.put(ImagesContract.URL, url);
                InAppBrowser.this.sendUpdate(jSONObject, true);
            } catch (JSONException unused) {
                LOG.d(InAppBrowser.LOG_TAG, "Should never happen");
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", InAppBrowser.LOAD_ERROR_EVENT);
                jSONObject.put(ImagesContract.URL, failingUrl);
                jSONObject.put("code", errorCode);
                jSONObject.put(InAppBrowser.MESSAGE_EVENT, description);
                InAppBrowser.this.sendUpdate(jSONObject, true, PluginResult.Status.ERROR);
            } catch (JSONException unused) {
                LOG.d(InAppBrowser.LOG_TAG, "Should never happen");
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", InAppBrowser.LOAD_ERROR_EVENT);
                jSONObject.put(ImagesContract.URL, error.getUrl());
                jSONObject.put("code", 0);
                jSONObject.put("sslerror", error.getPrimaryError());
                int primaryError = error.getPrimaryError();
                jSONObject.put(InAppBrowser.MESSAGE_EVENT, primaryError != 0 ? primaryError != 1 ? primaryError != 2 ? primaryError != 3 ? primaryError != 4 ? "A generic error occurred" : "The date of the certificate is invalid" : "The certificate authority is not trusted" : "Hostname mismatch" : "The certificate has expired" : "The certificate is not yet valid");
                InAppBrowser.this.sendUpdate(jSONObject, true, PluginResult.Status.ERROR);
            } catch (JSONException unused) {
                LOG.d(InAppBrowser.LOG_TAG, "Should never happen");
            }
            handler.cancel();
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.webkit.WebViewClient
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onReceivedHttpAuthRequest(android.webkit.WebView r6, android.webkit.HttpAuthHandler r7, java.lang.String r8, java.lang.String r9) {
            /*
                r5 = this;
                java.lang.String r0 = "InAppBrowser"
                org.apache.cordova.CordovaWebView r1 = r5.webView     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                java.lang.Class r1 = r1.getClass()     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                java.lang.String r2 = "getPluginManager"
                r3 = 0
                java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                java.lang.reflect.Method r1 = r1.getMethod(r2, r4)     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                org.apache.cordova.CordovaWebView r2 = r5.webView     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                java.lang.Object r1 = r1.invoke(r2, r3)     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                org.apache.cordova.PluginManager r1 = (org.apache.cordova.PluginManager) r1     // Catch: java.lang.reflect.InvocationTargetException -> L1c java.lang.IllegalAccessException -> L25 java.lang.NoSuchMethodException -> L2e
                goto L37
            L1c:
                r1 = move-exception
                java.lang.String r1 = r1.getLocalizedMessage()
                org.apache.cordova.LOG.d(r0, r1)
                goto L36
            L25:
                r1 = move-exception
                java.lang.String r1 = r1.getLocalizedMessage()
                org.apache.cordova.LOG.d(r0, r1)
                goto L36
            L2e:
                r1 = move-exception
                java.lang.String r1 = r1.getLocalizedMessage()
                org.apache.cordova.LOG.d(r0, r1)
            L36:
                r1 = 0
            L37:
                if (r1 != 0) goto L60
                org.apache.cordova.CordovaWebView r2 = r5.webView     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                java.lang.Class r2 = r2.getClass()     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                java.lang.String r3 = "pluginManager"
                java.lang.reflect.Field r2 = r2.getField(r3)     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                org.apache.cordova.CordovaWebView r3 = r5.webView     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                org.apache.cordova.PluginManager r2 = (org.apache.cordova.PluginManager) r2     // Catch: java.lang.IllegalAccessException -> L4f java.lang.NoSuchFieldException -> L58
                r1 = r2
                goto L60
            L4f:
                r2 = move-exception
                java.lang.String r2 = r2.getLocalizedMessage()
                org.apache.cordova.LOG.d(r0, r2)
                goto L60
            L58:
                r2 = move-exception
                java.lang.String r2 = r2.getLocalizedMessage()
                org.apache.cordova.LOG.d(r0, r2)
            L60:
                if (r1 == 0) goto L70
                org.apache.cordova.CordovaWebView r0 = r5.webView
                org.apache.cordova.CordovaHttpAuthHandler r2 = new org.apache.cordova.CordovaHttpAuthHandler
                r2.<init>(r7)
                boolean r0 = r1.onReceivedHttpAuthRequest(r0, r2, r8, r9)
                if (r0 == 0) goto L70
                return
            L70:
                super.onReceivedHttpAuthRequest(r6, r7, r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.cordova.inappbrowser.InAppBrowser.InAppBrowserClient.onReceivedHttpAuthRequest(android.webkit.WebView, android.webkit.HttpAuthHandler, java.lang.String, java.lang.String):void");
        }
    }
}
