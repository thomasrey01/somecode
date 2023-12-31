package com.google.zxing.client.android;

import android.net.Uri;
import com.google.zxing.Result;
import com.google.zxing.client.android.result.ResultHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/* loaded from: classes.dex */
final class ScanFromWebPageManager {
    private static final String RAW_PARAM = "raw";
    private static final String RETURN_URL_PARAM = "ret";
    private final boolean returnRaw;
    private final String returnUrlTemplate;
    private static final CharSequence CODE_PLACEHOLDER = "{CODE}";
    private static final CharSequence RAW_CODE_PLACEHOLDER = "{RAWCODE}";
    private static final CharSequence META_PLACEHOLDER = "{META}";
    private static final CharSequence FORMAT_PLACEHOLDER = "{FORMAT}";
    private static final CharSequence TYPE_PLACEHOLDER = "{TYPE}";

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScanFromWebPageManager(Uri uri) {
        this.returnUrlTemplate = uri.getQueryParameter(RETURN_URL_PARAM);
        this.returnRaw = uri.getQueryParameter(RAW_PARAM) != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isScanFromWebPage() {
        return this.returnUrlTemplate != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String buildReplyURL(Result result, ResultHandler resultHandler) {
        return replace(META_PLACEHOLDER, String.valueOf(result.getResultMetadata()), replace(TYPE_PLACEHOLDER, resultHandler.getType().toString(), replace(FORMAT_PLACEHOLDER, result.getBarcodeFormat().toString(), replace(RAW_CODE_PLACEHOLDER, result.getText(), replace(CODE_PLACEHOLDER, this.returnRaw ? result.getText() : resultHandler.getDisplayContents(), this.returnUrlTemplate)))));
    }

    private static String replace(CharSequence charSequence, CharSequence charSequence2, String str) {
        if (charSequence2 == null) {
            charSequence2 = "";
        }
        try {
            charSequence2 = URLEncoder.encode(charSequence2.toString(), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
        }
        return str.replace(charSequence, charSequence2);
    }
}
