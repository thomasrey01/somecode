package com.whitestein.securestorage;

import android.content.Context;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.nio.charset.Charset;
@CapacitorPlugin(name = "SecureStoragePlugin")
/* loaded from: classes.dex */
public class SecureStoragePluginPlugin extends Plugin {
    private PasswordStorageHelper passwordStorageHelper;

    @Override // com.getcapacitor.Plugin
    public void load() {
        super.load();
        this.passwordStorageHelper = new PasswordStorageHelper(getContext());
    }

    public void loadTextContext(Context context) {
        this.passwordStorageHelper = new PasswordStorageHelper(context);
    }

    @PluginMethod
    public void set(PluginCall call) {
        String string = call.getString("key");
        String string2 = call.getString("value");
        if (string2 == null) {
            string2 = "";
        }
        try {
            call.resolve(_set(string, string2));
        } catch (Exception e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void get(PluginCall call) {
        try {
            call.resolve(_get(call.getString("key")));
        } catch (Exception e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void keys(PluginCall call) {
        call.resolve(_keys());
    }

    @PluginMethod
    public void remove(PluginCall call) {
        String string = call.getString("key");
        try {
            if (has(string)) {
                call.resolve(_remove(string));
            } else {
                call.reject("Item with given key does not exist");
            }
        } catch (Exception e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void clear(PluginCall call) {
        try {
            call.resolve(_clear());
        } catch (Exception e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void getPlatform(PluginCall call) {
        call.resolve(_getPlatform());
    }

    public JSObject _set(String key, String value) {
        this.passwordStorageHelper.setData(key, value.getBytes(Charset.forName("UTF-8")));
        JSObject jSObject = new JSObject();
        jSObject.put("value", true);
        return jSObject;
    }

    public boolean has(String key) throws Exception {
        return this.passwordStorageHelper.getData(key) != null;
    }

    public JSObject _get(String key) throws Exception {
        byte[] data = this.passwordStorageHelper.getData(key);
        if (data != null) {
            String str = new String(data, Charset.forName("UTF-8"));
            JSObject jSObject = new JSObject();
            jSObject.put("value", str);
            return jSObject;
        }
        throw new Exception("Item with given key does not exist");
    }

    public JSObject _keys() {
        String[] keys = this.passwordStorageHelper.keys();
        JSObject jSObject = new JSObject();
        jSObject.put("value", (Object) JSArray.from(keys));
        return jSObject;
    }

    public JSObject _remove(String key) {
        this.passwordStorageHelper.remove(key);
        JSObject jSObject = new JSObject();
        jSObject.put("value", true);
        return jSObject;
    }

    public JSObject _clear() {
        this.passwordStorageHelper.clear();
        JSObject jSObject = new JSObject();
        jSObject.put("value", true);
        return jSObject;
    }

    public JSObject _getPlatform() {
        JSObject jSObject = new JSObject();
        jSObject.put("value", "android");
        return jSObject;
    }
}
