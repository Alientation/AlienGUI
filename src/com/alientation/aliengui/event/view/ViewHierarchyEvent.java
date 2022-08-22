package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;

@SuppressWarnings("unused")
public class ViewHierarchyEvent extends ViewEvent {
    protected View changedChildView, oldParentView, newParentView;

    public ViewHierarchyEvent(View view, View changedChildView) {
        super(view);
        this.changedChildView = changedChildView;
    }

    public ViewHierarchyEvent(View view, View oldParentView, View newParentView) {
        super(view);
        this.oldParentView = oldParentView;
        this.newParentView = newParentView;
    }

    public View getChangedChildView() {
        return changedChildView;
    }

    public View getOldParentView() {
        return oldParentView;
    }

    public View getNewParentView() {
        return newParentView;
    }
}
