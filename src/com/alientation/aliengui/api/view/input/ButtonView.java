package com.alientation.aliengui.api.view.input;

import com.alientation.aliengui.api.view.TextLabelView;
import com.alientation.aliengui.component.animation.AnimationComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.button.ButtonListener;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonView extends TextLabelView {
    protected EventListenerContainer<ButtonListener> buttonListeners = new EventListenerContainer<>();
    protected TextLabelView hoveredPopup; //displayed when hovered over for a certain amount of time, constraint to the mouse, deactivate when unhovered
    protected AnimationComponent animationComponent;
    protected boolean isPressed;
    protected boolean isHovered;



    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public ButtonView(Builder<?> builder) {
        super(builder);
        this.hoveredPopup = builder.hoveredPopup;
        this.animationComponent = builder.animationComponent;

        //listens to events that pertain to this view
        this.mouseListeners.addListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!isActive) return;
                hovered();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (!isActive) return;
                unhovered();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (!isActive) return;
                pressed();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (!isActive) return;
                released();
            }
        });

        //in the case that the user initially presses on the button but moves the mouse away and releases
        //on button press, it might want to be shaded a different color, but since when the mouse is released away from the view,
        //the view dependent mouse listener won't receive it
        windowView.getMouseListeners().addListener(new MouseListener() {
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
               if (!isActive) return;
               unhovered();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (!isActive()) return;
                released();
            }
        });

        if (animationComponent != null) animationComponent.registerSubscriber(this);
        hoveredPopup.init(this);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

    }

    @Override
    public void tick() {
        super.tick();
    }

    public void pressed() {
        this.isPressed = true;
    }

    public void released() {
        this.isPressed = false;
    }

    public void hovered() {
        this.isHovered = true;
    }

    public void unhovered() {
        this.isHovered = false;
    }


    //SETTERS

    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent.unregisterSubscriber(this);
        this.animationComponent = animationComponent;
        this.animationComponent.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    @Override
    public void setActive() { //TODO ButtonEvents active
        super.setActive();
    }

    @Override
    public void setInactive() { //TODO ButtonEvents inactive
        this.isHovered = false;
        this.isPressed = false;
        super.setInactive();
    }

    public void setPressed() { //TODO ButtonEvents pressed
        if (this.isPressed || !this.isActive) return;
        this.isPressed = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }
    public void sudoSetPressed() { //TODO ButtonEvents sudoPressed
        if (this.isPressed) return;
        this.isPressed = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setReleased() { //TODO ButtonEvents released
        if (!this.isPressed || !this.isActive) return;
        this.isPressed = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void sudoSetReleased() { //TODO ButtonEvents sudoReleased
        if (!this.isPressed) return;
        this.isPressed = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setHovered() { //TODO ButtonEvents hover
        this.isHovered = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void sudoSetHovered() { //TODO ButtonEvents sudoHovered
        if (this.isHovered) return;
        this.isHovered = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setUnhovered() { //TODO ButtonEvents unhovered
        this.isHovered = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void sudoSetUnhovered() { //TODO ButtonEvents sudoUnhovered
        if (!this.isHovered) return;
        this.isHovered = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }


    //GETTERS
    public EventListenerContainer<ButtonListener> getButtonListeners() { return buttonListeners; }
    public AnimationComponent getAnimationComponent() { return animationComponent; }
    public boolean isPressed() { return isPressed; }
    public boolean isHovered() { return isHovered; }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {
        protected AnimationComponent animationComponent;
        protected TextLabelView hoveredPopup;

        public Builder() {

        }

        public T animationComponent(AnimationComponent animationComponent) {
            this.animationComponent = animationComponent;
            return (T) this;
        }
        public T hoveredPopup(TextLabelView hoveredPopup) {
            this.hoveredPopup = hoveredPopup;
            return (T) this;
        }

        public void validate() {

        }

        public ButtonView build() {
            validate();
            return new ButtonView(this);
        }
    }
}
