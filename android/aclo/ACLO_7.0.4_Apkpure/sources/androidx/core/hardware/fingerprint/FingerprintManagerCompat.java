package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import androidx.core.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;
@Deprecated
/* loaded from: classes.dex */
public class FingerprintManagerCompat {
    private final Context mContext;

    /* loaded from: classes.dex */
    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) {
        }
    }

    public static FingerprintManagerCompat from(Context context) {
        return new FingerprintManagerCompat(context);
    }

    private FingerprintManagerCompat(Context context) {
        this.mContext = context;
    }

    public boolean hasEnrolledFingerprints() {
        FingerprintManager fingerprintManagerOrNull;
        return Build.VERSION.SDK_INT >= 23 && (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) != null && fingerprintManagerOrNull.hasEnrolledFingerprints();
    }

    public boolean isHardwareDetected() {
        FingerprintManager fingerprintManagerOrNull;
        return Build.VERSION.SDK_INT >= 23 && (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) != null && fingerprintManagerOrNull.isHardwareDetected();
    }

    public void authenticate(CryptoObject crypto, int flags, CancellationSignal cancel, AuthenticationCallback callback, Handler handler) {
        FingerprintManager fingerprintManagerOrNull;
        if (Build.VERSION.SDK_INT < 23 || (fingerprintManagerOrNull = getFingerprintManagerOrNull(this.mContext)) == null) {
            return;
        }
        fingerprintManagerOrNull.authenticate(wrapCryptoObject(crypto), cancel != null ? (android.os.CancellationSignal) cancel.getCancellationSignalObject() : null, flags, wrapCallback(callback), handler);
    }

    private static FingerprintManager getFingerprintManagerOrNull(Context context) {
        if (Build.VERSION.SDK_INT == 23) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        if (Build.VERSION.SDK_INT <= 23 || !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return null;
        }
        return (FingerprintManager) context.getSystemService(FingerprintManager.class);
    }

    private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    private static FingerprintManager.AuthenticationCallback wrapCallback(final AuthenticationCallback callback) {
        return new FingerprintManager.AuthenticationCallback() { // from class: androidx.core.hardware.fingerprint.FingerprintManagerCompat.1
            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                AuthenticationCallback.this.onAuthenticationError(errMsgId, errString);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                AuthenticationCallback.this.onAuthenticationHelp(helpMsgId, helpString);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                AuthenticationCallback.this.onAuthenticationSucceeded(new AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(result.getCryptoObject())));
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationFailed() {
                AuthenticationCallback.this.onAuthenticationFailed();
            }
        };
    }

    /* loaded from: classes.dex */
    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    /* loaded from: classes.dex */
    public static final class AuthenticationResult {
        private final CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject crypto) {
            this.mCryptoObject = crypto;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }
}
