package com.getcapacitor.util;

import android.graphics.Color;
/* loaded from: classes.dex */
public class WebColor {
    public static int parseColor(String colorString) {
        if (colorString.charAt(0) != '#') {
            colorString = "#" + colorString;
        }
        if (colorString.length() != 7 && colorString.length() != 9) {
            throw new IllegalArgumentException("The encoded color space is invalid or unknown");
        }
        if (colorString.length() == 7) {
            return Color.parseColor(colorString);
        }
        return Color.parseColor("#" + colorString.substring(7) + colorString.substring(1, 7));
    }
}
