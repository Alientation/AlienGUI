package com.aliengui.component;

import java.util.*;

/**
 * Pardon my naming, but
 * <p>
 * This essentially is used for parent objects that have subscribed objects,
 * Therefore, all this does is maintain the subscribed list and notifies subscribed objects
 * <p>
 * WARNING potentially dangerous infinite recursion with notify if it is implemented in both the Subscriber and Observer
 * and calls each other to register/unregister - THINK THIS IS FIXED
 *
 * @param <P>   Parent object type
 * @param <S>   Subscribed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Subscriber<P, S> {
    protected P parent;
    protected HashSet<S> subscribed = new HashSet<>();
    private final int maxSubscribers;

    public Subscriber(P parent) {
        this(parent,Integer.MAX_VALUE);
    }

    public Subscriber(P parent, int maxSubscribers) {
        this.parent = parent;
        this.maxSubscribers = maxSubscribers;
    }

    public Subscriber(P parent, Collection<S> subscribed) {
        this(parent);
        registerSubscribed(subscribed);
    }

    public Subscriber(P parent, Collection<S> subscribed, int maxSubscribers) {
        this(parent, maxSubscribers);
        registerSubscribed(subscribed);
    }

    public void replaceSubscribed(S toBeReplaced, S replacedWith) {
        if (toBeReplaced == replacedWith) return;
        unregisterSubscribed(toBeReplaced);
        registerSubscribed(replacedWith);
    }

    public void clearSubscribed() {
        S[] arr = (S[]) subscribed.toArray();
        for (S sub : arr)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void setSubscribed(Collection<S> subscribed) {
        clearSubscribed();
        for (S sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public final void setSubscribed(S... subscribed) {
        clearSubscribed();
        for (S sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(S subscribed) {
        if (this.subscribed.contains(subscribed)) throw new IllegalStateException("Duplicate Subscribed");
        if (this.subscribed.size() == maxSubscribers) throw new IllegalStateException("maxSubscribers limit reached");
        this.subscribed.add(subscribed);
        register(subscribed);
        notifySubscribers();
    }

    public void registerSubscribed(S... subscribed) {
        for (S sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(Collection<S> subscribed) {
        for (S sub : subscribed)
            this.registerSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(S subscribed) {
        if (!this.subscribed.contains(subscribed)) return;
        this.subscribed.remove(subscribed);
        unregister(subscribed);
        notifySubscribers();
    }

    public void unregisterSubscribed(S... subscribed) {
        for (S sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(Collection<S> subscribed) {
        for (S sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public int getSubscribedCount() { return subscribed.size(); }

    public P getParent() { return parent; }
    public List<S> getSubscribed() { return new ArrayList<>(subscribed); }
    public int getMaxSubscribers() { return maxSubscribers; }


    // TODO: 9/7/2022
    //should instead make interfaces for 'subscribed' so 'unregister' and 'register' methods can be offloaded to the subscribed class
    //same with the parents for 'notifySubscribers'

    public abstract void notifySubscribers();
    public abstract void unregister(S subscribed);
    public abstract void register(S subscribed);
}
