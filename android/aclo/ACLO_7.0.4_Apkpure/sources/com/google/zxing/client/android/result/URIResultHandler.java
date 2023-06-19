package com.google.zxing.client.android.result;

import android.app.Activity;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.zxing.client.android.LocaleManager;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import java.util.Locale;
/* loaded from: classes.dex */
public final class URIResultHandler extends ResultHandler {
    private static final String[] SECURE_PROTOCOLS = {"otpauth:"};
    private static final int[] buttons = {R.string.button_open_browser, R.string.button_share_by_email, R.string.button_share_by_sms, R.string.button_search_book_contents};

    public URIResultHandler(Activity activity, ParsedResult parsedResult) {
        super(activity, parsedResult);
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getButtonCount() {
        if (LocaleManager.isBookSearchUrl(((URIParsedResult) getResult()).getURI())) {
            return buttons.length;
        }
        return buttons.length - 1;
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getButtonText(int i) {
        return buttons[i];
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public Integer getDefaultButtonID() {
        return 0;
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public void handleButtonPress(int i) {
        String uri = ((URIParsedResult) getResult()).getURI();
        if (i == 0) {
            openURL(uri);
        } else if (i == 1) {
            shareByEmail(uri);
        } else if (i == 2) {
            shareBySMS(uri);
        } else if (i != 3) {
        } else {
            searchBookContents(uri);
        }
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getDisplayTitle() {
        return R.string.result_uri;
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public boolean areContentsSecure() {
        String lowerCase = ((URIParsedResult) getResult()).getURI().toLowerCase(Locale.ENGLISH);
        for (String str : SECURE_PROTOCOLS) {
            if (lowerCase.startsWith(str)) {
                return true;
            }
        }
        return false;
    }
}
