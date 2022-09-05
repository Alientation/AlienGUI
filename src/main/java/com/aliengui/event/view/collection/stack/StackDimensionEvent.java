package com.aliengui.event.view.collection.stack;

import com.aliengui.api.view.collection.stack.StackView;
import com.aliengui.component.dimension.DimensionComponent;

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

    public StackView getStackView() { return stackView; }
    public DimensionComponent getOldDimension() { return oldDimension; }
    public DimensionComponent getNewDimension() { return newDimension; }
    public DimensionComponent getChangedDimension() { return newDimension; }
}
