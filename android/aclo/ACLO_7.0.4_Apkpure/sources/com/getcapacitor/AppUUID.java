package com.getcapacitor;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import kotlin.UByte;
/* loaded from: classes.dex */
public final class AppUUID {
    private static final String KEY = "CapacitorAppUUID";

    public static String getAppUUID(AppCompatActivity activity) throws Exception {
        assertAppUUID(activity);
        return readUUID(activity);
    }

    public static void regenerateAppUUID(AppCompatActivity activity) throws Exception {
        try {
            writeUUID(activity, generateUUID());
        } catch (NoSuchAlgorithmException unused) {
            throw new Exception("Capacitor App UUID could not be generated.");
        }
    }

    private static void assertAppUUID(AppCompatActivity activity) throws Exception {
        if (readUUID(activity).equals("")) {
            regenerateAppUUID(activity);
        }
    }

    private static String generateUUID() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        return bytesToHex(messageDigest.digest());
    }

    private static String readUUID(AppCompatActivity activity) {
        return activity.getPreferences(0).getString(KEY, "");
    }

    private static void writeUUID(AppCompatActivity activity, String uuid) {
        SharedPreferences.Editor edit = activity.getPreferences(0).edit();
        edit.putString(KEY, uuid);
        edit.apply();
    }

    private static String bytesToHex(byte[] bytes) {
        byte[] bytes2 = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
        byte[] bArr = new byte[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int i2 = bytes[i] & UByte.MAX_VALUE;
            int i3 = i * 2;
            bArr[i3] = bytes2[i2 >>> 4];
            bArr[i3 + 1] = bytes2[i2 & 15];
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }
}
