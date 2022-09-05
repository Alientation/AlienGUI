package com.aliengui.event.view.collection.stack;

import com.aliengui.api.view.collection.stack.StackView;
import com.aliengui.event.Event;

@SuppressWarnings("unused")
public class StackEvent extends Event {
    protected StackView stackView;
    public StackEvent(StackView stackView) {
        this.stackView = stackView;
    }

    public StackView getStackView() { return stackView; }
}
