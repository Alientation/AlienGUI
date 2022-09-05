package com.aliengui.api.view.collection;

import com.aliengui.api.view.View;
import com.aliengui.api.view.collection.stack.CollectionElementView;
import com.aliengui.event.EventListenerContainer;
import com.aliengui.event.view.collection.CollectionEvent;
import com.aliengui.event.view.collection.CollectionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages collections of Views for implementing classes to use
 *
 */
@SuppressWarnings("unused")
public abstract class CollectionView extends View {
    protected EventListenerContainer<CollectionListener> collectionListeners = new EventListenerContainer<>();

    protected List<CollectionElementView> collection = new ArrayList<>();


    /**
     * Constructs a new CollectionView using the Builder pattern
     *
     * @param builder Builder for this CollectionView
     */
    protected CollectionView(Builder<?> builder) {
        super(builder);
        for (CollectionElementView view : builder.collection) add(view);
    }

    /**
     * Registers a view to this collection
     *
     * @param view  View to be added to this collection
     */
    public void add(CollectionElementView view) {
        if (collection.contains(view)) return; //no duplicates
        collection.add(view);

        if (view.isInitialized()) addChildViews(view);
        else view.init(this);

        getCollectionListeners().dispatch(listener -> listener.elementAdded(new CollectionEvent(this,view)));
    }

    public void addElement(View element) {
        for (CollectionElementView collectionElementView : collection)
            if (collectionElementView.getCollectionElement() == element)
                return;
        add(new CollectionElementView.Builder<>().collectionElement(element).build());
    }

    /**
     * Unregisters a view from this collection
     *
     * @param view  View to be removed from this collection
     */
    public void remove(CollectionElementView view) {
        if (!collection.contains(view)) return;
        collection.remove(view);

        removeChildViews(view);

        getCollectionListeners().dispatch(listener -> listener.elementRemoved(new CollectionEvent(this,view)));
    }

    public void removeElement(View element) {
        for (CollectionElementView collectionElementView : collection)
            if (collectionElementView.getCollectionElement() == element) {
                remove(collectionElementView);
                return;
            }
    }

    public EventListenerContainer<CollectionListener> getCollectionListeners() { return collectionListeners; }
    public List<View> getCollection() { return new ArrayList<>(collection); } //to prevent unauthorized altering the internal array



    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected List<CollectionElementView> collection = new ArrayList<>();
        public Builder() {

        }

        public T collection(List<CollectionElementView> collection) {
            this.collection.addAll(collection);
            return (T) this;
        }
        public T collection(CollectionElementView... collection) {
            this.collection.addAll(List.of(collection));
            return (T) this;
        }
        public T addElements(List<View> elements) {
            for (View view : elements)
                this.collection.add(new CollectionElementView.Builder<>().collectionElement(view).build());
            return (T) this;
        }
        public T addElements(View... elements) {
            for (View view : elements)
                collection.add(new CollectionElementView.Builder<>().collectionElement(view).build());
            return (T) this;
        }

        public void validate() {
            super.validate();
        }

        public View build() {
            validate();
            return null;
        }
    }
    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends CollectionView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
     */
}
