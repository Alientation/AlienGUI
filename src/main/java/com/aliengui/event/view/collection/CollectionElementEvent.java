package com.aliengui.event.view.collection;

import com.aliengui.api.view.View;
import com.aliengui.api.view.collection.stack.CollectionElementView;
import com.aliengui.event.Event;

@SuppressWarnings("unused")
public class CollectionElementEvent extends Event {
    protected CollectionElementView collectionElementView;
    protected View oldCollectionElement, newCollectionElement;

    public CollectionElementEvent(CollectionElementView collectionElementView) {
        this.collectionElementView = collectionElementView;
    }

    public CollectionElementEvent(CollectionElementView collectionElementView, View oldCollectionElement, View newCollectionElement) {
        this(collectionElementView);
        this.oldCollectionElement = oldCollectionElement;
        this.newCollectionElement = newCollectionElement;
    }

    public CollectionElementView getCollectionElementView() { return collectionElementView; }
    public View getOldCollectionElement() { return oldCollectionElement; }
    public View getNewCollectionElement() { return newCollectionElement; }
}
