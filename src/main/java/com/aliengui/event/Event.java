package com.aliengui.event;

public class Event {
    protected boolean cancelled, handled;

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isHandled() {
        return handled;
    }
}
