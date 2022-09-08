package com.aliengui.component;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest {
    ArrayList<Integer> list;
    Observer<Integer,Integer> observer;

    @BeforeEach
    public void setup() {
        list = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5,6,7,8,9}));

        observer = new Observer<>(0, list) {
            @Override
            public void notifyObservers() { }
            @Override
            public void unregister(Integer observed) { }
            @Override
            public void register(Integer observed) { }
        };
    }

    @Test
    @DisplayName("Observer::replaceObserved")
    public void testReplaceObserved() {
        observer.replaceObserved(2, 22);
        Assertions.assertTrue(observer.containsObserved(22));
        Assertions.assertFalse(observer.containsObserved(2));
        Assertions.assertEquals(observer.getObservedCount(),list.size());
    }

    @Test
    @DisplayName("Observer::clearObserved")
    public void testClearObserved() {
        observer.clearObserved();
        Assertions.assertEquals(observer.getObservedCount(),0);
    }

    @Test
    @DisplayName("Observer::setObserved")
    public void testSetObservedCollection() {
        ArrayList<Integer> newList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        observer.setObserved(newList);
        Assertions.assertEquals(observer.getObservedCount(),newList.size());
        for (int observed : newList)
            Assertions.assertTrue(observer.containsObserved(observed));
    }

    @Test
    @DisplayName("Observer::setObserved")
    public void testSetObservedParams() {
        ArrayList<Integer> newList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        observer.setObserved(newList.get(0),newList.get(1),newList.get(2),newList.get(3),newList.get(4));
        Assertions.assertEquals(observer.getObservedCount(),newList.size());
        for (int observed : newList)
            Assertions.assertTrue(observer.containsObserved(observed));
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObserved() {
        int size = observer.getObservedCount() + 1;
        observer.registerObserved(200);
        Assertions.assertTrue(observer.containsObserved(200));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObservedCollection() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        int size = observer.getObservedCount() + addedList.size();
        observer.registerObserved(addedList);
        for (int observed : addedList)
            Assertions.assertTrue(observer.containsObserved(observed));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }

    @Test
    @DisplayName("Observer::registerObserved")
    public void testRegisterObservedParams() {
        ArrayList<Integer> addedList = new ArrayList<>(List.of(new Integer[]{11,22,33,44,55}));
        int size = observer.getObservedCount() + addedList.size();
        observer.registerObserved(addedList.get(0),addedList.get(1),addedList.get(2),addedList.get(3),addedList.get(4));
        for (int observed : addedList)
            Assertions.assertTrue(observer.containsObserved(observed));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }


    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObserved() {
        int size = observer.getObservedCount() - 1;
        observer.unregisterObserved(1);
        Assertions.assertFalse(observer.containsObserved(1));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }

    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObservedCollection() {
        ArrayList<Integer> removedList = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5}));
        int size = observer.getObservedCount() - removedList.size();
        observer.unregisterObserved(removedList);
        for (int observed : removedList)
            Assertions.assertFalse(observer.containsObserved(observed));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }

    @Test
    @DisplayName("Observer::unregisterObserved")
    public void testUnregisterObservedParams() {
        ArrayList<Integer> removedList = new ArrayList<>(List.of(new Integer[]{1,2,3,4,5}));
        int size = observer.getObservedCount() - removedList.size();
        observer.unregisterObserved(removedList.get(0),removedList.get(1),removedList.get(2),removedList.get(3),removedList.get(4));
        for (int observed : removedList)
            Assertions.assertFalse(observer.containsObserved(observed));
        Assertions.assertEquals(observer.getObservedCount(),size);
    }
}
