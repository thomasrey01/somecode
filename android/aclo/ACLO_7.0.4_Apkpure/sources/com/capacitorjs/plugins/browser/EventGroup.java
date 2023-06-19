package com.capacitorjs.plugins.browser;
/* loaded from: classes.dex */
class EventGroup {
    private EventGroupCompletion completion;
    private int count = 0;
    private boolean isComplete = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface EventGroupCompletion {
        void onGroupCompletion();
    }

    public EventGroup(EventGroupCompletion onCompletion) {
        this.completion = onCompletion;
    }

    public void enter() {
        this.count++;
    }

    public void leave() {
        this.count--;
        checkForCompletion();
    }

    public void reset() {
        this.count = 0;
        this.isComplete = false;
    }

    private void checkForCompletion() {
        EventGroupCompletion eventGroupCompletion;
        if (this.count <= 0) {
            if (!this.isComplete && (eventGroupCompletion = this.completion) != null) {
                eventGroupCompletion.onGroupCompletion();
            }
            this.isComplete = true;
        }
    }
}
