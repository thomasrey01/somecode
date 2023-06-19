package com.whitestein.securestorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyChain;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
/* loaded from: classes.dex */
public class PasswordStorageHelper {
    private static final String LOG_TAG = "PasswordStorageHelper";
    private static final String PREFERENCES_FILE = "cap_sec";
    private PasswordStorageImpl passwordStorage;

    /* loaded from: classes.dex */
    private interface PasswordStorageImpl {
        void clear();

        byte[] getData(String key);

        boolean init(Context context);

        String[] keys();

        void remove(String key);

        void setData(String key, byte[] data);
    }

    public PasswordStorageHelper(Context context) {
        this.passwordStorage = null;
        if (Build.VERSION.SDK_INT < 18) {
            this.passwordStorage = new PasswordStorageHelper_SDK16();
        } else {
            this.passwordStorage = new PasswordStorageHelper_SDK18();
        }
        boolean z = false;
        try {
            z = this.passwordStorage.init(context);
        } catch (Exception e) {
            String str = LOG_TAG;
            Log.e(str, "PasswordStorage initialisation error:" + e.getMessage(), e);
        }
        if (z || !(this.passwordStorage instanceof PasswordStorageHelper_SDK18)) {
            return;
        }
        PasswordStorageHelper_SDK16 passwordStorageHelper_SDK16 = new PasswordStorageHelper_SDK16();
        this.passwordStorage = passwordStorageHelper_SDK16;
        passwordStorageHelper_SDK16.init(context);
    }

    public void setData(String key, byte[] data) {
        this.passwordStorage.setData(key, data);
    }

    public byte[] getData(String key) {
        return this.passwordStorage.getData(key);
    }

    public String[] keys() {
        return this.passwordStorage.keys();
    }

    public void remove(String key) {
        this.passwordStorage.remove(key);
    }

    public void clear() {
        this.passwordStorage.clear();
    }

    /* loaded from: classes.dex */
    private static class PasswordStorageHelper_SDK16 implements PasswordStorageImpl {
        private SharedPreferences preferences;

