package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.EventDispatch;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewListener;

@SuppressWarnings("unused")
public class StackElementView extends View {
    protected View stackElement;
    protected boolean dynamicStackElementJoin; //whether when stack elements are set, their dimensions will be changed

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public StackElementView(Builder<?> builder) {
        super(builder);
        setStackElement(builder.stackElement);
        dynamicStackElementJoin = builder.dynamicStackElementJoin;
    }

    public void setStackElement(View stackElement) {
        if (this.stackElement != null) this.removeChildViews(this.stackElement);
        this.stackElement = stackElement;

        if (stackElement.isInitialized())
            this.addChildViews(stackElement);
        else
            stackElement.init(this);
        if (dynamicStackElementJoin) {

        }
        //TODO StackElementEvent instead
        getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public View getStackElement() { return stackElement; }
    public boolean doDynamicStackElementJoin() { return dynamicStackElementJoin; }


    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected View stackElement;
        protected boolean dynamicStackElementJoin = true;

        public Builder() {

        }

        public T stackElement(View stackElement) {
            this.stackElement = stackElement;
            return (T) this;
        }
        public T dynamicStackElementJoin(boolean dynamicStackElementJoin) {
            this.dynamicStackElementJoin = dynamicStackElementJoin;
            return (T) this;
        }

        public void validate() {

        }

        public StackElementView build() {
            validate();
            return new StackElementView(this);
        }
    }
    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends StackElementView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public StackElementView build() {
            validate();
            return new StackElementView(this);
        }
    }
     */
}
