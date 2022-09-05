package com.aliengui.event.view.collection;

import com.aliengui.api.view.collection.CollectionView;
import com.aliengui.api.view.collection.stack.CollectionElementView;
import com.aliengui.event.Event;

@SuppressWarnings("unused")
public class CollectionEvent extends Event {
    protected CollectionView collectionView;
    protected CollectionElementView changedElement;

    public CollectionEvent(CollectionView collectionView) {
        this.collectionView = collectionView;
    }

    public CollectionEvent(CollectionView collectionView, CollectionElementView changedElement) {
        this(collectionView);
        this.changedElement = changedElement;
    }

    public CollectionView getCollectionView() { return collectionView; }
    public CollectionElementView getChangedElement() { return changedElement; }
}
