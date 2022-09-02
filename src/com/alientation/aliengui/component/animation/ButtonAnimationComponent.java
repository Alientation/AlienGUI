package com.alientation.aliengui.component.animation;

import com.alientation.aliengui.api.view.input.ButtonView;
import com.alientation.aliengui.component.color.ColorComponent;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonAnimationComponent extends AnimationComponent {
    protected ColorComponent buttonPressedBackgroundColor = new ColorComponent(new Color(140, 140, 145));
    protected ColorComponent buttonHoveredBackgroundColor = new ColorComponent(new Color(178, 180, 186));
    protected ColorComponent buttonBackgroundColor = new ColorComponent(new Color(200, 207, 208));


    protected ColorComponent previousColor = buttonBackgroundColor;
    protected ColorComponent currentColor = buttonBackgroundColor;
    protected float msOpacityTransition = 300; //time for the new color to fully overlay the previous color

    protected float frameTimeSinceStateChange = 0f;
    protected float tickTimeSinceStateChange = 0f;
    public ButtonAnimationComponent() {

    }

    public void draw(ButtonView view, Graphics g, float deltaFrame) { //TODO work on animating fade in/out color transition
        if (frameTimeSinceStateChange <= 1f) { //state change or first draw call

        }
        frameTimeSinceStateChange += deltaFrame;

        currentColor.draw(view,g,view.getBackgroundShape());
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
        previousColor = currentColor;
        currentColor = buttonPressedBackgroundColor;
    }

    public void buttonReleased(ButtonView buttonView) {
        resetTime();
        previousColor = currentColor;
        currentColor = buttonBackgroundColor;
    }

    public void buttonHovered(ButtonView buttonView) {
        resetTime();
        previousColor = currentColor;
        currentColor = buttonHoveredBackgroundColor;
    }

    public void buttonUnhovered(ButtonView buttonView) {
        resetTime();
        previousColor = currentColor;
        currentColor = buttonBackgroundColor;
    }

    public ButtonView getButtonView() { return subscribers.getSubscribedCount() > 0 ? (ButtonView) subscribers.getSubscribed().get(0) : null; }
}
