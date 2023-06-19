package com.capacitorjs.plugins.keyboard;

import android.os.Handler;
import com.capacitorjs.plugins.keyboard.Keyboard;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
@CapacitorPlugin(name = "Keyboard")
/* loaded from: classes.dex */
public class KeyboardPlugin extends Plugin {
    private Keyboard implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.-$$Lambda$KeyboardPlugin$opFgi3H-b4l2F5_A0BLNxPU4SkI
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$load$0$KeyboardPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$load$0$KeyboardPlugin() {
        Keyboard keyboard = new Keyboard(getActivity(), getConfig().getBoolean("resizeOnFullScreen", false));
        this.implementation = keyboard;
        keyboard.setKeyboardEventListener(new Keyboard.KeyboardEventListener() { // from class: com.capacitorjs.plugins.keyboard.-$$Lambda$WPuMFibH-0VsSZSkHOStHlfe21E
            @Override // com.capacitorjs.plugins.keyboard.Keyboard.KeyboardEventListener
            public final void onKeyboardEvent(String str, int i) {
                KeyboardPlugin.this.onKeyboardEvent(str, i);
            }
        });
    }

    @PluginMethod
    public void show(final PluginCall call) {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.-$$Lambda$KeyboardPlugin$RN5dg_H7Mro8y26r39uFy3O334w
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$show$2$KeyboardPlugin(call);
            }
        });
    }

    public /* synthetic */ void lambda$show$2$KeyboardPlugin(final PluginCall pluginCall) {
        new Handler().postDelayed(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.-$$Lambda$KeyboardPlugin$WldLJbAyC9G5i13iUK4vij0ccLk
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$show$1$KeyboardPlugin(pluginCall);
            }
        }, 350L);
    }

    public /* synthetic */ void lambda$show$1$KeyboardPlugin(PluginCall pluginCall) {
        this.implementation.show();
        pluginCall.resolve();
    }

    @PluginMethod
    public void hide(final PluginCall call) {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.-$$Lambda$KeyboardPlugin$I3ajYiS2TSDU4BXHZRyjvpYXd7U
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$hide$3$KeyboardPlugin(call);
            }
        });
    }

    public /* synthetic */ void lambda$hide$3$KeyboardPlugin(PluginCall pluginCall) {
        if (!this.implementation.hide()) {
            pluginCall.reject("Can't close keyboard, not currently focused");
        } else {
            pluginCall.resolve();
        }
    }

    @PluginMethod
    public void setAccessoryBarVisible(PluginCall call) {
        call.unimplemented();
    }

    @PluginMethod
    public void setStyle(PluginCall call) {
        call.unimplemented();
    }

    @PluginMethod
    public void setResizeMode(PluginCall call) {
        call.unimplemented();
    }

    @PluginMethod
    public void setScroll(PluginCall call) {
        call.unimplemented();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onKeyboardEvent(String event, int size) {
        JSObject jSObject = new JSObject();
        event.hashCode();
        char c = 65535;
        switch (event.hashCode()) {
            case -662060934:
                if (event.equals("keyboardDidHide")) {
                    c = 0;
                    break;
                }
                break;
            case -661733835:
                if (event.equals("keyboardDidShow")) {
                    c = 1;
                    break;
                }
                break;
            case -34092741:
                if (event.equals("keyboardWillHide")) {
                    c = 2;
                    break;
                }
                break;
            case -33765642:
                if (event.equals("keyboardWillShow")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 2:
                this.bridge.triggerWindowJSEvent(event);
                notifyListeners(event, jSObject);
                return;
            case 1:
            case 3:
                this.bridge.triggerWindowJSEvent(event, "{ 'keyboardHeight': " + size + " }");
                jSObject.put("keyboardHeight", size);
                notifyListeners(event, jSObject);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnDestroy() {
        this.implementation.setKeyboardEventListener(null);
    }
}
