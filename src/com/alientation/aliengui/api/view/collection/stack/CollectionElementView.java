package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;

/**
 *
 */
@SuppressWarnings("unused")
public class CollectionElementView extends View {
    protected View collectionElement;
    protected boolean dynamicCollectionElementJoin; //whether when collection elements are set, their dimensions will be changed

    /**
     * Constructs a new CollectionElementView using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public CollectionElementView(Builder<?> builder) {
        super(builder);
        setCollectionElement(builder.collectionElement);
        dynamicCollectionElementJoin = builder.dynamicCollectionElementJoin;
    }

    public void setCollectionElement(View collectionElement) {
        if (this.collectionElement != null) this.removeChildViews(this.collectionElement);
        this.collectionElement = collectionElement;

        if (collectionElement.isInitialized())
            this.addChildViews(collectionElement);
        else
            collectionElement.init(this);
        if (dynamicCollectionElementJoin) {

        }
        //TODO CollectionElementEvent instead
        getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setDynamicCollectionElementJoin(boolean dynamicCollectionElementJoin) {
        this.dynamicCollectionElementJoin = dynamicCollectionElementJoin;
        getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public View getCollectionElement() { return collectionElement; }
    public boolean doDynamicCollectionElementJoin() { return dynamicCollectionElementJoin; }


    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected View collectionElement;
        protected boolean dynamicCollectionElementJoin = true;

        public Builder() {

        }

        public T collectionElement(View collectionElement) {
            this.collectionElement = collectionElement;
            return (T) this;
        }
        public T dynamicCollectionElementJoin(boolean dynamicCollectionElementJoin) {
            this.dynamicCollectionElementJoin = dynamicCollectionElementJoin;
            return (T) this;
        }

        public void validate() {

        }

        public CollectionElementView build() {
            validate();
            return new CollectionElementView(this);
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
