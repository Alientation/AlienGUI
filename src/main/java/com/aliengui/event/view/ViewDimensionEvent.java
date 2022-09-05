package com.aliengui.event.view;

import com.aliengui.api.view.View;
import com.aliengui.component.dimension.DimensionComponent;

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
