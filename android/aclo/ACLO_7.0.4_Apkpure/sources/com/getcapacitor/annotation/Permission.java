package com.getcapacitor.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface Permission {
    String alias() default "";

    String[] strings() default {};
}
