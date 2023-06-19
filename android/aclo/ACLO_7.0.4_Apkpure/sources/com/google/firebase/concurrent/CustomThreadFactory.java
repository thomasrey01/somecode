package com.google.firebase.concurrent;

import android.os.Process;
import android.os.StrictMode;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class CustomThreadFactory implements ThreadFactory {
    private static final ThreadFactory DEFAULT = Executors.defaultThreadFactory();
    private final String namePrefix;
    private final StrictMode.ThreadPolicy policy;
    private final int priority;
    private final AtomicLong threadCount = new AtomicLong();

    /* JADX INFO: Access modifiers changed from: package-private */
    public CustomThreadFactory(String str, int i, @Nullable StrictMode.ThreadPolicy threadPolicy) {
        this.namePrefix = str;
        this.priority = i;
        this.policy = threadPolicy;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(final Runnable runnable) {
        Thread newThread = DEFAULT.newThread(new Runnable() { // from class: com.google.firebase.concurrent.-$$Lambda$CustomThreadFactory$25wFMnJXuxCXflRCTG3ggSOiDOE
            @Override // java.lang.Runnable
            public final void run() {
                CustomThreadFactory.this.lambda$newThread$0$CustomThreadFactory(runnable);
            }
        });
        newThread.setName(String.format(Locale.ROOT, "%s Thread #%d", this.namePrefix, Long.valueOf(this.threadCount.getAndIncrement())));
        return newThread;
    }

    public /* synthetic */ void lambda$newThread$0$CustomThreadFactory(Runnable runnable) {
        Process.setThreadPriority(this.priority);
        StrictMode.ThreadPolicy threadPolicy = this.policy;
        if (threadPolicy != null) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
        runnable.run();
    }
}
