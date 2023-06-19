package com.capacitorjs.plugins.haptics.arguments;
/* loaded from: classes.dex */
public enum HapticsImpactType implements HapticsVibrationType {
    LIGHT("LIGHT", new long[]{0, 50}, new int[]{0, 110}, new long[]{0, 20}),
    MEDIUM("MEDIUM", new long[]{0, 43}, new int[]{0, 180}, new long[]{0, 43}),
    HEAVY("HEAVY", new long[]{0, 60}, new int[]{0, 255}, new long[]{0, 61});
    
    private final int[] amplitudes;
    private final long[] oldSDKPattern;
    private final long[] timings;
    private final String type;

    HapticsImpactType(String type, long[] timings, int[] amplitudes, long[] oldSDKPattern) {
        this.type = type;
        this.timings = timings;
        this.amplitudes = amplitudes;
        this.oldSDKPattern = oldSDKPattern;
    }

    public static HapticsImpactType fromString(String style) {
        HapticsImpactType[] values;
        for (HapticsImpactType hapticsImpactType : values()) {
            if (hapticsImpactType.type.equals(style)) {
                return hapticsImpactType;
            }
        }
        return HEAVY;
    }

    @Override // com.capacitorjs.plugins.haptics.arguments.HapticsVibrationType
    public long[] getTimings() {
        return this.timings;
    }

    @Override // com.capacitorjs.plugins.haptics.arguments.HapticsVibrationType
    public int[] getAmplitudes() {
        return this.amplitudes;
    }

    @Override // com.capacitorjs.plugins.haptics.arguments.HapticsVibrationType
    public long[] getOldSDKPattern() {
        return this.oldSDKPattern;
    }
}
