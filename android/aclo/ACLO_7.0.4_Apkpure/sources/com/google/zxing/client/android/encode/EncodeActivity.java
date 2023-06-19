package com.google.zxing.client.android.encode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.net.MailTo;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.FinishListener;
import com.google.zxing.client.android.Intents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public final class EncodeActivity extends Activity {
    private static final int MAX_BARCODE_FILENAME_LENGTH = 24;
    private static final Pattern NOT_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]");
    private static final String TAG = "EncodeActivity";
    private static final String USE_VCARD_KEY = "USE_VCARD";
    private QRCodeEncoder qrCodeEncoder;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        String action = intent.getAction();
        if (Intents.Encode.ACTION.equals(action) || "android.intent.action.SEND".equals(action)) {
            setContentView(R.layout.encode);
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.encode, menu);
        QRCodeEncoder qRCodeEncoder = this.qrCodeEncoder;
        int i = qRCodeEncoder != null && qRCodeEncoder.isUseVCard() ? R.string.menu_encode_mecard : R.string.menu_encode_vcard;
        MenuItem findItem = menu.findItem(R.id.menu_encode);
        findItem.setTitle(i);
        Intent intent = getIntent();
        if (intent != null) {
            findItem.setVisible(Contents.Type.CONTACT.equals(intent.getStringExtra(Intents.Encode.TYPE)));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_share) {
            share();
            return true;
        } else if (itemId != R.id.menu_encode || (intent = getIntent()) == null) {
            return false;
        } else {
            intent.putExtra(USE_VCARD_KEY, !this.qrCodeEncoder.isUseVCard());
            intent.addFlags(67108864);
            startActivity(intent);
            finish();
            return true;
        }
    }

    private void share() {
        FileOutputStream fileOutputStream;
        QRCodeEncoder qRCodeEncoder = this.qrCodeEncoder;
        if (qRCodeEncoder == null) {
            Log.w(TAG, "No existing barcode to send?");
            return;
        }
        String contents = qRCodeEncoder.getContents();
        if (contents == null) {
            Log.w(TAG, "No existing barcode to send?");
            return;
        }
        try {
            Bitmap encodeAsBitmap = qRCodeEncoder.encodeAsBitmap();
            if (encodeAsBitmap == null) {
                return;
            }
            File file = new File(new File(Environment.getExternalStorageDirectory(), "BarcodeScanner"), "Barcodes");
            if (!file.exists() && !file.mkdirs()) {
                String str = TAG;
                Log.w(str, "Couldn't make dir " + file);
                showErrorMessage(R.string.msg_unmount_usb);
                return;
            }
            File file2 = new File(file, ((Object) makeBarcodeFileName(contents)) + ".png");
            if (!file2.delete()) {
                String str2 = TAG;
                Log.w(str2, "Could not delete " + file2);
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(file2);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException e) {
                e = e;
            }
            try {
                encodeAsBitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
                Intent intent = new Intent("android.intent.action.SEND", Uri.parse(MailTo.MAILTO_SCHEME));
                intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name) + " - " + qRCodeEncoder.getTitle());
                intent.putExtra("android.intent.extra.TEXT", contents);
                intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + file2.getAbsolutePath()));
                intent.setType("image/png");
                intent.addFlags(524288);
                startActivity(Intent.createChooser(intent, null));
            } catch (FileNotFoundException e2) {
                e = e2;
                fileOutputStream2 = fileOutputStream;
                String str3 = TAG;
                Log.w(str3, "Couldn't access file " + file2 + " due to " + e);
                showErrorMessage(R.string.msg_unmount_usb);
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (WriterException e3) {
            Log.w(TAG, e3);
        }
    }

    private static CharSequence makeBarcodeFileName(CharSequence charSequence) {
        String replaceAll = NOT_ALPHANUMERIC.matcher(charSequence).replaceAll("_");
        return replaceAll.length() > 24 ? replaceAll.substring(0, 24) : replaceAll;
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        Display defaultDisplay = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        if (i >= i2) {
            i = i2;
        }
        int i3 = (i * 7) / 8;
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        try {
            QRCodeEncoder qRCodeEncoder = new QRCodeEncoder(this, intent, i3, intent.getBooleanExtra(USE_VCARD_KEY, false));
            this.qrCodeEncoder = qRCodeEncoder;
            Bitmap encodeAsBitmap = qRCodeEncoder.encodeAsBitmap();
            if (encodeAsBitmap == null) {
                Log.w(TAG, "Could not encode barcode");
                showErrorMessage(R.string.msg_encode_contents_failed);
                this.qrCodeEncoder = null;
                return;
            }
            ((ImageView) findViewById(R.id.image_view)).setImageBitmap(encodeAsBitmap);
            TextView textView = (TextView) findViewById(R.id.contents_text_view);
            if (intent.getBooleanExtra(Intents.Encode.SHOW_CONTENTS, true)) {
                textView.setText(this.qrCodeEncoder.getDisplayContents());
                setTitle(this.qrCodeEncoder.getTitle());
                return;
            }
            textView.setText("");
            setTitle("");
        } catch (WriterException e) {
            Log.w(TAG, "Could not encode barcode", e);
            showErrorMessage(R.string.msg_encode_contents_failed);
            this.qrCodeEncoder = null;
        }
    }

    private void showErrorMessage(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(i);
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }
}
