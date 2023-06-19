package com.getcapacitor.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/* loaded from: classes.dex */
public interface HostMask {

    /* loaded from: classes.dex */
    public static class Nothing implements HostMask {
        @Override // com.getcapacitor.util.HostMask
        public boolean matches(String host) {
            return false;
        }
    }

    boolean matches(String host);

    /* loaded from: classes.dex */
    public static class Parser {
        private static HostMask NOTHING = new Nothing();

        public static HostMask parse(String[] masks) {
            return masks == null ? NOTHING : Any.parse(masks);
        }

        public static HostMask parse(String mask) {
            return mask == null ? NOTHING : Simple.parse(mask);
        }
    }

    /* loaded from: classes.dex */
    public static class Simple implements HostMask {
        private final List<String> maskParts;

        private Simple(List<String> maskParts) {
            if (maskParts == null) {
                throw new IllegalArgumentException("Mask parts can not be null");
            }
            this.maskParts = maskParts;
        }

        static Simple parse(String mask) {
            return new Simple(Util.splitAndReverse(mask));
        }

        @Override // com.getcapacitor.util.HostMask
        public boolean matches(String host) {
            if (host == null) {
                return false;
            }
            List<String> splitAndReverse = Util.splitAndReverse(host);
            int size = splitAndReverse.size();
            int size2 = this.maskParts.size();
            if (size2 <= 1 || size == size2) {
                int min = Math.min(size, size2);
                for (int i = 0; i < min; i++) {
                    if (!Util.matches(this.maskParts.get(i), splitAndReverse.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class Any implements HostMask {
        private final List<? extends HostMask> masks;

        Any(List<? extends HostMask> masks) {
            this.masks = masks;
        }

        @Override // com.getcapacitor.util.HostMask
        public boolean matches(String host) {
            for (HostMask hostMask : this.masks) {
                if (hostMask.matches(host)) {
                    return true;
                }
            }
            return false;
        }

        static Any parse(String... rawMasks) {
            ArrayList arrayList = new ArrayList();
            for (String str : rawMasks) {
                arrayList.add(Simple.parse(str));
            }
            return new Any(arrayList);
        }
    }

    /* loaded from: classes.dex */
    public static class Util {
        static boolean matches(String mask, String string) {
            if (mask == null) {
                return false;
            }
            if ("*".equals(mask)) {
                return true;
            }
            if (string == null) {
                return false;
            }
            return mask.toUpperCase().equals(string.toUpperCase());
        }

        static List<String> splitAndReverse(String string) {
            if (string == null) {
                throw new IllegalArgumentException("Can not split null argument");
            }
            List<String> asList = Arrays.asList(string.split("\\."));
            Collections.reverse(asList);
            return asList;
        }
    }
}
