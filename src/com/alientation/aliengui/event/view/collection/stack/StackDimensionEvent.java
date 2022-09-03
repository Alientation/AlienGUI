package com.alientation.aliengui.event.view.collection.stack;

import com.alientation.aliengui.api.view.collection.stack.StackView;
import com.alientation.aliengui.component.dimension.DimensionComponent;
import com.alientation.aliengui.event.view.ViewDimensionEvent;

public class StackDimensionEvent extends ViewDimensionEvent {

    public StackDimensionEvent(StackView view, DimensionComponent oldDimension, DimensionComponent newDimension) {
        super(view, oldDimension, newDimension);
    }

    public StackDimensionEvent(StackView view, DimensionComponent changedDimension) {
        super(view, changedDimension);
    }

    @Override
    public StackView getView() { return (StackView) view; }
}
