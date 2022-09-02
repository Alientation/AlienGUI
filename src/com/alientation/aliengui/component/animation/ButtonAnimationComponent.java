package com.alientation.aliengui.component.animation;

import com.alientation.aliengui.api.view.input.ButtonView;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonAnimationComponent extends AnimationComponent {
    protected Color buttonPressedBackgroundColor;
    protected Color buttonHoveredBackgroundColor;
    protected Color buttonBackgroundColor;

    protected float frameTimeSinceStateChange = 0f;
    protected float tickTimeSinceStateChange = 0f;
    public ButtonAnimationComponent() {

    }

    public void draw(ButtonView view, Graphics g, float deltaFrame) {

        frameTimeSinceStateChange += deltaFrame;
    }

    public void tick(ButtonView view, float deltaTick) {

        tickTimeSinceStateChange += deltaTick;
    }

    private void resetTime() {
        this.frameTimeSinceStateChange = 0f;
        this.tickTimeSinceStateChange = 0f;
    }

    public void buttonPressed(ButtonView buttonView) {
        resetTime();

    }

    public void buttonReleased(ButtonView buttonView) {
        resetTime();
    }

    public void buttonHovered(ButtonView buttonView) {
        resetTime();
    }

    public void buttonUnhovered(ButtonView buttonView) {
        resetTime();
    }

    public ButtonView getButtonView() { return subscribers.getSubscribedCount() > 0 ? (ButtonView) subscribers.getSubscribed().get(0) : null; }
}
