package com.alientation.aliengui.api.view.input;

import com.alientation.aliengui.api.view.TextLabelView;
import com.alientation.aliengui.component.animation.ButtonAnimationComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.button.ButtonEvent;
import com.alientation.aliengui.event.view.button.ButtonListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonView extends TextLabelView {
    protected EventListenerContainer<ButtonListener> buttonListeners = new EventListenerContainer<>();
    protected TextLabelView hoveredPopup; //displayed when hovered over for a certain amount of time, constraint to the mouse, deactivate when unhovered
    protected ButtonAnimationComponent animationComponent;
    protected boolean isPressed;
    protected boolean isHovered;
    protected boolean disableAnimation;

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    protected ButtonView(Builder<?> builder) {
        super(builder);
        this.hoveredPopup = builder.hoveredPopup;
        this.animationComponent = builder.animationComponent;
        this.disableAnimation = builder.disableAnimation;

        //listens to events that pertain to this view
        getMouseListeners().addListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent event) {
                super.mouseEntered(event);
                if (!isActive) return;
                setHovered();
            }

            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
                if (!isActive) return;
                setUnhovered();
            }

            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);
                if (!isActive) return;
                setPressed();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                super.mouseReleased(event);
                if (!isActive) return;
                setReleased();
            }
        });

        //in the case that the user initially presses on the button but moves the mouse away and releases
        //on button press, it might want to be shaded a different color, but since when the mouse is released away from the view,
        //the view dependent mouse listener won't receive it
        windowView.getMouseListeners().addListener(new MouseListener() {
            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
               if (!isActive) return;
               setUnhovered();
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                super.mouseReleased(event);
                if (!isActive()) return;
                setReleased();
            }
        });

        getButtonListeners().addListenerAtBeginning(new ButtonListener() {
            @Override
            public void buttonPressed(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void buttonReleased(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void buttonHovered(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void buttonUnhovered(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void buttonActivated(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void buttonDeactivated(ButtonEvent event) {
                windowView.requestRenderUpdate();
            }
        });

        animationComponent.registerSubscriber(this);
        hoveredPopup.init(this);
    }

    @Override
    public void render(Graphics g) {
        animationComponent.draw(this,g,windowView.getWindow().getMsLastFrame());
        textLabel.draw(this, g);
    }

    @Override
    public void tick() {
        super.tick();
    }


    //SETTERS

    public void setAnimationComponent(@NotNull ButtonAnimationComponent animationComponent) {
        this.animationComponent.unregisterSubscriber(this);
        this.animationComponent = animationComponent;
        this.animationComponent.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    @Override
    public void setActive() {
        isActive = true;
        getButtonListeners().dispatch(listener -> listener.buttonActivated(new ButtonEvent(this)));
    }

    @Override
    public void setInactive() {
        isActive = false;
        isHovered = false;
        isPressed = false;
        getButtonListeners().dispatch(listener -> listener.buttonDeactivated(new ButtonEvent(this)));
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
    public ButtonAnimationComponent getAnimationComponent() { return animationComponent; }
    public boolean isPressed() { return isPressed; }
    public boolean isHovered() { return isHovered; }
    public boolean isAnimationDisabled() { return disableAnimation; }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {
        protected ButtonAnimationComponent animationComponent = new ButtonAnimationComponent();
        protected TextLabelView hoveredPopup;
        protected boolean disableAnimation = false;

        public Builder() {
            textLabel.setLinedText("ButtonView");
        }

        public T animationComponent(@NotNull ButtonAnimationComponent animationComponent) {
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
