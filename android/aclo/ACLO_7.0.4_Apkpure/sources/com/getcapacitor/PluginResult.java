package com.getcapacitor;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
/* loaded from: classes.dex */
public class PluginResult {
    private final JSObject json;

    public PluginResult() {
        this(new JSObject());
    }

    public PluginResult(JSObject json) {
        this.json = json;
    }

    public PluginResult put(String name, boolean value) {
        return jsonPut(name, Boolean.valueOf(value));
    }

    public PluginResult put(String name, double value) {
        return jsonPut(name, Double.valueOf(value));
    }

    public PluginResult put(String name, int value) {
        return jsonPut(name, Integer.valueOf(value));
    }

    public PluginResult put(String name, long value) {
        return jsonPut(name, Long.valueOf(value));
    }

    public PluginResult put(String name, Date value) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        simpleDateFormat.setTimeZone(timeZone);
        return jsonPut(name, simpleDateFormat.format(value));
    }

    public PluginResult put(String name, Object value) {
        return jsonPut(name, value);
    }

    public PluginResult put(String name, PluginResult value) {
        return jsonPut(name, value.json);
    }

    PluginResult jsonPut(String name, Object value) {
        try {
            this.json.put(name, value);
        } catch (Exception e) {
            Logger.error(Logger.tags("Plugin"), "", e);
        }
        return this;
    }

    public String toString() {
        return this.json.toString();
    }

    public JSObject getWrappedResult() {
        JSObject jSObject = new JSObject();
        jSObject.put("pluginId", this.json.getString("pluginId"));
        jSObject.put("methodName", this.json.getString("methodName"));
        jSObject.put(FirebaseAnalytics.Param.SUCCESS, (Object) this.json.getBoolean(FirebaseAnalytics.Param.SUCCESS, false));
        jSObject.put("data", (Object) this.json.getJSObject("data"));
        jSObject.put("error", (Object) this.json.getJSObject("error"));
        return jSObject;
    }
}
