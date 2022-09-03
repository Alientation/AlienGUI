package com.alientation.aliengui.event.view.collection;

import com.alientation.aliengui.event.EventListener;

@SuppressWarnings("unused")
public abstract class CollectionListener extends EventListener {
    public void elementAdded(CollectionEvent event) {
        collectionStateChanged(event);
    }
    public void elementRemoved(CollectionEvent event) {
        collectionStateChanged(event);
    }

    public void collectionStateChanged(CollectionEvent event) { }

}
