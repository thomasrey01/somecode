package com.getcapacitor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.Bridge;
import com.getcapacitor.android.R;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class BridgeActivity extends AppCompatActivity {
    protected Bridge bridge;
    protected CapConfig config;
    protected boolean keepRunning = true;
    protected int activityDepth = 0;
    protected List<Class<? extends Plugin>> initialPlugins = new ArrayList();
    protected final Bridge.Builder bridgeBuilder = new Bridge.Builder(this);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bridgeBuilder.setInstanceState(savedInstanceState);
        getApplication().setTheme(getResources().getIdentifier("AppTheme_NoActionBar", "style", getPackageName()));
        setTheme(getResources().getIdentifier("AppTheme_NoActionBar", "style", getPackageName()));
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.bridge_layout_main);
    }

    @Deprecated
    protected void init(Bundle savedInstanceState, List<Class<? extends Plugin>> plugins) {
        init(savedInstanceState, plugins, null);
    }

    @Deprecated
    protected void init(Bundle savedInstanceState, List<Class<? extends Plugin>> plugins, CapConfig config) {
        this.initialPlugins = plugins;
        this.config = config;
        load();
    }

    @Deprecated
    protected void load(Bundle savedInstanceState) {
        load();
    }

    protected void load() {
        Logger.debug("Starting BridgeActivity");
        Bridge create = this.bridgeBuilder.addPlugins(this.initialPlugins).setConfig(this.config).create();
        this.bridge = create;
        this.keepRunning = create.shouldKeepRunning();
        onNewIntent(getIntent());
    }

    public void registerPlugin(Class<? extends Plugin> plugin) {
        this.bridgeBuilder.addPlugin(plugin);
    }

    public void registerPlugins(List<Class<? extends Plugin>> plugins) {
        this.bridgeBuilder.addPlugins(plugins);
    }

    public Bridge getBridge() {
        return this.bridge;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.bridge.saveInstanceState(outState);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (this.bridge == null) {
            try {
                this.bridgeBuilder.addPlugins(new PluginManager(getAssets()).loadPluginClasses());
            } catch (PluginLoadException e) {
                Logger.error("Error loading plugins.", e);
            }
            load();
        }
        this.activityDepth++;
        this.bridge.onStart();
        Logger.debug("App started");
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.bridge.onRestart();
        Logger.debug("App restarted");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.bridge.getApp().fireStatusChange(true);
        this.bridge.onResume();
        Logger.debug("App resumed");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.bridge.onPause();
        Logger.debug("App paused");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        int max = Math.max(0, this.activityDepth - 1);
        this.activityDepth = max;
        if (max == 0) {
            this.bridge.getApp().fireStatusChange(false);
        }
        this.bridge.onStop();
        Logger.debug("App stopped");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.bridge.onDestroy();
        Logger.debug("App destroyed");
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.bridge.onDetachedFromWindow();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Bridge bridge = this.bridge;
        if (bridge == null || bridge.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bridge bridge = this.bridge;
        if (bridge == null || bridge.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bridge bridge = this.bridge;
        if (bridge == null || intent == null) {
            return;
        }
        bridge.onNewIntent(intent);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Bridge bridge = this.bridge;
        if (bridge == null) {
            return;
        }
        bridge.onConfigurationChanged(newConfig);
    }
}
