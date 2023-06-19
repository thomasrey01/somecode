package com.capacitorjs.plugins.browser;

import android.net.Uri;
import com.capacitorjs.plugins.browser.Browser;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import com.google.android.gms.common.internal.ImagesContract;
@CapacitorPlugin(name = "Browser")
/* loaded from: classes.dex */
public class BrowserPlugin extends Plugin {
    private Browser implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        Browser browser = new Browser(getContext());
        this.implementation = browser;
        browser.setBrowserEventListener(new Browser.BrowserEventListener() { // from class: com.capacitorjs.plugins.browser.-$$Lambda$QbSCM4kFwIHH6tnGFE3cwCW9SFY
            @Override // com.capacitorjs.plugins.browser.Browser.BrowserEventListener
            public final void onBrowserEvent(int i) {
                BrowserPlugin.this.onBrowserEvent(i);
            }
        });
    }

    @PluginMethod
    public void open(PluginCall call) {
        String string = call.getString(ImagesContract.URL);
        if (string == null) {
            call.reject("Must provide a URL to open");
        } else if (string.isEmpty()) {
            call.reject("URL must not be empty");
        } else {
            try {
                Uri parse = Uri.parse(string);
                String string2 = call.getString("toolbarColor");
                Integer num = null;
                if (string2 != null) {
                    try {
                        num = Integer.valueOf(WebColor.parseColor(string2));
                    } catch (IllegalArgumentException unused) {
                        Logger.error(getLogTag(), "Invalid color provided for toolbarColor. Using default", null);
                    }
                }
                this.implementation.open(parse, num);
                call.resolve();
            } catch (Exception e) {
                call.reject(e.getLocalizedMessage());
            }
        }
    }

    @PluginMethod
    public void close(PluginCall call) {
        call.unimplemented();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnResume() {
        if (this.implementation.bindService()) {
            return;
        }
        Logger.error(getLogTag(), "Error binding to custom tabs service", null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnPause() {
        this.implementation.unbindService();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onBrowserEvent(int event) {
        if (event == 1) {
            notifyListeners("browserPageLoaded", null);
        } else if (event != 2) {
        } else {
            notifyListeners("browserFinished", null);
        }
    }
}
