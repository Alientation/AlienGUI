package com.alientation.aliengui.component;

import com.alientation.aliengui.api.view.View;

import java.util.*;

@SuppressWarnings("unused")
public abstract class Component {
    /**
     * Views that depend on this color component
     */
    protected Set<View> subscribers = new HashSet<>();

    /**
     * Notifies all subscribers of state change
     */
    public abstract void notifySubscribers();

    public void registerSubscriber(View subscriber) { this.subscribers.add(subscriber); }
    public void registerSubscribers(View... subscribers) { this.subscribers.addAll(Arrays.stream(subscribers).toList()); }
    public void registerSubscribers(Collection<View> subscribers) { this.subscribers.addAll(subscribers); }
    public void unregisterSubscriber(View subscriber) { this.subscribers.remove(subscriber); }
    public void unregisterSubscribers(View... subscribers) { Arrays.asList(subscribers).forEach(this.subscribers::remove); }
    public void unregisterSubscriber(Collection<View> subscribers) { this.subscribers.removeAll(subscribers); }

    public List<View> getSubscribers() { return subscribers.stream().toList(); }
}
