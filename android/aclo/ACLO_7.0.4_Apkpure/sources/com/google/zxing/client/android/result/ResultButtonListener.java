package com.google.zxing.client.android.result;

import android.view.View;
/* loaded from: classes.dex */
public final class ResultButtonListener implements View.OnClickListener {
    private final int index;
    private final ResultHandler resultHandler;

    public ResultButtonListener(ResultHandler resultHandler, int i) {
        this.resultHandler = resultHandler;
        this.index = i;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.resultHandler.handleButtonPress(this.index);
    }
}
