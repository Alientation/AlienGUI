package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.dimension.Dimension;

@SuppressWarnings("unused")
public class ViewDimensionEvent extends ViewEvent {
    protected Dimension dimension;

    public ViewDimensionEvent(View view, Dimension dimension) {
        super(view);
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
