package com.pantrist.firebase.dynamiclinks;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: CapacitorFirebaseDynamicLinks.kt */
@CapacitorPlugin
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0014¨\u0006\u0013"}, d2 = {"Lcom/pantrist/firebase/dynamiclinks/CapacitorFirebaseDynamicLinks;", "Lcom/getcapacitor/Plugin;", "()V", "buildAndroidParameters", "", NotificationCompat.CATEGORY_CALL, "Lcom/getcapacitor/PluginCall;", "builder", "Lcom/google/firebase/dynamiclinks/DynamicLink$Builder;", "buildGoogleAnalyticsParameters", "buildIOSParameters", "buildITunesParameters", "buildSocialMetaParameters", "createDynamicLink", "createDynamicShortLink", "handleOnNewIntent", "intent", "Landroid/content/Intent;", "Companion", "pantrist-capacitor-firebase-dynamic-links_release"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class CapacitorFirebaseDynamicLinks extends Plugin {
    public static final Companion Companion = new Companion(null);
    private static final String EVENT_DEEP_LINK = "deepLinkOpen";

    @PluginMethod
    public final void createDynamicLink(PluginCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        try {
            String string = call.getString(DynamicLink.Builder.KEY_DOMAIN_URI_PREFIX);
            if (string == null) {
                call.reject("domainUriPrefix is required!");
                return;
            }
            String string2 = call.getString("uri");
            if (string2 == null) {
                call.reject("uri is required!");
                return;
            }
            DynamicLink.Builder link = FirebaseDynamicLinks.getInstance().createDynamicLink().setDomainUriPrefix(string).setLink(Uri.parse(string2));
            Intrinsics.checkNotNullExpressionValue(link, "FirebaseDynamicLinks.get… .setLink(Uri.parse(uri))");
            buildAndroidParameters(call, link);
            buildIOSParameters(call, link);
            buildGoogleAnalyticsParameters(call, link);
            buildITunesParameters(call, link);
            buildSocialMetaParameters(call, link);
            DynamicLink buildDynamicLink = link.buildDynamicLink();
            Intrinsics.checkNotNullExpressionValue(buildDynamicLink, "builder.buildDynamicLink()");
            JSObject jSObject = new JSObject();
            jSObject.put("value", (Object) buildDynamicLink.getUri());
            call.resolve(jSObject);
        } catch (Exception e) {
            call.reject("Unable to create DynamicLink", e);
        }
    }

    @PluginMethod
    public final void createDynamicShortLink(final PluginCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        String string = call.getString(DynamicLink.Builder.KEY_DOMAIN_URI_PREFIX);
        if (string == null) {
            call.reject("domainUriPrefix is required!");
            return;
        }
        String string2 = call.getString("uri");
        if (string2 == null) {
            call.reject("uri is required!");
            return;
        }
        DynamicLink.Builder link = FirebaseDynamicLinks.getInstance().createDynamicLink().setDomainUriPrefix(string).setLink(Uri.parse(string2));
        Intrinsics.checkNotNullExpressionValue(link, "FirebaseDynamicLinks.get… .setLink(Uri.parse(uri))");
        buildAndroidParameters(call, link);
        buildIOSParameters(call, link);
        buildGoogleAnalyticsParameters(call, link);
        buildITunesParameters(call, link);
        buildSocialMetaParameters(call, link);
        final JSObject jSObject = new JSObject();
        link.buildShortDynamicLink().addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() { // from class: com.pantrist.firebase.dynamiclinks.CapacitorFirebaseDynamicLinks$createDynamicShortLink$1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(ShortDynamicLink it) {
                JSObject jSObject2 = JSObject.this;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                jSObject2.put("value", (Object) it.getShortLink());
                call.resolve(JSObject.this);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.pantrist.firebase.dynamiclinks.CapacitorFirebaseDynamicLinks$createDynamicShortLink$2
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception it) {
                Intrinsics.checkNotNullParameter(it, "it");
                PluginCall.this.reject(it.getLocalizedMessage());
            }
        });
    }

    private final void buildGoogleAnalyticsParameters(PluginCall pluginCall, DynamicLink.Builder builder) {
        JSObject object = pluginCall.getObject("googleAnalytics");
        if (object != null) {
            DynamicLink.GoogleAnalyticsParameters.Builder builder2 = new DynamicLink.GoogleAnalyticsParameters.Builder();
            if (object.getString("source") != null) {
                String string = object.getString("source");
                Intrinsics.checkNotNull(string);
                builder2.setSource(string);
            }
            if (object.getString("medium") != null) {
                String string2 = object.getString("medium");
                Intrinsics.checkNotNull(string2);
                builder2.setMedium(string2);
            }
            if (object.getString("campaign") != null) {
                String string3 = object.getString("campaign");
                Intrinsics.checkNotNull(string3);
                builder2.setCampaign(string3);
            }
            if (object.getString(FirebaseAnalytics.Param.TERM) != null) {
                String string4 = object.getString(FirebaseAnalytics.Param.TERM);
                Intrinsics.checkNotNull(string4);
                builder2.setTerm(string4);
            }
            if (object.getString("source") != null) {
                String string5 = object.getString(FirebaseAnalytics.Param.CONTENT);
                Intrinsics.checkNotNull(string5);
                builder2.setContent(string5);
            }
            builder.setGoogleAnalyticsParameters(builder2.build());
        }
    }

    private final void buildIOSParameters(PluginCall pluginCall, DynamicLink.Builder builder) {
        JSObject object = pluginCall.getObject("iosParameters");
        if (object.getString("bundleId") != null) {
            String string = object.getString("bundleId");
            Intrinsics.checkNotNull(string);
            DynamicLink.IosParameters.Builder builder2 = new DynamicLink.IosParameters.Builder(string);
            if (object.getString("appStoreId") != null) {
                String string2 = object.getString("appStoreId");
                Intrinsics.checkNotNull(string2);
                builder2.setAppStoreId(string2);
            }
            if (object.getString("fallbackUrl") != null) {
                builder2.setFallbackUrl(Uri.parse(object.getString("fallbackUrl")));
            }
            if (object.getString("customScheme") != null) {
                String string3 = object.getString("customScheme");
                Intrinsics.checkNotNull(string3);
                builder2.setCustomScheme(string3);
            }
            if (object.getString("ipadFallbackUrl") != null) {
                builder2.setIpadFallbackUrl(Uri.parse(object.getString("ipadFallbackUrl")));
            }
            if (object.getString("ipadBundleId") != null) {
                String string4 = object.getString("ipadBundleId");
                Intrinsics.checkNotNull(string4);
                builder2.setIpadBundleId(string4);
            }
            if (object.getString("minimumVersion") != null) {
                String string5 = object.getString("minimumVersion");
                Intrinsics.checkNotNull(string5);
                builder2.setMinimumVersion(string5);
            }
            builder.setIosParameters(builder2.build());
        }
    }

    private final void buildAndroidParameters(PluginCall pluginCall, DynamicLink.Builder builder) {
        JSObject object = pluginCall.getObject("androidParameters");
        if (object != null) {
            DynamicLink.AndroidParameters.Builder builder2 = new DynamicLink.AndroidParameters.Builder();
            if (object.getInteger("minimumVersion") != null) {
                Integer integer = object.getInteger("minimumVersion");
                Intrinsics.checkNotNull(integer);
                builder2.setMinimumVersion(integer.intValue());
            }
            if (object.getString("fallbackUrl") != null) {
                builder2.setFallbackUrl(Uri.parse(object.getString("fallbackUrl")));
            }
            builder.setAndroidParameters(builder2.build());
        }
    }

    private final void buildITunesParameters(PluginCall pluginCall, DynamicLink.Builder builder) {
        JSObject object = pluginCall.getObject("iTunesConnectAnalytics");
        if (object != null) {
            DynamicLink.ItunesConnectAnalyticsParameters.Builder builder2 = new DynamicLink.ItunesConnectAnalyticsParameters.Builder();
            if (object.getString("providerToken") != null) {
                String string = object.getString("providerToken");
                Intrinsics.checkNotNull(string);
                builder2.setProviderToken(string);
            }
            if (object.getString("affiliateToken") != null) {
                String string2 = object.getString("affiliateToken");
                Intrinsics.checkNotNull(string2);
                builder2.setAffiliateToken(string2);
            }
            if (object.getString("campaignToken") != null) {
                String string3 = object.getString("campaignToken");
                Intrinsics.checkNotNull(string3);
                builder2.setCampaignToken(string3);
            }
            builder.setItunesConnectAnalyticsParameters(builder2.build());
        }
    }

    private final void buildSocialMetaParameters(PluginCall pluginCall, DynamicLink.Builder builder) {
        JSObject object = pluginCall.getObject("socialMeta");
        if (object != null) {
            DynamicLink.SocialMetaTagParameters.Builder builder2 = new DynamicLink.SocialMetaTagParameters.Builder();
            if (object.getString("title") != null) {
                String string = object.getString("title");
                Intrinsics.checkNotNull(string);
                builder2.setTitle(string);
            }
            if (object.getString("description") != null) {
                String string2 = object.getString("description");
                Intrinsics.checkNotNull(string2);
                builder2.setDescription(string2);
            }
            if (object.getString("imageUrl") != null) {
                String string3 = object.getString("imageUrl");
                Intrinsics.checkNotNull(string3);
                builder2.setImageUrl(Uri.parse(string3));
            }
            builder.setSocialMetaTagParameters(builder2.build());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.getcapacitor.Plugin
    public void handleOnNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.handleOnNewIntent(intent);
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener(getActivity(), new OnSuccessListener<PendingDynamicLinkData>() { // from class: com.pantrist.firebase.dynamiclinks.CapacitorFirebaseDynamicLinks$handleOnNewIntent$1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Uri uri = null;
                if (pendingDynamicLinkData != null) {
                    uri = pendingDynamicLinkData.getLink();
                }
                if (uri != null) {
                    JSObject jSObject = new JSObject();
                    jSObject.put(ImagesContract.URL, uri.toString());
                    CapacitorFirebaseDynamicLinks.this.notifyListeners("deepLinkOpen", jSObject, true);
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() { // from class: com.pantrist.firebase.dynamiclinks.CapacitorFirebaseDynamicLinks$handleOnNewIntent$2
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception e) {
                String logTag;
                Intrinsics.checkNotNullParameter(e, "e");
                logTag = CapacitorFirebaseDynamicLinks.this.getLogTag();
                Log.e(logTag, "getDynamicLink:onFailure", e);
            }
        });
    }

    /* compiled from: CapacitorFirebaseDynamicLinks.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pantrist/firebase/dynamiclinks/CapacitorFirebaseDynamicLinks$Companion;", "", "()V", "EVENT_DEEP_LINK", "", "pantrist-capacitor-firebase-dynamic-links_release"}, k = 1, mv = {1, 4, 0})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
