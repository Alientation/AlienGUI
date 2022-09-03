package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.view.collection.CollectionElementEvent;
import com.alientation.aliengui.event.view.collection.CollectionElementListener;

/**
 *
 */
@SuppressWarnings("unused")
public class CollectionElementView extends View {
    protected EventListenerContainer<CollectionElementListener> collectionElementListeners = new EventListenerContainer<>();
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
        CollectionElementEvent event = new CollectionElementEvent(this, this.collectionElement, collectionElement);

        if (this.collectionElement != null) this.removeChildViews(this.collectionElement);
        this.collectionElement = collectionElement;

        if (collectionElement.isInitialized())
            this.addChildViews(collectionElement);
        else
            collectionElement.init(this);
        if (dynamicCollectionElementJoin) {

        }

        getCollectionElementListeners().dispatch(listener -> listener.collectionElementStateChanged(event));
    }

    public void setDynamicCollectionElementJoin(boolean dynamicCollectionElementJoin) {
        this.dynamicCollectionElementJoin = dynamicCollectionElementJoin;
        getCollectionElementListeners().dispatch(listener -> listener.collectionElementStateChanged(new CollectionElementEvent(this)));
    }


    //GETTERs

    public EventListenerContainer<CollectionElementListener> getCollectionElementListeners() { return collectionElementListeners; }
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
