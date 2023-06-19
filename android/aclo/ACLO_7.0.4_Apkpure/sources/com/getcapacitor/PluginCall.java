package com.getcapacitor;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class PluginCall {
    public static final String CALLBACK_ID_DANGLING = "-1";
    private final String callbackId;
    private final JSObject data;
    private final String methodName;
    private final MessageHandler msgHandler;
    private final String pluginId;
    private boolean keepAlive = false;
    @Deprecated
    private boolean isReleased = false;

    public PluginCall(MessageHandler msgHandler, String pluginId, String callbackId, String methodName, JSObject data) {
        this.msgHandler = msgHandler;
        this.pluginId = pluginId;
        this.callbackId = callbackId;
        this.methodName = methodName;
        this.data = data;
    }

    public void successCallback(PluginResult successResult) {
        if (CALLBACK_ID_DANGLING.equals(this.callbackId)) {
            return;
        }
        this.msgHandler.sendResponseMessage(this, successResult, null);
    }

    @Deprecated
    public void success(JSObject data) {
        this.msgHandler.sendResponseMessage(this, new PluginResult(data), null);
    }

    @Deprecated
    public void success() {
        resolve(new JSObject());
    }

    public void resolve(JSObject data) {
        this.msgHandler.sendResponseMessage(this, new PluginResult(data), null);
    }

    public void resolve() {
        this.msgHandler.sendResponseMessage(this, null, null);
    }

    public void errorCallback(String msg) {
        PluginResult pluginResult = new PluginResult();
        try {
            pluginResult.put("message", msg);
        } catch (Exception e) {
            Logger.error(Logger.tags("Plugin"), e.toString(), null);
        }
        this.msgHandler.sendResponseMessage(this, null, pluginResult);
    }

    @Deprecated
    public void error(String msg, Exception ex) {
        reject(msg, null, ex);
    }

    @Deprecated
    public void error(String msg, String code, Exception ex) {
        reject(msg, code, ex);
    }

    @Deprecated
    public void error(String msg) {
        reject(msg, null, null);
    }

    public void reject(String msg, String code, Exception ex) {
        PluginResult pluginResult = new PluginResult();
        if (ex != null) {
            Logger.error(Logger.tags("Plugin"), msg, ex);
        }
        try {
            pluginResult.put("message", msg);
            pluginResult.put("code", code);
        } catch (Exception e) {
            Logger.error(Logger.tags("Plugin"), e.getMessage(), null);
        }
        this.msgHandler.sendResponseMessage(this, null, pluginResult);
    }

    public void reject(String msg, Exception ex) {
        reject(msg, null, ex);
    }

    public void reject(String msg, String code) {
        reject(msg, code, null);
    }

    public void reject(String msg) {
        reject(msg, null, null);
    }

    public void unimplemented() {
        unimplemented("not implemented");
    }

    public void unimplemented(String msg) {
        reject(msg, "UNIMPLEMENTED", null);
    }

    public void unavailable() {
        unavailable("not available");
    }

    public void unavailable(String msg) {
        reject(msg, "UNAVAILABLE", null);
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public String getCallbackId() {
        return this.callbackId;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public JSObject getData() {
        return this.data;
    }

    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String defaultValue) {
        Object opt = this.data.opt(name);
        return (opt != null && (opt instanceof String)) ? (String) opt : defaultValue;
    }

    public Integer getInt(String name) {
        return getInt(name, null);
    }

    public Integer getInt(String name, Integer defaultValue) {
        Object opt = this.data.opt(name);
        return (opt != null && (opt instanceof Integer)) ? (Integer) opt : defaultValue;
    }

    public Long getLong(String name) {
        return getLong(name, null);
    }

    public Long getLong(String name, Long defaultValue) {
        Object opt = this.data.opt(name);
        return (opt != null && (opt instanceof Long)) ? (Long) opt : defaultValue;
    }

    public Float getFloat(String name) {
        return getFloat(name, null);
    }

    public Float getFloat(String name, Float defaultValue) {
        Object opt = this.data.opt(name);
        if (opt == null) {
            return defaultValue;
        }
        if (opt instanceof Float) {
            return (Float) opt;
        }
        if (opt instanceof Double) {
            return Float.valueOf(((Double) opt).floatValue());
        }
        return opt instanceof Integer ? Float.valueOf(((Integer) opt).floatValue()) : defaultValue;
    }

    public Double getDouble(String name) {
        return getDouble(name, null);
    }

    public Double getDouble(String name, Double defaultValue) {
        Object opt = this.data.opt(name);
        if (opt == null) {
            return defaultValue;
        }
        if (opt instanceof Double) {
            return (Double) opt;
        }
        if (opt instanceof Float) {
            return Double.valueOf(((Float) opt).doubleValue());
        }
        return opt instanceof Integer ? Double.valueOf(((Integer) opt).doubleValue()) : defaultValue;
    }

    public Boolean getBoolean(String name) {
        return getBoolean(name, null);
    }

    public Boolean getBoolean(String name, Boolean defaultValue) {
        Object opt = this.data.opt(name);
        return (opt != null && (opt instanceof Boolean)) ? (Boolean) opt : defaultValue;
    }

    public JSObject getObject(String name) {
        return getObject(name, new JSObject());
    }

    public JSObject getObject(String name, JSObject defaultValue) {
        Object opt = this.data.opt(name);
        if (opt != null && (opt instanceof JSONObject)) {
            try {
                return JSObject.fromJSONObject((JSONObject) opt);
            } catch (JSONException unused) {
            }
        }
        return defaultValue;
    }

    public JSArray getArray(String name) {
        return getArray(name, new JSArray());
    }

    public JSArray getArray(String name, JSArray defaultValue) {
        Object opt = this.data.opt(name);
        if (opt != null && (opt instanceof JSONArray)) {
            try {
                JSONArray jSONArray = (JSONArray) opt;
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.get(i));
                }
                return new JSArray(arrayList.toArray());
            } catch (JSONException unused) {
            }
        }
        return defaultValue;
    }

    public boolean hasOption(String name) {
        return this.data.has(name);
    }

    @Deprecated
    public void save() {
        setKeepAlive(true);
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive.booleanValue();
    }

    public void release(Bridge bridge) {
        this.keepAlive = false;
        bridge.releaseCall(this);
        this.isReleased = true;
    }

    @Deprecated
    public boolean isSaved() {
        return isKeptAlive();
    }

    public boolean isKeptAlive() {
        return this.keepAlive;
    }

    @Deprecated
    public boolean isReleased() {
        return this.isReleased;
    }

    /* loaded from: classes.dex */
    class PluginCallDataTypeException extends Exception {
        PluginCallDataTypeException(String m) {
            super(m);
        }
    }
}
