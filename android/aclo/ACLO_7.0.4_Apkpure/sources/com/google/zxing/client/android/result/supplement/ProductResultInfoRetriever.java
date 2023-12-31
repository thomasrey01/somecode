package com.google.zxing.client.android.result.supplement;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.zxing.client.android.HttpHelper;
import com.google.zxing.client.android.LocaleManager;
import com.google.zxing.client.android.history.HistoryManager;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
final class ProductResultInfoRetriever extends SupplementalInfoRetriever {
    private static final Pattern[] PRODUCT_NAME_PRICE_PATTERNS = {Pattern.compile(",event\\)\">([^<]+)</a></h3>.+<span class=psrp>([^<]+)</span>"), Pattern.compile("owb63p\">([^<]+).+zdi3pb\">([^<]+)")};
    private final Context context;
    private final String productID;
    private final String source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ProductResultInfoRetriever(TextView textView, String str, HistoryManager historyManager, Context context) {
        super(textView, historyManager);
        this.productID = str;
        this.source = context.getString(R.string.msg_google_product);
        this.context = context;
    }

    @Override // com.google.zxing.client.android.result.supplement.SupplementalInfoRetriever
    void retrieveSupplementalInfo() throws IOException {
        String str = "https://www.google." + LocaleManager.getProductSearchCountryTLD(this.context) + "/m/products?ie=utf8&oe=utf8&scoring=p&source=zxing&q=" + URLEncoder.encode(this.productID, "UTF-8");
        CharSequence downloadViaHttp = HttpHelper.downloadViaHttp(str, HttpHelper.ContentType.HTML);
        for (Pattern pattern : PRODUCT_NAME_PRICE_PATTERNS) {
            Matcher matcher = pattern.matcher(downloadViaHttp);
            if (matcher.find()) {
                append(this.productID, this.source, new String[]{unescapeHTML(matcher.group(1)), unescapeHTML(matcher.group(2))}, str);
                return;
            }
        }
    }

    private static String unescapeHTML(String str) {
        return Html.fromHtml(str).toString();
    }
}
