package com.alientation.aliengui.api.view;


import com.alientation.aliengui.api.controller.ViewController;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewHierarchyChanged;
import com.alientation.aliengui.component.dimension.DimensionComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.model.ModelListener;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewListener;

import java.awt.*;
import java.util.Set;

@SuppressWarnings("unused")
public class View {
    /**
     * Listeners for internal processes and applications to hook into
     */
    protected EventListenerContainer<KeyListener> keyListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ModelListener> modelListeners = new EventListenerContainer<>();
    protected EventListenerContainer<MouseListener> mouseListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ViewListener> viewListeners = new EventListenerContainer<>();

    /**
     * ViewController that handles this view
     */
    protected ViewController controller;

    /**
     * Dimensions of this view
     */
    protected DimensionComponent x, y, width, height, borderRadiusX, borderRadiusY, marginX, marginY;

    /**
     * The view this view is enclosed in
     */
    protected View parentView;

    /**
     * Window this view is a part of
     */
    protected WindowView windowView;

    /**
     * Views that are enclosed in this view
     */
    protected Set<View> childViews;

    /**
     * Whether this view is ready to be used after all properties are initialized
     */
    protected boolean initialized;

    /**
     * Visibility of this view, whether this view will be rendered or not
     */
    protected boolean visible;

    /**
     * Whether the visibility of this view will affect the child views
     */
    protected boolean visibilityAppliesToChildren= false;

    /**
     * The Z Index of this view, used to order views to be rendered
     */
    protected int zIndex;

