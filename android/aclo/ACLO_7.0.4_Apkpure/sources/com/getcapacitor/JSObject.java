package com.getcapacitor;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class JSObject extends JSONObject {
    public JSObject() {
    }

    public JSObject(String json) throws JSONException {
        super(json);
    }

    public JSObject(JSONObject obj, String[] names) throws JSONException {
        super(obj, names);
    }

    public static JSObject fromJSONObject(JSONObject obj) throws JSONException {
        Iterator<String> keys = obj.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            arrayList.add(keys.next());
        }
        return new JSObject(obj, (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    @Override // org.json.JSONObject
    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return !super.isNull(key) ? super.getString(key) : defaultValue;
    }

    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(super.getInt(key));
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        try {
            return Boolean.valueOf(super.getBoolean(key));
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    public Boolean getBool(String key) {
        return getBoolean(key, null);
    }

    public JSObject getJSObject(String name) {
        try {
            return getJSObject(name, null);
        } catch (JSONException unused) {
            return null;
        }
    }

    public JSObject getJSObject(String name, JSObject defaultValue) throws JSONException {
        try {
            Object obj = get(name);
            if (obj instanceof JSONObject) {
                Iterator<String> keys = ((JSONObject) obj).keys();
                ArrayList arrayList = new ArrayList();
                while (keys.hasNext()) {
                    arrayList.add(keys.next());
                }
                return new JSObject((JSONObject) obj, (String[]) arrayList.toArray(new String[arrayList.size()]));
            }
        } catch (JSONException unused) {
        }
        return defaultValue;
    }

    @Override // org.json.JSONObject
    public JSObject put(String key, boolean value) {
        try {
            super.put(key, value);
        } catch (JSONException unused) {
        }
        return this;
    }

    @Override // org.json.JSONObject
    public JSObject put(String key, int value) {
        try {
            super.put(key, value);
        } catch (JSONException unused) {
        }
        return this;
    }

    @Override // org.json.JSONObject
    public JSObject put(String key, long value) {
        try {
            super.put(key, value);
        } catch (JSONException unused) {
        }
        return this;
    }

    @Override // org.json.JSONObject
    public JSObject put(String key, double value) {
        try {
            super.put(key, value);
        } catch (JSONException unused) {
        }
        return this;
    }

    @Override // org.json.JSONObject
    public JSObject put(String key, Object value) {
        try {
            super.put(key, value);
        } catch (JSONException unused) {
        }
        return this;
    }

    public JSObject put(String key, String value) {
        try {
            super.put(key, (Object) value);
        } catch (JSONException unused) {
        }
        return this;
    }

    public JSObject putSafe(String key, Object value) throws JSONException {
        return (JSObject) super.put(key, value);
    }
}
