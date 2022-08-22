package com.alientation.aliengui.event.view;

import com.alientation.aliengui.event.EventListener;

@SuppressWarnings("unused")
public abstract class ViewListener extends EventListener {
    public void childViewAdded(ViewHierarchyEvent event) {}
    public void childViewRemoved(ViewHierarchyEvent event) {}
    public void parentViewChanged(ViewHierarchyEvent event) {}
    public void viewFocused(ViewEvent event) {}
    public void viewUnfocused(ViewEvent event) {}
    public void viewMoved(ViewEvent event) {}
    public void viewHidden(ViewEvent event) {}
    public void viewShown(ViewEvent event) {}
    public void viewDimensionChanged(ViewDimensionEvent event) {}


    public void viewStateChanged(ViewEvent event) {}
}
