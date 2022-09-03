package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.collection.CollectionView;
import com.alientation.aliengui.component.dimension.DimensionComponent;
import com.alientation.aliengui.component.dimension.StaticDimensionComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewListener;
import com.alientation.aliengui.event.view.collection.stack.StackDimensionEvent;
import com.alientation.aliengui.event.view.collection.stack.StackEvent;
import com.alientation.aliengui.event.view.collection.stack.StackListener;

@SuppressWarnings("unused")
public abstract class StackView extends CollectionView {
    protected EventListenerContainer<StackListener> stackListeners = new EventListenerContainer<>();
    protected DimensionComponent spacing;

    protected StackListener stackListener = new StackListener() {
        @Override
        public void stackResized(StackEvent event) {
            super.stackResized(event);
            resize();
        }
    };

    protected ViewListener listener = new ViewListener() {
        @Override
        public void viewStateChanged(ViewEvent event) {
            super.viewStateChanged(event);
            getStackListeners().dispatch(listener1 -> listener1.stackResized(new StackEvent(StackView.this)));
        }
    };

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public StackView(Builder<?> builder) {
        super(builder);
        this.spacing = builder.spacing;
        this.spacing.registerSubscriber(this);
        resize();
    }

    public abstract void resize();


    //SETTERS

    /**
     * Sets dimensions and updates dependencies
     *
     * @param spacing The new Spacing dimension
     */
    public void setSpacing(DimensionComponent spacing) {
        StackDimensionEvent event = new StackDimensionEvent(this,this.spacing,spacing);
        this.spacing.unregisterSubscriber(this);
        this.spacing = spacing;
        this.spacing.registerSubscriber(this);
        this.getStackListeners().dispatch(listener -> listener.stackDimensionChanged(event));
    }


    //GETTERS
    public EventListenerContainer<StackListener> getStackListeners() { return stackListeners; }
    public DimensionComponent getSpacing() { return spacing; }
    public int spacing() { return spacing.val(); }



    @SuppressWarnings("unchecked")
    public static class Builder<T extends StackView.Builder<T>> extends CollectionView.Builder<T> {
        protected DimensionComponent spacing = new StaticDimensionComponent.Builder<>().val(10).build();

        public Builder() {

        }

        public T spacing(DimensionComponent spacing) {
            this.spacing = spacing;
            return (T) this;
        }

        public void validate() {

        }

        public StackView build() {
            validate();
            return null;
        }
    }

    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends StackView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public HorizontalStackView build() {
            validate();
            return new HorizontalStackView(this);
        }
    }
     */
}
