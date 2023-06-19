package com.google.zxing.client.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class InactivityTimer {
    private static final long INACTIVITY_DELAY_MS = 300000;
    private static final String TAG = "InactivityTimer";
    private final Activity activity;
    private AsyncTask<Object, Object, Object> inactivityTask;
    private final BroadcastReceiver powerStatusReceiver = new PowerStatusReceiver();
    private boolean registered = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void onActivity() {
        cancel();
        InactivityAsyncTask inactivityAsyncTask = new InactivityAsyncTask();
        this.inactivityTask = inactivityAsyncTask;
        inactivityAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void onPause() {
        cancel();
        if (this.registered) {
            this.activity.unregisterReceiver(this.powerStatusReceiver);
            this.registered = false;
        } else {
            Log.w(TAG, "PowerStatusReceiver was never registered?");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void onResume() {
        if (this.registered) {
            Log.w(TAG, "PowerStatusReceiver was already registered?");
        } else {
            this.activity.registerReceiver(this.powerStatusReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.registered = true;
        }
        onActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void cancel() {
        AsyncTask<Object, Object, Object> asyncTask = this.inactivityTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.inactivityTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shutdown() {
        cancel();
    }

    /* loaded from: classes.dex */
    private final class PowerStatusReceiver extends BroadcastReceiver {
        private PowerStatusReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                if (!(intent.getIntExtra("plugged", -1) <= 0)) {
                    InactivityTimer.this.cancel();
                } else {
                    InactivityTimer.this.onActivity();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class InactivityAsyncTask extends AsyncTask<Object, Object, Object> {
        private InactivityAsyncTask() {
        }

        @Override // android.os.AsyncTask
        protected Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(300000L);
                Log.i(InactivityTimer.TAG, "Finishing activity due to inactivity");
                InactivityTimer.this.activity.finish();
                return null;
            } catch (InterruptedException unused) {
                return null;
            }
        }
    }
}
