package com.alientation.aliengui.event.view;

import com.alientation.aliengui.event.EventListener;

public abstract class ViewListener extends EventListener {
    public void viewAdded(ViewEvent event) {}
    public void viewRemoved(ViewEvent event) {}
    public void viewFocused(ViewEvent event) {}
    public void viewUnfocused(ViewEvent event) {}
    public void viewStateChanged(ViewEvent event) {}
}
