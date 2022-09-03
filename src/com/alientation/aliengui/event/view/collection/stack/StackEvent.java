package com.alientation.aliengui.event.view.collection.stack;

import com.alientation.aliengui.api.view.collection.stack.StackView;
import com.alientation.aliengui.event.Event;

@SuppressWarnings("unused")
public class StackEvent extends Event {
    protected StackView stackView;
    public StackEvent(StackView stackView) {
        this.stackView = stackView;
    }

    public StackView getStackView() {
        return stackView;
    }
}
