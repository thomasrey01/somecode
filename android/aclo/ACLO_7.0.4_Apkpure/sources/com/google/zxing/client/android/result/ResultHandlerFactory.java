package com.google.zxing.client.android.result;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
/* loaded from: classes.dex */
public final class ResultHandlerFactory {
    private ResultHandlerFactory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.zxing.client.android.result.ResultHandlerFactory$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$client$result$ParsedResultType;

        static {
            int[] iArr = new int[ParsedResultType.values().length];
            $SwitchMap$com$google$zxing$client$result$ParsedResultType = iArr;
            try {
                iArr[ParsedResultType.ADDRESSBOOK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.EMAIL_ADDRESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.PRODUCT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.URI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.WIFI.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.GEO.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.TEL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.SMS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.CALENDAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$zxing$client$result$ParsedResultType[ParsedResultType.ISBN.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static ResultHandler makeResultHandler(CaptureActivity captureActivity, Result result) {
        ParsedResult parseResult = parseResult(result);
        switch (AnonymousClass1.$SwitchMap$com$google$zxing$client$result$ParsedResultType[parseResult.getType().ordinal()]) {
            case 1:
                return new AddressBookResultHandler(captureActivity, parseResult);
            case 2:
                return new EmailAddressResultHandler(captureActivity, parseResult);
            case 3:
                return new ProductResultHandler(captureActivity, parseResult, result);
            case 4:
                return new URIResultHandler(captureActivity, parseResult);
            case 5:
                return new WifiResultHandler(captureActivity, parseResult);
            case 6:
                return new GeoResultHandler(captureActivity, parseResult);
            case 7:
                return new TelResultHandler(captureActivity, parseResult);
            case 8:
                return new SMSResultHandler(captureActivity, parseResult);
            case 9:
                return new CalendarResultHandler(captureActivity, parseResult);
            case 10:
                return new ISBNResultHandler(captureActivity, parseResult, result);
            default:
                return new TextResultHandler(captureActivity, parseResult, result);
        }
    }

    private static ParsedResult parseResult(Result result) {
        return ResultParser.parseResult(result);
    }
}
