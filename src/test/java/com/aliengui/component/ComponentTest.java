package com.aliengui.component;

import com.aliengui.api.view.View;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class ComponentTest {

    Component component;
    ArrayList<View> views;

    @BeforeEach
    public void setup() {
        component = new Component() { };
        views = new ArrayList<>(List.of(new View.Builder<>().build(),new View.Builder<>().build(),
                new View.Builder<>().build(),new View.Builder<>().build(),new View.Builder<>().build()));
    }


    @Test
    @DisplayName("Component::notifySubscribers")
    @Disabled
    public void testNotifySubscribers() { }

    @Test
    @DisplayName("Component::registerSubscriber")
    public void testRegisterSubscriber() {
        View addedView = new View.Builder<>().build();
        component.registerSubscribers(addedView);
        Assertions.assertTrue(component.subscribers.containsSubscribed(addedView));
    }

    @Test
    @DisplayName("Component::registerSubscribers")
    public void testRegisterSubscriberParams() { // TODO: 9/7/2022 Make it cleaner
        ArrayList<View> addedViews = new ArrayList<>(List.of(new View.Builder<>().build(),
                new View.Builder<>().build(),new View.Builder<>().build(),new View.Builder<>().build(),new View.Builder<>().build()));

        component.registerSubscribers(addedViews.get(0),addedViews.get(1),addedViews.get(2),addedViews.get(3),addedViews.get(4));
        for (View addedView : addedViews)
            Assertions.assertTrue(component.subscribers.containsSubscribed(addedView));
    }

    @Test
    @DisplayName("Component::registerSubscribers")
    public void testRegisterSubscriberCollection() {
        ArrayList<View> addedViews = new ArrayList<>(List.of(new View.Builder<>().build(),
                new View.Builder<>().build(),new View.Builder<>().build(),new View.Builder<>().build(),new View.Builder<>().build()));
        component.registerSubscribers(addedViews);
        for (View addedView : addedViews)
            Assertions.assertTrue(component.subscribers.containsSubscribed(addedView));
    }

    @Test
    @DisplayName("Component::unregisterSubscriber")
    public void testUnregisterSubscriber() {
        component.unregisterSubscriber(views.get(0));
        Assertions.assertFalse(component.subscribers.containsSubscribed(views.get(0)));
    }

    @Test
    @DisplayName("Component::unregisterSubscribers")
    public void testUnregisterSubscriberParams() {
        component.unregisterSubscribers(views.get(0),views.get(1),views.get(2),views.get(3),views.get(4));
        for (View viewRemoved : views)
            Assertions.assertFalse(component.subscribers.containsSubscribed(viewRemoved));
    }

    @Test
    @DisplayName("Component::unregisterSubscribers")
    public void testUnregisterSubscriberCollection() {
        component.unregisterSubscribers(views);
        for (View viewRemoved : views)
            Assertions.assertFalse(component.subscribers.containsSubscribed(viewRemoved));
    }
}
