package com.capacitorjs.plugins.storage;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONException;
@CapacitorPlugin(name = "Storage")
/* loaded from: classes.dex */
public class StoragePlugin extends Plugin {
    private Storage storage;

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.storage = new Storage(getContext(), StorageConfiguration.DEFAULTS);
    }

    @PluginMethod
    public void configure(PluginCall call) {
        try {
            StorageConfiguration m5clone = StorageConfiguration.DEFAULTS.m5clone();
            m5clone.group = call.getString("group", StorageConfiguration.DEFAULTS.group);
            this.storage = new Storage(getContext(), m5clone);
            call.resolve();
        } catch (CloneNotSupportedException e) {
            call.reject("Error while configuring", e);
        }
    }

    @PluginMethod
    public void get(PluginCall call) {
        String string = call.getString("key");
        if (string == null) {
            call.reject("Must provide key");
            return;
        }
        Object obj = this.storage.get(string);
        JSObject jSObject = new JSObject();
        if (obj == null) {
            obj = JSObject.NULL;
        }
        jSObject.put("value", obj);
        call.resolve(jSObject);
    }

    @PluginMethod
    public void set(PluginCall call) {
        String string = call.getString("key");
        if (string == null) {
            call.reject("Must provide key");
            return;
        }
        this.storage.set(string, call.getString("value"));
        call.resolve();
    }

    @PluginMethod
    public void remove(PluginCall call) {
        String string = call.getString("key");
        if (string == null) {
            call.reject("Must provide key");
            return;
        }
        this.storage.remove(string);
        call.resolve();
    }

    @PluginMethod
    public void keys(PluginCall call) {
        String[] strArr = (String[]) this.storage.keys().toArray(new String[0]);
        JSObject jSObject = new JSObject();
        try {
            jSObject.put("keys", (Object) new JSArray(strArr));
            call.resolve(jSObject);
        } catch (JSONException e) {
            call.reject("Unable to serialize response.", e);
        }
    }

    @PluginMethod
    public void clear(PluginCall call) {
        this.storage.clear();
        call.resolve();
    }

    @PluginMethod
    public void migrate(PluginCall call) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Storage storage = new Storage(getContext(), StorageConfiguration.DEFAULTS);
        for (String str : storage.keys()) {
            String str2 = storage.get(str);
            if (this.storage.get(str) == null) {
                this.storage.set(str, str2);
                arrayList.add(str);
            } else {
                arrayList2.add(str);
            }
        }
        JSObject jSObject = new JSObject();
        jSObject.put("migrated", (Object) new JSArray((Collection) arrayList));
        jSObject.put("existing", (Object) new JSArray((Collection) arrayList2));
        call.resolve(jSObject);
    }

    @PluginMethod
    public void removeOld(PluginCall call) {
        call.resolve();
    }
}
