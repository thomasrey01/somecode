package org.apache.cordova;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import org.apache.cordova.PluginResult;
/* loaded from: classes.dex */
public class NativeToJsMessageQueue {
    static final boolean DISABLE_EXEC_CHAINING = false;
    private static final boolean FORCE_ENCODE_USING_EVAL = false;
    private static final String LOG_TAG = "JsMessageQueue";
    private static int MAX_PAYLOAD_SIZE = 524288000;
    private BridgeMode activeBridgeMode;
    private boolean paused;
    private final LinkedList<JsMessage> queue = new LinkedList<>();
    private ArrayList<BridgeMode> bridgeModes = new ArrayList<>();

    /* loaded from: classes.dex */
    public static abstract class BridgeMode {
        public void notifyOfFlush(NativeToJsMessageQueue nativeToJsMessageQueue, boolean z) {
        }

        public abstract void onNativeToJsMessageAvailable(NativeToJsMessageQueue nativeToJsMessageQueue);

        public void reset() {
        }
    }

    /* loaded from: classes.dex */
    public static class NoOpBridgeMode extends BridgeMode {
        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void onNativeToJsMessageAvailable(NativeToJsMessageQueue nativeToJsMessageQueue) {
        }
    }

    public void addBridgeMode(BridgeMode bridgeMode) {
        this.bridgeModes.add(bridgeMode);
    }

    public boolean isBridgeEnabled() {
        return this.activeBridgeMode != null;
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void setBridgeMode(int i) {
        if (i < -1 || i >= this.bridgeModes.size()) {
            LOG.d(LOG_TAG, "Invalid NativeToJsBridgeMode: " + i);
            return;
        }
        BridgeMode bridgeMode = i < 0 ? null : this.bridgeModes.get(i);
        if (bridgeMode != this.activeBridgeMode) {
            StringBuilder sb = new StringBuilder();
            sb.append("Set native->JS mode to ");
            sb.append(bridgeMode == null ? "null" : bridgeMode.getClass().getSimpleName());
            LOG.d(LOG_TAG, sb.toString());
            synchronized (this) {
                this.activeBridgeMode = bridgeMode;
                if (bridgeMode != null) {
                    bridgeMode.reset();
                    if (!this.paused && !this.queue.isEmpty()) {
                        bridgeMode.onNativeToJsMessageAvailable(this);
                    }
                }
            }
        }
    }

    public void reset() {
        synchronized (this) {
            this.queue.clear();
            setBridgeMode(-1);
        }
    }

    private int calculatePackedMessageLength(JsMessage jsMessage) {
        int calculateEncodedLength = jsMessage.calculateEncodedLength();
        return String.valueOf(calculateEncodedLength).length() + calculateEncodedLength + 1;
    }

    private void packMessage(JsMessage jsMessage, StringBuilder sb) {
        sb.append(jsMessage.calculateEncodedLength());
        sb.append(' ');
        jsMessage.encodeAsMessage(sb);
    }

    public String popAndEncode(boolean z) {
        synchronized (this) {
            BridgeMode bridgeMode = this.activeBridgeMode;
            if (bridgeMode == null) {
                return null;
            }
            bridgeMode.notifyOfFlush(this, z);
            if (this.queue.isEmpty()) {
                return null;
            }
            Iterator<JsMessage> it = this.queue.iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                int calculatePackedMessageLength = calculatePackedMessageLength(it.next());
                if (i > 0) {
                    int i3 = i2 + calculatePackedMessageLength;
                    int i4 = MAX_PAYLOAD_SIZE;
                    if (i3 > i4 && i4 > 0) {
                        break;
                    }
                }
                i2 += calculatePackedMessageLength;
                i++;
            }
            StringBuilder sb = new StringBuilder(i2);
            for (int i5 = 0; i5 < i; i5++) {
                packMessage(this.queue.removeFirst(), sb);
            }
            if (!this.queue.isEmpty()) {
                sb.append('*');
            }
            return sb.toString();
        }
    }

