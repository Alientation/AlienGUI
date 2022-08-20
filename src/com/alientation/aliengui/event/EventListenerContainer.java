package com.alientation.aliengui.event;


import java.util.*;

public class EventListenerContainer<T extends EventListener> {
    public static final int PRIORITY_FIRST = 0;
    public static final int PRIORITY_LAST = 1000;
    private final TreeMap<Integer, ArrayList<T>> eventListenerMap = new TreeMap<>(Comparator.reverseOrder());

    public void dispatch(EventDispatch<T> dispatch) {
        for (int i : eventListenerMap.navigableKeySet())
            for (T listener : eventListenerMap.get(i))
                dispatch.dispatch(listener);
    }

    public boolean addListener(T listener, int priority) {
        if (priority < PRIORITY_FIRST) priority = PRIORITY_FIRST;
        if (priority > PRIORITY_LAST) priority = PRIORITY_LAST;
        removeListener(listener);
        eventListenerMap.computeIfAbsent(priority, k -> new ArrayList<>());
        return eventListenerMap.get(priority).add(listener);
    }

    public boolean addListenerAtEnd(T listener) {
        int currentEnd = eventListenerMap.lastKey() + 1;
        if (currentEnd > PRIORITY_LAST) currentEnd = PRIORITY_LAST;
        return addListener(listener,currentEnd+1);
    }

    public boolean addListenerAtBeginning(T listener) {
        int currentBeginning = eventListenerMap.firstKey() - 1;
        if (currentBeginning < PRIORITY_FIRST) currentBeginning = PRIORITY_FIRST;
        return addListener(listener,currentBeginning);
    }

    public boolean addListener(T listener) {
        return addListenerAtEnd(listener);
    }

    public boolean removeListener(T listener) {
        for (int i : eventListenerMap.keySet())
            return eventListenerMap.get(i).remove(listener);
        return false;
    }

    public boolean containsListener(T listener) {
        for (int i : eventListenerMap.keySet())
            if (eventListenerMap.get(i).contains(listener))
                return true;
        return false;
    }
}
