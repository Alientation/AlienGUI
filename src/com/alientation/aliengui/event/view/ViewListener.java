package com.alientation.aliengui.event.view;

import com.alientation.aliengui.event.EventListener;

@SuppressWarnings("unused")
public abstract class ViewListener extends EventListener {
    public void childViewAdded(ViewHierarchyEvent event) {
        viewStateChanged(event);
    }
    public void childViewRemoved(ViewHierarchyEvent event) {
        viewStateChanged(event);
    }
    public void parentViewChanged(ViewHierarchyEvent event) {
        viewStateChanged(event);
    }
    public void viewFocused(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewUnfocused(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewMoved(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewHidden(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewShown(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewDimensionChanged(ViewDimensionEvent event) {
        viewStateChanged(event);
    }

    public void viewStateChanged(ViewEvent event) {

    }
}