    /**
     * Whether this view can dynamically update the z index based on parent view's z index
     */
    protected boolean dynamicZIndexUpdate;

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder   Builder for this view
     */
    public View(Builder<?> builder) {
        getViewListeners().addListenerAtBeginning(new ViewListener() {
            @Override
            public void childViewAdded(ViewHierarchyChanged event) {
                windowView.requestZIndexUpdate();
            }

            @Override
            public void childViewRemoved(ViewHierarchyChanged event) {
                windowView.requestZIndexUpdate();
            }

            @Override
            public void parentViewChanged(ViewHierarchyChanged event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void viewFocused(ViewEvent event) {
                super.viewFocused(event);
            }

            @Override
            public void viewUnfocused(ViewEvent event) {
                super.viewUnfocused(event);
            }

            @Override
            public void viewMoved(ViewEvent event) {
                super.viewMoved(event);
            }

            @Override
            public void viewHidden(ViewEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void viewShown(ViewEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void viewDimensionChanged(ViewDimensionEvent event) {
                windowView.requestRenderUpdate();
            }

            @Override
            public void viewStateChanged(ViewEvent event) {
                windowView.requestZIndexUpdate();
            }
        });
    }

    /**
     * One of this view's dimensions has changed
     *
     * @param dimensionComponent Dimension of this view that was changed
     */
    public void dimensionChanged(DimensionComponent dimensionComponent) {
        getViewListeners().dispatch(listener -> listener.viewDimensionChanged(new ViewDimensionEvent(this, dimensionComponent)));
    }

    /**
     * Initializes this view
     */
    public void init() {
        if (initialized) return; //can't initialize twice!

        initialized = true;
    }

    /**
     * Render update
     *
     * @param g Graphics object to be drawn on
     */
    public void render(Graphics g) {
        if (!initialized) return;


    }

    /**
     * Tick update
     */
    public void tick() {
        if (!initialized) return;
    }


    /**
     * Sets dimensions and updates dependencies
     *
     * @param x The new X dimension
     */
    public void setX(DimensionComponent x) {
        if (this.x == x) return;
        this.x.unregisterDependency(this);
        this.x = x;
        this.x.registerDependency(this);
        this.x.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param y The new y dimension
     */
    public void setY(DimensionComponent y) {
        if (this.y == y) return;
        this.y.unregisterDependency(this);
        this.y = y;
        this.y.registerDependency(this);
        this.y.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param width The new Width dimension
     */
    public void setWidth(DimensionComponent width) {
        if (this.width == width) return;
        this.width.unregisterDependency(this);
        this.width = width;
        this.width.registerDependency(this);
        this.width.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param height The new Height dimension
     */
    public void setHeight(DimensionComponent height) {
        if (this.height == height) return;
        this.height.unregisterDependency(this);
        this.height = height;
        this.height.registerDependency(this);
        this.height.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusX The new Border Radius X dimension (curved borders)
     */
    public void setBorderRadiusX(DimensionComponent borderRadiusX) {
        if (this.borderRadiusX == borderRadiusX) return;
        this.borderRadiusX.unregisterDependency(this);
        this.borderRadiusX = borderRadiusX;
        this.borderRadiusX.registerDependency(this);
        this.borderRadiusX.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusY The new Border Radius Y dimension (curved borders)
     */
    public void setBorderRadiusY(DimensionComponent borderRadiusY) {
        if (this.borderRadiusY == borderRadiusY) return;
        this.borderRadiusY.unregisterDependency(this);
        this.borderRadiusY = borderRadiusY;
        this.borderRadiusY.registerDependency(this);
        this.borderRadiusY.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginX The new Margin X dimension
     */
    public void setMarginX(DimensionComponent marginX) {
        if (this.marginX == marginX) return;
        this.marginX.unregisterDependency(this);
        this.marginX = marginX;
        this.marginX.registerDependency(this);
        this.marginX.valueChanged();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginY The new Margin Y dimension
     */
    public void setMarginY(DimensionComponent marginY) {
        if (this.marginY == marginY) return;
        this.marginY.unregisterDependency(this);
        this.marginY = marginY;
        this.marginY.registerDependency(this);
        this.marginY.valueChanged();
    }

    /**
     * Sets parent view of this view and properly updates connections.
     * Does so without depending on other public hierarchical update methods
     *
     * @param parentView    The new parent view of this view
     */
    public void setParentView(View parentView) { //TODO update references
        if (this.parentView == parentView) return;
        ViewHierarchyChanged event = new ViewHierarchyChanged(this, this.parentView, parentView);
        this.parentView.getChildViews().remove(this);
        this.parentView = parentView;
        this.parentView.getChildViews().add(this);
        this.getViewListeners().dispatch(listener -> listener.parentViewChanged(event));
    }


    /**
     * Adds child views to this view and properly updates connections
     * Does so without depending on other public hierarchical update methods
     *
     * @param views child views to be added
     */
    public void addChildViews(View...views) {
        for (View view : views) {
            if (view.parentView != null) view.parentView.removeChildViews(view);
            view.parentView = this;
            childViews.add(view);
            this.getViewListeners().dispatch(listener -> listener.childViewAdded(new ViewHierarchyChanged(this,view)));
        }
    }

    /**
     * Removes child views to this view and properly updates connections
     * Does so without depending on other public hierarchical update methods
     *
     * @param views child views to be removed
     */
    public void removeChildViews(View... views) {
        for (View view : views) {
            if (view.parentView == this) view.parentView = null;
            childViews.remove(view);
            this.getViewListeners().dispatch(listener -> listener.childViewRemoved(new ViewHierarchyChanged(this,view)));
        }
    }

    /**
     * Hides this view and requests a render update
     */
    public void setHidden() {
        this.visible = false;
        this.getViewListeners().dispatch(listener -> listener.viewHidden(new ViewEvent(this)));
    }

    /**
     * Shows this view and requests a render update
     */
    public void setShown() {
        this.visible = true;
        this.getViewListeners().dispatch(listener -> listener.viewShown(new ViewEvent(this)));
    }

    /**
     * Sets whether children will adopt parent visibility
     *
     * @param visibilityAppliesToChildren   New visibility rule for children
     */
    public void setVisibilityAppliesToChildren(boolean visibilityAppliesToChildren) {
        this.visibilityAppliesToChildren = visibilityAppliesToChildren;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    /**
     * Updates the Z Index of this view and requests a Z Index update
     *
     * @param zIndex The new Z Index
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    /**
     * Sets whether this Z Index can be automatically changed during runtime
     *
     * @param dynamicZIndexUpdate   New Z Index update rule
     */
    public void setDynamicZIndexUpdate(boolean dynamicZIndexUpdate) {
        this.dynamicZIndexUpdate = dynamicZIndexUpdate;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    /**
     * Registers a ViewController to this view
     *
     * @param controller    The new ViewController
     */
    public void registerController(ViewController controller) {
        //update old controller -> remove reference to this view
        this.controller = controller;
        //update new controller -> add reference to this view
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    //GETTERS

    //LISTENERS
    public EventListenerContainer<KeyListener> getKeyListeners() { return keyListeners; }
    public EventListenerContainer<ModelListener> getModelListeners() { return modelListeners; }
    public EventListenerContainer<MouseListener> getMouseListeners() { return mouseListeners; }
    public EventListenerContainer<ViewListener> getViewListeners() { return viewListeners; }

    //VIEW CONTROLLER
    public ViewController getController() { return controller; }

    //DIMENSIONS
    public DimensionComponent getX() { return x; }
    public DimensionComponent getY() { return y; }
    public DimensionComponent getWidth() { return width; }
    public DimensionComponent getHeight() { return height; }
    public DimensionComponent getBorderRadiusX() { return borderRadiusX; }
    public DimensionComponent getBorderRadiusY() { return borderRadiusY; }
    public DimensionComponent getMarginX() { return marginX; }
    public DimensionComponent getMarginY() { return marginY; }

    //VIEW HIERARCHY
    public View getParentView() { return parentView; }
    public WindowView getWindowView() { return windowView; }
    public Set<View> getChildViews() { return childViews; }

    //RENDER PROPERTIES
    public boolean isInitialized() { return initialized; }
    public boolean isVisible() { return visible; }
    public int getZIndex() { return zIndex; }
    public boolean doDynamicZIndexUpdate() { return dynamicZIndexUpdate; }

    //BUILDER
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