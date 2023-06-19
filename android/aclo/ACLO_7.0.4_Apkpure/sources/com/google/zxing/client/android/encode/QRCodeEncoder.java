package com.google.zxing.client.android.encode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import androidx.core.content.IntentCompat;
import androidx.core.net.MailTo;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
/* loaded from: classes.dex */
final class QRCodeEncoder {
    private static final int BLACK = -16777216;
    private static final String TAG = "QRCodeEncoder";
    private static final int WHITE = -1;
    private final Context activity;
    private String contents;
    private final int dimension;
    private String displayContents;
    private BarcodeFormat format;
    private String title;
    private final boolean useVCard;

    /* JADX INFO: Access modifiers changed from: package-private */
    public QRCodeEncoder(Context context, Intent intent, int i, boolean z) throws WriterException {
        this.activity = context;
        this.dimension = i;
        this.useVCard = z;
        String action = intent.getAction();
        if (Intents.Encode.ACTION.equals(action)) {
            encodeContentsFromZXingIntent(intent);
        } else if ("android.intent.action.SEND".equals(action)) {
            encodeContentsFromShareIntent(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getContents() {
        return this.contents;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getDisplayContents() {
        return this.displayContents;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getTitle() {
        return this.title;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isUseVCard() {
        return this.useVCard;
    }

    private void encodeContentsFromZXingIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(Intents.Encode.FORMAT);
        this.format = null;
        if (stringExtra != null) {
            try {
                this.format = BarcodeFormat.valueOf(stringExtra);
            } catch (IllegalArgumentException unused) {
            }
        }
        BarcodeFormat barcodeFormat = this.format;
        if (barcodeFormat == null || barcodeFormat == BarcodeFormat.QR_CODE) {
            String stringExtra2 = intent.getStringExtra(Intents.Encode.TYPE);
            if (stringExtra2 == null || stringExtra2.isEmpty()) {
                return;
            }
            this.format = BarcodeFormat.QR_CODE;
            encodeQRCodeContents(intent, stringExtra2);
            return;
        }
        String stringExtra3 = intent.getStringExtra(Intents.Encode.DATA);
        if (stringExtra3 == null || stringExtra3.isEmpty()) {
            return;
        }
        this.contents = stringExtra3;
        this.displayContents = stringExtra3;
        this.title = this.activity.getString(R.string.contents_text);
    }

    private void encodeContentsFromShareIntent(Intent intent) throws WriterException {
        if (intent.hasExtra("android.intent.extra.STREAM")) {
            encodeFromStreamExtra(intent);
        } else {
            encodeFromTextExtras(intent);
        }
    }

    private void encodeFromTextExtras(Intent intent) throws WriterException {
        String trim = ContactEncoder.trim(intent.getStringExtra("android.intent.extra.TEXT"));
        if (trim == null && (trim = ContactEncoder.trim(intent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT))) == null && (trim = ContactEncoder.trim(intent.getStringExtra("android.intent.extra.SUBJECT"))) == null) {
            String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.EMAIL");
            trim = stringArrayExtra != null ? ContactEncoder.trim(stringArrayExtra[0]) : "?";
        }
        if (trim == null || trim.isEmpty()) {
            throw new WriterException("Empty EXTRA_TEXT");
        }
        this.contents = trim;
        this.format = BarcodeFormat.QR_CODE;
        if (intent.hasExtra("android.intent.extra.SUBJECT")) {
            this.displayContents = intent.getStringExtra("android.intent.extra.SUBJECT");
        } else if (intent.hasExtra("android.intent.extra.TITLE")) {
            this.displayContents = intent.getStringExtra("android.intent.extra.TITLE");
        } else {
            this.displayContents = this.contents;
        }
        this.title = this.activity.getString(R.string.contents_text);
    }

    private void encodeFromStreamExtra(Intent intent) throws WriterException {
        InputStream openInputStream;
        this.format = BarcodeFormat.QR_CODE;
        Bundle extras = intent.getExtras();
        if (extras == null) {
            throw new WriterException("No extras");
        }
        Uri uri = (Uri) extras.getParcelable("android.intent.extra.STREAM");
        if (uri == null) {
            throw new WriterException("No EXTRA_STREAM");
        }
        InputStream inputStream = null;
        try {
            try {
                openInputStream = this.activity.getContentResolver().openInputStream(uri);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            if (openInputStream == null) {
                throw new WriterException("Can't open stream for " + uri);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[2048];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String str = new String(byteArray, 0, byteArray.length, "UTF-8");
            if (openInputStream != null) {
                try {
                    openInputStream.close();
                } catch (IOException unused) {
                }
            }
            String str2 = TAG;
            Log.d(str2, "Encoding share intent content:");
            Log.d(str2, str);
            ParsedResult parseResult = ResultParser.parseResult(new Result(str, byteArray, null, BarcodeFormat.QR_CODE));
            if (!(parseResult instanceof AddressBookParsedResult)) {
                throw new WriterException("Result was not an address");
            }
            encodeQRCodeContents((AddressBookParsedResult) parseResult);
            String str3 = this.contents;
            if (str3 == null || str3.isEmpty()) {
                throw new WriterException("No content to encode");
            }
        } catch (IOException e2) {
            e = e2;
            throw new WriterException(e);
        } catch (Throwable th2) {
            th = th2;
            inputStream = openInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private void encodeQRCodeContents(Intent intent, String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1309271157:
                if (str.equals(Contents.Type.PHONE)) {
                    c = 0;
                    break;
                }
                break;
            case -670199783:
                if (str.equals(Contents.Type.CONTACT)) {
                    c = 1;
                    break;
                }
                break;
            case 709220992:
                if (str.equals(Contents.Type.SMS)) {
                    c = 2;
                    break;
                }
                break;
            case 1349204356:
                if (str.equals(Contents.Type.LOCATION)) {
                    c = 3;
                    break;
                }
                break;
            case 1778595596:
                if (str.equals(Contents.Type.TEXT)) {
                    c = 4;
                    break;
                }
                break;
            case 1833351709:
                if (str.equals(Contents.Type.EMAIL)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                String trim = ContactEncoder.trim(intent.getStringExtra(Intents.Encode.DATA));
                if (trim != null) {
                    this.contents = "tel:" + trim;
                    this.displayContents = PhoneNumberUtils.formatNumber(trim);
                    this.title = this.activity.getString(R.string.contents_phone);
                    return;
                }
                return;
            case 1:
                Bundle bundleExtra = intent.getBundleExtra(Intents.Encode.DATA);
                if (bundleExtra != null) {
                    String string = bundleExtra.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                    String string2 = bundleExtra.getString("company");
                    String string3 = bundleExtra.getString("postal");
                    List<String> allBundleValues = getAllBundleValues(bundleExtra, Contents.PHONE_KEYS);
                    List<String> allBundleValues2 = getAllBundleValues(bundleExtra, Contents.PHONE_TYPE_KEYS);
                    List<String> allBundleValues3 = getAllBundleValues(bundleExtra, Contents.EMAIL_KEYS);
                    String string4 = bundleExtra.getString(Contents.URL_KEY);
                    String[] encode = (this.useVCard ? new VCardContactEncoder() : new MECARDContactEncoder()).encode(Collections.singletonList(string), string2, Collections.singletonList(string3), allBundleValues, allBundleValues2, allBundleValues3, string4 == null ? null : Collections.singletonList(string4), bundleExtra.getString(Contents.NOTE_KEY));
                    if (encode[1].isEmpty()) {
                        return;
                    }
                    this.contents = encode[0];
                    this.displayContents = encode[1];
                    this.title = this.activity.getString(R.string.contents_contact);
                    return;
                }
                return;
            case 2:
                String trim2 = ContactEncoder.trim(intent.getStringExtra(Intents.Encode.DATA));
                if (trim2 != null) {
                    this.contents = "sms:" + trim2;
                    this.displayContents = PhoneNumberUtils.formatNumber(trim2);
                    this.title = this.activity.getString(R.string.contents_sms);
                    return;
                }
                return;
            case 3:
                Bundle bundleExtra2 = intent.getBundleExtra(Intents.Encode.DATA);
                if (bundleExtra2 != null) {
                    float f = bundleExtra2.getFloat("LAT", Float.MAX_VALUE);
                    float f2 = bundleExtra2.getFloat("LONG", Float.MAX_VALUE);
                    if (f == Float.MAX_VALUE || f2 == Float.MAX_VALUE) {
                        return;
                    }
                    this.contents = "geo:" + f + ',' + f2;
                    StringBuilder sb = new StringBuilder();
                    sb.append(f);
                    sb.append(",");
                    sb.append(f2);
                    this.displayContents = sb.toString();
                    this.title = this.activity.getString(R.string.contents_location);
                    return;
                }
                return;
            case 4:
                String stringExtra = intent.getStringExtra(Intents.Encode.DATA);
                if (stringExtra == null || stringExtra.isEmpty()) {
                    return;
                }
                this.contents = stringExtra;
                this.displayContents = stringExtra;
                this.title = this.activity.getString(R.string.contents_text);
                return;
            case 5:
                String trim3 = ContactEncoder.trim(intent.getStringExtra(Intents.Encode.DATA));
                if (trim3 != null) {
                    this.contents = MailTo.MAILTO_SCHEME + trim3;
                    this.displayContents = trim3;
                    this.title = this.activity.getString(R.string.contents_email);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private static List<String> getAllBundleValues(Bundle bundle, String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            Object obj = bundle.get(str);
            arrayList.add(obj == null ? null : obj.toString());
        }
        return arrayList;
    }

    private void encodeQRCodeContents(AddressBookParsedResult addressBookParsedResult) {
        String[] encode = (this.useVCard ? new VCardContactEncoder() : new MECARDContactEncoder()).encode(toList(addressBookParsedResult.getNames()), addressBookParsedResult.getOrg(), toList(addressBookParsedResult.getAddresses()), toList(addressBookParsedResult.getPhoneNumbers()), null, toList(addressBookParsedResult.getEmails()), toList(addressBookParsedResult.getURLs()), null);
        if (encode[1].isEmpty()) {
            return;
        }
        this.contents = encode[0];
        this.displayContents = encode[1];
        this.title = this.activity.getString(R.string.contents_contact);
    }

    private static List<String> toList(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return Arrays.asList(strArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Bitmap encodeAsBitmap() throws WriterException {
        EnumMap enumMap;
        String str = this.contents;
        if (str == null) {
            return null;
        }
        String guessAppropriateEncoding = guessAppropriateEncoding(str);
        if (guessAppropriateEncoding != null) {
            EnumMap enumMap2 = new EnumMap(EncodeHintType.class);
            enumMap2.put((EnumMap) EncodeHintType.CHARACTER_SET, (EncodeHintType) guessAppropriateEncoding);
            enumMap = enumMap2;
        } else {
            enumMap = null;
        }
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BarcodeFormat barcodeFormat = this.format;
            int i = this.dimension;
            BitMatrix encode = multiFormatWriter.encode(str, barcodeFormat, i, i, enumMap);
            int width = encode.getWidth();
            int height = encode.getHeight();
            int[] iArr = new int[width * height];
            for (int i2 = 0; i2 < height; i2++) {
                int i3 = i2 * width;
                for (int i4 = 0; i4 < width; i4++) {
                    iArr[i3 + i4] = encode.get(i4, i2) ? -16777216 : -1;
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
            return createBitmap;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private static String guessAppropriateEncoding(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) > 255) {
                return "UTF-8";
            }
        }
        return null;
    }
}
