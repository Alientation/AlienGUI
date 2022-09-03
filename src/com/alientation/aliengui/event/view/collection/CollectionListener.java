package com.alientation.aliengui.event.view.collection;

import com.alientation.aliengui.event.EventListener;
import com.alientation.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public abstract class CollectionListener extends EventListener {
    public void elementAdded(CollectionEvent event) {
        collectionStateChanged(event);
    }
    public void elementRemoved(CollectionEvent event) {
        collectionStateChanged(event);
    }

    public void collectionStateChanged(CollectionEvent event) {
        event.getCollectionView().getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(event.getCollectionView())));
    }

}
