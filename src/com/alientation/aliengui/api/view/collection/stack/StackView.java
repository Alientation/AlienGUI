package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.collection.CollectionView;
import com.alientation.aliengui.component.dimension.DimensionComponent;
import com.alientation.aliengui.component.dimension.StaticDimensionComponent;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewListener;

@SuppressWarnings("unused")
public abstract class StackView extends CollectionView {
    protected DimensionComponent spacing;

    protected ViewListener listener = new ViewListener() {
        @Override
        public void viewStateChanged(ViewEvent event) {
            super.viewStateChanged(event);
            resize();
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
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.spacing,spacing);
        this.spacing.unregisterSubscriber(this);
        this.spacing = spacing;
        this.spacing.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }


    //GETTERS
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
