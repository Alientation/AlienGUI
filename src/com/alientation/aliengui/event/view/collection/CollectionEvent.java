package com.alientation.aliengui.event.view.collection;

import com.alientation.aliengui.api.view.collection.CollectionView;
import com.alientation.aliengui.api.view.collection.stack.CollectionElementView;
import com.alientation.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public class CollectionEvent extends ViewEvent {
    protected CollectionElementView changedElement;

    public CollectionEvent(CollectionView collectionView) {
        super(collectionView);
    }

    public CollectionEvent(CollectionView collectionView, CollectionElementView changedElement) {
        this(collectionView);
        this.changedElement = changedElement;
    }

    @Override
    public CollectionView getView() { return (CollectionView) view; }
    public CollectionElementView getChangedElement() { return changedElement; }
}
