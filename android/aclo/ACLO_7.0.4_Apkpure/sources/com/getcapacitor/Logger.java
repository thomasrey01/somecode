package com.getcapacitor;

import android.text.TextUtils;
import android.util.Log;
/* loaded from: classes.dex */
public class Logger {
    public static final String LOG_TAG_CORE = "Capacitor";
    public static CapConfig config;
    private static Logger instance;

    private static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void init(CapConfig config2) {
        getInstance().loadConfig(config2);
    }

    private void loadConfig(CapConfig config2) {
        config = config2;
    }

    public static String tags(String... subtags) {
        if (subtags == null || subtags.length <= 0) {
            return LOG_TAG_CORE;
        }
        return "Capacitor/" + TextUtils.join("/", subtags);
    }

    public static void verbose(String message) {
        verbose(LOG_TAG_CORE, message);
    }

    public static void verbose(String tag, String message) {
        if (shouldLog()) {
            Log.v(tag, message);
        }
    }

    public static void debug(String message) {
        debug(LOG_TAG_CORE, message);
    }

    public static void debug(String tag, String message) {
        if (shouldLog()) {
            Log.d(tag, message);
        }
    }

    public static void info(String message) {
        info(LOG_TAG_CORE, message);
    }

    public static void info(String tag, String message) {
        if (shouldLog()) {
            Log.i(tag, message);
        }
    }

    public static void warn(String message) {
        warn(LOG_TAG_CORE, message);
    }

    public static void warn(String tag, String message) {
        if (shouldLog()) {
            Log.w(tag, message);
        }
    }

    public static void error(String message) {
        error(LOG_TAG_CORE, message, null);
    }

    public static void error(String message, Throwable e) {
        error(LOG_TAG_CORE, message, e);
    }

    public static void error(String tag, String message, Throwable e) {
        if (shouldLog()) {
            Log.e(tag, message, e);
        }
    }

    protected static boolean shouldLog() {
        CapConfig capConfig = config;
        return capConfig == null || capConfig.isLoggingEnabled();
    }
}
