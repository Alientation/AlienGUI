package com.alientation.aliengui.api.view.input;

import com.alientation.aliengui.api.view.TextLabelView;
import com.alientation.aliengui.component.animation.AnimationComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.button.ButtonListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonView extends TextLabelView {
    protected EventListenerContainer<ButtonListener> buttonListeners = new EventListenerContainer<>();
    protected TextLabelView hoveredPopup; //displayed when hovered over for a certain amount of time, constraint to the mouse, deactivate when unhovered
    protected AnimationComponent animationComponent;
    protected boolean isPressed;
    protected boolean isHovered;
    protected boolean disableAnimation;



    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public ButtonView(Builder<?> builder) {
        super(builder);
        this.hoveredPopup = builder.hoveredPopup;
        this.animationComponent = builder.animationComponent;
        this.disableAnimation = builder.disableAnimation;

        //listens to events that pertain to this view
        this.mouseListeners.addListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (!isActive) return;
                setHovered();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (!isActive) return;
                setUnhovered();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (!isActive) return;
                setPressed();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (!isActive) return;
                setReleased();
            }
        });

        //in the case that the user initially presses on the button but moves the mouse away and releases
        //on button press, it might want to be shaded a different color, but since when the mouse is released away from the view,
        //the view dependent mouse listener won't receive it
        windowView.getMouseListeners().addListener(new MouseListener() {
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
               if (!isActive) return;
               setUnhovered();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (!isActive()) return;
                setReleased();
            }
        });

        animationComponent.registerSubscriber(this);
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


    //SETTERS

    public void setAnimationComponent(@NotNull AnimationComponent animationComponent) {
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
        isHovered = false;
        isPressed = false;
        super.setInactive();
    }

    public void setPressed() {
        if (isPressed) return;
        isPressed = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setReleased() {
        if (!isPressed) return;
        isPressed = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setHovered() {
        if (isHovered) return;
        isHovered = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setUnhovered() {
        if (!isHovered) return;
        isHovered = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setDisableAnimation(boolean disableAnimation) {
        if (this.disableAnimation == disableAnimation) return;
        this.disableAnimation = disableAnimation;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }


    //GETTERS
    public EventListenerContainer<ButtonListener> getButtonListeners() { return buttonListeners; }
    public AnimationComponent getAnimationComponent() { return animationComponent; }
    public boolean isPressed() { return isPressed; }
    public boolean isHovered() { return isHovered; }
    public boolean isAnimationDisabled() { return disableAnimation; }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {
        protected AnimationComponent animationComponent;
        protected TextLabelView hoveredPopup;
        protected boolean disableAnimation = false;

        public Builder() {

        }

        public T animationComponent(@NotNull AnimationComponent animationComponent) {
            this.animationComponent = animationComponent;
            return (T) this;
        }
        public T hoveredPopup(@NotNull TextLabelView hoveredPopup) {
            this.hoveredPopup = hoveredPopup;
            return (T) this;
        }
        public T disableAnimation(boolean disableAnimation) {
            this.disableAnimation = disableAnimation;
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
