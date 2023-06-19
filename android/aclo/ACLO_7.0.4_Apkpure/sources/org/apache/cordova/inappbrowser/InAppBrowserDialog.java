package org.apache.cordova.inappbrowser;

import android.app.Dialog;
import android.content.Context;
/* loaded from: classes.dex */
public class InAppBrowserDialog extends Dialog {
    Context context;
    InAppBrowser inAppBrowser;

    public InAppBrowserDialog(Context context, int theme) {
        super(context, theme);
        this.inAppBrowser = null;
        this.context = context;
    }

    public void setInAppBroswer(InAppBrowser browser) {
        this.inAppBrowser = browser;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        InAppBrowser inAppBrowser = this.inAppBrowser;
        if (inAppBrowser == null) {
            dismiss();
        } else if (inAppBrowser.hardwareBack() && this.inAppBrowser.canGoBack()) {
            this.inAppBrowser.goBack();
        } else {
            this.inAppBrowser.closeDialog();
        }
    }
}
