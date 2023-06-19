package com.getcapacitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
/* loaded from: classes.dex */
class JSInjector {
    private String bridgeJS;
    private String cordovaJS;
    private String cordovaPluginsFileJS;
    private String cordovaPluginsJS;
    private String globalJS;
    private String localUrlJS;
    private String pluginJS;

    public JSInjector(String globalJS, String bridgeJS, String pluginJS, String cordovaJS, String cordovaPluginsJS, String cordovaPluginsFileJS, String localUrlJS) {
        this.globalJS = globalJS;
        this.bridgeJS = bridgeJS;
        this.pluginJS = pluginJS;
        this.cordovaJS = cordovaJS;
        this.cordovaPluginsJS = cordovaPluginsJS;
        this.cordovaPluginsFileJS = cordovaPluginsFileJS;
        this.localUrlJS = localUrlJS;
    }

    public String getScriptString() {
        return this.globalJS + "\n\n" + this.localUrlJS + "\n\n" + this.bridgeJS + "\n\n" + this.pluginJS + "\n\n" + this.cordovaJS + "\n\n" + this.cordovaPluginsFileJS + "\n\n" + this.cordovaPluginsJS;
    }

    public InputStream getInjectedStream(InputStream responseStream) {
        String str = "<script type=\"text/javascript\">" + getScriptString() + "</script>";
        String readAssetStream = readAssetStream(responseStream);
        if (readAssetStream.contains("<head>")) {
            readAssetStream = readAssetStream.replace("<head>", "<head>\n" + str + "\n");
        } else if (readAssetStream.contains("</head>")) {
            readAssetStream = readAssetStream.replace("</head>", str + "\n</head>");
        } else {
            Logger.error("Unable to inject Capacitor, Plugins won't work");
        }
        return new ByteArrayInputStream(readAssetStream.getBytes(StandardCharsets.UTF_8));
    }

    private String readAssetStream(InputStream stream) {
        try {
            char[] cArr = new char[1024];
            StringBuilder sb = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            while (true) {
                int read = inputStreamReader.read(cArr, 0, 1024);
                if (read >= 0) {
                    sb.append(cArr, 0, read);
                } else {
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            Logger.error("Unable to process HTML asset file. This is a fatal error", e);
            return "";
        }
    }
}
