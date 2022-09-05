package com.alientation.aliengui.component;

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
 * @param <D>   Parent object type
 * @param <T>   Observed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Subscriber<D,T> {
    protected D parent;
    protected Set<T> subscribed = new HashSet<>();
    private final int maxSubscribers;

    public Subscriber(D parent) {
        this(parent,Integer.MAX_VALUE);
    }

    public Subscriber(D parent, int maxSubscribers) {
        this.parent = parent;
        this.maxSubscribers = maxSubscribers;
    }

    public Subscriber(D parent, Collection<T> subscribed) {
        this(parent);
        registerSubscribed(subscribed);
    }

    public Subscriber(D parent, Collection<T> subscribed, int maxSubscribers) {
        this(parent, maxSubscribers);
        registerSubscribed(subscribed);
    }

    public void replaceSubscribed(T toBeReplaced, T replacedWith) {
        if (toBeReplaced == replacedWith) return;
        unregisterSubscribed(toBeReplaced);
        registerSubscribed(replacedWith);
    }

    public void clearSubscribed() {
        for (T sub : this.subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void setSubscribed(Collection<T> subscribed) {
        clearSubscribed();
        for (T sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public final void setSubscribed(T... subscribed) {
        clearSubscribed();
        for (T sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(T subscribed) {
        if (this.subscribed.contains(subscribed)) throw new IllegalStateException("Duplicate Subscribed");
        if (this.subscribed.size() == maxSubscribers) throw new IllegalStateException("maxSubscribers limit reached");
        this.subscribed.add(subscribed);
        register(subscribed);
        notifySubscribers();
    }

    public void registerSubscribed(T... subscribed) {
        for (T sub : subscribed)
            registerSubscribed(sub);
        notifySubscribers();
    }

    public void registerSubscribed(Collection<T> subscribed) {
        for (T sub : subscribed)
            this.registerSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(T subscribed) {
        if (!this.subscribed.contains(subscribed)) return;
        this.subscribed.remove(subscribed);
        unregister(subscribed);
        notifySubscribers();
    }

    public void unregisterSubscribed(T... subscribed) {
        for (T sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public void unregisterSubscribed(Collection<T> subscribed) {
        for (T sub : subscribed)
            unregisterSubscribed(sub);
        notifySubscribers();
    }

    public int getSubscribedCount() { return subscribed.size(); }

    public D getParent() { return parent; }
    public List<T> getSubscribed() { return new ArrayList<>(subscribed); }
    public int getMaxSubscribers() { return maxSubscribers; }

    public abstract void notifySubscribers();
    public abstract void unregister(T subscribed);
    public abstract void register(T subscribed);
}
