package com.getcapacitor;
/* loaded from: classes.dex */
public class App {
    private AppRestoredListener appRestoredListener;
    private boolean isActive = false;
    private AppStatusChangeListener statusChangeListener;

    /* loaded from: classes.dex */
    public interface AppRestoredListener {
        void onAppRestored(PluginResult result);
    }

    /* loaded from: classes.dex */
    public interface AppStatusChangeListener {
        void onAppStatusChanged(Boolean isActive);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setStatusChangeListener(AppStatusChangeListener listener) {
        this.statusChangeListener = listener;
    }

    public void setAppRestoredListener(AppRestoredListener listener) {
        this.appRestoredListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void fireRestoredResult(PluginResult result) {
        AppRestoredListener appRestoredListener = this.appRestoredListener;
        if (appRestoredListener != null) {
            appRestoredListener.onAppRestored(result);
        }
    }

    public void fireStatusChange(boolean isActive) {
        this.isActive = isActive;
        AppStatusChangeListener appStatusChangeListener = this.statusChangeListener;
        if (appStatusChangeListener != null) {
            appStatusChangeListener.onAppStatusChanged(Boolean.valueOf(isActive));
        }
    }
}
