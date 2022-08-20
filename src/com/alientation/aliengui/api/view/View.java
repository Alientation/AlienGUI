package com.alientation.aliengui.api.view;


import com.alientation.aliengui.api.controller.ViewController;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.model.ModelListener;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewListener;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class View {
    protected EventListenerContainer<KeyListener> keyListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ModelListener> modelListeners = new EventListenerContainer<>();
    protected EventListenerContainer<MouseListener> mouseListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ViewListener> viewListeners = new EventListenerContainer<>();

    protected ViewController controller;

    protected int x, y, width, height, minWidth, minHeight, maxWidth, maxHeight; //make dimension class for this



    protected View container;
    protected WindowView windowView;
    protected List<View> subviews;
    protected boolean initialized; //whether all properties are initialized and ready to render
    protected boolean visible;
    protected int zIndex;

    public View(Builder<?> builder) {


    }

    public void render(Graphics graphics) {

    }

    public void tick() {

    }

    public void addSubviews(View...views) {
        subviews.addAll(Arrays.stream(views).toList());
    }

    public View getContainer() {
        return container;
    }

    public WindowView getWindowView() {
        return windowView;
    }

    public List<View> getSubviews() {
        return subviews;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getZIndex() {
        return zIndex;
    }

    public void setContainer(View container) { //TODO update references
        this.container = container;
    }

    public void setHidden() {
        this.visible = false;
    }

    public void setShown() {
        this.visible = true;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }
    public void registerController(ViewController controller) {
        //update old controller -> remove reference to this view
        this.controller = controller;
        //update new controller -> add reference to this view
    }

    public EventListenerContainer<KeyListener> getKeyListeners() { return keyListeners; }
    public EventListenerContainer<ModelListener> getModelListeners() { return modelListeners; }
    public EventListenerContainer<MouseListener> getMouseListeners() { return mouseListeners; }
    public EventListenerContainer<ViewListener> getViewListeners() { return viewListeners; }
    public ViewController getController() { return controller; }

    @SuppressWarnings("unused")
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
static class Builder<T extends Builder<T>> extends View.Builder<T> {

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