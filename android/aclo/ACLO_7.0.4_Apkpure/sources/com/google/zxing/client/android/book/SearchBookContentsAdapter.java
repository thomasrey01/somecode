package com.google.zxing.client.android.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import barcodescanner.xservices.nl.barcodescanner.R;
import java.util.List;
/* loaded from: classes.dex */
final class SearchBookContentsAdapter extends ArrayAdapter<SearchBookContentsResult> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public SearchBookContentsAdapter(Context context, List<SearchBookContentsResult> list) {
        super(context, R.layout.search_book_contents_list_item, 0, list);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        SearchBookContentsListItem searchBookContentsListItem;
        SearchBookContentsListItem searchBookContentsListItem2;
        if (view == null) {
            searchBookContentsListItem2 = (SearchBookContentsListItem) LayoutInflater.from(getContext()).inflate(R.layout.search_book_contents_list_item, viewGroup, false);
        } else {
            boolean z = view instanceof SearchBookContentsListItem;
            searchBookContentsListItem = view;
            if (z) {
                searchBookContentsListItem2 = (SearchBookContentsListItem) view;
            }
            return searchBookContentsListItem;
        }
        searchBookContentsListItem2.set(getItem(i));
        searchBookContentsListItem = searchBookContentsListItem2;
        return searchBookContentsListItem;
    }
}
