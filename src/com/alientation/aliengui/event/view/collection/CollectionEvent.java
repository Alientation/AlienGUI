package com.alientation.aliengui.event.view.collection;

import com.alientation.aliengui.api.view.collection.CollectionView;
import com.alientation.aliengui.api.view.collection.stack.CollectionElementView;
import com.alientation.aliengui.event.Event;

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
