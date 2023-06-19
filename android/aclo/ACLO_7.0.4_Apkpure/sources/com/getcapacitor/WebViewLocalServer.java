package com.getcapacitor;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import androidx.browser.trusted.sharing.ShareTarget;
import androidx.work.impl.Scheduler;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/* loaded from: classes.dex */
public class WebViewLocalServer {
    private static final String capacitorContentStart = "/_capacitor_content_";
    private static final String capacitorFileStart = "/_capacitor_file_";
    private final ArrayList<String> authorities;
    private String basePath;
    private final Bridge bridge;
    private final boolean html5mode;
    private boolean isAsset;
    private final JSInjector jsInjector;
    private final AndroidProtocolHandler protocolHandler;
    private final UriMatcher uriMatcher = new UriMatcher(null);

    /* loaded from: classes.dex */
    public static abstract class PathHandler {
        private String charset;
        private String encoding;
        protected String mimeType;
        private String reasonPhrase;
        private Map<String, String> responseHeaders;
        private int statusCode;

        public abstract InputStream handle(Uri url);

        public PathHandler() {
            this(null, null, Scheduler.MAX_GREEDY_SCHEDULER_LIMIT, "OK", null);
        }

        public PathHandler(String encoding, String charset, int statusCode, String reasonPhrase, Map<String, String> responseHeaders) {
            this.encoding = encoding;
            this.charset = charset;
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
            responseHeaders = responseHeaders == null ? new HashMap<>() : responseHeaders;
            responseHeaders.put("Cache-Control", "no-cache");
            this.responseHeaders = responseHeaders;
        }

        public InputStream handle(WebResourceRequest request) {
            return handle(request.getUrl());
        }

        public String getEncoding() {
            return this.encoding;
        }

