package com.getcapacitor.plugin.http;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import org.json.JSONException;
/* loaded from: classes.dex */
public class JSValue {
    private final Object value;

    public JSValue(PluginCall call, String name) {
        this.value = toValue(call, name);
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return getValue().toString();
    }

    public JSObject toJSObject() throws JSONException {
        Object obj = this.value;
        if (obj instanceof JSObject) {
            return (JSObject) obj;
        }
        throw new JSONException("JSValue could not be coerced to JSObject.");
    }

    public JSArray toJSArray() throws JSONException {
        Object obj = this.value;
        if (obj instanceof JSArray) {
            return (JSArray) obj;
        }
        throw new JSONException("JSValue could not be coerced to JSArray.");
    }

    private Object toValue(PluginCall call, String name) {
        JSArray array = call.getArray(name, null);
        if (array != null) {
            return array;
        }
        JSObject object = call.getObject(name, null);
        if (object != null) {
            return object;
        }
        String string = call.getString(name, null);
        return string != null ? string : call.getData().opt(name);
    }
}
