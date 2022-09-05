package com.aliengui.event.view.collection;

import com.aliengui.event.EventListener;
import com.aliengui.event.view.ViewEvent;

@SuppressWarnings("unused")
public abstract class CollectionElementListener extends EventListener {
    public void changedCollectionElement(CollectionElementEvent event) {
        collectionElementStateChanged(event);
    }

    public void collectionElementStateChanged(CollectionElementEvent event) {
        event.getCollectionElementView().getViewListeners().dispatch(listener -> new ViewEvent(event.getCollectionElementView()));
    }
}
