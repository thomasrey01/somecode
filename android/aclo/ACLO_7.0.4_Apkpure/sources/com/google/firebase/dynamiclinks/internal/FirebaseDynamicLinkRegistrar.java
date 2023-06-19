package com.google.firebase.dynamiclinks.internal;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;
/* loaded from: classes.dex */
public final class FirebaseDynamicLinkRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-dl";

    @Override // com.google.firebase.components.ComponentRegistrar
    public List<Component<?>> getComponents() {
        return Arrays.asList(Component.builder(FirebaseDynamicLinks.class).name(LIBRARY_NAME).add(Dependency.required(FirebaseApp.class)).add(Dependency.optionalProvider(AnalyticsConnector.class)).factory(new ComponentFactory() { // from class: com.google.firebase.dynamiclinks.internal.-$$Lambda$FirebaseDynamicLinkRegistrar$9AzdSXjbbbUZm_4Go9ALk9dSjCE
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                return FirebaseDynamicLinkRegistrar.lambda$getComponents$0(componentContainer);
            }
        }).build(), LibraryVersionComponent.create(LIBRARY_NAME, "21.1.0"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ FirebaseDynamicLinks lambda$getComponents$0(ComponentContainer componentContainer) {
        return new FirebaseDynamicLinksImpl((FirebaseApp) componentContainer.get(FirebaseApp.class), componentContainer.getProvider(AnalyticsConnector.class));
    }
}