    public String popAndEncodeAsJs() {
        synchronized (this) {
            if (this.queue.size() == 0) {
                return null;
            }
            Iterator<JsMessage> it = this.queue.iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                int calculateEncodedLength = it.next().calculateEncodedLength() + 50;
                if (i > 0) {
                    int i3 = i2 + calculateEncodedLength;
                    int i4 = MAX_PAYLOAD_SIZE;
                    if (i3 > i4 && i4 > 0) {
                        break;
                    }
                }
                i2 += calculateEncodedLength;
                i++;
            }
            int i5 = i == this.queue.size() ? 1 : 0;
            StringBuilder sb = new StringBuilder(i2 + (i5 != 0 ? 0 : 100));
            for (int i6 = 0; i6 < i; i6++) {
                JsMessage removeFirst = this.queue.removeFirst();
                if (i5 != 0 && i6 + 1 == i) {
                    removeFirst.encodeAsJsMessage(sb);
                } else {
                    sb.append("try{");
                    removeFirst.encodeAsJsMessage(sb);
                    sb.append("}finally{");
                }
            }
            if (i5 == 0) {
                sb.append("window.setTimeout(function(){cordova.require('cordova/plugin/android/polling').pollOnce();},0);");
            }
            while (i5 < i) {
                sb.append('}');
                i5++;
            }
            return sb.toString();
        }
    }

    public void addJavaScript(String str) {
        enqueueMessage(new JsMessage(str));
    }

    public void addPluginResult(PluginResult pluginResult, String str) {
        if (str == null) {
            LOG.e(LOG_TAG, "Got plugin result with no callbackId", new Throwable());
            return;
        }
        boolean z = pluginResult.getStatus() == PluginResult.Status.NO_RESULT.ordinal();
        boolean keepCallback = pluginResult.getKeepCallback();
        if (z && keepCallback) {
            return;
        }
        enqueueMessage(new JsMessage(pluginResult, str));
    }

    private void enqueueMessage(JsMessage jsMessage) {
        synchronized (this) {
            if (this.activeBridgeMode == null) {
                LOG.d(LOG_TAG, "Dropping Native->JS message due to disabled bridge");
                return;
            }
            this.queue.add(jsMessage);
            if (!this.paused) {
                this.activeBridgeMode.onNativeToJsMessageAvailable(this);
            }
        }
    }

    public void setPaused(boolean z) {
        BridgeMode bridgeMode;
        if (this.paused && z) {
            LOG.e(LOG_TAG, "nested call to setPaused detected.", new Throwable());
        }
        this.paused = z;
        if (z) {
            return;
        }
        synchronized (this) {
            if (!this.queue.isEmpty() && (bridgeMode = this.activeBridgeMode) != null) {
                bridgeMode.onNativeToJsMessageAvailable(this);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class LoadUrlBridgeMode extends BridgeMode {
        private final CordovaInterface cordova;
        private final CordovaWebViewEngine engine;

        public LoadUrlBridgeMode(CordovaWebViewEngine cordovaWebViewEngine, CordovaInterface cordovaInterface) {
            this.engine = cordovaWebViewEngine;
            this.cordova = cordovaInterface;
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void onNativeToJsMessageAvailable(final NativeToJsMessageQueue nativeToJsMessageQueue) {
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.NativeToJsMessageQueue.LoadUrlBridgeMode.1
                @Override // java.lang.Runnable
                public void run() {
                    String popAndEncodeAsJs = nativeToJsMessageQueue.popAndEncodeAsJs();
                    if (popAndEncodeAsJs != null) {
                        CordovaWebViewEngine cordovaWebViewEngine = LoadUrlBridgeMode.this.engine;
                        cordovaWebViewEngine.loadUrl("javascript:" + popAndEncodeAsJs, false);
                    }
                }
            });
        }
    }

    /* loaded from: classes.dex */
    public static class OnlineEventsBridgeMode extends BridgeMode {
        private final OnlineEventsBridgeModeDelegate delegate;
        private boolean ignoreNextFlush;
        private boolean online;

        /* loaded from: classes.dex */
        public interface OnlineEventsBridgeModeDelegate {
            void runOnUiThread(Runnable runnable);

            void setNetworkAvailable(boolean z);
        }

        public OnlineEventsBridgeMode(OnlineEventsBridgeModeDelegate onlineEventsBridgeModeDelegate) {
            this.delegate = onlineEventsBridgeModeDelegate;
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void reset() {
            this.delegate.runOnUiThread(new Runnable() { // from class: org.apache.cordova.NativeToJsMessageQueue.OnlineEventsBridgeMode.1
                @Override // java.lang.Runnable
                public void run() {
                    OnlineEventsBridgeMode.this.online = false;
                    OnlineEventsBridgeMode.this.ignoreNextFlush = true;
                    OnlineEventsBridgeMode.this.delegate.setNetworkAvailable(true);
                }
            });
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void onNativeToJsMessageAvailable(final NativeToJsMessageQueue nativeToJsMessageQueue) {
            this.delegate.runOnUiThread(new Runnable() { // from class: org.apache.cordova.NativeToJsMessageQueue.OnlineEventsBridgeMode.2
                @Override // java.lang.Runnable
                public void run() {
                    if (nativeToJsMessageQueue.isEmpty()) {
                        return;
                    }
                    OnlineEventsBridgeMode.this.ignoreNextFlush = false;
                    OnlineEventsBridgeMode.this.delegate.setNetworkAvailable(OnlineEventsBridgeMode.this.online);
                }
            });
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void notifyOfFlush(NativeToJsMessageQueue nativeToJsMessageQueue, boolean z) {
            if (!z || this.ignoreNextFlush) {
                return;
            }
            this.online = !this.online;
        }
    }

    /* loaded from: classes.dex */
    public static class EvalBridgeMode extends BridgeMode {
        private final CordovaInterface cordova;
        private final CordovaWebViewEngine engine;

        public EvalBridgeMode(CordovaWebViewEngine cordovaWebViewEngine, CordovaInterface cordovaInterface) {
            this.engine = cordovaWebViewEngine;
            this.cordova = cordovaInterface;
        }

        @Override // org.apache.cordova.NativeToJsMessageQueue.BridgeMode
        public void onNativeToJsMessageAvailable(final NativeToJsMessageQueue nativeToJsMessageQueue) {
            this.cordova.getActivity().runOnUiThread(new Runnable() { // from class: org.apache.cordova.NativeToJsMessageQueue.EvalBridgeMode.1
                @Override // java.lang.Runnable
                public void run() {
                    String popAndEncodeAsJs = nativeToJsMessageQueue.popAndEncodeAsJs();
                    if (popAndEncodeAsJs != null) {
                        EvalBridgeMode.this.engine.evaluateJavascript(popAndEncodeAsJs, null);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class JsMessage {
        final String jsPayloadOrCallbackId;
        final PluginResult pluginResult;

        JsMessage(String str) {
            Objects.requireNonNull(str);
            this.jsPayloadOrCallbackId = str;
            this.pluginResult = null;
        }

        JsMessage(PluginResult pluginResult, String str) {
            if (str == null || pluginResult == null) {
                throw null;
            }
            this.jsPayloadOrCallbackId = str;
            this.pluginResult = pluginResult;
        }

        static int calculateEncodedLengthHelper(PluginResult pluginResult) {
            switch (pluginResult.getMessageType()) {
                case 1:
                    return pluginResult.getStrMessage().length() + 1;
                case 2:
                default:
                    return pluginResult.getMessage().length();
                case 3:
                    return pluginResult.getMessage().length() + 1;
                case 4:
                case 5:
                    return 1;
                case 6:
                    return pluginResult.getMessage().length() + 1;
                case 7:
                    return pluginResult.getMessage().length() + 1;
                case 8:
                    int i = 1;
                    for (int i2 = 0; i2 < pluginResult.getMultipartMessagesSize(); i2++) {
                        int calculateEncodedLengthHelper = calculateEncodedLengthHelper(pluginResult.getMultipartMessage(i2));
                        i += String.valueOf(calculateEncodedLengthHelper).length() + 1 + calculateEncodedLengthHelper;
                    }
                    return i;
            }
        }

        int calculateEncodedLength() {
            PluginResult pluginResult = this.pluginResult;
            if (pluginResult == null) {
                return this.jsPayloadOrCallbackId.length() + 1;
            }
            return String.valueOf(pluginResult.getStatus()).length() + 2 + 1 + this.jsPayloadOrCallbackId.length() + 1 + calculateEncodedLengthHelper(this.pluginResult);
        }

        static void encodeAsMessageHelper(StringBuilder sb, PluginResult pluginResult) {
            switch (pluginResult.getMessageType()) {
                case 1:
                    sb.append('s');
                    sb.append(pluginResult.getStrMessage());
                    return;
                case 2:
                default:
                    sb.append(pluginResult.getMessage());
                    return;
                case 3:
                    sb.append('n');
                    sb.append(pluginResult.getMessage());
                    return;
                case 4:
                    sb.append(pluginResult.getMessage().charAt(0));
                    return;
                case 5:
                    sb.append('N');
                    return;
                case 6:
                    sb.append('A');
                    sb.append(pluginResult.getMessage());
                    return;
                case 7:
                    sb.append('S');
                    sb.append(pluginResult.getMessage());
                    return;
                case 8:
                    sb.append('M');
                    for (int i = 0; i < pluginResult.getMultipartMessagesSize(); i++) {
                        PluginResult multipartMessage = pluginResult.getMultipartMessage(i);
                        sb.append(String.valueOf(calculateEncodedLengthHelper(multipartMessage)));
                        sb.append(' ');
                        encodeAsMessageHelper(sb, multipartMessage);
                    }
                    return;
            }
        }

        void encodeAsMessage(StringBuilder sb) {
            PluginResult pluginResult = this.pluginResult;
            if (pluginResult == null) {
                sb.append('J');
                sb.append(this.jsPayloadOrCallbackId);
                return;
            }
            int status = pluginResult.getStatus();
            boolean z = status == PluginResult.Status.NO_RESULT.ordinal();
            boolean z2 = status == PluginResult.Status.OK.ordinal();
            boolean keepCallback = this.pluginResult.getKeepCallback();
            sb.append((z || z2) ? 'S' : 'F');
            sb.append(keepCallback ? '1' : '0');
            sb.append(status);
            sb.append(' ');
            sb.append(this.jsPayloadOrCallbackId);
            sb.append(' ');
            encodeAsMessageHelper(sb, this.pluginResult);
        }

        void buildJsMessage(StringBuilder sb) {
            int messageType = this.pluginResult.getMessageType();
            if (messageType == 6) {
                sb.append("cordova.require('cordova/base64').toArrayBuffer('");
                sb.append(this.pluginResult.getMessage());
                sb.append("')");
            } else if (messageType == 7) {
                sb.append("atob('");
                sb.append(this.pluginResult.getMessage());
                sb.append("')");
            } else if (messageType == 8) {
                int multipartMessagesSize = this.pluginResult.getMultipartMessagesSize();
                for (int i = 0; i < multipartMessagesSize; i++) {
                    new JsMessage(this.pluginResult.getMultipartMessage(i), this.jsPayloadOrCallbackId).buildJsMessage(sb);
                    if (i < multipartMessagesSize - 1) {
                        sb.append(",");
                    }
                }
            } else {
                sb.append(this.pluginResult.getMessage());
            }
        }

        void encodeAsJsMessage(StringBuilder sb) {
            PluginResult pluginResult = this.pluginResult;
            if (pluginResult == null) {
                sb.append(this.jsPayloadOrCallbackId);
                return;
            }
            int status = pluginResult.getStatus();
            boolean z = status == PluginResult.Status.OK.ordinal() || status == PluginResult.Status.NO_RESULT.ordinal();
            sb.append("cordova.callbackFromNative('");
            sb.append(this.jsPayloadOrCallbackId);
            sb.append("',");
            sb.append(z);
            sb.append(",");
            sb.append(status);
            sb.append(",[");
            buildJsMessage(sb);
            sb.append("],");
            sb.append(this.pluginResult.getKeepCallback());
            sb.append(");");
        }
    }
}
