package com.google.android.gms.internal.measurement;
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzpf implements zzpe {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;
    public static final zzib zze;

    static {
        zzhy zza2 = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zzf("measurement.collection.enable_session_stitching_token.client.dev", true);
        zzb = zza2.zzf("measurement.session_stitching_token_enabled", false);
        zzc = zza2.zzf("measurement.collection.enable_session_stitching_token.service", false);
        zzd = zza2.zzf("measurement.collection.enable_session_stitching_token.service_new", true);
        zze = zza2.zzd("measurement.id.collection.enable_session_stitching_token.client.dev", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzpe
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzpe
    public final boolean zzb() {
        return ((Boolean) zza.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzpe
    public final boolean zzc() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzpe
    public final boolean zzd() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzpe
    public final boolean zze() {
        return ((Boolean) zzd.zzb()).booleanValue();
    }
}
