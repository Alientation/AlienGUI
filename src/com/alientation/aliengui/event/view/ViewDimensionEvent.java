package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.dimension.DimensionComponent;

@SuppressWarnings("unused")
public class ViewDimensionEvent extends ViewEvent {
    protected DimensionComponent oldDimension, newDimension;

    public ViewDimensionEvent(View view, DimensionComponent oldDimension, DimensionComponent newDimension) {
        super(view);
        this.oldDimension = oldDimension;
        this.newDimension = newDimension;
    }

    public ViewDimensionEvent(View view, DimensionComponent changedDimension) {
        super(view);
        this.newDimension = changedDimension;
    }

    public DimensionComponent getOldDimension() { return oldDimension; }
    public DimensionComponent getNewDimension() { return newDimension; }
    public DimensionComponent getChangedDimension() { return newDimension; }
}
