package com.getcapacitor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.util.PermissionHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONException;
/* loaded from: classes.dex */
public class Plugin {
    private static final String BUNDLE_PERSISTED_OPTIONS_JSON_KEY = "_json";
    protected Bridge bridge;
    protected PluginHandle handle;
    private String lastPluginCallId;
    @Deprecated
    protected PluginCall savedLastCall;
    private final Map<String, ActivityResultLauncher<Intent>> activityLaunchers = new HashMap();
    private final Map<String, ActivityResultLauncher<String[]>> permissionLaunchers = new HashMap();
    private final Map<String, List<PluginCall>> eventListeners = new HashMap();
    private final Map<String, JSObject> retainedEventArguments = new HashMap();

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnConfigurationChanged(Configuration newConfig) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnDestroy() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnNewIntent(Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnPause() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnRestart() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnResume() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnStart() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleOnStop() {
    }

    public void load() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void restoreState(Bundle state) {
    }

    public Boolean shouldOverrideLoad(Uri url) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initializeActivityLaunchers() {
        ArrayList<Method> arrayList = new ArrayList();
        for (Class<?> cls = getClass(); !cls.getName().equals(Object.class.getName()); cls = cls.getSuperclass()) {
            arrayList.addAll(Arrays.asList(cls.getDeclaredMethods()));
        }
        for (final Method method : arrayList) {
            if (method.isAnnotationPresent(ActivityCallback.class)) {
                this.activityLaunchers.put(method.getName(), this.bridge.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.getcapacitor.-$$Lambda$Plugin$9tit0PZrRMA5UA4Z_xqI4yDk4JY
                    @Override // androidx.activity.result.ActivityResultCallback
                    public final void onActivityResult(Object obj) {
                        Plugin.this.lambda$initializeActivityLaunchers$0$Plugin(method, (ActivityResult) obj);
                    }
                }));
            } else if (method.isAnnotationPresent(PermissionCallback.class)) {
                this.permissionLaunchers.put(method.getName(), this.bridge.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.getcapacitor.-$$Lambda$Plugin$g-uDx5XqRWyrlYV2wp0-DfAj-NQ
                    @Override // androidx.activity.result.ActivityResultCallback
                    public final void onActivityResult(Object obj) {
                        Plugin.this.lambda$initializeActivityLaunchers$1$Plugin(method, (Map) obj);
                    }
                }));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: triggerPermissionCallback */
    public void lambda$initializeActivityLaunchers$1$Plugin(Method method, Map<String, Boolean> permissionResultMap) {
        PluginCall permissionCall = this.bridge.getPermissionCall(this.handle.getId());
        if (this.bridge.validatePermissions(this, permissionCall, permissionResultMap)) {
            try {
                method.setAccessible(true);
                method.invoke(this, permissionCall);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: triggerActivityCallback */
    public void lambda$initializeActivityLaunchers$0$Plugin(Method method, ActivityResult result) {
        PluginCall savedCall = this.bridge.getSavedCall(this.lastPluginCallId);
        if (savedCall == null) {
            savedCall = this.bridge.getPluginCallForLastActivity();
        }
        try {
            method.setAccessible(true);
            method.invoke(this, savedCall, result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void startActivityForResult(PluginCall call, Intent intent, String callbackName) {
        ActivityResultLauncher<Intent> activityLauncherOrReject = getActivityLauncherOrReject(call, callbackName);
        if (activityLauncherOrReject == null) {
            return;
        }
        this.bridge.setPluginCallForLastActivity(call);
        this.lastPluginCallId = call.getCallbackId();
        this.bridge.saveCall(call);
        activityLauncherOrReject.launch(intent);
    }

    private void permissionActivityResult(PluginCall call, String[] permissionStrings, String callbackName) {
        ActivityResultLauncher<String[]> permissionLauncherOrReject = getPermissionLauncherOrReject(call, callbackName);
        if (permissionLauncherOrReject == null) {
            return;
        }
        this.bridge.savePermissionCall(call);
        permissionLauncherOrReject.launch(permissionStrings);
    }

    public Context getContext() {
        return this.bridge.getContext();
    }

    public AppCompatActivity getActivity() {
        return this.bridge.getActivity();
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }

    public Bridge getBridge() {
        return this.bridge;
    }

    public void setPluginHandle(PluginHandle pluginHandle) {
        this.handle = pluginHandle;
    }

    public PluginHandle getPluginHandle() {
        return this.handle;
    }

    public String getAppId() {
        return getContext().getPackageName();
    }

    @Deprecated
    public void saveCall(PluginCall lastCall) {
        this.savedLastCall = lastCall;
    }

    @Deprecated
    public void freeSavedCall() {
        this.savedLastCall.release(this.bridge);
        this.savedLastCall = null;
    }

    @Deprecated
    public PluginCall getSavedCall() {
        return this.savedLastCall;
    }

    public PluginConfig getConfig() {
        return this.bridge.getConfig().getPluginConfiguration(this.handle.getId());
    }

    @Deprecated
    public Object getConfigValue(String key) {
        try {
            return getConfig().getConfigJSON().get(key);
        } catch (JSONException unused) {
            return null;
        }
    }

    @Deprecated
    public boolean hasDefinedPermissions(String[] permissions) {
        for (String str : permissions) {
            if (!PermissionHelper.hasDefinedPermission(getContext(), str)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public boolean hasDefinedRequiredPermissions() {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation == null) {
            return hasDefinedPermissions(this.handle.getLegacyPluginAnnotation().permissions());
        }
        for (Permission permission : pluginAnnotation.permissions()) {
            for (String str : permission.strings()) {
                if (!PermissionHelper.hasDefinedPermission(getContext(), str)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPermissionDeclared(String alias) {
        Permission[] permissions;
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation != null) {
            for (Permission permission : pluginAnnotation.permissions()) {
                if (alias.equalsIgnoreCase(permission.alias())) {
                    boolean z = true;
                    for (String str : permission.strings()) {
                        z = z && PermissionHelper.hasDefinedPermission(getContext(), str);
                    }
                    return z;
                }
            }
        }
        Logger.error(String.format("isPermissionDeclared: No alias defined for %s or missing @CapacitorPlugin annotation.", alias));
        return false;
    }

    @Deprecated
    public boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(getContext(), permission) == 0;
    }

    @Deprecated
    public boolean hasRequiredPermissions() {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation == null) {
            for (String str : this.handle.getLegacyPluginAnnotation().permissions()) {
                if (ActivityCompat.checkSelfPermission(getContext(), str) != 0) {
                    return false;
                }
            }
            return true;
        }
        for (Permission permission : pluginAnnotation.permissions()) {
            for (String str2 : permission.strings()) {
                if (ActivityCompat.checkSelfPermission(getContext(), str2) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void requestAllPermissions(PluginCall call, String callbackName) {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation != null) {
            HashSet hashSet = new HashSet();
            for (Permission permission : pluginAnnotation.permissions()) {
                hashSet.addAll(Arrays.asList(permission.strings()));
            }
            permissionActivityResult(call, (String[]) hashSet.toArray(new String[0]), callbackName);
        }
    }

    protected void requestPermissionForAlias(String alias, PluginCall call, String callbackName) {
        requestPermissionForAliases(new String[]{alias}, call, callbackName);
    }

    protected void requestPermissionForAliases(String[] aliases, PluginCall call, String callbackName) {
        if (aliases.length == 0) {
            Logger.error("No permission alias was provided");
            return;
        }
        String[] permissionStringsForAliases = getPermissionStringsForAliases(aliases);
        if (permissionStringsForAliases.length > 0) {
            permissionActivityResult(call, permissionStringsForAliases, callbackName);
        }
    }

    private String[] getPermissionStringsForAliases(String[] aliases) {
        Permission[] permissions;
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        HashSet hashSet = new HashSet();
        for (Permission permission : pluginAnnotation.permissions()) {
            if (Arrays.asList(aliases).contains(permission.alias())) {
                hashSet.addAll(Arrays.asList(permission.strings()));
            }
        }
        return (String[]) hashSet.toArray(new String[0]);
    }

    private ActivityResultLauncher<Intent> getActivityLauncherOrReject(PluginCall call, String methodName) {
        ActivityResultLauncher<Intent> activityResultLauncher = this.activityLaunchers.get(methodName);
        if (activityResultLauncher == null) {
            String format = String.format(Locale.US, "There is no ActivityCallback method registered for the name: %s. Please define a callback method annotated with @ActivityCallback that receives arguments: (PluginCall, ActivityResult)", methodName);
            Logger.error(format);
            call.reject(format);
            return null;
        }
        return activityResultLauncher;
    }

    private ActivityResultLauncher<String[]> getPermissionLauncherOrReject(PluginCall call, String methodName) {
        ActivityResultLauncher<String[]> activityResultLauncher = this.permissionLaunchers.get(methodName);
        if (activityResultLauncher == null) {
            String format = String.format(Locale.US, "There is no PermissionCallback method registered for the name: %s. Please define a callback method annotated with @PermissionCallback that receives arguments: (PluginCall)", methodName);
            Logger.error(format);
            call.reject(format);
            return null;
        }
        return activityResultLauncher;
    }

    @Deprecated
    public void pluginRequestAllPermissions() {
        NativePlugin legacyPluginAnnotation = this.handle.getLegacyPluginAnnotation();
        ActivityCompat.requestPermissions(getActivity(), legacyPluginAnnotation.permissions(), legacyPluginAnnotation.permissionRequestCode());
    }

    @Deprecated
    public void pluginRequestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
    }

    @Deprecated
    public void pluginRequestPermissions(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

    public PermissionState getPermissionState(String alias) {
        return getPermissionStates().get(alias);
    }

    public Map<String, PermissionState> getPermissionStates() {
        return this.bridge.getPermissionStates(this);
    }

    private void addEventListener(String eventName, PluginCall call) {
        List<PluginCall> list = this.eventListeners.get(eventName);
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            this.eventListeners.put(eventName, arrayList);
            arrayList.add(call);
            sendRetainedArgumentsForEvent(eventName);
            return;
        }
        list.add(call);
    }

    private void removeEventListener(String eventName, PluginCall call) {
        List<PluginCall> list = this.eventListeners.get(eventName);
        if (list == null) {
            return;
        }
        list.remove(call);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifyListeners(String eventName, JSObject data, boolean retainUntilConsumed) {
        String logTag = getLogTag();
        Logger.verbose(logTag, "Notifying listeners for event " + eventName);
        List<PluginCall> list = this.eventListeners.get(eventName);
        if (list == null || list.isEmpty()) {
            String logTag2 = getLogTag();
            Logger.debug(logTag2, "No listeners found for event " + eventName);
            if (retainUntilConsumed) {
                this.retainedEventArguments.put(eventName, data);
                return;
            }
            return;
        }
        Iterator it = new CopyOnWriteArrayList(list).iterator();
        while (it.hasNext()) {
            ((PluginCall) it.next()).resolve(data);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void notifyListeners(String eventName, JSObject data) {
        notifyListeners(eventName, data, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasListeners(String eventName) {
        List<PluginCall> list = this.eventListeners.get(eventName);
        if (list == null) {
            return false;
        }
        return !list.isEmpty();
    }

    private void sendRetainedArgumentsForEvent(String eventName) {
        JSObject jSObject = this.retainedEventArguments.get(eventName);
        if (jSObject == null) {
            return;
        }
        notifyListeners(eventName, jSObject);
        this.retainedEventArguments.remove(eventName);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall call) {
        String string = call.getString("eventName");
        call.setKeepAlive(true);
        addEventListener(string, call);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeListener(PluginCall call) {
        String string = call.getString("eventName");
        PluginCall savedCall = this.bridge.getSavedCall(call.getString("callbackId"));
        if (savedCall != null) {
            removeEventListener(string, savedCall);
            this.bridge.releaseCall(savedCall);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeAllListeners(PluginCall call) {
        this.eventListeners.clear();
    }

    @PluginMethod
    @PermissionCallback
    public void checkPermissions(PluginCall pluginCall) {
        Map<String, PermissionState> permissionStates = getPermissionStates();
        if (permissionStates.size() == 0) {
            pluginCall.resolve();
            return;
        }
        JSObject jSObject = new JSObject();
        for (Map.Entry<String, PermissionState> entry : permissionStates.entrySet()) {
            jSObject.put(entry.getKey(), (Object) entry.getValue());
        }
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        List list;
        Permission[] permissions;
        Permission[] permissions2;
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation == null) {
            NativePlugin legacyPluginAnnotation = this.handle.getLegacyPluginAnnotation();
            String[] permissions3 = legacyPluginAnnotation.permissions();
            if (permissions3.length > 0) {
                saveCall(call);
                pluginRequestPermissions(permissions3, legacyPluginAnnotation.permissionRequestCode());
                return;
            }
            call.resolve();
            return;
        }
        HashSet<String> hashSet = new HashSet();
        String[] strArr = null;
        try {
            list = call.getArray("permissions").toList();
        } catch (JSONException unused) {
            list = null;
        }
        HashSet hashSet2 = new HashSet();
        if (list == null || list.isEmpty()) {
            for (Permission permission : pluginAnnotation.permissions()) {
                if (permission.strings().length == 0 || (permission.strings().length == 1 && permission.strings()[0].isEmpty())) {
                    if (!permission.alias().isEmpty()) {
                        hashSet.add(permission.alias());
                    }
                } else {
                    hashSet2.add(permission.alias());
                }
            }
            strArr = (String[]) hashSet2.toArray(new String[0]);
        } else {
            for (Permission permission2 : pluginAnnotation.permissions()) {
                if (list.contains(permission2.alias())) {
                    hashSet2.add(permission2.alias());
                }
            }
            if (hashSet2.isEmpty()) {
                call.reject("No valid permission alias was requested of this plugin.");
            } else {
                strArr = (String[]) hashSet2.toArray(new String[0]);
            }
        }
        if (strArr != null && strArr.length > 0) {
            requestPermissionForAliases(strArr, call, "checkPermissions");
        } else if (!hashSet.isEmpty()) {
            JSObject jSObject = new JSObject();
            for (String str : hashSet) {
                jSObject.put(str, PermissionState.GRANTED.toString());
            }
            call.resolve(jSObject);
        } else {
            call.resolve();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String[] undefinedPermissions;
        if (hasDefinedPermissions(permissions)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Missing the following permissions in AndroidManifest.xml:\n");
        for (String str : PermissionHelper.getUndefinedPermissions(getContext(), permissions)) {
            sb.append(str + "\n");
        }
        this.savedLastCall.reject(sb.toString());
        this.savedLastCall = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Bundle saveInstanceState() {
        PluginCall savedCall = this.bridge.getSavedCall(this.lastPluginCallId);
        if (savedCall == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        JSObject data = savedCall.getData();
        if (data != null) {
            bundle.putString(BUNDLE_PERSISTED_OPTIONS_JSON_KEY, data.toString());
        }
        return bundle;
    }

    @Deprecated
    protected void startActivityForResult(PluginCall call, Intent intent, int resultCode) {
        this.bridge.startActivityForPluginWithResult(call, intent, resultCode);
    }

    public void execute(Runnable runnable) {
        this.bridge.execute(runnable);
    }

    protected String getLogTag(String... subTags) {
        return Logger.tags(subTags);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getLogTag() {
        return Logger.tags(getClass().getSimpleName());
    }
}
