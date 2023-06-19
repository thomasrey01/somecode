package org.apache.cordova;

import android.net.Uri;
import com.getcapacitor.Bridge;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class Whitelist {
    public static final String TAG = "Whitelist";
    private ArrayList<URLPattern> whiteList = new ArrayList<>();

    /* loaded from: classes.dex */
    private static class URLPattern {
        public Pattern host;
        public Pattern path;
        public Integer port;
        public Pattern scheme;

        private String regexFromPattern(String str, boolean z) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '*' && z) {
                    sb.append(".");
                } else if ("\\.[]{}()^$?+|".indexOf(charAt) > -1) {
                    sb.append('\\');
                }
                sb.append(charAt);
            }
            return sb.toString();
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0024 A[Catch: NumberFormatException -> 0x008b, TryCatch #0 {NumberFormatException -> 0x008b, blocks: (B:4:0x000a, B:7:0x0011, B:9:0x001e, B:11:0x0024, B:17:0x005b, B:20:0x0062, B:23:0x0073, B:26:0x007c, B:27:0x0088, B:21:0x006f, B:12:0x0027, B:14:0x002f, B:15:0x004f, B:8:0x001c), top: B:31:0x000a }] */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0027 A[Catch: NumberFormatException -> 0x008b, TryCatch #0 {NumberFormatException -> 0x008b, blocks: (B:4:0x000a, B:7:0x0011, B:9:0x001e, B:11:0x0024, B:17:0x005b, B:20:0x0062, B:23:0x0073, B:26:0x007c, B:27:0x0088, B:21:0x006f, B:12:0x0027, B:14:0x002f, B:15:0x004f, B:8:0x001c), top: B:31:0x000a }] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0073 A[Catch: NumberFormatException -> 0x008b, TryCatch #0 {NumberFormatException -> 0x008b, blocks: (B:4:0x000a, B:7:0x0011, B:9:0x001e, B:11:0x0024, B:17:0x005b, B:20:0x0062, B:23:0x0073, B:26:0x007c, B:27:0x0088, B:21:0x006f, B:12:0x0027, B:14:0x002f, B:15:0x004f, B:8:0x001c), top: B:31:0x000a }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public URLPattern(java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) throws java.net.MalformedURLException {
            /*
                r5 = this;
                r5.<init>()
                r0 = 0
                java.lang.String r1 = "*"
                r2 = 2
                r3 = 0
                if (r6 == 0) goto L1c
                boolean r4 = r1.equals(r6)     // Catch: java.lang.NumberFormatException -> L8b
                if (r4 == 0) goto L11
                goto L1c
            L11:
                java.lang.String r6 = r5.regexFromPattern(r6, r0)     // Catch: java.lang.NumberFormatException -> L8b
                java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6, r2)     // Catch: java.lang.NumberFormatException -> L8b
                r5.scheme = r6     // Catch: java.lang.NumberFormatException -> L8b
                goto L1e
            L1c:
                r5.scheme = r3     // Catch: java.lang.NumberFormatException -> L8b
            L1e:
                boolean r6 = r1.equals(r7)     // Catch: java.lang.NumberFormatException -> L8b
                if (r6 == 0) goto L27
                r5.host = r3     // Catch: java.lang.NumberFormatException -> L8b
                goto L59
            L27:
                java.lang.String r6 = "*."
                boolean r6 = r7.startsWith(r6)     // Catch: java.lang.NumberFormatException -> L8b
                if (r6 == 0) goto L4f
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L8b
                r6.<init>()     // Catch: java.lang.NumberFormatException -> L8b
                java.lang.String r4 = "([a-z0-9.-]*\\.)?"
                r6.append(r4)     // Catch: java.lang.NumberFormatException -> L8b
                java.lang.String r7 = r7.substring(r2)     // Catch: java.lang.NumberFormatException -> L8b
                java.lang.String r7 = r5.regexFromPattern(r7, r0)     // Catch: java.lang.NumberFormatException -> L8b
                r6.append(r7)     // Catch: java.lang.NumberFormatException -> L8b
                java.lang.String r6 = r6.toString()     // Catch: java.lang.NumberFormatException -> L8b
                java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6, r2)     // Catch: java.lang.NumberFormatException -> L8b
                r5.host = r6     // Catch: java.lang.NumberFormatException -> L8b
                goto L59
            L4f:
                java.lang.String r6 = r5.regexFromPattern(r7, r0)     // Catch: java.lang.NumberFormatException -> L8b
                java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6, r2)     // Catch: java.lang.NumberFormatException -> L8b
                r5.host = r6     // Catch: java.lang.NumberFormatException -> L8b
            L59:
                if (r8 == 0) goto L6f
                boolean r6 = r1.equals(r8)     // Catch: java.lang.NumberFormatException -> L8b
                if (r6 == 0) goto L62
                goto L6f
            L62:
                r6 = 10
                int r6 = java.lang.Integer.parseInt(r8, r6)     // Catch: java.lang.NumberFormatException -> L8b
                java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.NumberFormatException -> L8b
                r5.port = r6     // Catch: java.lang.NumberFormatException -> L8b
                goto L71
            L6f:
                r5.port = r3     // Catch: java.lang.NumberFormatException -> L8b
            L71:
                if (r9 == 0) goto L88
                java.lang.String r6 = "/*"
                boolean r6 = r6.equals(r9)     // Catch: java.lang.NumberFormatException -> L8b
                if (r6 == 0) goto L7c
                goto L88
            L7c:
                r6 = 1
                java.lang.String r6 = r5.regexFromPattern(r9, r6)     // Catch: java.lang.NumberFormatException -> L8b
                java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch: java.lang.NumberFormatException -> L8b
                r5.path = r6     // Catch: java.lang.NumberFormatException -> L8b
                goto L8a
            L88:
                r5.path = r3     // Catch: java.lang.NumberFormatException -> L8b
            L8a:
                return
            L8b:
                java.net.MalformedURLException r6 = new java.net.MalformedURLException
                java.lang.String r7 = "Port must be a number"
                r6.<init>(r7)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.cordova.Whitelist.URLPattern.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
        }

        public boolean matches(Uri uri) {
            try {
                Pattern pattern = this.scheme;
                if (pattern == null || pattern.matcher(uri.getScheme()).matches()) {
                    Pattern pattern2 = this.host;
                    if (pattern2 == null || pattern2.matcher(uri.getHost()).matches()) {
                        Integer num = this.port;
                        if (num == null || num.equals(Integer.valueOf(uri.getPort()))) {
                            Pattern pattern3 = this.path;
                            if (pattern3 != null) {
                                if (!pattern3.matcher(uri.getPath()).matches()) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            } catch (Exception e) {
                LOG.d(Whitelist.TAG, e.toString());
                return false;
            }
        }
    }

    public void addWhiteListEntry(String str, boolean z) {
        String str2 = "*";
        if (this.whiteList != null) {
            try {
                if (str.compareTo("*") == 0) {
                    LOG.d(TAG, "Unlimited access to network resources");
                    this.whiteList = null;
                    return;
                }
                Matcher matcher = Pattern.compile("^((\\*|[A-Za-z-]+):(//)?)?(\\*|((\\*\\.)?[^*/:]+))?(:(\\d+))?(/.*)?").matcher(str);
                if (matcher.matches()) {
                    String group = matcher.group(2);
                    String group2 = matcher.group(4);
                    if ((!"file".equals(group) && !FirebaseAnalytics.Param.CONTENT.equals(group)) || group2 != null) {
                        str2 = group2;
                    }
                    String group3 = matcher.group(8);
                    String group4 = matcher.group(9);
                    if (group == null) {
                        this.whiteList.add(new URLPattern(Bridge.CAPACITOR_HTTP_SCHEME, str2, group3, group4));
                        this.whiteList.add(new URLPattern(Bridge.CAPACITOR_HTTPS_SCHEME, str2, group3, group4));
                        return;
                    }
                    this.whiteList.add(new URLPattern(group, str2, group3, group4));
                }
            } catch (Exception unused) {
                LOG.d(TAG, "Failed to add origin %s", str);
            }
        }
    }

    public boolean isUrlWhiteListed(String str) {
        if (this.whiteList == null) {
            return true;
        }
        Uri parse = Uri.parse(str);
        Iterator<URLPattern> it = this.whiteList.iterator();
        while (it.hasNext()) {
            if (it.next().matches(parse)) {
                return true;
            }
        }
        return false;
    }
}
