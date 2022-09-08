package com.aliengui.component;

import java.util.*;

/**
 * Pardon my naming, but
 * <p>
 * This essentially is used for parent objects that observe another set of objects for state changes,
 * Therefore, all this does is link the parent object to the observed set of objects by registering the parent
 * as a subscriber to the set of objects
 *
 * @param <P>   Parent object type
 * @param <O>   Observed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Observer<P, O> {
    protected P parent;
    protected Set<O> observed = new HashSet<>();
    private final int maxObservers;

    public Observer(P parent) {
        this(parent,Integer.MAX_VALUE);
    }

    public Observer(P parent, int maxObservers) {
        this.parent = parent;
        this.maxObservers = maxObservers;
    }

    public Observer(P parent, Collection<O> observed) {
        this(parent);
        setObserved(observed);
    }

    public Observer(P parent, Collection<O> observed, int maxObservers) {
        this(parent, maxObservers);
        setObserved(observed);
    }

    public void replaceObserved(O toBeReplaced, O replacedWith) {
        if (toBeReplaced == replacedWith) return;
        unregisterObserved(toBeReplaced);
        registerObserved(replacedWith);
    }

    public void clearObserved() {
        for (O obs : this.observed)
            unregisterObserved(obs);
        notifyObservers();
    }

    public void setObserved(Collection<O> observed) {
        clearObserved();
        for (O obs : observed)
            registerObserved(obs);
        notifyObservers();
    }

    public final void setObserved(O... observed) {
        clearObserved();
        for (O obs : observed)
            registerObserved(obs);
        notifyObservers();
    }

    public void registerObserved(O observed) {
        if (this.observed.contains(observed)) throw new IllegalStateException("Observer::registerObserved Duplicate Observed");
        if (this.observed.size() == maxObservers) throw new IllegalStateException("Observer::registerObserved maxObservers limit reached");
        this.observed.add(observed);
        register(observed);
        notifyObservers();
    }

    public void registerObserved(O... observed) {
        for (O obs : observed)
            registerObserved(obs);
    }

    public void registerObserved(Collection<O> observed) {
        for (O obs : observed)
            registerObserved(obs);
    }

    public void unregisterObserved(O observed) {
        if (!this.observed.contains(observed)) return;
        this.observed.remove(observed);
        unregister(observed);
        notifyObservers();
    }

    public void unregisterObserved(O... observed) {
        for (O obs : observed)
            unregisterObserved(obs);
    }

    public void unregisterObserved(Collection<O> observed) {
        for (O obs : observed)
            unregisterObserved(obs);
    }

    public int getObservedCount() { return observed.size(); }

    public P getParent() { return parent; }
    public List<O> getObserved() { return new ArrayList<>(observed); }
    public int getMaxObservers() { return maxObservers; }

    // TODO: 9/7/2022  
    //should instead make interfaces for 'observed' so 'unregister' and 'register' methods can be offloaded to the observed class
    //same with the parents for 'notifyObservers'
    
    public abstract void notifyObservers();
    public abstract void unregister(O observed);
    public abstract void register(O observed);
}