package com.google.zxing.client.android.result.supplement;

import android.text.Html;
import android.widget.TextView;
import com.google.zxing.client.android.HttpHelper;
import com.google.zxing.client.android.history.HistoryManager;
import com.google.zxing.client.result.URIParsedResult;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
final class TitleRetriever extends SupplementalInfoRetriever {
    private static final int MAX_TITLE_LEN = 100;
    private static final Pattern TITLE_PATTERN = Pattern.compile("<title>([^<]+)");
    private final String httpUrl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TitleRetriever(TextView textView, URIParsedResult uRIParsedResult, HistoryManager historyManager) {
        super(textView, historyManager);
        this.httpUrl = uRIParsedResult.getURI();
    }

    @Override // com.google.zxing.client.android.result.supplement.SupplementalInfoRetriever
    void retrieveSupplementalInfo() {
        String group;
        try {
            CharSequence downloadViaHttp = HttpHelper.downloadViaHttp(this.httpUrl, HttpHelper.ContentType.HTML, 4096);
            if (downloadViaHttp == null || downloadViaHttp.length() <= 0) {
                return;
            }
            Matcher matcher = TITLE_PATTERN.matcher(downloadViaHttp);
            if (!matcher.find() || (group = matcher.group(1)) == null || group.isEmpty()) {
                return;
            }
            String obj = Html.fromHtml(group).toString();
            if (obj.length() > 100) {
                obj = obj.substring(0, 100) + "...";
            }
            String str = this.httpUrl;
            append(str, null, new String[]{obj}, str);
        } catch (IOException unused) {
        }
    }
}
