package com.google.zxing.client.android;

import android.util.Log;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
/* loaded from: classes.dex */
public final class HttpHelper {
    private static final Collection<String> REDIRECTOR_DOMAINS = new HashSet(Arrays.asList("amzn.to", "bit.ly", "bitly.com", "fb.me", "goo.gl", "is.gd", "j.mp", "lnkd.in", "ow.ly", "R.BEETAGG.COM", "r.beetagg.com", "SCN.BY", "su.pr", "t.co", "tinyurl.com", "tr.im"));
    private static final String TAG = "HttpHelper";

    /* loaded from: classes.dex */
    public enum ContentType {
        HTML,
        JSON,
        XML,
        TEXT
    }

    private HttpHelper() {
    }

    public static CharSequence downloadViaHttp(String str, ContentType contentType) throws IOException {
        return downloadViaHttp(str, contentType, Integer.MAX_VALUE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.zxing.client.android.HttpHelper$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType;

        static {
            int[] iArr = new int[ContentType.values().length];
            $SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType = iArr;
            try {
                iArr[ContentType.HTML.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType[ContentType.JSON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType[ContentType.XML.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType[ContentType.TEXT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static CharSequence downloadViaHttp(String str, ContentType contentType, int i) throws IOException {
        int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$client$android$HttpHelper$ContentType[contentType.ordinal()];
        return downloadViaHttp(str, i2 != 1 ? i2 != 2 ? i2 != 3 ? "text/*,*/*" : "application/xml,text/*,*/*" : "application/json,text/*,*/*" : "application/xhtml+xml,text/html,text/*,*/*", i);
    }

    private static CharSequence downloadViaHttp(String str, String str2, int i) throws IOException {
        int i2 = 0;
        while (i2 < 5) {
            HttpURLConnection safelyOpenConnection = safelyOpenConnection(new URL(str));
            safelyOpenConnection.setInstanceFollowRedirects(true);
            safelyOpenConnection.setRequestProperty("Accept", str2);
            safelyOpenConnection.setRequestProperty("Accept-Charset", "utf-8,*");
            safelyOpenConnection.setRequestProperty("User-Agent", "ZXing (Android)");
            try {
                int safelyConnect = safelyConnect(safelyOpenConnection);
                if (safelyConnect == 200) {
                    return consume(safelyOpenConnection, i);
                }
                if (safelyConnect == 302) {
                    String headerField = safelyOpenConnection.getHeaderField("Location");
                    if (headerField == null) {
                        throw new IOException("No Location");
                    }
                    i2++;
                    safelyOpenConnection.disconnect();
                    str = headerField;
                } else {
                    throw new IOException("Bad HTTP response: " + safelyConnect);
                }
            } finally {
                safelyOpenConnection.disconnect();
            }
        }
        throw new IOException("Too many redirects");
    }

    private static String getEncoding(URLConnection uRLConnection) {
        int indexOf;
        String headerField = uRLConnection.getHeaderField("Content-Type");
        return (headerField == null || (indexOf = headerField.indexOf("charset=")) < 0) ? "UTF-8" : headerField.substring(indexOf + 8);
    }

    private static CharSequence consume(URLConnection uRLConnection, int i) throws IOException {
        int read;
        String encoding = getEncoding(uRLConnection);
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        try {
            InputStreamReader inputStreamReader2 = new InputStreamReader(uRLConnection.getInputStream(), encoding);
            try {
                char[] cArr = new char[1024];
                while (sb.length() < i && (read = inputStreamReader2.read(cArr)) > 0) {
                    sb.append(cArr, 0, read);
                }
                try {
                    inputStreamReader2.close();
                } catch (IOException | NullPointerException unused) {
                }
                return sb;
            } catch (Throwable th) {
                th = th;
                inputStreamReader = inputStreamReader2;
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException | NullPointerException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static URI unredirect(URI uri) throws IOException {
        if (REDIRECTOR_DOMAINS.contains(uri.getHost())) {
            HttpURLConnection safelyOpenConnection = safelyOpenConnection(uri.toURL());
            safelyOpenConnection.setInstanceFollowRedirects(false);
            safelyOpenConnection.setDoInput(false);
            safelyOpenConnection.setRequestMethod("HEAD");
            safelyOpenConnection.setRequestProperty("User-Agent", "ZXing (Android)");
            try {
                int safelyConnect = safelyConnect(safelyOpenConnection);
                if (safelyConnect != 307) {
                    switch (safelyConnect) {
                        case 300:
                        case 301:
                        case 302:
                        case 303:
                            break;
                        default:
                            return uri;
                    }
                }
                String headerField = safelyOpenConnection.getHeaderField("Location");
                if (headerField != null) {
                    try {
                        return new URI(headerField);
                    } catch (URISyntaxException unused) {
                    }
                }
                return uri;
            } finally {
                safelyOpenConnection.disconnect();
            }
        }
        return uri;
    }

    private static HttpURLConnection safelyOpenConnection(URL url) throws IOException {
        try {
            URLConnection openConnection = url.openConnection();
            if (!(openConnection instanceof HttpURLConnection)) {
                throw new IOException();
            }
            return (HttpURLConnection) openConnection;
        } catch (NullPointerException e) {
            String str = TAG;
            Log.w(str, "Bad URI? " + url);
            throw new IOException(e);
        }
    }

    private static int safelyConnect(HttpURLConnection httpURLConnection) throws IOException {
        try {
            httpURLConnection.connect();
            try {
                return httpURLConnection.getResponseCode();
            } catch (IllegalArgumentException | NullPointerException | StringIndexOutOfBoundsException e) {
                throw new IOException(e);
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException | NullPointerException | SecurityException e2) {
            throw new IOException(e2);
        }
    }
}
