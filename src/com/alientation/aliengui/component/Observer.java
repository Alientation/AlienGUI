package com.alientation.aliengui.component;

import java.util.*;

/**
 * Pardon my naming, but
 *
 * This essentially is used for parent objects that observe another set of objects for state changes,
 * Therefore, all this does is link the parent object to the observed set of objects by registering the parent
 * as a subscriber to the set of objects
 *
 * TODO potential infinite recursion, see Subscriber.java notes
 *
 * @param <D>   Parent object type
 * @param <T>   Observed objects type
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Observer<D,T> {
    protected D parent;
    protected Set<T> observed = new HashSet<>();

    public Observer(D parent) {
        this.parent = parent;
    }

    public Observer(Collection<T> observed) {
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

    public D getParent() {
        return parent;
    }

    public List<T> getObserved() {
        return new ArrayList<>(observed);
    }

    public abstract void notifyObservers();
    public abstract void unregister(T observed);
    public abstract void register(T observed);
}