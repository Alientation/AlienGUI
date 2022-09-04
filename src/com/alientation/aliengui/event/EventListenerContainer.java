package com.alientation.aliengui.event;


import java.util.*;

/**
 * Container class for EventListeners to handle dispatching events to listeners in order of priority
 * Reserves limits on priority values to ensure internal systems can operate
 *
 * @param <T>   EventListener contained within this
 */
@SuppressWarnings("unused")
public class EventListenerContainer<T extends EventListener> {
    public static final int PRIORITY_FIRST = 0;
    public static final int PRIORITY_LAST = 1000;
    private final TreeMap<Integer, ArrayList<T>> eventListenerMap = new TreeMap<>(Comparator.reverseOrder());

    public void dispatch(EventDispatch<T> dispatch) {
        for (int i : eventListenerMap.navigableKeySet())
            for (T listener : eventListenerMap.get(i))
                dispatch.dispatch(listener);
    }

    public void addListener(T listener, int priority) {
        if (priority < PRIORITY_FIRST) priority = PRIORITY_FIRST;
        if (priority > PRIORITY_LAST) priority = PRIORITY_LAST;
        removeListener(listener);
        eventListenerMap.computeIfAbsent(priority, k -> new ArrayList<>());

        //ensure first ones to add to the last spot will be at the end of the list
        if (priority == PRIORITY_LAST) eventListenerMap.get(priority).add(0, listener);
        else eventListenerMap.get(priority).add(listener);
    }

    public void addListenerAtEnd(T listener) {
        int currentEnd = eventListenerMap.lastKey() + 1;
        addListener(listener,currentEnd+1);
    }

    public void addListenerAtBeginning(T listener) {
        int currentBeginning = eventListenerMap.firstKey() - 1;
        if (currentBeginning < PRIORITY_FIRST) currentBeginning = PRIORITY_FIRST;
        addListener(listener,currentBeginning);
    }

    public void addListener(T listener) {
        addListenerAtEnd(listener);
    }

    public void removeListener(T listener) {
        for (int i : eventListenerMap.keySet())
            eventListenerMap.get(i).remove(listener);
    }

    public boolean containsListener(T listener) {
        for (int i : eventListenerMap.keySet())
            if (eventListenerMap.get(i).contains(listener))
                return true;
        return false;
    }
}