        private PasswordStorageHelper_SDK16() {
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public boolean init(Context context) {
            this.preferences = context.getSharedPreferences(PasswordStorageHelper.PREFERENCES_FILE, 0);
            return true;
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void setData(String key, byte[] data) {
            if (data == null) {
                return;
            }
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.putString(key, Base64.encodeToString(data, 0));
            edit.commit();
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public byte[] getData(String key) {
            String string = this.preferences.getString(key, null);
            if (string == null) {
                return null;
            }
            return Base64.decode(string, 0);
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public String[] keys() {
            Set<String> keySet = this.preferences.getAll().keySet();
            return (String[]) keySet.toArray(new String[keySet.size()]);
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void remove(String key) {
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.remove(key);
            edit.commit();
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void clear() {
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.clear();
            edit.commit();
        }
    }

    /* loaded from: classes.dex */
    private static class PasswordStorageHelper_SDK18 implements PasswordStorageImpl {
        private static final String KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore";
        private static final String KEY_ALGORITHM_RSA = "RSA";
        private static int KEY_LENGTH = 2048;
        private static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
        private String alias;
        private SharedPreferences preferences;

        private PasswordStorageHelper_SDK18() {
            this.alias = null;
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public boolean init(Context context) {
            AlgorithmParameterSpec build;
            boolean isInsideSecureHardware;
            this.preferences = context.getSharedPreferences(PasswordStorageHelper.PREFERENCES_FILE, 0);
            this.alias = context.getPackageName() + "_cap_sec";
            try {
                KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
                keyStore.load(null);
                if (((PrivateKey) keyStore.getKey(this.alias, null)) != null && keyStore.getCertificate(this.alias) != null) {
                    if (keyStore.getCertificate(this.alias).getPublicKey() != null) {
                        return true;
                    }
                }
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
                gregorianCalendar2.add(1, 10);
                if (Build.VERSION.SDK_INT < 23) {
                    KeyPairGeneratorSpec.Builder alias = new KeyPairGeneratorSpec.Builder(context).setAlias(this.alias);
                    build = alias.setSubject(new X500Principal("CN=" + this.alias)).setSerialNumber(BigInteger.valueOf(1337L)).setStartDate(gregorianCalendar.getTime()).setEndDate(gregorianCalendar2.getTime()).build();
                } else {
                    build = new KeyGenParameterSpec.Builder(this.alias, 2).setDigests("SHA-256", "SHA-512").setEncryptionPaddings("PKCS1Padding").build();
                }
                try {
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
                    keyPairGenerator.initialize(build);
                    keyPairGenerator.generateKeyPair();
                } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
                    e.printStackTrace();
                }
                try {
                    if (Build.VERSION.SDK_INT < 23) {
                        isInsideSecureHardware = KeyChain.isBoundKeyAlgorithm(KEY_ALGORITHM_RSA);
                    } else {
                        PrivateKey privateKey = (PrivateKey) keyStore.getKey(this.alias, null);
                        KeyChain.isBoundKeyAlgorithm(KEY_ALGORITHM_RSA);
                        isInsideSecureHardware = ((KeyInfo) KeyFactory.getInstance(privateKey.getAlgorithm(), KEYSTORE_PROVIDER_ANDROID_KEYSTORE).getKeySpec(privateKey, KeyInfo.class)).isInsideSecureHardware();
                    }
                    String str = PasswordStorageHelper.LOG_TAG;
                    Log.d(str, "Hardware-Backed Keystore Supported: " + isInsideSecureHardware);
                } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | UnrecoverableKeyException | InvalidKeySpecException unused) {
                }
                return true;
            } catch (Exception unused2) {
                return false;
            }
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void setData(String key, byte[] data) {
            try {
                KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
                keyStore.load(null);
                if (keyStore.getCertificate(this.alias) == null) {
                    return;
                }
                PublicKey publicKey = keyStore.getCertificate(this.alias).getPublicKey();
                if (publicKey == null) {
                    Log.d(PasswordStorageHelper.LOG_TAG, "Error: Public key was not found in Keystore");
                    return;
                }
                String encrypt = encrypt(publicKey, data);
                SharedPreferences.Editor edit = this.preferences.edit();
                edit.putString(key, encrypt);
                edit.commit();
            } catch (IOException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertificateException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                e.printStackTrace();
            }
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public byte[] getData(String key) {
            try {
                KeyStore keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
                keyStore.load(null);
                return decrypt((PrivateKey) keyStore.getKey(this.alias, null), this.preferences.getString(key, null));
            } catch (IOException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | UnrecoverableEntryException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public String[] keys() {
            Set<String> keySet = this.preferences.getAll().keySet();
            return (String[]) keySet.toArray(new String[keySet.size()]);
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void remove(String key) {
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.remove(key);
            edit.commit();
        }

        @Override // com.whitestein.securestorage.PasswordStorageHelper.PasswordStorageImpl
        public void clear() {
            SharedPreferences.Editor edit = this.preferences.edit();
            edit.clear();
            edit.commit();
        }

        private static String encrypt(PublicKey encryptionKey, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeySpecException {
            if (data.length <= (KEY_LENGTH / 8) - 11) {
                Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
                cipher.init(1, encryptionKey);
                return Base64.encodeToString(cipher.doFinal(data), 0);
            }
            Cipher cipher2 = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher2.init(1, encryptionKey);
            int i = (KEY_LENGTH / 8) - 11;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i2 = 0; i2 < data.length; i2 += i) {
                if (data.length - i2 < i) {
                    i = data.length - i2;
                }
                try {
                    byteArrayOutputStream.write(cipher2.doFinal(data, i2, i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        }

        private static byte[] decrypt(PrivateKey decryptionKey, String encryptedData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
            if (encryptedData == null) {
                return null;
            }
            byte[] decode = Base64.decode(encryptedData, 0);
            if (decode.length <= KEY_LENGTH / 8) {
                Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
                cipher.init(2, decryptionKey);
                return cipher.doFinal(decode);
            }
            Cipher cipher2 = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher2.init(2, decryptionKey);
            int i = KEY_LENGTH / 8;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i2 = 0; i2 < decode.length; i2 += i) {
                if (decode.length - i2 < i) {
                    i = decode.length - i2;
                }
                try {
                    byteArrayOutputStream.write(cipher2.doFinal(decode, i2, i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return byteArrayOutputStream.toByteArray();
        }
    }
}
