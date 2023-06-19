package com.google.zxing.client.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import barcodescanner.xservices.nl.barcodescanner.R;
/* loaded from: classes.dex */
public final class HelpActivity extends Activity {
    private static final String BASE_URL = "file:///android_asset/html-" + LocaleManager.getTranslatedAssetLanguage() + '/';
    private WebView webView;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.help);
        WebView webView = (WebView) findViewById(R.id.help_contents);
        this.webView = webView;
        if (bundle == null) {
            webView.loadUrl(BASE_URL + "index.html");
            return;
        }
        webView.restoreState(bundle);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.webView.saveState(bundle);
    }
}
