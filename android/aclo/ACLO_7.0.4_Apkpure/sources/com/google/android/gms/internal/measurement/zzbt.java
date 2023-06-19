package com.google.android.gms.internal.measurement;

import java.lang.reflect.Method;
/* compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* loaded from: classes.dex */
public final class zzbt {
    private static final Method zza;
    private static final Method zzb;

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 6
            r2 = 0
            r3 = 24
            java.lang.String r4 = "JobSchedulerCompat"
            r5 = 0
            if (r0 < r3) goto L35
            r0 = 4
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch: java.lang.NoSuchMethodException -> L2a
            java.lang.Class<android.app.job.JobInfo> r6 = android.app.job.JobInfo.class
            r0[r2] = r6     // Catch: java.lang.NoSuchMethodException -> L2a
            r6 = 1
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r0[r6] = r7     // Catch: java.lang.NoSuchMethodException -> L2a
            r6 = 2
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch: java.lang.NoSuchMethodException -> L2a
            r0[r6] = r7     // Catch: java.lang.NoSuchMethodException -> L2a
            r6 = 3
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r0[r6] = r7     // Catch: java.lang.NoSuchMethodException -> L2a
            java.lang.Class<android.app.job.JobScheduler> r6 = android.app.job.JobScheduler.class
            java.lang.String r7 = "scheduleAsPackage"
            java.lang.reflect.Method r0 = r6.getDeclaredMethod(r7, r0)     // Catch: java.lang.NoSuchMethodException -> L2a
            goto L36
        L2a:
            boolean r0 = android.util.Log.isLoggable(r4, r1)
            if (r0 == 0) goto L35
            java.lang.String r0 = "No scheduleAsPackage method available, falling back to schedule"
            android.util.Log.e(r4, r0)
        L35:
            r0 = r5
        L36:
            com.google.android.gms.internal.measurement.zzbt.zza = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r3) goto L52
            java.lang.Class<android.os.UserHandle> r0 = android.os.UserHandle.class
            java.lang.String r3 = "myUserId"
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchMethodException -> L47
            java.lang.reflect.Method r5 = r0.getDeclaredMethod(r3, r2)     // Catch: java.lang.NoSuchMethodException -> L47
            goto L52
        L47:
            boolean r0 = android.util.Log.isLoggable(r4, r1)
            if (r0 == 0) goto L52
            java.lang.String r0 = "No myUserId method available"
            android.util.Log.e(r4, r0)
        L52:
            com.google.android.gms.internal.measurement.zzbt.zzb = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzbt.<clinit>():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int zza(android.content.Context r5, android.app.job.JobInfo r6, java.lang.String r7, java.lang.String r8) {
        /*
            java.lang.String r7 = "jobscheduler"
            java.lang.Object r7 = r5.getSystemService(r7)
            android.app.job.JobScheduler r7 = (android.app.job.JobScheduler) r7
            java.util.Objects.requireNonNull(r7)
            java.lang.reflect.Method r8 = com.google.android.gms.internal.measurement.zzbt.zza
            if (r8 == 0) goto L74
            java.lang.String r8 = "android.permission.UPDATE_DEVICE_STATS"
            int r5 = r5.checkSelfPermission(r8)
            if (r5 == 0) goto L18
            goto L74
        L18:
            java.lang.reflect.Method r5 = com.google.android.gms.internal.measurement.zzbt.zzb
            r8 = 0
            if (r5 == 0) goto L3f
            java.lang.Class<android.os.UserHandle> r0 = android.os.UserHandle.class
            java.lang.Object[] r1 = new java.lang.Object[r8]     // Catch: java.lang.reflect.InvocationTargetException -> L2e java.lang.IllegalAccessException -> L30
            java.lang.Object r5 = r5.invoke(r0, r1)     // Catch: java.lang.reflect.InvocationTargetException -> L2e java.lang.IllegalAccessException -> L30
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.lang.reflect.InvocationTargetException -> L2e java.lang.IllegalAccessException -> L30
            if (r5 == 0) goto L3f
            int r5 = r5.intValue()     // Catch: java.lang.reflect.InvocationTargetException -> L2e java.lang.IllegalAccessException -> L30
            goto L40
        L2e:
            r5 = move-exception
            goto L31
        L30:
            r5 = move-exception
        L31:
            r0 = 6
            java.lang.String r1 = "JobSchedulerCompat"
            boolean r0 = android.util.Log.isLoggable(r1, r0)
            if (r0 == 0) goto L3f
            java.lang.String r0 = "myUserId invocation illegal"
            android.util.Log.e(r1, r0, r5)
        L3f:
            r5 = r8
        L40:
            java.lang.reflect.Method r0 = com.google.android.gms.internal.measurement.zzbt.zza
            java.lang.String r1 = "com.google.android.gms"
            java.lang.String r2 = "UploadAlarm"
            if (r0 == 0) goto L6f
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            r3[r8] = r6     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            r4 = 1
            r3[r4] = r1     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            r1 = 2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            r3[r1] = r5     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            r5 = 3
            r3[r5] = r2     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            java.lang.Object r5 = r0.invoke(r7, r3)     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            if (r5 == 0) goto L73
            int r8 = r5.intValue()     // Catch: java.lang.reflect.InvocationTargetException -> L67 java.lang.IllegalAccessException -> L69
            goto L73
        L67:
            r5 = move-exception
            goto L6a
        L69:
            r5 = move-exception
        L6a:
            java.lang.String r8 = "error calling scheduleAsPackage"
            android.util.Log.e(r2, r8, r5)
        L6f:
            int r8 = r7.schedule(r6)
        L73:
            return r8
        L74:
            int r5 = r7.schedule(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzbt.zza(android.content.Context, android.app.job.JobInfo, java.lang.String, java.lang.String):int");
    }
}
