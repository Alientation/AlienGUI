package com.aliengui.component;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class ObserverTest {

    @Test
    @DisplayName("Observer::replaceObserved")
    public void testReplaceObserved() {
        ArrayList<Integer> list = new ArrayList<>(List.of(new Integer[]{1, 2, 3,4,5,6,7,8,9}));

        Observer<Integer,Integer> observer = new Observer<>(0, list) {
            @Override
            public void notifyObservers() { }
            @Override
            public void unregister(Integer observed) { }
            @Override
            public void register(Integer observed) { }
        };

        observer.replaceObserved(2, 22);
        Assertions.assertTrue(observer.getObserved().contains(22));
        Assertions.assertFalse(observer.getObserved().contains(2));
        Assertions.assertEquals(observer.getObservedCount(),list.size());
    }

    @Test
    @DisplayName("Observer::clearObserved")
    public void testClearObserved() {

    }

}
