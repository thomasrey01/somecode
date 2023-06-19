package com.google.zxing.client.android.history;

import com.google.zxing.Result;
/* loaded from: classes.dex */
public final class HistoryItem {
    private final String details;
    private final String display;
    private final Result result;

    /* JADX INFO: Access modifiers changed from: package-private */
    public HistoryItem(Result result, String str, String str2) {
        this.result = result;
        this.display = str;
        this.details = str2;
    }

    public Result getResult() {
        return this.result;
    }

    public String getDisplayAndDetails() {
        StringBuilder sb = new StringBuilder();
        String str = this.display;
        if (str == null || str.isEmpty()) {
            sb.append(this.result.getText());
        } else {
            sb.append(this.display);
        }
        String str2 = this.details;
        if (str2 != null && !str2.isEmpty()) {
            sb.append(" : ");
            sb.append(this.details);
        }
        return sb.toString();
    }
}
