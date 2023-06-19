package com.google.zxing.client.android;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import barcodescanner.xservices.nl.barcodescanner.R;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
/* loaded from: classes.dex */
public final class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private CheckBoxPreference[] checkBoxPrefs;

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        this.checkBoxPrefs = findDecodePrefs(preferenceScreen, PreferencesActivity.KEY_DECODE_1D_PRODUCT, PreferencesActivity.KEY_DECODE_1D_INDUSTRIAL, PreferencesActivity.KEY_DECODE_QR, PreferencesActivity.KEY_DECODE_DATA_MATRIX, PreferencesActivity.KEY_DECODE_AZTEC, PreferencesActivity.KEY_DECODE_PDF417);
        disableLastCheckedPref();
        ((EditTextPreference) preferenceScreen.findPreference(PreferencesActivity.KEY_CUSTOM_PRODUCT_SEARCH)).setOnPreferenceChangeListener(new CustomSearchURLValidator());
    }

    private static CheckBoxPreference[] findDecodePrefs(PreferenceScreen preferenceScreen, String... strArr) {
        CheckBoxPreference[] checkBoxPreferenceArr = new CheckBoxPreference[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            checkBoxPreferenceArr[i] = (CheckBoxPreference) preferenceScreen.findPreference(strArr[i]);
        }
        return checkBoxPreferenceArr;
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        disableLastCheckedPref();
    }

    private void disableLastCheckedPref() {
        CheckBoxPreference[] checkBoxPreferenceArr;
        CheckBoxPreference[] checkBoxPreferenceArr2;
        ArrayList arrayList = new ArrayList(this.checkBoxPrefs.length);
        for (CheckBoxPreference checkBoxPreference : this.checkBoxPrefs) {
            if (checkBoxPreference.isChecked()) {
                arrayList.add(checkBoxPreference);
            }
        }
        boolean z = arrayList.size() <= 1;
        for (CheckBoxPreference checkBoxPreference2 : this.checkBoxPrefs) {
            checkBoxPreference2.setEnabled((z && arrayList.contains(checkBoxPreference2)) ? false : true);
        }
    }

    /* loaded from: classes.dex */
    private class CustomSearchURLValidator implements Preference.OnPreferenceChangeListener {
        private CustomSearchURLValidator() {
        }

        @Override // android.preference.Preference.OnPreferenceChangeListener
        public boolean onPreferenceChange(Preference preference, Object obj) {
            if (isValid(obj)) {
                return true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(PreferencesFragment.this.getActivity());
            builder.setTitle(R.string.msg_error);
            builder.setMessage(R.string.msg_invalid_value);
            builder.setCancelable(true);
            builder.show();
            return false;
        }

        private boolean isValid(Object obj) {
            if (obj == null) {
                return true;
            }
            String obj2 = obj.toString();
            if (obj2.isEmpty()) {
                return true;
            }
            try {
                return new URI(obj2.replaceAll("%[st]", "").replaceAll("%f(?![0-9a-f])", "")).getScheme() != null;
            } catch (URISyntaxException unused) {
                return false;
            }
        }
    }
}
