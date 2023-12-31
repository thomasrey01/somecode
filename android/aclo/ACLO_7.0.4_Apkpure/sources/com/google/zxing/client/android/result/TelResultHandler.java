package com.google.zxing.client.android.result;

import android.app.Activity;
import android.telephony.PhoneNumberUtils;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.TelParsedResult;
/* loaded from: classes.dex */
public final class TelResultHandler extends ResultHandler {
    private static final int[] buttons = {R.string.button_dial, R.string.button_add_contact};

    public TelResultHandler(Activity activity, ParsedResult parsedResult) {
        super(activity, parsedResult);
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getButtonCount() {
        return buttons.length;
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getButtonText(int i) {
        return buttons[i];
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public void handleButtonPress(int i) {
        TelParsedResult telParsedResult = (TelParsedResult) getResult();
        if (i == 0) {
            dialPhoneFromUri(telParsedResult.getTelURI());
            getActivity().finish();
        } else if (i != 1) {
        } else {
            addPhoneOnlyContact(new String[]{telParsedResult.getNumber()}, null);
        }
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public CharSequence getDisplayContents() {
        return PhoneNumberUtils.formatNumber(getResult().getDisplayResult().replace("\r", ""));
    }

    @Override // com.google.zxing.client.android.result.ResultHandler
    public int getDisplayTitle() {
        return R.string.result_tel;
    }
}
