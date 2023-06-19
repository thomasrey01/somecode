package com.getcapacitor;

import com.getcapacitor.annotation.CapacitorPlugin;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class PluginHandle {
    private final Bridge bridge;
    private Plugin instance;
    private NativePlugin legacyPluginAnnotation;
    private CapacitorPlugin pluginAnnotation;
    private final Class<? extends Plugin> pluginClass;
    private final String pluginId;
    private Map<String, PluginMethodHandle> pluginMethods = new HashMap();

    public PluginHandle(Bridge bridge, Class<? extends Plugin> pluginClass) throws InvalidPluginException, PluginLoadException {
        this.bridge = bridge;
        this.pluginClass = pluginClass;
        CapacitorPlugin capacitorPlugin = (CapacitorPlugin) pluginClass.getAnnotation(CapacitorPlugin.class);
        if (capacitorPlugin == null) {
            NativePlugin nativePlugin = (NativePlugin) pluginClass.getAnnotation(NativePlugin.class);
            if (nativePlugin == null) {
                throw new InvalidPluginException("No @CapacitorPlugin annotation found for plugin " + pluginClass.getName());
            }
            if (!nativePlugin.name().equals("")) {
                this.pluginId = nativePlugin.name();
            } else {
                this.pluginId = pluginClass.getSimpleName();
            }
            this.legacyPluginAnnotation = nativePlugin;
        } else {
            if (!capacitorPlugin.name().equals("")) {
                this.pluginId = capacitorPlugin.name();
            } else {
                this.pluginId = pluginClass.getSimpleName();
            }
            this.pluginAnnotation = capacitorPlugin;
        }
        indexMethods(pluginClass);
        load();
    }

    public Class<? extends Plugin> getPluginClass() {
        return this.pluginClass;
    }

    public String getId() {
        return this.pluginId;
    }

    public NativePlugin getLegacyPluginAnnotation() {
        return this.legacyPluginAnnotation;
    }

    public CapacitorPlugin getPluginAnnotation() {
        return this.pluginAnnotation;
    }

    public Plugin getInstance() {
        return this.instance;
    }

    public Collection<PluginMethodHandle> getMethods() {
        return this.pluginMethods.values();
    }

    public Plugin load() throws PluginLoadException {
        Plugin plugin = this.instance;
        if (plugin != null) {
            return plugin;
        }
        try {
            Plugin newInstance = this.pluginClass.newInstance();
            this.instance = newInstance;
            newInstance.setPluginHandle(this);
            this.instance.setBridge(this.bridge);
            this.instance.load();
            this.instance.initializeActivityLaunchers();
            return this.instance;
        } catch (IllegalAccessException | InstantiationException unused) {
            throw new PluginLoadException("Unable to load plugin instance. Ensure plugin is publicly accessible");
        }
    }

    public void invoke(String methodName, PluginCall call) throws PluginLoadException, InvalidPluginMethodException, InvocationTargetException, IllegalAccessException {
        if (this.instance == null) {
            load();
        }
        PluginMethodHandle pluginMethodHandle = this.pluginMethods.get(methodName);
        if (pluginMethodHandle == null) {
            throw new InvalidPluginMethodException("No method " + methodName + " found for plugin " + this.pluginClass.getName());
        }
        pluginMethodHandle.getMethod().invoke(this.instance, call);
    }

    private void indexMethods(Class<? extends Plugin> plugin) {
        Method[] methods;
        for (Method method : this.pluginClass.getMethods()) {
            PluginMethod pluginMethod = (PluginMethod) method.getAnnotation(PluginMethod.class);
            if (pluginMethod != null) {
                this.pluginMethods.put(method.getName(), new PluginMethodHandle(method, pluginMethod));
            }
        }
    }
}
