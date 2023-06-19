package com.google.zxing.client.android.result.supplement;

import android.content.Context;
import android.widget.TextView;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.client.android.HttpHelper;
import com.google.zxing.client.android.LocaleManager;
import com.google.zxing.client.android.history.HistoryManager;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
/* loaded from: classes.dex */
final class BookResultInfoRetriever extends SupplementalInfoRetriever {
    private final Context context;
    private final String isbn;
    private final String source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BookResultInfoRetriever(TextView textView, String str, HistoryManager historyManager, Context context) {
        super(textView, historyManager);
        this.isbn = str;
        this.source = context.getString(R.string.msg_google_books);
        this.context = context;
    }

    @Override // com.google.zxing.client.android.result.supplement.SupplementalInfoRetriever
    void retrieveSupplementalInfo() throws IOException {
        JSONObject jSONObject;
        ArrayList arrayList;
        CharSequence downloadViaHttp = HttpHelper.downloadViaHttp("https://www.googleapis.com/books/v1/volumes?q=isbn:" + this.isbn, HttpHelper.ContentType.JSON);
        if (downloadViaHttp.length() == 0) {
            return;
        }
        try {
            JSONArray optJSONArray = ((JSONObject) new JSONTokener(downloadViaHttp.toString()).nextValue()).optJSONArray(FirebaseAnalytics.Param.ITEMS);
            if (optJSONArray != null) {
                if (optJSONArray.isNull(0) || (jSONObject = ((JSONObject) optJSONArray.get(0)).getJSONObject("volumeInfo")) == null) {
                    return;
                }
                String optString = jSONObject.optString("title");
                String optString2 = jSONObject.optString("pageCount");
                JSONArray optJSONArray2 = jSONObject.optJSONArray("authors");
                String str = null;
                if (optJSONArray2 == null || optJSONArray2.isNull(0)) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(optJSONArray2.length());
                    for (int i = 0; i < optJSONArray2.length(); i++) {
                        arrayList.add(optJSONArray2.getString(i));
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                maybeAddText(optString, arrayList2);
                maybeAddTextSeries(arrayList, arrayList2);
                if (optString2 != null && !optString2.isEmpty()) {
                    str = optString2 + "pp.";
                }
                maybeAddText(str, arrayList2);
                append(this.isbn, this.source, (String[]) arrayList2.toArray(new String[arrayList2.size()]), ("http://www.google." + LocaleManager.getBookSearchCountryTLD(this.context) + "/search?tbm=bks&source=zxing&q=") + this.isbn);
            }
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}
