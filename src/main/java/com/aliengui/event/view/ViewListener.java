package com.aliengui.event.view;

import com.aliengui.event.EventListener;

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
    public void viewActivated(ViewEvent event) {
        viewStateChanged(event);
    }
    public void viewDeactivated(ViewEvent event) {
        viewStateChanged(event);
    }


    public void viewStateChanged(ViewEvent event) {

    }
}
