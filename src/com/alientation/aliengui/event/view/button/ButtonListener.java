package com.alientation.aliengui.event.view.button;

import com.alientation.aliengui.event.view.ViewListener;

@SuppressWarnings("unused")
public abstract class ButtonListener extends ViewListener {
    public void buttonPressed(ButtonEvent event) {}
    public void buttonReleased(ButtonEvent event) {}
    public void buttonHovered(ButtonEvent event) {}
    public void buttonUnhovered(ButtonEvent event) { }
    public void buttonActivated(ButtonEvent event) {
        event.getView().getViewListeners().dispatch(listener -> listener.viewActivated(event));
    }
    public void buttonDeactivated(ButtonEvent event) {
        event.getView().getViewListeners().dispatch(listener -> listener.viewDeactivated(event));
    }
}
