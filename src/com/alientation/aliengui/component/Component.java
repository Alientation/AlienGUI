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
    //TODO optimization, instead of instantly notifying listeners of state changes, mark this and wait until the next tick event
    //This way, instead of have tons of tiny events, the events are grouped in batches between tick events
    public abstract void notifySubscribers();


    //HOLY THIS IS SO MUCH CODE REPETITION TODO CLEAN THIS STUFF UP!!!!
    //HAVE ANOTHER CLASS SIMPLY TO STORE THE HASHSET AND HANDLE ADDING AND REMOVING, THIS CLASS CAN SIMPLY JUST BE AN ADAPTER TO THAT
    //PROBABLY GONNA NEED TO SUPPLY AN INTERFACE FOR THE ACTUAL ADD AND REMOVE STUFF
    public void registerSubscriber(View subscriber) { this.subscribers.add(subscriber); }
    public void registerSubscribers(View... subscribers) { this.subscribers.addAll(Arrays.stream(subscribers).toList()); }
    public void registerSubscribers(Collection<View> subscribers) { this.subscribers.addAll(subscribers); }
    public void unregisterSubscriber(View subscriber) { this.subscribers.remove(subscriber); }
    public void unregisterSubscribers(View... subscribers) { Arrays.asList(subscribers).forEach(this.subscribers::remove); }
    public void unregisterSubscriber(Collection<View> subscribers) { this.subscribers.removeAll(subscribers); }

    public List<View> getSubscribers() { return subscribers.stream().toList(); }
}
