package com.aliengui;

import com.aliengui.component.Subscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscriberTest {
    ArrayList<Integer> list;
    Subscriber<Integer,Integer> subscriber;

    @BeforeEach
    public void setup() {
        list = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5,6,7,8,9}));

        subscriber = new Subscriber<>(0, list) {
            @Override
            public void notifySubscribers() { }
            @Override
            public void unregister(Integer subscribed) { }
            @Override
            public void register(Integer subscribed) { }
        };
    }

    @Test
    @DisplayName("Observer::replaceObserved")
    public void testReplaceObserved() {
        subscriber.replaceSubscribed(2, 22);
        Assertions.assertTrue(subscriber.containsSubscribed(22));
        Assertions.assertFalse(subscriber.containsSubscribed(2));
        Assertions.assertEquals(subscriber.getSubscribedCount(),list.size());
    }

    @Test
    @DisplayName("Observer::clearObserved")
    public void testClearObserved() {
        subscriber.clearSubscribed();
        Assertions.assertEquals(subscriber.getSubscribedCount(),0);
    }

    @Test
    @DisplayName("Observer::setObserved")
    public void testSetObservedCollection() {
        ArrayList<Integer> newList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        subscriber.setSubscribed(newList);
        Assertions.assertEquals(subscriber.getSubscribedCount(),newList.size());
        for (int observed : newList)
            Assertions.assertTrue(subscriber.containsSubscribed(observed));
    }

    @Test
    @DisplayName("Observer::setObserved")
    public void testSetObservedParams() {
        ArrayList<Integer> newList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        subscriber.setSubscribed(newList.get(0),newList.get(1),newList.get(2),newList.get(3),newList.get(4));
        Assertions.assertEquals(subscriber.getSubscribedCount(),newList.size());
        for (int observed : newList)
            Assertions.assertTrue(subscriber.containsSubscribed(observed));
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObserved() {
        int size = subscriber.getSubscribedCount() + 1;
        subscriber.registerSubscribed(200);
        Assertions.assertTrue(subscriber.containsSubscribed(200));
        Assertions.assertEquals(subscriber.getSubscribedCount(),size);
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObservedCollection() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        int size = subscriber.getSubscribedCount() + addedList.size();
        subscriber.registerSubscribed(addedList);
        for (int observed : addedList)
            Assertions.assertTrue(subscriber.containsSubscribed(observed));
        Assertions.assertEquals(subscriber.getSubscribedCount(),size);
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObservedParams() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        int size = subscriber.getSubscribedCount() + addedList.size();
        subscriber.registerSubscribed(addedList.get(0),addedList.get(1),addedList.get(2),addedList.get(3),addedList.get(4));
        for (int observed : addedList)
            Assertions.assertTrue(subscriber.containsSubscribed(observed));
        Assertions.assertEquals(subscriber.getSubscribedCount(),size);
    }


    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObserved() {
        int size = subscriber.getSubscribedCount() - 1;
        subscriber.unregisterSubscribed(1);
        Assertions.assertFalse(subscriber.containsSubscribed(1));
        Assertions.assertEquals(subscriber.getSubscribedCount(),size);
    }

    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObservedCollection() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5}));
        int size = subscriber.getSubscribedCount() - addedList.size();
        subscriber.unregisterSubscribed(addedList);
        for (int observed : addedList)
            Assertions.assertFalse(subscriber.containsSubscribed(observed));
        Assertions.assertEquals(subscriber.getMaxSubscribers(),size);
    }

    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObservedParams() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5}));
        int size = subscriber.getSubscribedCount() - addedList.size();
        subscriber.unregisterSubscribed(addedList.get(0),addedList.get(1),addedList.get(2),addedList.get(3),addedList.get(4));
        for (int observed : addedList)
            Assertions.assertFalse(subscriber.containsSubscribed(observed));
        Assertions.assertEquals(subscriber.getSubscribedCount(),size);
    }
}
