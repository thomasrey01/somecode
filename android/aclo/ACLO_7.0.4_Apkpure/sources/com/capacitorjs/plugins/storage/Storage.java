package com.capacitorjs.plugins.storage;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
/* loaded from: classes.dex */
public class Storage {
    private SharedPreferences preferences;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface StorageOperation {
        void execute(SharedPreferences.Editor editor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Storage(Context context, StorageConfiguration configuration) {
        this.preferences = context.getSharedPreferences(configuration.group, 0);
    }

    public String get(String key) {
        return this.preferences.getString(key, null);
    }

    public void set(final String key, final String value) {
        executeOperation(new StorageOperation() { // from class: com.capacitorjs.plugins.storage.-$$Lambda$Storage$AwEqU6wEjHVYtujDLDrvulyFfPY
            @Override // com.capacitorjs.plugins.storage.Storage.StorageOperation
            public final void execute(SharedPreferences.Editor editor) {
                editor.putString(key, value);
            }
        });
    }

    public void remove(final String key) {
        executeOperation(new StorageOperation() { // from class: com.capacitorjs.plugins.storage.-$$Lambda$Storage$easdfCqnnNTalE0R_0L0uqoJQdE
            @Override // com.capacitorjs.plugins.storage.Storage.StorageOperation
            public final void execute(SharedPreferences.Editor editor) {
                editor.remove(key);
            }
        });
    }

    public Set<String> keys() {
        return this.preferences.getAll().keySet();
    }

    public void clear() {
        executeOperation(new StorageOperation() { // from class: com.capacitorjs.plugins.storage.-$$Lambda$7EOXBE-TNeo1gdcwBG784qXgCyo
            @Override // com.capacitorjs.plugins.storage.Storage.StorageOperation
            public final void execute(SharedPreferences.Editor editor) {
                editor.clear();
            }
        });
    }

    private void executeOperation(StorageOperation op) {
        SharedPreferences.Editor edit = this.preferences.edit();
        op.execute(edit);
        edit.apply();
    }
}
