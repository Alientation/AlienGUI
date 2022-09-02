package com.alientation.aliengui.component;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;

import java.util.*;

@SuppressWarnings("unused")
public abstract class Component {
    protected int maxSubscribers = Integer.MAX_VALUE; //throws error if limit reached

    public Component() {

    }

    public Component(int maxSubscribers) {
        this.maxSubscribers = maxSubscribers;
    }

    /**
     * Views that depend on this color component
     */
    protected Subscriber<Component,View> subscribers = new Subscriber<>(this) {
        @Override
        public void notifySubscribers() {
            for (View view : subscribers.getSubscribed())
                view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
        }

        @Override
        public void unregister(View subscribed) { }
        @Override
        public void register(View subscribed) { }
    };

    /**
     * Notifies all subscribers of state change
     */
    //TODO optimization, instead of instantly notifying listeners of state changes, mark this and wait until the next tick event
    //This way, instead of have tons of tiny events, the events are grouped in batches between tick events
    public void notifySubscribers() {
        subscribers.notifySubscribers();
    }

    public void registerSubscriber(View subscriber) { this.subscribers.registerSubscribed(subscriber); }
    public void registerSubscribers(View... subscribers) { this.subscribers.registerSubscribed(Arrays.stream(subscribers).toList()); }
    public void registerSubscribers(Collection<View> subscribers) { this.subscribers.registerSubscribed(subscribers); }
    public void unregisterSubscriber(View subscriber) { this.subscribers.unregisterSubscribed(subscriber); }
    public void unregisterSubscribers(View... subscribers) { this.subscribers.unregisterSubscribed(subscribers); }
    public void unregisterSubscriber(Collection<View> subscribers) { this.subscribers.unregisterSubscribed(subscribers); }

    public Subscriber<Component, View> getSubscribers() { return subscribers; }
    public int getMaxSubscribers() { return maxSubscribers; }
}
