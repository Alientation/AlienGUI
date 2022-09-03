package com.alientation.aliengui.event.view.collection;

import com.alientation.aliengui.event.EventListener;
import com.alientation.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public abstract class CollectionElementListener extends EventListener {
    public void changedCollectionElement(CollectionElementEvent event) {
        collectionElementStateChanged(event);
    }

    public void collectionElementStateChanged(CollectionElementEvent event) {
        event.getCollectionElementView().getViewListeners().dispatch(listener -> new ViewEvent(event.getCollectionElementView()));
    }
}
