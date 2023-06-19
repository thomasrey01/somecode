package com.capacitorjs.plugins.statusbar;

import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
/* loaded from: classes.dex */
public class StatusBar {
    private AppCompatActivity activity;
    private int currentStatusbarColor;
    private String defaultStyle = getStyle();

    public StatusBar(AppCompatActivity activity) {
        this.activity = activity;
        this.currentStatusbarColor = activity.getWindow().getStatusBarColor();
    }

    public void setStyle(String style) {
        View decorView = this.activity.getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        if (style.equals("DEFAULT")) {
            style = this.defaultStyle;
        }
        if (style.equals("DARK")) {
            decorView.setSystemUiVisibility(systemUiVisibility & (-8193));
        } else {
            decorView.setSystemUiVisibility(systemUiVisibility | 8192);
        }
    }

    public void setBackgroundColor(int color) {
        Window window = this.activity.getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(color);
        this.currentStatusbarColor = color;
    }

    public void hide() {
        View decorView = this.activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility((decorView.getSystemUiVisibility() | 4) & (-1));
    }

    public void show() {
        View decorView = this.activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility((decorView.getSystemUiVisibility() | 0) & (-5));
    }

    public void setOverlaysWebView(Boolean overlays) {
        if (overlays.booleanValue()) {
            View decorView = this.activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 256 | 1024);
            this.currentStatusbarColor = this.activity.getWindow().getStatusBarColor();
            this.activity.getWindow().setStatusBarColor(0);
            return;
        }
        View decorView2 = this.activity.getWindow().getDecorView();
        decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() & (-257) & (-1025));
        this.activity.getWindow().setStatusBarColor(this.currentStatusbarColor);
    }

    public StatusBarInfo getInfo() {
        Window window = this.activity.getWindow();
        View decorView = window.getDecorView();
        StatusBarInfo statusBarInfo = new StatusBarInfo();
        statusBarInfo.setStyle(getStyle());
        statusBarInfo.setOverlays((decorView.getSystemUiVisibility() & 1024) == 1024);
        statusBarInfo.setVisible((decorView.getSystemUiVisibility() & 4) != 4);
        statusBarInfo.setColor(String.format("#%06X", Integer.valueOf(window.getStatusBarColor() & ViewCompat.MEASURED_SIZE_MASK)));
        return statusBarInfo;
    }

    private String getStyle() {
        return (this.activity.getWindow().getDecorView().getSystemUiVisibility() & 8192) == 8192 ? "LIGHT" : "DARK";
    }
}
