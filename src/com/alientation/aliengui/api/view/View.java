package com.alientation.aliengui.api.view;


import com.alientation.aliengui.api.controller.ViewController;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyInputListener;
import com.alientation.aliengui.event.model.ModelListener;
import com.alientation.aliengui.event.mouse.MouseInputListener;
import com.alientation.aliengui.event.view.ViewListener;

public class View {
    private EventListenerContainer<KeyInputListener> keyInputListeners = new EventListenerContainer<>();
    private EventListenerContainer<ModelListener> modelListeners = new EventListenerContainer<>();
    private EventListenerContainer<MouseInputListener> mouseInputListeners = new EventListenerContainer<>();
    private EventListenerContainer<ViewListener> viewListeners = new EventListenerContainer<>();

    protected ViewController controller;

    public View(Builder<?> builder) {


    }

    public void registerController(ViewController controller) {
        //update old controller -> remove reference to this view
        this.controller = controller;
        //update new controller -> add reference to this view
    }

    public EventListenerContainer<KeyInputListener> getKeyInputListeners() { return keyInputListeners; }
    public EventListenerContainer<ModelListener> getModelListeners() { return modelListeners; }
    public EventListenerContainer<MouseInputListener> getMouseInputListeners() { return mouseInputListeners; }
    public EventListenerContainer<ViewListener> getViewListeners() { return viewListeners; }
    public ViewController getController() { return controller; }

    static class Builder<T extends Builder<T>> {

        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
}

/* Builder pattern boilerplate code
static class Builder<T extends Builder<T>> {

        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
 */