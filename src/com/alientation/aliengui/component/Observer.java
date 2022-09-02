package com.alientation.aliengui.component;

import java.util.*;

/**
 * Pardon my naming, but
 * <p>
 * This essentially is used for parent objects that observe another set of objects for state changes,
 * Therefore, all this does is link the parent object to the observed set of objects by registering the parent
 * as a subscriber to the set of objects
 *
 * @param <D>   Parent object type
 * @param <T>   Observed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Observer<D,T> {
    protected D parent;
    protected Set<T> observed = new HashSet<>();
    protected int maxObservers; //TODO implement checks

    public Observer(D parent) {
        this(parent,Integer.MAX_VALUE);
    }

    public Observer(D parent, int maxObservers) {
        this.parent = parent;
        this.maxObservers = maxObservers;
    }

    public Observer(D parent, Collection<T> observed) {
        this(parent);
        setObserved(observed);
    }

    public Observer(D parent, Collection<T> observed, int maxObservers) {
        this(parent, maxObservers);
        setObserved(observed);
    }

    public void replaceObserved(T toBeReplaced, T replacedWith) {
        if (toBeReplaced == replacedWith) return;
        unregisterObserved(toBeReplaced);
        registerObserved(replacedWith);
    }

    public void clearObserved() {
        for (T obs : this.observed)
            unregisterObserved(obs);
        notifyObservers();
    }

    public void setObserved(Collection<T> observed) {
        clearObserved();
        for (T obs : observed)
            registerObserved(obs);
        notifyObservers();
    }

    public final void setObserved(T... observed) {
        clearObserved();
        for (T obs : observed)
            registerObserved(obs);
        notifyObservers();
    }

    public void registerObserved(T observed) {
        if (this.observed.contains(observed)) return;
        this.observed.add(observed);
        register(observed);
        notifyObservers();
    }

    public void registerObserved(T... observed) {
        for (T obs : observed)
            registerObserved(obs);
    }

    public void registerObserved(Collection<T> observed) {
        for (T obs : observed)
            registerObserved(obs);
    }

    public void unregisterObserved(T observed) {
        if (!this.observed.contains(observed)) return;
        this.observed.remove(observed);
        unregister(observed);
        notifyObservers();
    }

    public void unregisterObserved(T... observed) {
        for (T obs : observed)
            unregisterObserved(obs);
    }

    public void unregisterObserved(Collection<T> observed) {
        for (T obs : observed)
            unregisterObserved(obs);
    }

    public D getParent() { return parent; }
    public List<T> getObserved() { return new ArrayList<>(observed); }
    public int getMaxObservers() { return maxObservers; }

    public abstract void notifyObservers();
    public abstract void unregister(T observed);
    public abstract void register(T observed);
}