        public String getCharset() {
            return this.charset;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getReasonPhrase() {
            return this.reasonPhrase;
        }

        public Map<String, String> getResponseHeaders() {
            return this.responseHeaders;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WebViewLocalServer(Context context, Bridge bridge, JSInjector jsInjector, ArrayList<String> authorities, boolean html5mode) {
        this.html5mode = html5mode;
        this.protocolHandler = new AndroidProtocolHandler(context.getApplicationContext());
        this.authorities = authorities;
        this.bridge = bridge;
        this.jsInjector = jsInjector;
    }

    private static Uri parseAndVerifyUrl(String url) {
        if (url == null) {
            return null;
        }
        Uri parse = Uri.parse(url);
        if (parse == null) {
            Logger.error("Malformed URL: " + url);
            return null;
        }
        String path = parse.getPath();
        if (path == null || path.isEmpty()) {
            Logger.error("URL does not have a path: " + url);
            return null;
        }
        return parse;
    }

    public WebResourceResponse shouldInterceptRequest(WebResourceRequest request) {
        PathHandler pathHandler;
        Uri url = request.getUrl();
        synchronized (this.uriMatcher) {
            pathHandler = (PathHandler) this.uriMatcher.match(request.getUrl());
        }
        if (pathHandler == null) {
            return null;
        }
        if (isLocalFile(url) || isMainUrl(url) || !isAllowedUrl(url)) {
            Logger.debug("Handling local request: " + request.getUrl().toString());
            return handleLocalRequest(request, pathHandler);
        }
        return handleProxyRequest(request, pathHandler);
    }

    private boolean isLocalFile(Uri uri) {
        String path = uri.getPath();
        return path.startsWith("/_capacitor_content_") || path.startsWith("/_capacitor_file_");
    }

    private boolean isMainUrl(Uri loadingUrl) {
        return this.bridge.getServerUrl() == null && loadingUrl.getHost().equalsIgnoreCase(this.bridge.getHost());
    }

    private boolean isAllowedUrl(Uri loadingUrl) {
        return this.bridge.getServerUrl() != null || this.bridge.getAppAllowNavigationMask().matches(loadingUrl.getHost());
    }

    private WebResourceResponse handleLocalRequest(WebResourceRequest request, PathHandler handler) {
        InputStream openFile;
        int i;
        String path = request.getUrl().getPath();
        if (request.getRequestHeaders().get("Range") != null) {
            LollipopLazyInputStream lollipopLazyInputStream = new LollipopLazyInputStream(handler, request);
            String mimeType = getMimeType(path, lollipopLazyInputStream);
            Map<String, String> responseHeaders = handler.getResponseHeaders();
            try {
                int available = lollipopLazyInputStream.available();
                String[] split = request.getRequestHeaders().get("Range").split("=")[1].split("-");
                String str = split[0];
                int i2 = available - 1;
                if (split.length > 1) {
                    i2 = Integer.parseInt(split[1]);
                }
                responseHeaders.put("Accept-Ranges", "bytes");
                responseHeaders.put("Content-Range", "bytes " + str + "-" + i2 + "/" + available);
                i = 206;
            } catch (IOException unused) {
                i = 404;
            }
            return new WebResourceResponse(mimeType, handler.getEncoding(), i, handler.getReasonPhrase(), responseHeaders, lollipopLazyInputStream);
        } else if (isLocalFile(request.getUrl())) {
            LollipopLazyInputStream lollipopLazyInputStream2 = new LollipopLazyInputStream(handler, request);
            return new WebResourceResponse(getMimeType(request.getUrl().getPath(), lollipopLazyInputStream2), handler.getEncoding(), getStatusCode(lollipopLazyInputStream2, handler.getStatusCode()), handler.getReasonPhrase(), handler.getResponseHeaders(), lollipopLazyInputStream2);
        } else if (path.equals("/cordova.js")) {
            return new WebResourceResponse("application/javascript", handler.getEncoding(), handler.getStatusCode(), handler.getReasonPhrase(), handler.getResponseHeaders(), null);
        } else {
            if (path.equals("/") || (!request.getUrl().getLastPathSegment().contains(".") && this.html5mode)) {
                try {
                    String str2 = this.basePath + "/index.html";
                    if (this.bridge.getRouteProcessor() != null) {
                        ProcessedRoute process = this.bridge.getRouteProcessor().process(this.basePath, "/index.html");
                        String path2 = process.getPath();
                        this.isAsset = process.isAsset();
                        str2 = path2;
                    }
                    if (this.isAsset) {
                        openFile = this.protocolHandler.openAsset(str2);
                    } else {
                        openFile = this.protocolHandler.openFile(str2);
                    }
                    InputStream injectedStream = this.jsInjector.getInjectedStream(openFile);
                    return new WebResourceResponse("text/html", handler.getEncoding(), getStatusCode(injectedStream, handler.getStatusCode()), handler.getReasonPhrase(), handler.getResponseHeaders(), injectedStream);
                } catch (IOException e) {
                    Logger.error("Unable to open index.html", e);
                    return null;
                }
            }
            if ("/favicon.ico".equalsIgnoreCase(path)) {
                try {
                    return new WebResourceResponse("image/png", null, null);
                } catch (Exception e2) {
                    Logger.error("favicon handling failed", e2);
                }
            }
            if (path.lastIndexOf(".") >= 0) {
                String substring = path.substring(path.lastIndexOf("."));
                InputStream lollipopLazyInputStream3 = new LollipopLazyInputStream(handler, request);
                if (substring.equals(".html")) {
                    lollipopLazyInputStream3 = this.jsInjector.getInjectedStream(lollipopLazyInputStream3);
                }
                InputStream inputStream = lollipopLazyInputStream3;
                return new WebResourceResponse(getMimeType(path, inputStream), handler.getEncoding(), getStatusCode(inputStream, handler.getStatusCode()), handler.getReasonPhrase(), handler.getResponseHeaders(), inputStream);
            }
            return null;
        }
    }

    private WebResourceResponse handleProxyRequest(WebResourceRequest request, PathHandler handler) {
        String method = request.getMethod();
        if (method.equals(ShareTarget.METHOD_GET)) {
            try {
                String uri = request.getUrl().toString();
                Map<String, String> requestHeaders = request.getRequestHeaders();
                boolean z = false;
                Iterator<Map.Entry<String, String>> it = requestHeaders.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry<String, String> next = it.next();
                    if (next.getKey().equalsIgnoreCase("Accept") && next.getValue().toLowerCase().contains("text/html")) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri).openConnection();
                    for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                    String cookie = CookieManager.getInstance().getCookie(uri);
                    if (cookie != null) {
                        httpURLConnection.setRequestProperty("Cookie", cookie);
                    }
                    httpURLConnection.setRequestMethod(method);
                    httpURLConnection.setReadTimeout(30000);
                    httpURLConnection.setConnectTimeout(30000);
                    if (request.getUrl().getUserInfo() != null) {
                        String encodeToString = Base64.encodeToString(request.getUrl().getUserInfo().getBytes(StandardCharsets.UTF_8), 2);
                        httpURLConnection.setRequestProperty("Authorization", "Basic " + encodeToString);
                    }
                    String headerField = httpURLConnection.getHeaderField("Set-Cookie");
                    if (headerField != null) {
                        CookieManager.getInstance().setCookie(uri, headerField);
                    }
                    return new WebResourceResponse("text/html", handler.getEncoding(), handler.getStatusCode(), handler.getReasonPhrase(), handler.getResponseHeaders(), this.jsInjector.getInjectedStream(httpURLConnection.getInputStream()));
                }
                return null;
            } catch (Exception e) {
                this.bridge.handleAppUrlLoadError(e);
                return null;
            }
        }
        return null;
    }

