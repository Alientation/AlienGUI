package com.aliengui.event.view.collection.stack;

import com.aliengui.event.EventListener;
import com.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public abstract class StackListener extends EventListener {
    public void stackDimensionChanged(StackDimensionEvent event) {
        stackStateChanged(event);
    }
    public void stackResized(StackEvent event) {
        stackStateChanged(event);
    }

    public void stackStateChanged(StackEvent event) {
        event.getStackView().getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(event.getStackView())));
    }
}
