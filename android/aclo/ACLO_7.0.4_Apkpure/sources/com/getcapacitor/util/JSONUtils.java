package com.getcapacitor.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class JSONUtils {
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        try {
            String string = getDeepestObject(jsonObject, key).getString(getDeepestKey(key));
            return string == null ? defaultValue : string;
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(JSONObject jsonObject, String key, boolean defaultValue) {
        try {
            return getDeepestObject(jsonObject, key).getBoolean(getDeepestKey(key));
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        try {
            return getDeepestObject(jsonObject, key).getInt(getDeepestKey(key));
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    public static JSONObject getObject(JSONObject jsonObject, String key) {
        try {
            return getDeepestObject(jsonObject, key).getJSONObject(getDeepestKey(key));
        } catch (JSONException unused) {
            return null;
        }
    }

    public static String[] getArray(JSONObject jsonObject, String key, String[] defaultValue) {
        try {
            JSONArray jSONArray = getDeepestObject(jsonObject, key).getJSONArray(getDeepestKey(key));
            if (jSONArray == null) {
                return defaultValue;
            }
            int length = jSONArray.length();
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = (String) jSONArray.get(i);
            }
            return strArr;
        } catch (JSONException unused) {
            return defaultValue;
        }
    }

    private static String getDeepestKey(String key) {
        String[] split = key.split("\\.");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        return null;
    }

    private static JSONObject getDeepestObject(JSONObject jsonObject, String key) throws JSONException {
        String[] split = key.split("\\.");
        for (int i = 0; i < split.length - 1; i++) {
            jsonObject = jsonObject.getJSONObject(split[i]);
        }
        return jsonObject;
    }
}
