package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* loaded from: classes.dex */
public final class zzjb implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzaw zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzjm zze;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjb(zzjm zzjmVar, boolean z, zzq zzqVar, boolean z2, zzaw zzawVar, String str) {
        this.zze = zzjmVar;
        this.zza = zzqVar;
        this.zzb = z2;
        this.zzc = zzawVar;
        this.zzd = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzdx zzdxVar;
        zzjm zzjmVar = this.zze;
        zzdxVar = zzjmVar.zzb;
        if (zzdxVar == null) {
            zzjmVar.zzt.zzay().zzd().zza("Discarding data. Failed to send event to service");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        this.zze.zzD(zzdxVar, this.zzb ? null : this.zzc, this.zza);
        this.zze.zzQ();
    }
}
