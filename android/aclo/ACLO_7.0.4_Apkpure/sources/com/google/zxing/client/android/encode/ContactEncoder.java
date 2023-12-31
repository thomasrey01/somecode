package com.google.zxing.client.android.encode;

import java.util.HashSet;
import java.util.List;
/* loaded from: classes.dex */
abstract class ContactEncoder {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String[] encode(List<String> list, String str, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, String str2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.isEmpty()) {
            return null;
        }
        return trim;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void append(StringBuilder sb, StringBuilder sb2, String str, String str2, Formatter formatter, char c) {
        String trim = trim(str2);
        if (trim != null) {
            sb.append(str);
            sb.append(formatter.format(trim, 0));
            sb.append(c);
            sb2.append(trim);
            sb2.append('\n');
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void appendUpToUnique(StringBuilder sb, StringBuilder sb2, String str, List<String> list, int i, Formatter formatter, Formatter formatter2, char c) {
        if (list == null) {
            return;
        }
        HashSet hashSet = new HashSet(2);
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            String trim = trim(list.get(i3));
            if (trim != null && !trim.isEmpty() && !hashSet.contains(trim)) {
                sb.append(str);
                sb.append(formatter2.format(trim, i3));
                sb.append(c);
                sb2.append(formatter == null ? trim : formatter.format(trim, i3));
                sb2.append('\n');
                i2++;
                if (i2 == i) {
                    return;
                }
                hashSet.add(trim);
            }
        }
    }
}
