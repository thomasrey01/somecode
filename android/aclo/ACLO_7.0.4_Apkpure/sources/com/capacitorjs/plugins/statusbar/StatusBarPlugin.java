package com.capacitorjs.plugins.statusbar;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.WebColor;
import java.util.Locale;
@CapacitorPlugin(name = "StatusBar")
/* loaded from: classes.dex */
public class StatusBarPlugin extends Plugin {
    private StatusBar implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.implementation = new StatusBar(getActivity());
    }

    @PluginMethod
    public void setStyle(final PluginCall call) {
        final String string = call.getString("style");
        if (string == null) {
            call.reject("Style must be provided");
        } else {
            getBridge().executeOnMainThread(new Runnable() { // from class: com.capacitorjs.plugins.statusbar.-$$Lambda$StatusBarPlugin$P0evQBlBNWXSyrGe3nP2-0V9ExA
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarPlugin.this.lambda$setStyle$0$StatusBarPlugin(string, call);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setStyle$0$StatusBarPlugin(String str, PluginCall pluginCall) {
        this.implementation.setStyle(str);
        pluginCall.resolve();
    }

    @PluginMethod
    public void setBackgroundColor(final PluginCall call) {
        final String string = call.getString("color");
        if (string == null) {
            call.reject("Color must be provided");
        } else {
            getBridge().executeOnMainThread(new Runnable() { // from class: com.capacitorjs.plugins.statusbar.-$$Lambda$StatusBarPlugin$Id0JHQlHUMxnnWRrMZ-p-6z87Aw
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarPlugin.this.lambda$setBackgroundColor$1$StatusBarPlugin(string, call);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setBackgroundColor$1$StatusBarPlugin(String str, PluginCall pluginCall) {
        try {
            this.implementation.setBackgroundColor(WebColor.parseColor(str.toUpperCase(Locale.ROOT)));
            pluginCall.resolve();
        } catch (IllegalArgumentException unused) {
            pluginCall.reject("Invalid color provided. Must be a hex string (ex: #ff0000");
        }
    }

    @PluginMethod
    public void hide(final PluginCall call) {
        getBridge().executeOnMainThread(new Runnable() { // from class: com.capacitorjs.plugins.statusbar.-$$Lambda$StatusBarPlugin$D8TNFlLzY8Z70TGxKz6uu_kW1II
            @Override // java.lang.Runnable
            public final void run() {
                StatusBarPlugin.this.lambda$hide$2$StatusBarPlugin(call);
            }
        });
    }

    public /* synthetic */ void lambda$hide$2$StatusBarPlugin(PluginCall pluginCall) {
        this.implementation.hide();
        pluginCall.resolve();
    }

    @PluginMethod
    public void show(final PluginCall call) {
        getBridge().executeOnMainThread(new Runnable() { // from class: com.capacitorjs.plugins.statusbar.-$$Lambda$StatusBarPlugin$c-VPxw7fYmU9WfZnJh6dWvi-HPw
            @Override // java.lang.Runnable
            public final void run() {
                StatusBarPlugin.this.lambda$show$3$StatusBarPlugin(call);
            }
        });
    }

    public /* synthetic */ void lambda$show$3$StatusBarPlugin(PluginCall pluginCall) {
        this.implementation.show();
        pluginCall.resolve();
    }

    @PluginMethod
    public void getInfo(final PluginCall call) {
        StatusBarInfo info = this.implementation.getInfo();
        JSObject jSObject = new JSObject();
        jSObject.put("visible", info.isVisible());
        jSObject.put("style", info.getStyle());
        jSObject.put("color", info.getColor());
        jSObject.put("overlays", info.isOverlays());
        call.resolve(jSObject);
    }

    @PluginMethod
    public void setOverlaysWebView(final PluginCall call) {
        final Boolean bool = call.getBoolean("overlay", true);
        getBridge().executeOnMainThread(new Runnable() { // from class: com.capacitorjs.plugins.statusbar.-$$Lambda$StatusBarPlugin$ZgO5sqfaENNwBgu9DBYX4zUG40s
            @Override // java.lang.Runnable
            public final void run() {
                StatusBarPlugin.this.lambda$setOverlaysWebView$4$StatusBarPlugin(bool, call);
            }
        });
    }

    public /* synthetic */ void lambda$setOverlaysWebView$4$StatusBarPlugin(Boolean bool, PluginCall pluginCall) {
        this.implementation.setOverlaysWebView(bool);
        pluginCall.resolve();
    }
}
