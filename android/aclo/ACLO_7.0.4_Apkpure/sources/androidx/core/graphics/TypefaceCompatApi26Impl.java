package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;
/* loaded from: classes.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class<?> mFontFamily;
    protected final Constructor<?> mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Method method;
        Constructor<?> constructor;
        Method method2;
        Method method3;
        Method method4;
        Method method5;
        Class<?> cls = null;
        try {
            Class<?> obtainFontFamily = obtainFontFamily();
            constructor = obtainFontFamilyCtor(obtainFontFamily);
            method2 = obtainAddFontFromAssetManagerMethod(obtainFontFamily);
            method3 = obtainAddFontFromBufferMethod(obtainFontFamily);
            method4 = obtainFreezeMethod(obtainFontFamily);
            method5 = obtainAbortCreationMethod(obtainFontFamily);
            method = obtainCreateFromFamiliesWithDefaultMethod(obtainFontFamily);
            cls = obtainFontFamily;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            method = null;
            constructor = null;
            method2 = null;
            method3 = null;
            method4 = null;
            method5 = null;
        }
        this.mFontFamily = cls;
        this.mFontFamilyCtor = constructor;
        this.mAddFontFromAssetManager = method2;
        this.mAddFontFromBuffer = method3;
        this.mFreeze = method4;
        this.mAbortCreation = method5;
        this.mCreateFromFamiliesWithDefault = method;
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    private boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style, FontVariationAxis[] axes) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(family, context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), axes)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(family, buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    protected Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object newInstance = Array.newInstance(this.mFontFamily, 1);
            Array.set(newInstance, 0, family);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, newInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    private boolean freeze(Object family) {
        try {
            return ((Boolean) this.mFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private void abortCreation(Object family) {
        try {
            this.mAbortCreation.invoke(family, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        FontResourcesParserCompat.FontFileResourceEntry[] entries;
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object newFamily = newFamily();
        if (newFamily == null) {
            return null;
        }
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : entry.getEntries()) {
            if (!addFontFromAssetManager(context, newFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                abortCreation(newFamily);
                return null;
            }
        }
        if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        }
        return null;
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        Typeface createFromFamiliesWithDefault;
        if (fonts.length < 1) {
            return null;
        }
        if (!isFontFamilyPrivateAPIAvailable()) {
            FontsContractCompat.FontInfo findBestInfo = findBestInfo(fonts, style);
            try {
                ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(findBestInfo.getUri(), "r", cancellationSignal);
                if (openFileDescriptor == null) {
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                    }
                    return null;
                }
                Typeface build = new Typeface.Builder(openFileDescriptor.getFileDescriptor()).setWeight(findBestInfo.getWeight()).setItalic(findBestInfo.isItalic()).build();
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
                return build;
            } catch (IOException unused) {
                return null;
            }
        }
        Map<Uri, ByteBuffer> readFontInfoIntoByteBuffer = TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fonts, cancellationSignal);
        Object newFamily = newFamily();
        if (newFamily == null) {
            return null;
        }
        boolean z = false;
        for (FontsContractCompat.FontInfo fontInfo : fonts) {
            ByteBuffer byteBuffer = readFontInfoIntoByteBuffer.get(fontInfo.getUri());
            if (byteBuffer != null) {
                if (!addFontFromBuffer(newFamily, byteBuffer, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic() ? 1 : 0)) {
                    abortCreation(newFamily);
                    return null;
                }
                z = true;
            }
        }
        if (!z) {
            abortCreation(newFamily);
            return null;
        } else if (freeze(newFamily) && (createFromFamiliesWithDefault = createFromFamiliesWithDefault(newFamily)) != null) {
            return Typeface.create(createFromFamiliesWithDefault, style);
        } else {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object newFamily = newFamily();
        if (newFamily == null) {
            return null;
        }
        if (!addFontFromAssetManager(context, newFamily, path, 0, -1, -1, null)) {
            abortCreation(newFamily);
            return null;
        } else if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        } else {
            return null;
        }
    }

    protected Class<?> obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    protected Constructor<?> obtainFontFamilyCtor(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getConstructor(new Class[0]);
    }

    protected Method obtainAddFontFromAssetManagerMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
    }

    protected Method obtainAddFontFromBufferMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
    }

    protected Method obtainFreezeMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(FREEZE_METHOD, new Class[0]);
    }

    protected Method obtainAbortCreationMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    protected Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> fontFamily) throws NoSuchMethodException {
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(fontFamily, 1).getClass(), Integer.TYPE, Integer.TYPE);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
