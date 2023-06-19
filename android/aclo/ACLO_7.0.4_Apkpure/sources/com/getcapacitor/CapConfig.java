package com.getcapacitor;

import android.content.Context;
import android.content.res.AssetManager;
import com.getcapacitor.util.JSONUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class CapConfig {
    private static final String LOG_BEHAVIOR_DEBUG = "debug";
    private static final String LOG_BEHAVIOR_NONE = "none";
    private static final String LOG_BEHAVIOR_PRODUCTION = "production";
    private boolean allowMixedContent;
    private String[] allowNavigation;
    private String androidScheme;
    private String appendedUserAgentString;
    private String backgroundColor;
    private boolean captureInput;
    private JSONObject configJSON;
    private String hostname;
    private boolean html5mode;
    private boolean initialFocus;
    private boolean loggingEnabled;
    private String overriddenUserAgentString;
    private Map<String, PluginConfig> pluginsConfiguration;
    private String serverUrl;
    private String startPath;
    private boolean webContentsDebuggingEnabled;

    private CapConfig() {
        this.html5mode = true;
        this.hostname = "localhost";
        this.androidScheme = Bridge.CAPACITOR_HTTP_SCHEME;
        this.allowMixedContent = false;
        this.captureInput = false;
        this.webContentsDebuggingEnabled = false;
        this.loggingEnabled = true;
        this.initialFocus = true;
        this.pluginsConfiguration = null;
        this.configJSON = new JSONObject();
    }

    @Deprecated
    public CapConfig(AssetManager assetManager, JSONObject config) {
        this.html5mode = true;
        this.hostname = "localhost";
        this.androidScheme = Bridge.CAPACITOR_HTTP_SCHEME;
        this.allowMixedContent = false;
        this.captureInput = false;
        this.webContentsDebuggingEnabled = false;
        this.loggingEnabled = true;
        this.initialFocus = true;
        this.pluginsConfiguration = null;
        this.configJSON = new JSONObject();
        if (config != null) {
            this.configJSON = config;
        } else {
            loadConfig(assetManager);
        }
        deserializeConfig(null);
    }

    public static CapConfig loadDefault(Context context) {
        CapConfig capConfig = new CapConfig();
        if (context == null) {
            Logger.error("Capacitor Config could not be created from file. Context must not be null.");
            return capConfig;
        }
        capConfig.loadConfig(context.getAssets());
        capConfig.deserializeConfig(context);
        return capConfig;
    }

    private CapConfig(Builder builder) {
        this.html5mode = true;
        this.hostname = "localhost";
        this.androidScheme = Bridge.CAPACITOR_HTTP_SCHEME;
        this.allowMixedContent = false;
        this.captureInput = false;
        this.webContentsDebuggingEnabled = false;
        this.loggingEnabled = true;
        this.initialFocus = true;
        this.pluginsConfiguration = null;
        this.configJSON = new JSONObject();
        this.html5mode = builder.html5mode;
        this.serverUrl = builder.serverUrl;
        this.hostname = builder.hostname;
        this.androidScheme = builder.androidScheme;
        this.allowNavigation = builder.allowNavigation;
        this.overriddenUserAgentString = builder.overriddenUserAgentString;
        this.appendedUserAgentString = builder.appendedUserAgentString;
        this.backgroundColor = builder.backgroundColor;
        this.allowMixedContent = builder.allowMixedContent;
        this.captureInput = builder.captureInput;
        this.webContentsDebuggingEnabled = builder.webContentsDebuggingEnabled.booleanValue();
        this.loggingEnabled = builder.loggingEnabled;
        this.initialFocus = builder.initialFocus;
        this.startPath = builder.startPath;
        this.pluginsConfiguration = builder.pluginsConfiguration;
    }

    private void loadConfig(AssetManager assetManager) {
        try {
            this.configJSON = new JSONObject(FileUtils.readFile(assetManager, "capacitor.config.json"));
        } catch (IOException e) {
            Logger.error("Unable to load capacitor.config.json. Run npx cap copy first", e);
        } catch (JSONException e2) {
            Logger.error("Unable to parse capacitor.config.json. Make sure it's valid json", e2);
        }
    }

    private void deserializeConfig(Context context) {
        boolean z = (context == null || (context.getApplicationInfo().flags & 2) == 0) ? false : true;
        this.html5mode = JSONUtils.getBoolean(this.configJSON, "server.html5mode", this.html5mode);
        this.serverUrl = JSONUtils.getString(this.configJSON, "server.url", null);
        this.hostname = JSONUtils.getString(this.configJSON, "server.hostname", this.hostname);
        this.androidScheme = JSONUtils.getString(this.configJSON, "server.androidScheme", this.androidScheme);
        this.allowNavigation = JSONUtils.getArray(this.configJSON, "server.allowNavigation", null);
        JSONObject jSONObject = this.configJSON;
        this.overriddenUserAgentString = JSONUtils.getString(jSONObject, "android.overrideUserAgent", JSONUtils.getString(jSONObject, "overrideUserAgent", null));
        JSONObject jSONObject2 = this.configJSON;
        this.appendedUserAgentString = JSONUtils.getString(jSONObject2, "android.appendUserAgent", JSONUtils.getString(jSONObject2, "appendUserAgent", null));
        JSONObject jSONObject3 = this.configJSON;
        this.backgroundColor = JSONUtils.getString(jSONObject3, "android.backgroundColor", JSONUtils.getString(jSONObject3, "backgroundColor", null));
        JSONObject jSONObject4 = this.configJSON;
        this.allowMixedContent = JSONUtils.getBoolean(jSONObject4, "android.allowMixedContent", JSONUtils.getBoolean(jSONObject4, "allowMixedContent", this.allowMixedContent));
        this.captureInput = JSONUtils.getBoolean(this.configJSON, "android.captureInput", this.captureInput);
        this.webContentsDebuggingEnabled = JSONUtils.getBoolean(this.configJSON, "android.webContentsDebuggingEnabled", z);
        JSONObject jSONObject5 = this.configJSON;
        String string = JSONUtils.getString(jSONObject5, "android.loggingBehavior", JSONUtils.getString(jSONObject5, "loggingBehavior", null));
        if (string == null) {
            JSONObject jSONObject6 = this.configJSON;
            string = JSONUtils.getBoolean(jSONObject6, "android.hideLogs", JSONUtils.getBoolean(jSONObject6, "hideLogs", false)) ? "none" : LOG_BEHAVIOR_DEBUG;
        }
        String lowerCase = string.toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        if (lowerCase.equals("none")) {
            this.loggingEnabled = false;
        } else if (lowerCase.equals(LOG_BEHAVIOR_PRODUCTION)) {
            this.loggingEnabled = true;
        } else {
            this.loggingEnabled = z;
        }
        this.initialFocus = JSONUtils.getBoolean(this.configJSON, "android.initialFocus", this.initialFocus);
        this.pluginsConfiguration = deserializePluginsConfig(JSONUtils.getObject(this.configJSON, "plugins"));
    }

    public boolean isHTML5Mode() {
        return this.html5mode;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getStartPath() {
        return this.startPath;
    }

    public String getAndroidScheme() {
        return this.androidScheme;
    }

    public String[] getAllowNavigation() {
        return this.allowNavigation;
    }

    public String getOverriddenUserAgentString() {
        return this.overriddenUserAgentString;
    }

    public String getAppendedUserAgentString() {
        return this.appendedUserAgentString;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public boolean isMixedContentAllowed() {
        return this.allowMixedContent;
    }

    public boolean isInputCaptured() {
        return this.captureInput;
    }

    public boolean isWebContentsDebuggingEnabled() {
        return this.webContentsDebuggingEnabled;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public boolean isInitialFocus() {
        return this.initialFocus;
    }

    public PluginConfig getPluginConfiguration(String pluginId) {
        PluginConfig pluginConfig = this.pluginsConfiguration.get(pluginId);
        return pluginConfig == null ? new PluginConfig(new JSONObject()) : pluginConfig;
    }

    @Deprecated
    public JSONObject getObject(String key) {
        try {
            return this.configJSON.getJSONObject(key);
        } catch (Exception unused) {
            return null;
        }
    }

    @Deprecated
    public String getString(String key) {
        return JSONUtils.getString(this.configJSON, key, null);
    }

    @Deprecated
    public String getString(String key, String defaultValue) {
        return JSONUtils.getString(this.configJSON, key, defaultValue);
    }

    @Deprecated
    public boolean getBoolean(String key, boolean defaultValue) {
        return JSONUtils.getBoolean(this.configJSON, key, defaultValue);
    }

    @Deprecated
    public int getInt(String key, int defaultValue) {
        return JSONUtils.getInt(this.configJSON, key, defaultValue);
    }

    @Deprecated
    public String[] getArray(String key) {
        return JSONUtils.getArray(this.configJSON, key, null);
    }

    @Deprecated
    public String[] getArray(String key, String[] defaultValue) {
        return JSONUtils.getArray(this.configJSON, key, defaultValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, PluginConfig> deserializePluginsConfig(JSONObject pluginsConfig) {
        HashMap hashMap = new HashMap();
        if (pluginsConfig == null) {
            return hashMap;
        }
        Iterator<String> keys = pluginsConfig.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                hashMap.put(next, new PluginConfig(pluginsConfig.getJSONObject(next)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private String[] allowNavigation;
        private String appendedUserAgentString;
        private String backgroundColor;
        private Context context;
        private String overriddenUserAgentString;
        private String serverUrl;
        private boolean html5mode = true;
        private String hostname = "localhost";
        private String androidScheme = Bridge.CAPACITOR_HTTP_SCHEME;
        private boolean allowMixedContent = false;
        private boolean captureInput = false;
        private Boolean webContentsDebuggingEnabled = null;
        private boolean loggingEnabled = true;
        private boolean initialFocus = false;
        private String startPath = null;
        private Map<String, PluginConfig> pluginsConfiguration = new HashMap();

        public Builder(Context context) {
            this.context = context;
        }

        public CapConfig create() {
            if (this.webContentsDebuggingEnabled == null) {
                this.webContentsDebuggingEnabled = Boolean.valueOf((this.context.getApplicationInfo().flags & 2) != 0);
            }
            return new CapConfig(this);
        }

        public Builder setPluginsConfiguration(JSONObject pluginsConfiguration) {
            this.pluginsConfiguration = CapConfig.deserializePluginsConfig(pluginsConfiguration);
            return this;
        }

        public Builder setHTML5mode(boolean html5mode) {
            this.html5mode = html5mode;
            return this;
        }

        public Builder setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public Builder setHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder setStartPath(String path) {
            this.startPath = path;
            return this;
        }

        public Builder setAndroidScheme(String androidScheme) {
            this.androidScheme = androidScheme;
            return this;
        }

        public Builder setAllowNavigation(String[] allowNavigation) {
            this.allowNavigation = allowNavigation;
            return this;
        }

        public Builder setOverriddenUserAgentString(String overriddenUserAgentString) {
            this.overriddenUserAgentString = overriddenUserAgentString;
            return this;
        }

        public Builder setAppendedUserAgentString(String appendedUserAgentString) {
            this.appendedUserAgentString = appendedUserAgentString;
            return this;
        }

        public Builder setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setAllowMixedContent(boolean allowMixedContent) {
            this.allowMixedContent = allowMixedContent;
            return this;
        }

        public Builder setCaptureInput(boolean captureInput) {
            this.captureInput = captureInput;
            return this;
        }

        public Builder setWebContentsDebuggingEnabled(boolean webContentsDebuggingEnabled) {
            this.webContentsDebuggingEnabled = Boolean.valueOf(webContentsDebuggingEnabled);
            return this;
        }

        public Builder setLoggingEnabled(boolean enabled) {
            this.loggingEnabled = enabled;
            return this;
        }

        public Builder setInitialFocus(boolean focus) {
            this.initialFocus = focus;
            return this;
        }
    }
}
