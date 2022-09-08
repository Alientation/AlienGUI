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
 * @param <O>   Subscribed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Subscriber<P, O> {
    protected P parent;
    protected Set<O> subscribed = new HashSet<>();
    private final int maxSubscribers;

    public Subscriber(P parent) {
        this(parent,Integer.MAX_VALUE);
    }

    public Subscriber(P parent, int maxSubscribers) {
        this.parent = parent;
        this.maxSubscribers = maxSubscribers;
    }

    public Subscriber(P parent, Collection<O> subscribed) {
        this(parent);
        registerSubscribed(subscribed);
    }

    public Subscriber(P parent, Collection<O> subscribed, int maxSubscribers) {
        this(parent, maxSubscribers);
        registerSubscribed(subscribed);
    }

    public void replaceSubscribed(O toBeReplaced, O replacedWith) {
        if (toBeReplaced == replacedWith) return;
        unregisterSubscribed(toBeReplaced);
        registerSubscribed(replacedWith);
    }

    public void clearSubscribed() {
        for (O sub : this.subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void setSubscribed(Collection<O> subscribed) {
        clearSubscribed();
        for (O sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public final void setSubscribed(O... subscribed) {
        clearSubscribed();
        for (O sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(O subscribed) {
        if (this.subscribed.contains(subscribed)) throw new IllegalStateException("Duplicate Subscribed");
        if (this.subscribed.size() == maxSubscribers) throw new IllegalStateException("maxSubscribers limit reached");
        this.subscribed.add(subscribed);
        register(subscribed);
        notifySubscribers();
    }

    public void registerSubscribed(O... subscribed) {
        for (O sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(Collection<O> subscribed) {
        for (O sub : subscribed)
            this.registerSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(O subscribed) {
        if (!this.subscribed.contains(subscribed)) return;
        this.subscribed.remove(subscribed);
        unregister(subscribed);
        notifySubscribers();
    }

    public void unregisterSubscribed(O... subscribed) {
        for (O sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(Collection<O> subscribed) {
        for (O sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public int getSubscribedCount() { return subscribed.size(); }

    public P getParent() { return parent; }
    public List<O> getSubscribed() { return new ArrayList<>(subscribed); }
    public int getMaxSubscribers() { return maxSubscribers; }


    // TODO: 9/7/2022
    //should instead make interfaces for 'subscribed' so 'unregister' and 'register' methods can be offloaded to the subscribed class
    //same with the parents for 'notifySubscribers'

    public abstract void notifySubscribers();
    public abstract void unregister(O subscribed);
    public abstract void register(O subscribed);
}
