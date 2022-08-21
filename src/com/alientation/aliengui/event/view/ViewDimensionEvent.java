package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.dimension.DimensionComponent;

@SuppressWarnings("unused")
public class ViewDimensionEvent extends ViewEvent {
    protected DimensionComponent dimensionComponent;

    public ViewDimensionEvent(View view, DimensionComponent dimensionComponent) {
        super(view);
        this.dimensionComponent = dimensionComponent;
    }

    public DimensionComponent getDimension() {
        return dimensionComponent;
    }
}
