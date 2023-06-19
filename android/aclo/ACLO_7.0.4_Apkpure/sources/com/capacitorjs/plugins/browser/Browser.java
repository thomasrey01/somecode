package com.capacitorjs.plugins.browser;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import com.capacitorjs.plugins.browser.EventGroup;
/* loaded from: classes.dex */
public class Browser {
    public static final int BROWSER_FINISHED = 2;
    public static final int BROWSER_LOADED = 1;
    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private BrowserEventListener browserEventListener;
    private CustomTabsSession browserSession;
    private Context context;
    private CustomTabsClient customTabsClient;
    private boolean isInitialLoad = false;
    private CustomTabsServiceConnection connection = new CustomTabsServiceConnection() { // from class: com.capacitorjs.plugins.browser.Browser.1
        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override // androidx.browser.customtabs.CustomTabsServiceConnection
        public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
            Browser.this.customTabsClient = client;
            client.warmup(0L);
        }
    };
    private EventGroup group = new EventGroup(new EventGroup.EventGroupCompletion() { // from class: com.capacitorjs.plugins.browser.-$$Lambda$Browser$RNO0dQJYWpG4GFb2UKrF_tzxNfQ
        @Override // com.capacitorjs.plugins.browser.EventGroup.EventGroupCompletion
        public final void onGroupCompletion() {
            Browser.this.handleGroupCompletion();
        }
    });

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface BrowserEventListener {
        void onBrowserEvent(int event);
    }

    public Browser(Context context) {
        this.context = context;
    }

    public void setBrowserEventListener(BrowserEventListener listener) {
        this.browserEventListener = listener;
    }

    public BrowserEventListener getBrowserEventListenerListener() {
        return this.browserEventListener;
    }

    public void open(Uri url) {
        open(url, null);
    }

    public void open(Uri url, Integer toolbarColor) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(getCustomTabsSession());
        builder.addDefaultShareMenuItem();
        if (toolbarColor != null) {
            builder.setToolbarColor(toolbarColor.intValue());
        }
        CustomTabsIntent build = builder.build();
        Intent intent = build.intent;
        intent.putExtra("android.intent.extra.REFERRER", Uri.parse("2//" + this.context.getPackageName()));
        this.isInitialLoad = true;
        this.group.reset();
        build.launchUrl(this.context, url);
    }

    public boolean bindService() {
        boolean bindCustomTabsService = CustomTabsClient.bindCustomTabsService(this.context, CUSTOM_TAB_PACKAGE_NAME, this.connection);
        this.group.leave();
        return bindCustomTabsService;
    }

    public void unbindService() {
        this.context.unbindService(this.connection);
        this.group.enter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handledNavigationEvent(int navigationEvent) {
        if (navigationEvent != 2) {
            if (navigationEvent == 5) {
                this.group.enter();
            } else if (navigationEvent != 6) {
            } else {
                this.group.leave();
            }
        } else if (this.isInitialLoad) {
            BrowserEventListener browserEventListener = this.browserEventListener;
            if (browserEventListener != null) {
                browserEventListener.onBrowserEvent(1);
            }
            this.isInitialLoad = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGroupCompletion() {
        BrowserEventListener browserEventListener = this.browserEventListener;
        if (browserEventListener != null) {
            browserEventListener.onBrowserEvent(2);
        }
    }

    private CustomTabsSession getCustomTabsSession() {
        CustomTabsClient customTabsClient = this.customTabsClient;
        if (customTabsClient == null) {
            return null;
        }
        if (this.browserSession == null) {
            this.browserSession = customTabsClient.newSession(new CustomTabsCallback() { // from class: com.capacitorjs.plugins.browser.Browser.2
                @Override // androidx.browser.customtabs.CustomTabsCallback
                public void onNavigationEvent(int navigationEvent, Bundle extras) {
                    Browser.this.handledNavigationEvent(navigationEvent);
                }
            });
        }
        return this.browserSession;
    }
}
