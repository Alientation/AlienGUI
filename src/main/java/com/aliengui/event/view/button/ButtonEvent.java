package com.aliengui.event.view.button;

import com.aliengui.api.view.input.ButtonView;
import com.aliengui.event.view.ViewEvent;

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
