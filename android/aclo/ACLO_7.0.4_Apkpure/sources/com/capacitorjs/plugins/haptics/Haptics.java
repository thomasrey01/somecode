package com.capacitorjs.plugins.haptics;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.capacitorjs.plugins.haptics.arguments.HapticsSelectionType;
import com.capacitorjs.plugins.haptics.arguments.HapticsVibrationType;
/* loaded from: classes.dex */
public class Haptics {
    private Context context;
    private boolean selectionStarted = false;
    private final Vibrator vibrator;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Haptics(Context context) {
        this.context = context;
        this.vibrator = (Vibrator) context.getSystemService("vibrator");
    }

    public void vibrate(int duration) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.vibrator.vibrate(VibrationEffect.createOneShot(duration, -1));
        } else {
            vibratePre26(duration);
        }
    }

    private void vibratePre26(int duration) {
        this.vibrator.vibrate(duration);
    }

    private void vibratePre26(long[] pattern, int repeat) {
        this.vibrator.vibrate(pattern, repeat);
    }

    public void selectionStart() {
        this.selectionStarted = true;
    }

    public void selectionChanged() {
        if (this.selectionStarted) {
            performHaptics(new HapticsSelectionType());
        }
    }

    public void selectionEnd() {
        this.selectionStarted = false;
    }

    public void performHaptics(HapticsVibrationType type) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.vibrator.vibrate(VibrationEffect.createWaveform(type.getTimings(), type.getAmplitudes(), -1));
        } else {
            vibratePre26(type.getOldSDKPattern(), -1);
        }
    }
}
