package com.chariotsolutions.nfc.plugin;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class Util {
    static final String TAG = "NfcPlugin";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static JSONObject ndefToJSON(Ndef ndef) {
        JSONObject jSONObject = new JSONObject();
        if (ndef != null) {
            try {
                Tag tag = ndef.getTag();
                if (tag != null) {
                    jSONObject.put("id", byteArrayToJSON(tag.getId()));
                    jSONObject.put("techTypes", new JSONArray((Collection) Arrays.asList(tag.getTechList())));
                }
                jSONObject.put("type", translateType(ndef.getType()));
                jSONObject.put("maxSize", ndef.getMaxSize());
                jSONObject.put("isWritable", ndef.isWritable());
                jSONObject.put("ndefMessage", messageToJSON(ndef.getCachedNdefMessage()));
                try {
                    jSONObject.put("canMakeReadOnly", ndef.canMakeReadOnly());
                } catch (NullPointerException unused) {
                    jSONObject.put("canMakeReadOnly", JSONObject.NULL);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Failed to convert ndef into json: " + ndef.toString(), e);
            }
        }
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static JSONObject tagToJSON(Tag tag) {
        JSONObject jSONObject = new JSONObject();
        if (tag != null) {
            try {
                jSONObject.put("id", byteArrayToJSON(tag.getId()));
                jSONObject.put("techTypes", new JSONArray((Collection) Arrays.asList(tag.getTechList())));
            } catch (JSONException e) {
                Log.e(TAG, "Failed to convert tag into json: " + tag.toString(), e);
            }
        }
        return jSONObject;
    }

    static String translateType(String type) {
        return type.equals("org.nfcforum.ndef.type1") ? "NFC Forum Type 1" : type.equals("org.nfcforum.ndef.type2") ? "NFC Forum Type 2" : type.equals("org.nfcforum.ndef.type3") ? "NFC Forum Type 3" : type.equals("org.nfcforum.ndef.type4") ? "NFC Forum Type 4" : type;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static NdefRecord[] jsonToNdefRecords(String ndefMessageAsJSON) throws JSONException {
        JSONArray jSONArray = new JSONArray(ndefMessageAsJSON);
        NdefRecord[] ndefRecordArr = new NdefRecord[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            ndefRecordArr[i] = new NdefRecord((byte) jSONObject.getInt("tnf"), jsonToByteArray(jSONObject.getJSONArray("type")), jsonToByteArray(jSONObject.getJSONArray("id")), jsonToByteArray(jSONObject.getJSONArray("payload")));
        }
        return ndefRecordArr;
    }

    static JSONArray byteArrayToJSON(byte[] bytes) {
        JSONArray jSONArray = new JSONArray();
        for (byte b : bytes) {
            jSONArray.put((int) b);
        }
        return jSONArray;
    }

    static byte[] jsonToByteArray(JSONArray json) throws JSONException {
        byte[] bArr = new byte[json.length()];
        for (int i = 0; i < json.length(); i++) {
            bArr[i] = (byte) json.getInt(i);
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static JSONArray messageToJSON(NdefMessage message) {
        if (message == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NdefRecord ndefRecord : message.getRecords()) {
            arrayList.add(recordToJSON(ndefRecord));
        }
        return new JSONArray((Collection) arrayList);
    }

    static JSONObject recordToJSON(NdefRecord record) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tnf", (int) record.getTnf());
            jSONObject.put("type", byteArrayToJSON(record.getType()));
            jSONObject.put("id", byteArrayToJSON(record.getId()));
            jSONObject.put("payload", byteArrayToJSON(record.getPayload()));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to convert ndef record into json: " + record.toString(), e);
        }
        return jSONObject;
    }
}
