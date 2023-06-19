package com.google.zxing.client.android.wifi;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import com.google.zxing.client.result.WifiParsedResult;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.text.Typography;
/* loaded from: classes.dex */
public final class WifiConfigManager extends AsyncTask<WifiParsedResult, Object, Object> {
    private static final Pattern HEX_DIGITS = Pattern.compile("[0-9A-Fa-f]+");
    private static final String TAG = "WifiConfigManager";
    private final WifiManager wifiManager;

    public WifiConfigManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Object doInBackground(WifiParsedResult... wifiParsedResultArr) {
        int i = 0;
        WifiParsedResult wifiParsedResult = wifiParsedResultArr[0];
        if (!this.wifiManager.isWifiEnabled()) {
            String str = TAG;
            Log.i(str, "Enabling wi-fi...");
            if (this.wifiManager.setWifiEnabled(true)) {
                Log.i(str, "Wi-fi enabled");
                while (!this.wifiManager.isWifiEnabled()) {
                    if (i >= 10) {
                        Log.i(TAG, "Took too long to enable wi-fi, quitting");
                        return null;
                    }
                    Log.i(TAG, "Still waiting for wi-fi to enable...");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException unused) {
                    }
                    i++;
                }
            } else {
                Log.w(str, "Wi-fi could not be enabled!");
                return null;
            }
        }
        String networkEncryption = wifiParsedResult.getNetworkEncryption();
        try {
            NetworkType forIntentValue = NetworkType.forIntentValue(networkEncryption);
            if (forIntentValue == NetworkType.NO_PASSWORD) {
                changeNetworkUnEncrypted(this.wifiManager, wifiParsedResult);
            } else {
                String password = wifiParsedResult.getPassword();
                if (password != null && !password.isEmpty()) {
                    if (forIntentValue == NetworkType.WEP) {
                        changeNetworkWEP(this.wifiManager, wifiParsedResult);
                    } else if (forIntentValue == NetworkType.WPA) {
                        changeNetworkWPA(this.wifiManager, wifiParsedResult);
                    }
                }
            }
            return null;
        } catch (IllegalArgumentException unused2) {
            String str2 = TAG;
            Log.w(str2, "Bad network type; see NetworkType values: " + networkEncryption);
            return null;
        }
    }

    private static void updateNetwork(WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
        Integer findNetworkInExistingConfig = findNetworkInExistingConfig(wifiManager, wifiConfiguration.SSID);
        if (findNetworkInExistingConfig != null) {
            String str = TAG;
            Log.i(str, "Removing old configuration for network " + wifiConfiguration.SSID);
            wifiManager.removeNetwork(findNetworkInExistingConfig.intValue());
            wifiManager.saveConfiguration();
        }
        int addNetwork = wifiManager.addNetwork(wifiConfiguration);
        if (addNetwork >= 0) {
            if (wifiManager.enableNetwork(addNetwork, true)) {
                String str2 = TAG;
                Log.i(str2, "Associating to network " + wifiConfiguration.SSID);
                wifiManager.saveConfiguration();
                return;
            }
            String str3 = TAG;
            Log.w(str3, "Failed to enable network " + wifiConfiguration.SSID);
            return;
        }
        String str4 = TAG;
        Log.w(str4, "Unable to add network " + wifiConfiguration.SSID);
    }

    private static WifiConfiguration changeNetworkCommon(WifiParsedResult wifiParsedResult) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = quoteNonHex(wifiParsedResult.getSsid(), new int[0]);
        wifiConfiguration.hiddenSSID = wifiParsedResult.isHidden();
        return wifiConfiguration;
    }

    private static void changeNetworkWEP(WifiManager wifiManager, WifiParsedResult wifiParsedResult) {
        WifiConfiguration changeNetworkCommon = changeNetworkCommon(wifiParsedResult);
        changeNetworkCommon.wepKeys[0] = quoteNonHex(wifiParsedResult.getPassword(), 10, 26, 58);
        changeNetworkCommon.wepTxKeyIndex = 0;
        changeNetworkCommon.allowedAuthAlgorithms.set(1);
        changeNetworkCommon.allowedKeyManagement.set(0);
        changeNetworkCommon.allowedGroupCiphers.set(2);
        changeNetworkCommon.allowedGroupCiphers.set(3);
        changeNetworkCommon.allowedGroupCiphers.set(0);
        changeNetworkCommon.allowedGroupCiphers.set(1);
        updateNetwork(wifiManager, changeNetworkCommon);
    }

    private static void changeNetworkWPA(WifiManager wifiManager, WifiParsedResult wifiParsedResult) {
        WifiConfiguration changeNetworkCommon = changeNetworkCommon(wifiParsedResult);
        changeNetworkCommon.preSharedKey = quoteNonHex(wifiParsedResult.getPassword(), 64);
        changeNetworkCommon.allowedAuthAlgorithms.set(0);
        changeNetworkCommon.allowedProtocols.set(0);
        changeNetworkCommon.allowedProtocols.set(1);
        changeNetworkCommon.allowedKeyManagement.set(1);
        changeNetworkCommon.allowedKeyManagement.set(2);
        changeNetworkCommon.allowedPairwiseCiphers.set(1);
        changeNetworkCommon.allowedPairwiseCiphers.set(2);
        changeNetworkCommon.allowedGroupCiphers.set(2);
        changeNetworkCommon.allowedGroupCiphers.set(3);
        updateNetwork(wifiManager, changeNetworkCommon);
    }

    private static void changeNetworkUnEncrypted(WifiManager wifiManager, WifiParsedResult wifiParsedResult) {
        WifiConfiguration changeNetworkCommon = changeNetworkCommon(wifiParsedResult);
        changeNetworkCommon.allowedKeyManagement.set(0);
        updateNetwork(wifiManager, changeNetworkCommon);
    }

    private static Integer findNetworkInExistingConfig(WifiManager wifiManager, String str) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                String str2 = wifiConfiguration.SSID;
                if (str2 != null && str2.equals(str)) {
                    return Integer.valueOf(wifiConfiguration.networkId);
                }
            }
            return null;
        }
        return null;
    }

    private static String quoteNonHex(String str, int... iArr) {
        return isHexOfLength(str, iArr) ? str : convertToQuotedString(str);
    }

    private static String convertToQuotedString(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
            return str;
        }
        return Typography.quote + str + Typography.quote;
    }

    private static boolean isHexOfLength(CharSequence charSequence, int... iArr) {
        if (charSequence != null && HEX_DIGITS.matcher(charSequence).matches()) {
            if (iArr.length == 0) {
                return true;
            }
            for (int i : iArr) {
                if (charSequence.length() == i) {
                    return true;
                }
            }
        }
        return false;
    }
}
