package com.getcapacitor;

import java.util.ArrayList;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class UriMatcher {
    private static final int EXACT = 0;
    private static final int MASK = 3;
    static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("/");
    private static final int REST = 2;
    private static final int TEXT = 1;
    private ArrayList<UriMatcher> mChildren;
    private Object mCode;
    private String mText;
    private int mWhich;

    public UriMatcher(Object code) {
        this.mCode = code;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    private UriMatcher() {
        this.mCode = null;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    public void addURI(String scheme, String authority, String path, Object code) {
        String str;
        String str2 = path;
        if (code == null) {
            throw new IllegalArgumentException("Code can't be null");
        }
        String[] strArr = null;
        if (str2 != null) {
            if (!path.isEmpty() && str2.charAt(0) == '/') {
                str2 = str2.substring(1);
            }
            strArr = PATH_SPLIT_PATTERN.split(str2);
        }
        int length = strArr != null ? strArr.length : 0;
        UriMatcher uriMatcher = this;
        int i = -2;
        while (i < length) {
            if (i == -2) {
                str = scheme;
            } else {
                str = i == -1 ? authority : strArr[i];
            }
            ArrayList<UriMatcher> arrayList = uriMatcher.mChildren;
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                UriMatcher uriMatcher2 = arrayList.get(i2);
                if (str.equals(uriMatcher2.mText)) {
                    uriMatcher = uriMatcher2;
                    break;
                }
                i2++;
            }
            if (i2 == size) {
                UriMatcher uriMatcher3 = new UriMatcher();
                if (i == -1 && str.contains("*")) {
                    uriMatcher3.mWhich = 3;
                } else if (str.equals("**")) {
                    uriMatcher3.mWhich = 2;
                } else if (str.equals("*")) {
                    uriMatcher3.mWhich = 1;
                } else {
                    uriMatcher3.mWhich = 0;
                }
                uriMatcher3.mText = str;
                uriMatcher.mChildren.add(uriMatcher3);
                uriMatcher = uriMatcher3;
            }
            i++;
        }
        uriMatcher.mCode = code;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0059, code lost:
        if (com.getcapacitor.util.HostMask.Parser.parse(r10.mText).matches(r5) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0065, code lost:
        if (r10.mText.equals(r5) != false) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006b A[LOOP:1: B:20:0x0039->B:40:0x006b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x006e A[EDGE_INSN: B:51:0x006e->B:41:0x006e ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object match(android.net.Uri r14) {
        /*
            r13 = this;
            java.util.List r0 = r14.getPathSegments()
            int r1 = r0.size()
            if (r1 != 0) goto L13
            java.lang.String r2 = r14.getAuthority()
            if (r2 != 0) goto L13
            java.lang.Object r14 = r13.mCode
            return r14
        L13:
            r2 = -2
            r4 = r13
            r3 = r2
        L16:
            if (r3 >= r1) goto L75
            if (r3 != r2) goto L1f
            java.lang.String r5 = r14.getScheme()
            goto L2d
        L1f:
            r5 = -1
            if (r3 != r5) goto L27
            java.lang.String r5 = r14.getAuthority()
            goto L2d
        L27:
            java.lang.Object r5 = r0.get(r3)
            java.lang.String r5 = (java.lang.String) r5
        L2d:
            java.util.ArrayList<com.getcapacitor.UriMatcher> r6 = r4.mChildren
            if (r6 != 0) goto L32
            goto L75
        L32:
            int r4 = r6.size()
            r7 = 0
            r8 = 0
            r9 = r8
        L39:
            if (r7 >= r4) goto L6e
            java.lang.Object r10 = r6.get(r7)
            com.getcapacitor.UriMatcher r10 = (com.getcapacitor.UriMatcher) r10
            int r11 = r10.mWhich
            if (r11 == 0) goto L5f
            r12 = 1
            if (r11 == r12) goto L67
            r12 = 2
            if (r11 == r12) goto L5c
            r12 = 3
            if (r11 == r12) goto L4f
            goto L68
        L4f:
            java.lang.String r11 = r10.mText
            com.getcapacitor.util.HostMask r11 = com.getcapacitor.util.HostMask.Parser.parse(r11)
            boolean r11 = r11.matches(r5)
            if (r11 == 0) goto L68
            goto L67
        L5c:
            java.lang.Object r14 = r10.mCode
            return r14
        L5f:
            java.lang.String r11 = r10.mText
            boolean r11 = r11.equals(r5)
            if (r11 == 0) goto L68
        L67:
            r9 = r10
        L68:
            if (r9 == 0) goto L6b
            goto L6e
        L6b:
            int r7 = r7 + 1
            goto L39
        L6e:
            r4 = r9
            if (r4 != 0) goto L72
            return r8
        L72:
            int r3 = r3 + 1
            goto L16
        L75:
            java.lang.Object r14 = r4.mCode
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getcapacitor.UriMatcher.match(android.net.Uri):java.lang.Object");
    }
}
