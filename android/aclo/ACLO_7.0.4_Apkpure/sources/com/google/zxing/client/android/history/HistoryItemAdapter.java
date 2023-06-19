package com.google.zxing.client.android.history;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import barcodescanner.xservices.nl.barcodescanner.R;
import com.google.zxing.Result;
import java.util.ArrayList;
/* loaded from: classes.dex */
final class HistoryItemAdapter extends ArrayAdapter<HistoryItem> {
    private final Context activity;

    /* JADX INFO: Access modifiers changed from: package-private */
    public HistoryItemAdapter(Context context) {
        super(context, R.layout.history_list_item, new ArrayList());
        this.activity = context;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        String string;
        String string2;
        if (!(view instanceof LinearLayout)) {
            view = LayoutInflater.from(this.activity).inflate(R.layout.history_list_item, viewGroup, false);
        }
        HistoryItem item = getItem(i);
        Result result = item.getResult();
        if (result != null) {
            string = result.getText();
            string2 = item.getDisplayAndDetails();
        } else {
            Resources resources = getContext().getResources();
            string = resources.getString(R.string.history_empty);
            string2 = resources.getString(R.string.history_empty_detail);
        }
        ((TextView) view.findViewById(R.id.history_title)).setText(string);
        ((TextView) view.findViewById(R.id.history_detail)).setText(string2);
        return view;
    }
}
