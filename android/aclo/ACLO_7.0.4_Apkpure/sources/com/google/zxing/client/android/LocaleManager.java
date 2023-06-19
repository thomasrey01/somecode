package com.google.zxing.client.android;

import android.content.Context;
import android.preference.PreferenceManager;
import com.google.firebase.dynamiclinks.DynamicLink;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/* loaded from: classes.dex */
public final class LocaleManager {
    private static final String DEFAULT_COUNTRY = "US";
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String DEFAULT_TLD = "com";
    private static final Map<String, String> GOOGLE_BOOK_SEARCH_COUNTRY_TLD;
    private static final Map<String, String> GOOGLE_COUNTRY_TLD;
    private static final Map<String, String> GOOGLE_PRODUCT_SEARCH_COUNTRY_TLD;
    private static final Collection<String> TRANSLATED_HELP_ASSET_LANGUAGES;

    static {
        HashMap hashMap = new HashMap();
        GOOGLE_COUNTRY_TLD = hashMap;
        hashMap.put("AR", "com.ar");
        hashMap.put("AU", "com.au");
        hashMap.put("BR", "com.br");
        hashMap.put("BG", "bg");
        hashMap.put(Locale.CANADA.getCountry(), "ca");
        hashMap.put(Locale.CHINA.getCountry(), "cn");
        hashMap.put("CZ", "cz");
        hashMap.put("DK", "dk");
        hashMap.put("FI", "fi");
        hashMap.put(Locale.FRANCE.getCountry(), "fr");
        hashMap.put(Locale.GERMANY.getCountry(), "de");
        hashMap.put("GR", "gr");
        hashMap.put("HU", "hu");
        hashMap.put("ID", "co.id");
        hashMap.put("IL", "co.il");
        hashMap.put(Locale.ITALY.getCountry(), "it");
        hashMap.put(Locale.JAPAN.getCountry(), "co.jp");
        hashMap.put(Locale.KOREA.getCountry(), "co.kr");
        hashMap.put("NL", "nl");
        hashMap.put("PL", "pl");
        hashMap.put("PT", DynamicLink.ItunesConnectAnalyticsParameters.KEY_ITUNES_CONNECT_PT);
        hashMap.put("RO", "ro");
        hashMap.put("RU", "ru");
        hashMap.put("SK", "sk");
        hashMap.put("SI", DynamicLink.SocialMetaTagParameters.KEY_SOCIAL_IMAGE_LINK);
        hashMap.put("ES", "es");
        hashMap.put("SE", "se");
        hashMap.put("CH", "ch");
        hashMap.put(Locale.TAIWAN.getCountry(), "tw");
        hashMap.put("TR", "com.tr");
        hashMap.put("UA", "com.ua");
        hashMap.put(Locale.UK.getCountry(), "co.uk");
        hashMap.put(Locale.US.getCountry(), DEFAULT_TLD);
        HashMap hashMap2 = new HashMap();
        GOOGLE_PRODUCT_SEARCH_COUNTRY_TLD = hashMap2;
        hashMap2.put("AU", "com.au");
        hashMap2.put(Locale.FRANCE.getCountry(), "fr");
        hashMap2.put(Locale.GERMANY.getCountry(), "de");
        hashMap2.put(Locale.ITALY.getCountry(), "it");
        hashMap2.put(Locale.JAPAN.getCountry(), "co.jp");
        hashMap2.put("NL", "nl");
        hashMap2.put("ES", "es");
        hashMap2.put("CH", "ch");
        hashMap2.put(Locale.UK.getCountry(), "co.uk");
        hashMap2.put(Locale.US.getCountry(), DEFAULT_TLD);
        GOOGLE_BOOK_SEARCH_COUNTRY_TLD = hashMap;
        TRANSLATED_HELP_ASSET_LANGUAGES = Arrays.asList("de", DEFAULT_LANGUAGE, "es", "fr", "it", "ja", "ko", "nl", DynamicLink.ItunesConnectAnalyticsParameters.KEY_ITUNES_CONNECT_PT, "ru", "uk", "zh-rCN", "zh-rTW", "zh-rHK");
    }

    private LocaleManager() {
    }

    public static String getCountryTLD(Context context) {
        return doGetTLD(GOOGLE_COUNTRY_TLD, context);
    }

    public static String getProductSearchCountryTLD(Context context) {
        return doGetTLD(GOOGLE_PRODUCT_SEARCH_COUNTRY_TLD, context);
    }

    public static String getBookSearchCountryTLD(Context context) {
        return doGetTLD(GOOGLE_BOOK_SEARCH_COUNTRY_TLD, context);
    }

    public static boolean isBookSearchUrl(String str) {
        return str.startsWith("http://google.com/books") || str.startsWith("http://books.google.");
    }

    private static String getSystemCountry() {
        Locale locale = Locale.getDefault();
        return locale == null ? DEFAULT_COUNTRY : locale.getCountry();
    }

    private static String getSystemLanguage() {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return DEFAULT_LANGUAGE;
        }
        String language = locale.getLanguage();
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(language)) {
            return language + "-r" + getSystemCountry();
        }
        return language;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getTranslatedAssetLanguage() {
        String systemLanguage = getSystemLanguage();
        return TRANSLATED_HELP_ASSET_LANGUAGES.contains(systemLanguage) ? systemLanguage : DEFAULT_LANGUAGE;
    }

    private static String doGetTLD(Map<String, String> map, Context context) {
        String str = map.get(getCountry(context));
        return str == null ? DEFAULT_TLD : str;
    }

    private static String getCountry(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(PreferencesActivity.KEY_SEARCH_COUNTRY, "-");
        return (string == null || string.isEmpty() || "-".equals(string)) ? getSystemCountry() : string;
    }
}
