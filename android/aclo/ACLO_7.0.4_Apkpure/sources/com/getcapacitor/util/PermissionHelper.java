package com.getcapacitor.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Arrays;
/* loaded from: classes.dex */
public class PermissionHelper {
    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String str : permissions) {
            if (ActivityCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasDefinedPermission(Context context, String permission) {
        String[] manifestPermissions = getManifestPermissions(context);
        return manifestPermissions != null && manifestPermissions.length > 0 && new ArrayList(Arrays.asList(manifestPermissions)).contains(permission);
    }

    public static boolean hasDefinedPermissions(Context context, String[] permissions) {
        for (String str : permissions) {
            if (!hasDefinedPermission(context, str)) {
                return false;
            }
        }
        return true;
    }

    public static String[] getManifestPermissions(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
            if (packageInfo != null) {
                return packageInfo.requestedPermissions;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String[] getUndefinedPermissions(Context context, String[] neededPermissions) {
        ArrayList arrayList = new ArrayList();
        String[] manifestPermissions = getManifestPermissions(context);
        if (manifestPermissions == null || manifestPermissions.length <= 0) {
            return neededPermissions;
        }
        ArrayList arrayList2 = new ArrayList(Arrays.asList(manifestPermissions));
        for (String str : neededPermissions) {
            if (!arrayList2.contains(str)) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
