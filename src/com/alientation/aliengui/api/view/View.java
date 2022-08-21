package com.alientation.aliengui.api.view;


import com.alientation.aliengui.api.controller.ViewController;
import com.alientation.aliengui.util.dimension.Dimension;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.model.ModelListener;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewListener;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;

@SuppressWarnings("unused")
public class View {
    protected EventListenerContainer<KeyListener> keyListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ModelListener> modelListeners = new EventListenerContainer<>();
    protected EventListenerContainer<MouseListener> mouseListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ViewListener> viewListeners = new EventListenerContainer<>();

    protected ViewController controller;

    //protected int x, y, minX, maxX, minY, maxY, width, height, minWidth, minHeight, maxWidth, maxHeight, borderRadiusX, borderRadiusY, marginX, marginY; //make dimension class for this
    protected Dimension x, y, width, height, borderRadiusX, borderRadiusY, marginX, marginY;

    protected View parentView;
    protected WindowView windowView;
    protected Set<View> subviews;
    protected boolean initialized; //whether all properties are initialized and ready to render
    protected boolean visible;
    protected int zIndex;
    protected boolean dynamicZIndexUpdate;

    public View(Builder<?> builder) {


    }

    public void dimensionChanged(Dimension dimension) {

    }

    public void init() {
        if (initialized) return; //can't initialize twice!


        initialized = true;
    }

    public void render(Graphics g) {
        if (!initialized) return;


    }

    public void tick() {
        if (!initialized) return;
    }

    public Dimension getX() {
        return x;
    }

    public Dimension getY() {
        return y;
    }

    public Dimension getWidth() {
        return width;
    }

    public Dimension getHeight() {
        return height;
    }

    public Dimension getBorderRadiusX() {
        return borderRadiusX;
    }

    public Dimension getBorderRadiusY() {
        return borderRadiusY;
    }

    public Dimension getMarginX() {
        return marginX;
    }

    public Dimension getMarginY() {
        return marginY;
    }

    public void addSubviews(View...views) {
        subviews.addAll(Arrays.stream(views).toList());
    }

    public View getParentView() {
        return parentView;
    }

    public WindowView getWindowView() {
        return windowView;
    }

    public Set<View> getSubviews() {
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

    public boolean doDynamicZIndexUpdate() {
        return dynamicZIndexUpdate;
    }

    public void setX(Dimension x) {
        this.x.unregisterDependency(this);
        this.x = x;
        this.x.registerDependency(this);
        this.x.valueChanged();
    }

    public void setY(Dimension y) {
        this.y.unregisterDependency(this);
        this.y = y;
        this.y.registerDependency(this);
        this.y.valueChanged();
    }

    public void setWidth(Dimension width) {
        this.width.unregisterDependency(this);
        this.width = width;
        this.width.registerDependency(this);
        this.width.valueChanged();
    }

    public void setHeight(Dimension height) {
        this.height.unregisterDependency(this);
        this.height = height;
        this.height.registerDependency(this);
        this.height.valueChanged();
    }

    public void setBorderRadiusX(Dimension borderRadiusX) {
        this.borderRadiusX.unregisterDependency(this);
        this.borderRadiusX = borderRadiusX;
        this.borderRadiusX.registerDependency(this);
        this.borderRadiusX.valueChanged();
    }

    public void setBorderRadiusY(Dimension borderRadiusY) {
        this.borderRadiusY.unregisterDependency(this);
        this.borderRadiusY = borderRadiusY;
        this.borderRadiusY.registerDependency(this);
        this.borderRadiusY.valueChanged();
    }

    public void setMarginX(Dimension marginX) {
        this.marginX.unregisterDependency(this);
        this.marginX = marginX;
        this.marginX.registerDependency(this);
        this.marginX.valueChanged();
    }

    public void setMarginY(Dimension marginY) {
        this.marginY.unregisterDependency(this);
        this.marginY = marginY;
        this.marginY.registerDependency(this);
        this.marginY.valueChanged();
    }

    public void setParentView(View parentView) { //TODO update references
        this.parentView.getSubviews().remove(this);
        this.parentView = parentView;
        this.parentView.getSubviews().add(this);
        windowView.requireRenderUpdate();
    }

    public void setHidden() {
        this.visible = false;
        windowView.requireRenderUpdate();
    }

    public void setShown() {
        this.visible = true;
        windowView.requireRenderUpdate();
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        windowView.requireZIndexUpdate();
    }

    public void setDynamicZIndexUpdate(boolean dynamicZIndexUpdate) {
        this.dynamicZIndexUpdate = dynamicZIndexUpdate;
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