package com.alientation.aliengui.event.view.collection.stack;

import com.alientation.aliengui.api.view.collection.stack.StackView;
import com.alientation.aliengui.event.view.collection.CollectionEvent;

public class StackEvent extends CollectionEvent {

    public StackEvent(StackView stackView) {
        super(stackView);
    }

    @Override
    public StackView getView() {
        return (StackView) view;
    }
}
