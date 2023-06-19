package com.capacitorjs.plugins.storage;
/* loaded from: classes.dex */
public class StorageConfiguration implements Cloneable {
    static final StorageConfiguration DEFAULTS;
    String group;

    static {
        StorageConfiguration storageConfiguration = new StorageConfiguration();
        DEFAULTS = storageConfiguration;
        storageConfiguration.group = "CapacitorStorage";
    }

    /* renamed from: clone */
    public StorageConfiguration m5clone() throws CloneNotSupportedException {
        return (StorageConfiguration) super.clone();
    }
}
