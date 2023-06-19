package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/* loaded from: classes.dex */
public final class PackageInfoCompat {
    public static long getLongVersionCode(PackageInfo info) {
        if (Build.VERSION.SDK_INT >= 28) {
            return info.getLongVersionCode();
        }
        return info.versionCode;
    }

    public static List<Signature> getSignatures(PackageManager packageManager, String packageName) throws PackageManager.NameNotFoundException {
        Signature[] signatureArr;
        if (Build.VERSION.SDK_INT >= 28) {
            SigningInfo signingInfo = packageManager.getPackageInfo(packageName, 134217728).signingInfo;
            if (Api28Impl.hasMultipleSigners(signingInfo)) {
                signatureArr = Api28Impl.getApkContentsSigners(signingInfo);
            } else {
                signatureArr = Api28Impl.getSigningCertificateHistory(signingInfo);
            }
        } else {
            signatureArr = packageManager.getPackageInfo(packageName, 64).signatures;
        }
        if (signatureArr == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(signatureArr);
    }

    public static boolean hasSignatures(PackageManager packageManager, String packageName, Map<byte[], Integer> certificatesAndType, boolean matchExact) throws PackageManager.NameNotFoundException {
        if (certificatesAndType.isEmpty()) {
            return false;
        }
        Set<byte[]> keySet = certificatesAndType.keySet();
        for (byte[] bArr : keySet) {
            if (bArr == null) {
                throw new IllegalArgumentException("Cert byte array cannot be null when verifying " + packageName);
            }
            Integer num = certificatesAndType.get(bArr);
            if (num == null) {
                throw new IllegalArgumentException("Type must be specified for cert when verifying " + packageName);
            }
            int intValue = num.intValue();
            if (intValue != 0 && intValue != 1) {
                throw new IllegalArgumentException("Unsupported certificate type " + num + " when verifying " + packageName);
            }
        }
        List<Signature> signatures = getSignatures(packageManager, packageName);
        if (!matchExact && Build.VERSION.SDK_INT >= 28) {
            for (byte[] bArr2 : keySet) {
                if (!Api28Impl.hasSigningCertificate(packageManager, packageName, bArr2, certificatesAndType.get(bArr2).intValue())) {
                    return false;
                }
            }
            return true;
        }
        if (signatures.size() != 0 && certificatesAndType.size() <= signatures.size() && (!matchExact || certificatesAndType.size() == signatures.size())) {
            byte[][] bArr3 = null;
            if (certificatesAndType.containsValue(1)) {
                bArr3 = new byte[signatures.size()];
                for (int i = 0; i < signatures.size(); i++) {
                    bArr3[i] = computeSHA256Digest(signatures.get(i).toByteArray());
                }
            }
            Iterator<byte[]> it = keySet.iterator();
            if (it.hasNext()) {
                byte[] next = it.next();
                Integer num2 = certificatesAndType.get(next);
                int intValue2 = num2.intValue();
                if (intValue2 != 0) {
                    if (intValue2 == 1) {
                        if (!byteArrayContains(bArr3, next)) {
                            return false;
                        }
                    } else {
                        throw new IllegalArgumentException("Unsupported certificate type " + num2);
                    }
                } else if (!signatures.contains(new Signature(next))) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean byteArrayContains(byte[][] array, byte[] expected) {
        for (byte[] bArr : array) {
            if (Arrays.equals(expected, bArr)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] computeSHA256Digest(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Device doesn't support SHA256 cert checking", e);
        }
    }

    private PackageInfoCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class Api28Impl {
        private Api28Impl() {
        }

        static boolean hasSigningCertificate(PackageManager packageManager, String packageName, byte[] bytes, int type) {
            return packageManager.hasSigningCertificate(packageName, bytes, type);
        }

        static boolean hasMultipleSigners(SigningInfo signingInfo) {
            return signingInfo.hasMultipleSigners();
        }

        static Signature[] getApkContentsSigners(SigningInfo signingInfo) {
            return signingInfo.getApkContentsSigners();
        }

        static Signature[] getSigningCertificateHistory(SigningInfo signingInfo) {
            return signingInfo.getSigningCertificateHistory();
        }
    }
}
