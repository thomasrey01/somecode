package com.chariotsolutions.nfc.plugin;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.TagTechnology;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class NfcPlugin extends CordovaPlugin implements NfcAdapter.OnNdefPushCompleteCallback {
    private static final String CHANNEL = "channel";
    private static final String CLOSE = "close";
    private static final String CONNECT = "connect";
    private static final String DISABLE_READER_MODE = "disableReaderMode";
    private static final String ENABLED = "enabled";
    private static final String ERASE_TAG = "eraseTag";
    private static final String HANDOVER = "handover";
    private static final String INIT = "init";
    private static final String MAKE_READ_ONLY = "makeReadOnly";
    private static final String NDEF = "ndef";
    private static final String NDEF_FORMATABLE = "ndef-formatable";
    private static final String NDEF_MIME = "ndef-mime";
    private static final String READER_MODE = "readerMode";
    private static final String REGISTER_DEFAULT_TAG = "registerTag";
    private static final String REGISTER_MIME_TYPE = "registerMimeType";
    private static final String REGISTER_NDEF = "registerNdef";
    private static final String REGISTER_NDEF_FORMATABLE = "registerNdefFormatable";
    private static final String REMOVE_DEFAULT_TAG = "removeTag";
    private static final String REMOVE_MIME_TYPE = "removeMimeType";
    private static final String REMOVE_NDEF = "removeNdef";
    private static final String SHARE_TAG = "shareTag";
    private static final String SHOW_SETTINGS = "showSettings";
    private static final String STATUS_NDEF_PUSH_DISABLED = "NDEF_PUSH_DISABLED";
    private static final String STATUS_NFC_DISABLED = "NFC_DISABLED";
    private static final String STATUS_NFC_OK = "NFC_OK";
    private static final String STATUS_NO_NFC = "NO_NFC";
    private static final String STOP_HANDOVER = "stopHandover";
    private static final String TAG = "NfcPlugin";
    private static final String TAG_DEFAULT = "tag";
    private static final String TRANSCEIVE = "transceive";
    private static final String UNSHARE_TAG = "unshareTag";
    private static final String WRITE_TAG = "writeTag";
    private CallbackContext channelCallback;
    private CallbackContext handoverCallback;
    private CallbackContext readerModeCallback;
    private CallbackContext shareTagCallback;
    private Class<?> tagTechnologyClass;
    private TagTechnology tagTechnology = null;
    private final List<IntentFilter> intentFilters = new ArrayList();
    private final ArrayList<String[]> techLists = new ArrayList<>();
    private NdefMessage p2pMessage = null;
    private PendingIntent pendingIntent = null;
    private Intent savedIntent = null;
    private NfcAdapter.ReaderCallback callback = new NfcAdapter.ReaderCallback() { // from class: com.chariotsolutions.nfc.plugin.NfcPlugin.1
        @Override // android.nfc.NfcAdapter.ReaderCallback
        public void onTagDiscovered(Tag tag) {
            JSONObject tagToJSON;
            if (Arrays.asList(tag.getTechList()).contains(Ndef.class.getName())) {
                tagToJSON = Util.ndefToJSON(Ndef.get(tag));
            } else {
                tagToJSON = Util.tagToJSON(tag);
            }
            Intent intent = new Intent();
            intent.putExtra("android.nfc.extra.TAG", tag);
            NfcPlugin.this.setIntent(intent);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, tagToJSON);
            pluginResult.setKeepCallback(true);
            if (NfcPlugin.this.readerModeCallback != null) {
                NfcPlugin.this.readerModeCallback.sendPluginResult(pluginResult);
            } else {
                Log.i(NfcPlugin.TAG, "readerModeCallback is null - reader mode probably disabled in the meantime");
            }
        }
    };

    @Override // org.apache.cordova.CordovaPlugin
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "execute " + action);
        if (action.equalsIgnoreCase(SHOW_SETTINGS)) {
            showSettings(callbackContext);
            return true;
        } else if (action.equalsIgnoreCase(CHANNEL)) {
            this.channelCallback = callbackContext;
            return true;
        } else if (action.equalsIgnoreCase(DISABLE_READER_MODE)) {
            disableReaderMode(callbackContext);
            return true;
        } else if (!getNfcStatus().equals(STATUS_NFC_OK)) {
            callbackContext.error(getNfcStatus());
            return true;
        } else {
            createPendingIntent();
            if (action.equalsIgnoreCase(READER_MODE)) {
                readerMode(data.getInt(0), callbackContext);
            } else if (action.equalsIgnoreCase(REGISTER_MIME_TYPE)) {
                registerMimeType(data, callbackContext);
            } else if (action.equalsIgnoreCase(REMOVE_MIME_TYPE)) {
                removeMimeType(data, callbackContext);
            } else if (action.equalsIgnoreCase(REGISTER_NDEF)) {
                registerNdef(callbackContext);
            } else if (action.equalsIgnoreCase(REMOVE_NDEF)) {
                removeNdef(callbackContext);
            } else if (action.equalsIgnoreCase(REGISTER_NDEF_FORMATABLE)) {
                registerNdefFormatable(callbackContext);
            } else if (action.equals(REGISTER_DEFAULT_TAG)) {
                registerDefaultTag(callbackContext);
            } else if (action.equals(REMOVE_DEFAULT_TAG)) {
                removeDefaultTag(callbackContext);
            } else if (action.equalsIgnoreCase(WRITE_TAG)) {
                writeTag(data, callbackContext);
            } else if (action.equalsIgnoreCase(MAKE_READ_ONLY)) {
                makeReadOnly(callbackContext);
            } else if (action.equalsIgnoreCase(ERASE_TAG)) {
                eraseTag(callbackContext);
            } else if (action.equalsIgnoreCase(SHARE_TAG)) {
                shareTag(data, callbackContext);
            } else if (action.equalsIgnoreCase(UNSHARE_TAG)) {
                unshareTag(callbackContext);
            } else if (action.equalsIgnoreCase(HANDOVER)) {
                handover(data, callbackContext);
            } else if (action.equalsIgnoreCase(STOP_HANDOVER)) {
                stopHandover(callbackContext);
            } else if (action.equalsIgnoreCase(INIT)) {
                init(callbackContext);
            } else if (action.equalsIgnoreCase(ENABLED)) {
                callbackContext.success(STATUS_NFC_OK);
            } else if (action.equalsIgnoreCase(CONNECT)) {
                connect(data.getString(0), data.optInt(1, -1), callbackContext);
            } else if (action.equalsIgnoreCase(TRANSCEIVE)) {
                transceive(new CordovaArgs(data).getArrayBuffer(0), callbackContext);
            } else if (!action.equalsIgnoreCase(CLOSE)) {
                return false;
            } else {
                close(callbackContext);
            }
            return true;
        }
    }

    private String getNfcStatus() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        return defaultAdapter == null ? STATUS_NO_NFC : !defaultAdapter.isEnabled() ? STATUS_NFC_DISABLED : STATUS_NFC_OK;
    }

    private void readerMode(final int flags, CallbackContext callbackContext) {
        final Bundle bundle = new Bundle();
        this.readerModeCallback = callbackContext;
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$8JZzBa99MFoUQNpt0fiHa-V7cwg
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$readerMode$0$NfcPlugin(flags, bundle);
            }
        });
    }

    public /* synthetic */ void lambda$readerMode$0$NfcPlugin(int i, Bundle bundle) {
        NfcAdapter.getDefaultAdapter(getActivity()).enableReaderMode(getActivity(), this.callback, i, bundle);
    }

    private void disableReaderMode(final CallbackContext callbackContext) {
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$YO6ZSgH8re8rPtQn71Yp0VbWwe4
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$disableReaderMode$1$NfcPlugin(callbackContext);
            }
        });
    }

    public /* synthetic */ void lambda$disableReaderMode$1$NfcPlugin(CallbackContext callbackContext) {
        this.readerModeCallback = null;
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter != null) {
            defaultAdapter.disableReaderMode(getActivity());
        }
        callbackContext.success();
    }

    private void registerDefaultTag(CallbackContext callbackContext) {
        addTagFilter();
        restartNfc();
        callbackContext.success();
    }

    private void removeDefaultTag(CallbackContext callbackContext) {
        removeTagFilter();
        restartNfc();
        callbackContext.success();
    }

    private void registerNdefFormatable(CallbackContext callbackContext) {
        addTechList(new String[]{NdefFormatable.class.getName()});
        restartNfc();
        callbackContext.success();
    }

    private void registerNdef(CallbackContext callbackContext) {
        addTechList(new String[]{Ndef.class.getName()});
        restartNfc();
        callbackContext.success();
    }

    private void removeNdef(CallbackContext callbackContext) {
        removeTechList(new String[]{Ndef.class.getName()});
        restartNfc();
        callbackContext.success();
    }

    private void unshareTag(CallbackContext callbackContext) {
        this.p2pMessage = null;
        stopNdefPush();
        this.shareTagCallback = null;
        callbackContext.success();
    }

    private void init(CallbackContext callbackContext) {
        Log.d(TAG, "Enabling plugin " + getIntent());
        startNfc();
        if (!recycledIntent()) {
            parseMessage();
        }
        callbackContext.success();
    }

    private void removeMimeType(JSONArray data, CallbackContext callbackContext) throws JSONException {
        removeIntentFilter(data.getString(0));
        restartNfc();
        callbackContext.success();
    }

    private void registerMimeType(JSONArray data, CallbackContext callbackContext) throws JSONException {
        String str;
        try {
            str = data.getString(0);
            try {
                this.intentFilters.add(createIntentFilter(str));
                restartNfc();
                callbackContext.success();
            } catch (IntentFilter.MalformedMimeTypeException unused) {
                callbackContext.error("Invalid MIME Type " + str);
            }
        } catch (IntentFilter.MalformedMimeTypeException unused2) {
            str = "";
        }
    }

    private void eraseTag(CallbackContext callbackContext) {
        writeNdefMessage(new NdefMessage(new NdefRecord[]{new NdefRecord((short) 0, new byte[0], new byte[0], new byte[0])}), (Tag) this.savedIntent.getParcelableExtra("android.nfc.extra.TAG"), callbackContext);
    }

    private void writeTag(JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (getIntent() == null) {
            callbackContext.error("Failed to write tag, received null intent");
        }
        writeNdefMessage(new NdefMessage(Util.jsonToNdefRecords(data.getString(0))), (Tag) this.savedIntent.getParcelableExtra("android.nfc.extra.TAG"), callbackContext);
    }

    private void writeNdefMessage(final NdefMessage message, final Tag tag, final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$gnmzzlM0RxGz48Wd_ejLvTCXGqQ
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.lambda$writeNdefMessage$2(tag, message, callbackContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$writeNdefMessage$2(Tag tag, NdefMessage ndefMessage, CallbackContext callbackContext) {
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (ndef.isWritable()) {
                    int length = ndefMessage.toByteArray().length;
                    if (ndef.getMaxSize() < length) {
                        callbackContext.error("Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + length + " bytes.");
                    } else {
                        ndef.writeNdefMessage(ndefMessage);
                        callbackContext.success();
                    }
                } else {
                    callbackContext.error("Tag is read only");
                }
                ndef.close();
                return;
            }
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);
            if (ndefFormatable != null) {
                ndefFormatable.connect();
                ndefFormatable.format(ndefMessage);
                callbackContext.success();
                ndefFormatable.close();
                return;
            }
            callbackContext.error("Tag doesn't support NDEF");
        } catch (FormatException e) {
            callbackContext.error(e.getMessage());
        } catch (TagLostException e2) {
            callbackContext.error(e2.getMessage());
        } catch (IOException e3) {
            callbackContext.error(e3.getMessage());
        }
    }

    private void makeReadOnly(final CallbackContext callbackContext) {
        if (getIntent() == null) {
            callbackContext.error("Failed to make tag read only, received null intent");
            return;
        }
        final Tag tag = (Tag) this.savedIntent.getParcelableExtra("android.nfc.extra.TAG");
        if (tag == null) {
            callbackContext.error("Failed to make tag read only, tag is null");
        } else {
            this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$kFdtDM4b9lRxkqr0YFQlX4ggzyA
                @Override // java.lang.Runnable
                public final void run() {
                    NfcPlugin.lambda$makeReadOnly$3(tag, callbackContext);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$makeReadOnly$3(Tag tag, CallbackContext callbackContext) {
        String iOException;
        Ndef ndef = Ndef.get(tag);
        boolean z = false;
        if (ndef != null) {
            try {
                ndef.connect();
                if (!ndef.isWritable()) {
                    iOException = "Tag is not writable";
                } else if (ndef.canMakeReadOnly()) {
                    z = ndef.makeReadOnly();
                    iOException = "Could not make tag read only";
                } else {
                    iOException = "Tag can not be made read only";
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to make tag read only", e);
                if (e.getMessage() != null) {
                    iOException = e.getMessage();
                } else {
                    iOException = e.toString();
                }
            }
        } else {
            iOException = "Tag is not NDEF";
        }
        if (z) {
            callbackContext.success();
        } else {
            callbackContext.error(iOException);
        }
    }

    private void shareTag(JSONArray data, CallbackContext callbackContext) throws JSONException {
        this.p2pMessage = new NdefMessage(Util.jsonToNdefRecords(data.getString(0)));
        startNdefPush(callbackContext);
    }

    private void handover(JSONArray data, CallbackContext callbackContext) throws JSONException {
        Uri[] uriArr = new Uri[data.length()];
        for (int i = 0; i < data.length(); i++) {
            uriArr[i] = Uri.parse(data.getString(i));
        }
        startNdefBeam(callbackContext, uriArr);
    }

    private void stopHandover(CallbackContext callbackContext) {
        stopNdefBeam();
        this.handoverCallback = null;
        callbackContext.success();
    }

    private void showSettings(CallbackContext callbackContext) {
        if (Build.VERSION.SDK_INT >= 16) {
            getActivity().startActivity(new Intent("android.settings.NFC_SETTINGS"));
        } else {
            getActivity().startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
        }
        callbackContext.success();
    }

    private void createPendingIntent() {
        if (this.pendingIntent == null) {
            Activity activity = getActivity();
            Intent intent = new Intent(activity, activity.getClass());
            intent.addFlags(603979776);
            this.pendingIntent = PendingIntent.getActivity(activity, 0, intent, 67108864);
        }
    }

    private void addTechList(String[] list) {
        addTechFilter();
        addToTechList(list);
    }

    private void removeTechList(String[] list) {
        removeTechFilter();
        removeFromTechList(list);
    }

    private void addTechFilter() {
        this.intentFilters.add(new IntentFilter("android.nfc.action.TECH_DISCOVERED"));
    }

    private void removeTechFilter() {
        Iterator<IntentFilter> it = this.intentFilters.iterator();
        while (it.hasNext()) {
            if ("android.nfc.action.TECH_DISCOVERED".equals(it.next().getAction(0))) {
                it.remove();
            }
        }
    }

    private void addTagFilter() {
        this.intentFilters.add(new IntentFilter("android.nfc.action.TAG_DISCOVERED"));
    }

    private void removeTagFilter() {
        Iterator<IntentFilter> it = this.intentFilters.iterator();
        while (it.hasNext()) {
            if ("android.nfc.action.TAG_DISCOVERED".equals(it.next().getAction(0))) {
                it.remove();
            }
        }
    }

    private void restartNfc() {
        stopNfc();
        startNfc();
    }

    private void startNfc() {
        createPendingIntent();
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$cwkSWflM9wG7aEQYkVMy89llMQQ
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$startNfc$4$NfcPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$startNfc$4$NfcPlugin() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter == null || getActivity().isFinishing()) {
            return;
        }
        try {
            IntentFilter[] intentFilters = getIntentFilters();
            String[][] techLists = getTechLists();
            if (intentFilters.length > 0 || techLists.length > 0) {
                defaultAdapter.enableForegroundDispatch(getActivity(), getPendingIntent(), intentFilters, techLists);
            }
            NdefMessage ndefMessage = this.p2pMessage;
            if (ndefMessage != null) {
                defaultAdapter.setNdefPushMessage(ndefMessage, getActivity(), new Activity[0]);
            }
        } catch (IllegalStateException unused) {
            Log.w(TAG, "Illegal State Exception starting NFC. Assuming application is terminating.");
        }
    }

    private void stopNfc() {
        Log.d(TAG, "stopNfc");
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$ljCvLMVf7bMstv5SnqVpXx3Z8II
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$stopNfc$5$NfcPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$stopNfc$5$NfcPlugin() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter != null) {
            try {
                defaultAdapter.disableForegroundDispatch(getActivity());
            } catch (IllegalStateException unused) {
                Log.w(TAG, "Illegal State Exception stopping NFC. Assuming application is terminating.");
            }
        }
    }

    private void startNdefBeam(final CallbackContext callbackContext, final Uri[] uris) {
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$O6jURV-lk5_-pwzXH1R1leU_RI4
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$startNdefBeam$6$NfcPlugin(callbackContext, uris);
            }
        });
    }

    public /* synthetic */ void lambda$startNdefBeam$6$NfcPlugin(CallbackContext callbackContext, Uri[] uriArr) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter == null) {
            callbackContext.error(STATUS_NO_NFC);
        } else if (!defaultAdapter.isNdefPushEnabled()) {
            callbackContext.error(STATUS_NDEF_PUSH_DISABLED);
        } else {
            defaultAdapter.setOnNdefPushCompleteCallback(this, getActivity(), new Activity[0]);
            try {
                defaultAdapter.setBeamPushUris(uriArr, getActivity());
                PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
                pluginResult.setKeepCallback(true);
                this.handoverCallback = callbackContext;
                callbackContext.sendPluginResult(pluginResult);
            } catch (IllegalArgumentException e) {
                callbackContext.error(e.getMessage());
            }
        }
    }

    private void startNdefPush(final CallbackContext callbackContext) {
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$F2ZEkNpzUgA9a-a9ZqjY88rumKM
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$startNdefPush$7$NfcPlugin(callbackContext);
            }
        });
    }

    public /* synthetic */ void lambda$startNdefPush$7$NfcPlugin(CallbackContext callbackContext) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter == null) {
            callbackContext.error(STATUS_NO_NFC);
        } else if (!defaultAdapter.isNdefPushEnabled()) {
            callbackContext.error(STATUS_NDEF_PUSH_DISABLED);
        } else {
            defaultAdapter.setNdefPushMessage(this.p2pMessage, getActivity(), new Activity[0]);
            defaultAdapter.setOnNdefPushCompleteCallback(this, getActivity(), new Activity[0]);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            this.shareTagCallback = callbackContext;
            callbackContext.sendPluginResult(pluginResult);
        }
    }

    private void stopNdefPush() {
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$FHlT5cruyLvwDpsf1jxEYmRPDgU
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$stopNdefPush$8$NfcPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$stopNdefPush$8$NfcPlugin() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter != null) {
            defaultAdapter.setNdefPushMessage(null, getActivity(), new Activity[0]);
        }
    }

    private void stopNdefBeam() {
        getActivity().runOnUiThread(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$R1ZB-SwV7piASn0fS-risdgwqrU
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$stopNdefBeam$9$NfcPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$stopNdefBeam$9$NfcPlugin() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (defaultAdapter != null) {
            defaultAdapter.setBeamPushUris(null, getActivity());
        }
    }

    private void addToTechList(String[] techs) {
        this.techLists.add(techs);
    }

    private void removeFromTechList(String[] techs) {
        Iterator<String[]> it = this.techLists.iterator();
        while (it.hasNext()) {
            if (Arrays.equals(it.next(), techs)) {
                it.remove();
            }
        }
    }

    private void removeIntentFilter(String mimeType) {
        Iterator<IntentFilter> it = this.intentFilters.iterator();
        while (it.hasNext()) {
            if (mimeType.equals(it.next().getDataType(0))) {
                it.remove();
            }
        }
    }

    private IntentFilter createIntentFilter(String mimeType) throws IntentFilter.MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter("android.nfc.action.NDEF_DISCOVERED");
        intentFilter.addDataType(mimeType);
        return intentFilter;
    }

    private PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    private IntentFilter[] getIntentFilters() {
        List<IntentFilter> list = this.intentFilters;
        return (IntentFilter[]) list.toArray(new IntentFilter[list.size()]);
    }

    private String[][] getTechLists() {
        return (String[][]) this.techLists.toArray((String[][]) Array.newInstance(String.class, 0, 0));
    }

    private void parseMessage() {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$8qvszOgjpGqW46Vg-QVXuocmsg8
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$parseMessage$10$NfcPlugin();
            }
        });
    }

    public /* synthetic */ void lambda$parseMessage$10$NfcPlugin() {
        String[] techList;
        Log.d(TAG, "parseMessage " + getIntent());
        Intent intent = getIntent();
        String action = intent.getAction();
        Log.d(TAG, "action " + action);
        if (action == null) {
            return;
        }
        Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
        if (action.equals("android.nfc.action.NDEF_DISCOVERED")) {
            fireNdefEvent(NDEF_MIME, Ndef.get(tag), parcelableArrayExtra);
        } else if (action.equals("android.nfc.action.TECH_DISCOVERED")) {
            for (String str : tag.getTechList()) {
                Log.d(TAG, str);
                if (str.equals(NdefFormatable.class.getName())) {
                    fireNdefFormatableEvent(tag);
                } else if (str.equals(Ndef.class.getName())) {
                    fireNdefEvent(NDEF, Ndef.get(tag), parcelableArrayExtra);
                }
            }
        }
        if (action.equals("android.nfc.action.TAG_DISCOVERED")) {
            fireTagEvent(tag);
        }
        setIntent(new Intent());
    }

    private void sendEvent(String type, JSONObject tag) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", type);
            jSONObject.put(TAG_DEFAULT, tag);
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jSONObject);
            pluginResult.setKeepCallback(true);
            this.channelCallback.sendPluginResult(pluginResult);
        } catch (JSONException e) {
            Log.e(TAG, "Error sending NFC event through the channel", e);
        }
    }

    private void fireNdefEvent(String type, Ndef ndef, Parcelable[] messages) {
        sendEvent(type, buildNdefJSON(ndef, messages));
    }

    private void fireNdefFormatableEvent(Tag tag) {
        sendEvent(NDEF_FORMATABLE, Util.tagToJSON(tag));
    }

    private void fireTagEvent(Tag tag) {
        sendEvent(TAG_DEFAULT, Util.tagToJSON(tag));
    }

    private JSONObject buildNdefJSON(Ndef ndef, Parcelable[] messages) {
        JSONObject ndefToJSON = Util.ndefToJSON(ndef);
        if (ndef == null && messages != null) {
            try {
                if (messages.length > 0) {
                    ndefToJSON.put("ndefMessage", Util.messageToJSON((NdefMessage) messages[0]));
                    ndefToJSON.put("type", "NDEF Push Protocol");
                }
                if (messages.length > 1) {
                    Log.wtf(TAG, "Expected one ndefMessage but found " + messages.length);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Failed to convert ndefMessage into json", e);
            }
        }
        return ndefToJSON;
    }

    private boolean recycledIntent() {
        if ((getIntent().getFlags() & 1048576) == 1048576) {
            Log.i(TAG, "Launched from history, killing recycled intent");
            setIntent(new Intent());
            return true;
        }
        return false;
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onPause(boolean multitasking) {
        Log.d(TAG, "onPause " + getIntent());
        super.onPause(multitasking);
        if (multitasking) {
            stopNfc();
        }
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onResume(boolean multitasking) {
        Log.d(TAG, "onResume " + getIntent());
        super.onResume(multitasking);
        startNfc();
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent " + intent);
        super.onNewIntent(intent);
        setIntent(intent);
        this.savedIntent = intent;
        parseMessage();
    }

    private Activity getActivity() {
        return this.cordova.getActivity();
    }

    private Intent getIntent() {
        return getActivity().getIntent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIntent(Intent intent) {
        getActivity().setIntent(intent);
    }

    @Override // android.nfc.NfcAdapter.OnNdefPushCompleteCallback
    public void onNdefPushComplete(NfcEvent event) {
        if (this.handoverCallback != null) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "Beamed Message to Peer");
            pluginResult.setKeepCallback(true);
            this.handoverCallback.sendPluginResult(pluginResult);
        } else if (this.shareTagCallback != null) {
            PluginResult pluginResult2 = new PluginResult(PluginResult.Status.OK, "Shared Message with Peer");
            pluginResult2.setKeepCallback(true);
            this.shareTagCallback.sendPluginResult(pluginResult2);
        }
    }

    private void connect(final String tech, final int timeout, final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$eczQKQsqpYGMtjrGeX_uhtJj8Eg
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$connect$11$NfcPlugin(callbackContext, tech, timeout);
            }
        });
    }

    public /* synthetic */ void lambda$connect$11$NfcPlugin(CallbackContext callbackContext, String str, int i) {
        Intent intent;
        try {
            try {
                Tag tag = (Tag) getIntent().getParcelableExtra("android.nfc.extra.TAG");
                if (tag == null && (intent = this.savedIntent) != null) {
                    tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
                }
                if (tag == null) {
                    Log.e(TAG, "No Tag");
                    callbackContext.error("No Tag");
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                if (Arrays.asList(tag.getTechList()).contains(str)) {
                    Class<?> cls = Class.forName(str);
                    this.tagTechnologyClass = cls;
                    this.tagTechnology = (TagTechnology) cls.getMethod("get", Tag.class).invoke(null, tag);
                    try {
                        jSONObject.put("maxTransceiveLength", this.tagTechnologyClass.getMethod("getMaxTransceiveLength", new Class[0]).invoke(this.tagTechnology, new Object[0]));
                    } catch (NoSuchMethodException unused) {
                    } catch (JSONException e) {
                        Log.e(TAG, "Error serializing JSON", e);
                    }
                }
                TagTechnology tagTechnology = this.tagTechnology;
                if (tagTechnology == null) {
                    callbackContext.error("Tag does not support " + str);
                    return;
                }
                tagTechnology.connect();
                setTimeout(i);
                callbackContext.success(jSONObject);
            } catch (NoSuchMethodException e2) {
                Log.e(TAG, e2.getMessage(), e2);
                callbackContext.error(e2.getMessage());
            }
        } catch (IOException e3) {
            Log.e(TAG, "Tag connection failed", e3);
            callbackContext.error("Tag connection failed");
        } catch (ClassNotFoundException e4) {
            Log.e(TAG, e4.getMessage(), e4);
            callbackContext.error(e4.getMessage());
        } catch (IllegalAccessException e5) {
            Log.e(TAG, e5.getMessage(), e5);
            callbackContext.error(e5.getMessage());
        } catch (InvocationTargetException e6) {
            Log.e(TAG, e6.getMessage(), e6);
            callbackContext.error(e6.getMessage());
        }
    }

    private void setTimeout(int timeout) {
        if (timeout < 0) {
            return;
        }
        try {
            this.tagTechnologyClass.getMethod("setTimeout", Integer.TYPE).invoke(this.tagTechnology, Integer.valueOf(timeout));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
    }

    private void close(final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$ZMsTRqBc5PA9F93_tG40Cvaw9hA
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$close$12$NfcPlugin(callbackContext);
            }
        });
    }

    public /* synthetic */ void lambda$close$12$NfcPlugin(CallbackContext callbackContext) {
        try {
            TagTechnology tagTechnology = this.tagTechnology;
            if (tagTechnology != null && tagTechnology.isConnected()) {
                this.tagTechnology.close();
                this.tagTechnology = null;
                callbackContext.success();
            } else {
                callbackContext.success();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing nfc connection", e);
            callbackContext.error("Error closing nfc connection " + e.getLocalizedMessage());
        }
    }

    private void transceive(final byte[] data, final CallbackContext callbackContext) {
        this.cordova.getThreadPool().execute(new Runnable() { // from class: com.chariotsolutions.nfc.plugin.-$$Lambda$NfcPlugin$xD8RU6cSfAlyGxIIvIG3ra_8c2E
            @Override // java.lang.Runnable
            public final void run() {
                NfcPlugin.this.lambda$transceive$13$NfcPlugin(callbackContext, data);
            }
        });
    }

    public /* synthetic */ void lambda$transceive$13$NfcPlugin(CallbackContext callbackContext, byte[] bArr) {
        try {
            TagTechnology tagTechnology = this.tagTechnology;
            if (tagTechnology == null) {
                Log.e(TAG, "No Tech");
                callbackContext.error("No Tech");
            } else if (!tagTechnology.isConnected()) {
                Log.e(TAG, "Not connected");
                callbackContext.error("Not connected");
            } else {
                callbackContext.success((byte[]) this.tagTechnologyClass.getMethod(TRANSCEIVE, byte[].class).invoke(this.tagTechnology, bArr));
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(e.getMessage());
        } catch (NoSuchMethodException e2) {
            String str = "TagTechnology " + this.tagTechnologyClass.getName() + " does not have a transceive function";
            Log.e(TAG, str, e2);
            callbackContext.error(str);
        } catch (NullPointerException e3) {
            Log.e(TAG, e3.getMessage(), e3);
            callbackContext.error(e3.getMessage());
        } catch (InvocationTargetException e4) {
            Log.e(TAG, e4.getMessage(), e4);
            callbackContext.error(e4.getCause().getMessage());
        }
    }
}
