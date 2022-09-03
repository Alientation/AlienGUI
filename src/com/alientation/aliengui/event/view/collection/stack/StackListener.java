package com.alientation.aliengui.event.view.collection.stack;

import com.alientation.aliengui.event.EventListener;

@SuppressWarnings("unused")
public class StackListener extends EventListener {
    public void stackDimensionChanged(StackDimensionEvent event) {
        stackStateChanged(event);
    }

    public void stackStateChanged(StackEvent event) { }
}
