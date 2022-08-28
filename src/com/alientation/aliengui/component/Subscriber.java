package com.alientation.aliengui.component;

import java.util.*;

/**
 * Pardon my naming, but
 *
 * This essentially is used for parent objects that have subscribed objects,
 * Therefore, all this does is maintain the subscribed list and notifies subscribed objects
 *
 * WARNING potentially dangerous infinite recursion with notify if it is implemented in both the Subscriber and Observer
 * and calls each other to register/unregister
 * TODO prevent this
 *
 * @param <D>   Parent object type
 * @param <T>   Observed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Subscriber<D,T> {
    protected D parent;
    protected Set<T> subscribed = new HashSet<>();

    public Subscriber(D parent) {
        this.parent = parent;
    }

    public Subscriber(Collection<T> subscribed) {
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

    public D getParent() {
        return parent;
    }

    public List<T> getSubscribed() {
        return new ArrayList<>(subscribed);
    }

    public abstract void notifySubscribers();
    public abstract void unregister(T subscribed);
    public abstract void register(T subscribed);
}