    private String getMimeType(String path, InputStream stream) {
        String str;
        try {
            String guessContentTypeFromName = URLConnection.guessContentTypeFromName(path);
            if (guessContentTypeFromName != null) {
                try {
                    if (path.endsWith(".js") && guessContentTypeFromName.equals("image/x-icon")) {
                        Logger.debug("We shouldn't be here");
                    }
                } catch (Exception e) {
                    e = e;
                    Logger.error("Unable to get mime type" + path, e);
                    return str;
                }
            }
            if (guessContentTypeFromName == null) {
                if (!path.endsWith(".js") && !path.endsWith(".mjs")) {
                    if (path.endsWith(".wasm")) {
                        return "application/wasm";
                    }
                    str = URLConnection.guessContentTypeFromStream(stream);
                    return str;
                }
                return "application/javascript";
            }
            return guessContentTypeFromName;
        } catch (Exception e2) {
            e = e2;
            str = null;
        }
    }

    private int getStatusCode(InputStream stream, int defaultCode) {
        try {
            if (stream.available() == -1) {
                return 404;
            }
            return defaultCode;
        } catch (IOException unused) {
            return 500;
        }
    }

    void register(Uri uri, PathHandler handler) {
        synchronized (this.uriMatcher) {
            this.uriMatcher.addURI(uri.getScheme(), uri.getAuthority(), uri.getPath(), handler);
        }
    }

    public void hostAssets(String assetPath) {
        this.isAsset = true;
        this.basePath = assetPath;
        createHostingDetails();
    }

    public void hostFiles(final String basePath) {
        this.isAsset = false;
        this.basePath = basePath;
        createHostingDetails();
    }

    private void createHostingDetails() {
        final String str = this.basePath;
        if (str.indexOf(42) != -1) {
            throw new IllegalArgumentException("assetPath cannot contain the '*' character.");
        }
        PathHandler pathHandler = new PathHandler() { // from class: com.getcapacitor.WebViewLocalServer.1
            @Override // com.getcapacitor.WebViewLocalServer.PathHandler
            public InputStream handle(Uri url) {
                String path = url.getPath();
                RouteProcessor routeProcessor = WebViewLocalServer.this.bridge.getRouteProcessor();
                if (routeProcessor != null) {
                    ProcessedRoute process = WebViewLocalServer.this.bridge.getRouteProcessor().process("", path);
                    String path2 = process.getPath();
                    WebViewLocalServer.this.isAsset = process.isAsset();
                    path = path2;
                }
                try {
                    if (path.startsWith("/_capacitor_content_")) {
                        return WebViewLocalServer.this.protocolHandler.openContentUrl(url);
                    }
                    if (path.startsWith("/_capacitor_file_")) {
                        return WebViewLocalServer.this.protocolHandler.openFile(path);
                    }
                    if (!WebViewLocalServer.this.isAsset) {
                        if (routeProcessor == null) {
                            path = WebViewLocalServer.this.basePath + url.getPath();
                        }
                        return WebViewLocalServer.this.protocolHandler.openFile(path);
                    }
                    return WebViewLocalServer.this.protocolHandler.openAsset(str + path);
                } catch (IOException unused) {
                    Logger.error("Unable to open asset URL: " + url);
                    return null;
                }
            }
        };
        Iterator<String> it = this.authorities.iterator();
        while (it.hasNext()) {
            String next = it.next();
            registerUriForScheme(Bridge.CAPACITOR_HTTP_SCHEME, pathHandler, next);
            registerUriForScheme(Bridge.CAPACITOR_HTTPS_SCHEME, pathHandler, next);
            String scheme = this.bridge.getScheme();
            if (!scheme.equals(Bridge.CAPACITOR_HTTP_SCHEME) && !scheme.equals(Bridge.CAPACITOR_HTTPS_SCHEME)) {
                registerUriForScheme(scheme, pathHandler, next);
            }
        }
    }

    private void registerUriForScheme(String scheme, PathHandler handler, String authority) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme);
        builder.authority(authority);
        builder.path("");
        Uri build = builder.build();
        register(Uri.withAppendedPath(build, "/"), handler);
        register(Uri.withAppendedPath(build, "**"), handler);
    }

    /* loaded from: classes.dex */
    private static abstract class LazyInputStream extends InputStream {
        protected final PathHandler handler;
        private InputStream is = null;

        protected abstract InputStream handle();

        public LazyInputStream(PathHandler handler) {
            this.handler = handler;
        }

        private InputStream getInputStream() {
            if (this.is == null) {
                this.is = handle();
            }
            return this.is;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                return inputStream.available();
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                return inputStream.read();
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] b) throws IOException {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                return inputStream.read(b);
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] b, int off, int len) throws IOException {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                return inputStream.read(b, off, len);
            }
            return -1;
        }

        @Override // java.io.InputStream
        public long skip(long n) throws IOException {
            InputStream inputStream = getInputStream();
            if (inputStream != null) {
                return inputStream.skip(n);
            }
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class LollipopLazyInputStream extends LazyInputStream {
        private InputStream is;
        private WebResourceRequest request;

        public LollipopLazyInputStream(PathHandler handler, WebResourceRequest request) {
            super(handler);
            this.request = request;
        }

        @Override // com.getcapacitor.WebViewLocalServer.LazyInputStream
        protected InputStream handle() {
            return this.handler.handle(this.request);
        }
    }

    public String getBasePath() {
        return this.basePath;
    }
}
