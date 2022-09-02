package com.alientation.aliengui.component.animation;

import com.alientation.aliengui.api.view.input.ButtonView;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonAnimationComponent extends AnimationComponent {
    public ButtonAnimationComponent() {

    }


    public void draw(ButtonView view, Graphics g, float deltaFrame) {

    }

    public void tick(ButtonView view, float deltaTick) {

    }

    public ButtonView getButtonView() { return subscribers.getSubscribedCount() > 0 ? (ButtonView) subscribers.getSubscribed().get(0) : null; }
}
