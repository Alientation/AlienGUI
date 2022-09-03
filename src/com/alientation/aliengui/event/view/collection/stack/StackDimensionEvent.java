package com.alientation.aliengui.event.view.collection.stack;

import com.alientation.aliengui.api.view.collection.stack.StackView;
import com.alientation.aliengui.component.dimension.DimensionComponent;

@SuppressWarnings("unused")
public class StackDimensionEvent extends StackEvent {
    protected StackView stackView;
    protected DimensionComponent oldDimension, newDimension;
    public StackDimensionEvent(StackView stackView, DimensionComponent oldDimension, DimensionComponent newDimension) {
        super(stackView);
        this.oldDimension = oldDimension;
        this.newDimension = newDimension;
    }

    public StackDimensionEvent(StackView stackView, DimensionComponent changedDimension) {
        super(stackView);
        this.newDimension = changedDimension;
    }

    public StackView getStackView() { return (StackView) stackView; }
    public DimensionComponent getOldDimension() { return oldDimension; }
    public DimensionComponent getNewDimension() { return newDimension; }
    public DimensionComponent getChangedDimension() { return newDimension; }
}
