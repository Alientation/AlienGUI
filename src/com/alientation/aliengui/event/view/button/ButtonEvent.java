package com.alientation.aliengui.event.view.button;

import com.alientation.aliengui.api.view.input.ButtonView;
import com.alientation.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public class ButtonEvent extends ViewEvent {

    public ButtonEvent(ButtonView view) {
        super(view);
    }

    @Override
    public ButtonView getView() {
        return (ButtonView) view;
    }
}
