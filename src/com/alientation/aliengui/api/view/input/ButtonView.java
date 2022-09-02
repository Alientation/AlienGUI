package com.alientation.aliengui.api.view.input;

import com.alientation.aliengui.api.view.TextLabelView;
import com.alientation.aliengui.component.animation.AnimationComponent;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewEvent;

import java.awt.*;

@SuppressWarnings("unused")
public class ButtonView extends TextLabelView {
    //make this contain TextLabelViews instead of being a TextLabelView
    protected AnimationComponent animationComponent;
    protected boolean isActive;
    protected boolean isPressed;
    protected boolean isHovered;

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public ButtonView(Builder<?> builder) {
        super(builder);
        this.animationComponent = builder.animationComponent;

        this.mouseListeners.addListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);
            }
        });

        //in the case that the user initially presses on the button but moves the mouse away and releases
        //on button press, it might want to be shaded a different color, but since when the mouse is released away from the view,
        //the view dependent mouse listener won't receive it
        windowView.getMouseListeners().addListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);
            }
        });

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

    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent.unregisterSubscriber(this);
        this.animationComponent = animationComponent;
        this.animationComponent.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }


    //GETTERS

    public AnimationComponent getAnimationComponent() { return animationComponent; }
    public boolean isActive() { return isActive; }
    public boolean isPressed() { return isPressed; }
    public boolean isHovered() { return isHovered; }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {
        protected AnimationComponent animationComponent;
        public Builder() {

        }

        public T animationComponent(AnimationComponent animationComponent) {
            this.animationComponent = animationComponent;
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
