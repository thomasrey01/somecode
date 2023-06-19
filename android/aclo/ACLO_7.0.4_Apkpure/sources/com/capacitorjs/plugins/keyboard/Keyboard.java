package com.capacitorjs.plugins.keyboard;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.Logger;
/* loaded from: classes.dex */
public class Keyboard {
    static final String EVENT_KB_DID_HIDE = "keyboardDidHide";
    static final String EVENT_KB_DID_SHOW = "keyboardDidShow";
    static final String EVENT_KB_WILL_HIDE = "keyboardWillHide";
    static final String EVENT_KB_WILL_SHOW = "keyboardWillShow";
    private AppCompatActivity activity;
    private FrameLayout.LayoutParams frameLayoutParams;
    private KeyboardEventListener keyboardEventListener;
    private ViewTreeObserver.OnGlobalLayoutListener list;
    private View mChildOfContent;
    private View rootView;
    private int usableHeightPrevious;

    /* loaded from: classes.dex */
    interface KeyboardEventListener {
        void onKeyboardEvent(String event, int size);
    }

    public KeyboardEventListener getKeyboardEventListener() {
        return this.keyboardEventListener;
    }

    public void setKeyboardEventListener(KeyboardEventListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    public Keyboard(AppCompatActivity activity) {
        this(activity, false);
    }

    public Keyboard(final AppCompatActivity activity, final boolean resizeOnFullScreen) {
        this.activity = activity;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final float f = displayMetrics.density;
        FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView().findViewById(16908290);
        this.rootView = frameLayout.getRootView();
        this.list = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.capacitorjs.plugins.keyboard.Keyboard.1
            int previousHeightDiff = 0;

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (resizeOnFullScreen) {
                    possiblyResizeChildOfContent();
                }
                Rect rect = new Rect();
                Keyboard.this.rootView.getWindowVisibleDisplayFrame(rect);
                int height = Keyboard.this.rootView.getRootView().getHeight();
                int i = rect.bottom;
                if (Build.VERSION.SDK_INT >= 23) {
                    i += Keyboard.this.rootView.getRootWindowInsets().getStableInsetBottom();
                } else {
                    Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
                    Point point = new Point();
                    defaultDisplay.getSize(point);
                    height = point.y;
                }
                int i2 = (int) ((height - i) / f);
                if (Keyboard.this.keyboardEventListener != null) {
                    if (i2 > 100 && i2 != this.previousHeightDiff) {
                        Keyboard.this.keyboardEventListener.onKeyboardEvent(Keyboard.EVENT_KB_WILL_SHOW, i2);
                        Keyboard.this.keyboardEventListener.onKeyboardEvent(Keyboard.EVENT_KB_DID_SHOW, i2);
                    } else {
                        int i3 = this.previousHeightDiff;
                        if (i2 != i3 && i3 - i2 > 100) {
                            Keyboard.this.keyboardEventListener.onKeyboardEvent(Keyboard.EVENT_KB_WILL_HIDE, 0);
                            Keyboard.this.keyboardEventListener.onKeyboardEvent(Keyboard.EVENT_KB_DID_HIDE, 0);
                        }
                    }
                } else {
                    Logger.warn("Native Keyboard Event Listener not found");
                }
                this.previousHeightDiff = i2;
            }

            private void possiblyResizeChildOfContent() {
                int computeUsableHeight = computeUsableHeight();
                if (Keyboard.this.usableHeightPrevious != computeUsableHeight) {
                    Keyboard.this.frameLayoutParams.height = computeUsableHeight;
                    Keyboard.this.mChildOfContent.requestLayout();
                    Keyboard.this.usableHeightPrevious = computeUsableHeight;
                }
            }

            private int computeUsableHeight() {
                Rect rect = new Rect();
                Keyboard.this.mChildOfContent.getWindowVisibleDisplayFrame(rect);
                return isOverlays() ? rect.bottom : rect.height();
            }

            private boolean isOverlays() {
                return (activity.getWindow().getDecorView().getSystemUiVisibility() & 1024) == 1024;
            }
        };
        this.mChildOfContent = frameLayout.getChildAt(0);
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.list);
        this.frameLayoutParams = (FrameLayout.LayoutParams) this.mChildOfContent.getLayoutParams();
    }

    public void show() {
        ((InputMethodManager) this.activity.getSystemService("input_method")).toggleSoftInput(0, 1);
    }

    public boolean hide() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.activity.getSystemService("input_method");
        View currentFocus = this.activity.getCurrentFocus();
        if (currentFocus == null) {
            return false;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
        return true;
    }
}
