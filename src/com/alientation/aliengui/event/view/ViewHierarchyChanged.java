package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;

@SuppressWarnings("unused")
public class ViewHierarchyChanged extends ViewEvent {
    protected View movedView;

    public ViewHierarchyChanged(View view, View movedView) {
        super(view);
        this.movedView = movedView;
    }

    public View getMovedView() {
        return movedView;
    }
}
