package com.getcapacitor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/* loaded from: classes.dex */
public class BridgeWebViewClient extends WebViewClient {
    private Bridge bridge;

    public BridgeWebViewClient(Bridge bridge) {
        this.bridge = bridge;
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return this.bridge.getLocalServer().shouldInterceptRequest(request);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return this.bridge.launchIntent(request.getUrl());
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return this.bridge.launchIntent(Uri.parse(url));
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (this.bridge.getWebViewListeners() == null || view.getProgress() != 100) {
            return;
        }
        for (WebViewListener webViewListener : this.bridge.getWebViewListeners()) {
            webViewListener.onPageLoaded(view);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (this.bridge.getWebViewListeners() != null) {
            for (WebViewListener webViewListener : this.bridge.getWebViewListeners()) {
                webViewListener.onReceivedError(view);
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        this.bridge.reset();
        if (this.bridge.getWebViewListeners() != null) {
            for (WebViewListener webViewListener : this.bridge.getWebViewListeners()) {
                webViewListener.onPageStarted(view);
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        if (this.bridge.getWebViewListeners() != null) {
            for (WebViewListener webViewListener : this.bridge.getWebViewListeners()) {
                webViewListener.onReceivedHttpError(view);
            }
        }
    }
}
