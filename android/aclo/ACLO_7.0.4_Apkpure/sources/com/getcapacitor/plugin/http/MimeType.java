package com.getcapacitor.plugin.http;
/* loaded from: classes.dex */
enum MimeType {
    APPLICATION_JSON("application/json"),
    APPLICATION_VND_API_JSON("application/vnd.api+json"),
    TEXT_HTML("text/html");
    
    private final String value;

    MimeType(String value) {
        this.value = value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getValue() {
        return this.value;
    }
}
