package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.0 */
/* loaded from: classes.dex */
final class zzmg implements Iterator {
    final /* synthetic */ zzmk zza;
    private int zzb = -1;
    private boolean zzc;
    private Iterator zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzmg(zzmk zzmkVar, zzmf zzmfVar) {
        this.zza = zzmkVar;
    }

    private final Iterator zza() {
        Map map;
        if (this.zzd == null) {
            map = this.zza.zzc;
            this.zzd = map.entrySet().iterator();
        }
        return this.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        List list;
        Map map;
        int i = this.zzb + 1;
        list = this.zza.zzb;
        if (i >= list.size()) {
            map = this.zza.zzc;
            return !map.isEmpty() && zza().hasNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        List list;
        List list2;
        this.zzc = true;
        int i = this.zzb + 1;
        this.zzb = i;
        list = this.zza.zzb;
        if (i < list.size()) {
            list2 = this.zza.zzb;
            return (Map.Entry) list2.get(this.zzb);
        }
        return (Map.Entry) zza().next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        List list;
        if (!this.zzc) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzc = false;
        this.zza.zzn();
        int i = this.zzb;
        list = this.zza.zzb;
        if (i < list.size()) {
            zzmk zzmkVar = this.zza;
            int i2 = this.zzb;
            this.zzb = i2 - 1;
            zzmkVar.zzl(i2);
            return;
        }
        zza().remove();
    }
}
