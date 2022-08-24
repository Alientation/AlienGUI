package com.alientation.aliengui.api.view;


import com.alientation.aliengui.api.controller.ViewController;
import com.alientation.aliengui.api.view.window.WindowView;
import com.alientation.aliengui.component.color.ColorComponent;
import com.alientation.aliengui.component.dimension.StaticDimensionComponent;
import com.alientation.aliengui.component.image.ImageComponent;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewHierarchyEvent;
import com.alientation.aliengui.component.dimension.DimensionComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.model.ModelListener;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.view.ViewListener;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Base View that renders, handles events, and communicates with the controller.
 */
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
    protected DimensionComponent x, y, width, height, borderRadiusX, borderRadiusY, borderThickness, marginX, marginY;

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
    protected boolean visibilityAppliesToChildren;

    /**
     * The Z Index of this view, used to order views to be rendered
     */
    protected int zIndex;

    /**
     * Whether this view can dynamically update the z index based on parent view's z index
     */
    protected boolean dynamicZIndexUpdate;

    /**
     * Background color of this view (initial layer) TODO use ColorComponent instead
     */

    protected ColorComponent backgroundColor;
    protected ImageComponent backgroundImage;
    protected ColorComponent frameColor;



    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder   Builder for this view
     */
    public View(Builder<?> builder) {
        x = builder.x;
        y = builder.y;
        width = builder.width;
        height = builder.height;
        marginX = builder.marginX;
        marginY = builder.marginY;
        borderRadiusX = builder.borderRadiusX;
        borderRadiusY = builder.borderRadiusY;
        borderThickness = builder.borderThickness;

        visible = builder.visible;
        visibilityAppliesToChildren = builder.visibilityAppliesToChildren;

        zIndex = builder.zIndex;
        dynamicZIndexUpdate = builder.dynamicZIndexUpdate;

        backgroundColor = builder.backgroundColor;
        backgroundImage = builder.backgroundImage;
        frameColor = builder.frameColor;

        getViewListeners().addListenerAtBeginning(new ViewListener() {
            @Override
            public void childViewAdded(ViewHierarchyEvent event) {
                getWindowView().requestZIndexUpdate();
            }

            @Override
            public void childViewRemoved(ViewHierarchyEvent event) {
                getWindowView().requestZIndexUpdate();
            }

            @Override
            public void parentViewChanged(ViewHierarchyEvent event) {
                getWindowView().requestRenderUpdate();
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
                getWindowView().requestRenderUpdate();
            }

            @Override
            public void viewShown(ViewEvent event) {
                getWindowView().requestRenderUpdate();
            }

            @Override
            public void viewDimensionChanged(ViewDimensionEvent event) {
                getWindowView().requestRenderUpdate();
            }

            @Override
            public void viewStateChanged(ViewEvent event) {
                getWindowView().requestZIndexUpdate();
            }
        });

        //registering dependencies
        x.registerSubscriber(this);
        y.registerSubscriber(this);
        width.registerSubscriber(this);
        height.registerSubscriber(this);
        borderRadiusX.registerSubscriber(this);
        borderRadiusY.registerSubscriber(this);
        borderThickness.registerSubscriber(this);
        marginX.registerSubscriber(this);
        marginY.registerSubscriber(this);

        frameColor.registerSubscriber(this);
        backgroundColor.registerSubscriber(this);
        if (backgroundImage != null) backgroundImage.registerSubscriber(this);
    }

    /**
     * Initializes this view
     */
    public void init(View parentView) {
        if (initialized) return; //can't initialize twice!

        this.parentView = parentView;
        parentView.childViews.add(this);
        windowView = parentView.getWindowView();

        if (controller == null) controller = new ViewController();

        initialized = true;
    }

    /**
     * Render update
     *
     * @param g Graphics object to be drawn on
     */
    public void render(Graphics g) {
        if (!initialized) return;

        //frame outline
        BufferedImage frameColorImage = frameColor.draw(this,new RoundRectangle2D.Float(x() - borderThickness(),y() - borderThickness(),width() + borderThickness()<<1,height() + borderThickness()<<1,
                borderRadiusX() + borderThickness(), borderRadiusY() + borderThickness()));
        g.drawImage(frameColorImage,x() - borderThickness(), y() - borderThickness(),null);

        //background
        BufferedImage backgroundColorImage = backgroundColor.draw(this,new RoundRectangle2D.Float(x(),y(),width(),height(),borderRadiusX(),borderRadiusY()));
        g.drawImage(backgroundColorImage,x(),y(),null);

        //background image
        if (backgroundImage != null) {
            BufferedImage backgroundImage = this.backgroundImage.draw(this);
            g.drawImage(backgroundImage, x(), y(), width(), height(), null);
        }
    }

    /**
     * Tick update
     */
    public void tick() {
        if (!initialized) return;
        for (View childView : childViews) childView.tick();
    }


    //SETTERS

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

    /**
     * Sets dimensions and updates dependencies
     *
     * @param x The new X dimension
     */
    public void setX(DimensionComponent x) {
        if (this.x == x) return;
        this.x.unregisterSubscriber(this);
        this.x = x;
        this.x.registerSubscriber(this);
        this.x.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param y The new y dimension
     */
    public void setY(DimensionComponent y) {
        if (this.y == y) return;
        this.y.unregisterSubscriber(this);
        this.y = y;
        this.y.registerSubscriber(this);
        this.y.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param width The new Width dimension
     */
    public void setWidth(DimensionComponent width) {
        if (this.width == width) return;
        this.width.unregisterSubscriber(this);
        this.width = width;
        this.width.registerSubscriber(this);
        this.width.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param height The new Height dimension
     */
    public void setHeight(DimensionComponent height) {
        if (this.height == height) return;
        this.height.unregisterSubscriber(this);
        this.height = height;
        this.height.registerSubscriber(this);
        this.height.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusX The new Border Radius X dimension (curved borders)
     */
    public void setBorderRadiusX(DimensionComponent borderRadiusX) {
        if (this.borderRadiusX == borderRadiusX) return;
        this.borderRadiusX.unregisterSubscriber(this);
        this.borderRadiusX = borderRadiusX;
        this.borderRadiusX.registerSubscriber(this);
        this.borderRadiusX.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusY The new Border Radius Y dimension (curved borders)
     */
    public void setBorderRadiusY(DimensionComponent borderRadiusY) {
        if (this.borderRadiusY == borderRadiusY) return;
        this.borderRadiusY.unregisterSubscriber(this);
        this.borderRadiusY = borderRadiusY;
        this.borderRadiusY.registerSubscriber(this);
        this.borderRadiusY.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderThickness The new Border Thickness dimension
     */
    public void setBorderThickness(DimensionComponent borderThickness) {
        if (this.borderThickness == borderThickness) return;
        this.borderThickness.unregisterSubscriber(this);
        this.borderThickness = borderThickness;
        this.borderThickness.registerSubscriber(this);
        this.borderThickness.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginX The new Margin X dimension
     */
    public void setMarginX(DimensionComponent marginX) {
        if (this.marginX == marginX) return;
        this.marginX.unregisterSubscriber(this);
        this.marginX = marginX;
        this.marginX.registerSubscriber(this);
        this.marginX.notifySubscribers();
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginY The new Margin Y dimension
     */
    public void setMarginY(DimensionComponent marginY) {
        if (this.marginY == marginY) return;
        this.marginY.unregisterSubscriber(this);
        this.marginY = marginY;
        this.marginY.registerSubscriber(this);
        this.marginY.notifySubscribers();
    }

    /**
     * Sets parent view of this view and properly updates connections.
     * Does so without depending on other public hierarchical update methods
     *
     * @param parentView    The new parent view of this view
     */
    public void setParentView(View parentView) { //TODO update references
        if (this.parentView == parentView) return;
        ViewHierarchyEvent event = new ViewHierarchyEvent(this, this.parentView, parentView);
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
            this.getViewListeners().dispatch(listener -> listener.childViewAdded(new ViewHierarchyEvent(this,view)));
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
            this.getViewListeners().dispatch(listener -> listener.childViewRemoved(new ViewHierarchyEvent(this,view)));
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

    public void setBackgroundColor(ColorComponent backgroundColor) {
        this.backgroundColor.unregisterSubscriber(this);
        this.backgroundColor = backgroundColor;
        this.backgroundColor.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setBackgroundImage(ImageComponent backgroundImage) {
        this.backgroundImage.unregisterSubscriber(this);
        this.backgroundImage = backgroundImage;
        this.backgroundImage.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setFrameColor(ColorComponent frameColor) {
        this.frameColor.unregisterSubscriber(this);
        this.frameColor = frameColor;
        this.frameColor.registerSubscriber(this);
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
    public int x() { return x.val(); }
    public int absX() { return x() + parentView.absX(); } //potentially compose a new dimension component - perhaps a new type of component that is essentially a combination of 2 dimensions
    public int safeX() { return x() + marginX(); }
    public int absSafeX() { return marginX() + absX(); }
    public DimensionComponent getY() { return y; }
    public int y() { return y.val(); }
    public int absY() { return y() + parentView.absY(); }
    public int safeY() { return y() + marginY(); }
    public int absSafeY() { return marginY() + absY(); }
    public DimensionComponent getWidth() { return width; }
    public int width() { return width.val(); }
    public int safeWidth() { return width() - marginX() << 1; }
    public DimensionComponent getHeight() { return height; }
    public int height() { return height.val(); }
    public int safeHeight() { return height() - marginY() << 1; }
    public DimensionComponent getBorderRadiusX() { return borderRadiusX; }
    public int borderRadiusX() { return borderRadiusX.val(); }
    public DimensionComponent getBorderRadiusY() { return borderRadiusY; }
    public int borderRadiusY() { return borderRadiusY.val(); }
    public DimensionComponent getBorderThickness() { return borderThickness; }
    public int borderThickness() { return borderThickness.val(); }
    public DimensionComponent getMarginX() { return marginX; }
    public int marginX() { return marginX.val(); }
    public DimensionComponent getMarginY() { return marginY; }
    public int marginY() { return marginY.val(); }

    //PROPERTIES
    public RoundRectangle2D getArea() { return new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY()); }
    public RoundRectangle2D getSafeArea() { return new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY()); }
    public RoundRectangle2D getAbsoluteArea() { return new RoundRectangle2D.Float(absX(), absY(), width(), height(), borderRadiusX(), borderRadiusY()); } //TODO use the Area class to perform accurate collision box detectors (for clicks)
    public RoundRectangle2D getAbsoluteSafeArea() { return new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY()); }

    //VIEW HIERARCHY
    public View getParentView() { return parentView; }
    public WindowView getWindowView() { return windowView == null && parentView != null ? parentView.getWindowView() : windowView; } //attempts to return a valid window view
    public Set<View> getChildViews() { return childViews; }

    //RENDER PROPERTIES
    public boolean isInitialized() { return initialized; }
    public boolean isVisible() { return visible; }
    public int getZIndex() { return zIndex; }
    public boolean doDynamicZIndexUpdate() { return dynamicZIndexUpdate; }
    public boolean doesVisibilityAppliesToChildren() { return visibilityAppliesToChildren; }
    public ColorComponent getBackgroundColor() { return backgroundColor; }
    public ImageComponent getBackgroundImage() { return backgroundImage; }
    public ColorComponent getFrameColor() { return frameColor; }

    //BUILDER
    @SuppressWarnings({"unused", "unchecked"})
    public static class Builder<T extends Builder<T>> {
        protected DimensionComponent x = StaticDimensionComponent.MIN;
        protected DimensionComponent y = StaticDimensionComponent.MIN;
        protected DimensionComponent width = StaticDimensionComponent.BASE;
        protected DimensionComponent height = StaticDimensionComponent.BASE;
        protected DimensionComponent borderRadiusX = StaticDimensionComponent.MIN;
        protected DimensionComponent borderRadiusY = StaticDimensionComponent.MIN;
        protected DimensionComponent borderThickness = StaticDimensionComponent.MIN;
        protected DimensionComponent marginX = StaticDimensionComponent.MIN;
        protected DimensionComponent marginY = StaticDimensionComponent.MIN;
        protected boolean visible = true;
        protected boolean visibilityAppliesToChildren= false;
        protected int zIndex = 0;
        protected boolean dynamicZIndexUpdate = true;

        protected ColorComponent backgroundColor = new ColorComponent(Color.WHITE);
        protected ImageComponent backgroundImage;
        protected ColorComponent frameColor = new ColorComponent(Color.WHITE);
        public Builder() {

        }

        public T x(DimensionComponent x) {
            this.x = x;
            return (T) this;
        }
        public T y(DimensionComponent y) {
            this.y = y;
            return (T) this;
        }
        public T width(DimensionComponent width) {
            this.width = width;
            return (T) this;
        }
        public T height(DimensionComponent height) {
            this.height = height;
            return (T) this;
        }
        public T borderRadiusX(DimensionComponent borderRadiusX) {
            this.borderRadiusX = borderRadiusX;
            return (T) this;
        }
        public T borderRadiusY(DimensionComponent borderRadiusY) {
            this.borderRadiusY = borderRadiusY;
            return (T) this;
        }
        public T borderThickness(DimensionComponent borderThickness) {
            this.borderThickness = borderThickness;
            return (T) this;
        }
        public T marginX(DimensionComponent marginX) {
            this.marginX = marginX;
            return (T) this;
        }
        public T marginY(DimensionComponent marginY) {
            this.marginY = marginY;
            return (T) this;
        }
        public T visible(boolean visible) {
            this.visible = visible;
            return (T) this;
        }
        public T visibilityAppliesToChildren(boolean visibilityAppliesToChildren) {
            this.visibilityAppliesToChildren = visibilityAppliesToChildren;
            return (T) this;
        }
        public T zIndex(int zIndex) {
            this.zIndex = zIndex;
            return (T) this;
        }
        public T dynamicZIndexUpdate(boolean dynamicZIndexUpdate) {
            this.dynamicZIndexUpdate = dynamicZIndexUpdate;
            return (T) this;
        }
        public T backgroundColor(ColorComponent backgroundColor) {
            this.backgroundColor = backgroundColor;
            return (T) this;
        }
        public T backgroundImage(ImageComponent backgroundImage) {
            this.backgroundImage = backgroundImage;
            return (T) this;
        }
        public T frameColor(ColorComponent frameColor) {
            this.frameColor = frameColor;
            return (T) this;
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
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {

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