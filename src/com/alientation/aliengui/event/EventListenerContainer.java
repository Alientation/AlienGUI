package com.alientation.aliengui.event;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EventListenerContainer<T extends EventListener> {
    private final Map<Integer, Set<T>> eventListenerMap = new TreeMap<>();

    public boolean addListener(T listener, int priority) {
        removeListener(listener);
        eventListenerMap.computeIfAbsent(priority, k -> new HashSet<>());
        return eventListenerMap.get(priority).add(listener);
    }

    public boolean addListenerAtEnd(T listener) {
        int currentEnd = Integer.MIN_VALUE;
        for (int i : eventListenerMap.keySet())
            if (i > currentEnd)
                currentEnd = i;
        return addListener(listener,currentEnd+1);
    }

    public boolean addListenerAtBeginning(T listener) {
        int currentBeginning = Integer.MAX_VALUE;
        for (int i : eventListenerMap.keySet())
            if (i < currentBeginning)
                currentBeginning = i;
        return addListener(listener,currentBeginning-1);
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
