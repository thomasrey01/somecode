package com.capacitorjs.plugins.haptics;

import com.capacitorjs.plugins.haptics.arguments.HapticsImpactType;
import com.capacitorjs.plugins.haptics.arguments.HapticsNotificationType;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
@CapacitorPlugin(name = "Haptics")
/* loaded from: classes.dex */
public class HapticsPlugin extends Plugin {
    private Haptics implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.implementation = new Haptics(getContext());
    }

    @PluginMethod
    public void vibrate(PluginCall call) {
        this.implementation.vibrate(call.getInt("duration", 300).intValue());
        call.resolve();
    }

    @PluginMethod
    public void impact(PluginCall call) {
        this.implementation.performHaptics(HapticsImpactType.fromString(call.getString("style")));
        call.resolve();
    }

    @PluginMethod
    public void notification(PluginCall call) {
        this.implementation.performHaptics(HapticsNotificationType.fromString(call.getString("type")));
        call.resolve();
    }

    @PluginMethod
    public void selectionStart(PluginCall call) {
        this.implementation.selectionStart();
        call.resolve();
    }

    @PluginMethod
    public void selectionChanged(PluginCall call) {
        this.implementation.selectionChanged();
        call.resolve();
    }

    @PluginMethod
    public void selectionEnd(PluginCall call) {
        this.implementation.selectionEnd();
        call.resolve();
    }
}
