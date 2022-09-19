package com.aliengui.api.view;


import com.aliengui.api.controller.ViewController;
import com.aliengui.api.view.window.WindowView;
import com.aliengui.component.color.ColorComponent;
import com.aliengui.component.dimension.StaticDimensionComponent;
import com.aliengui.component.image.ImageComponent;
import com.aliengui.event.view.ViewDimensionEvent;
import com.aliengui.event.view.ViewEvent;
import com.aliengui.event.view.ViewHierarchyEvent;
import com.aliengui.component.dimension.DimensionComponent;
import com.aliengui.event.EventListenerContainer;
import com.aliengui.event.key.KeyListener;
import com.aliengui.event.model.ModelListener;
import com.aliengui.event.mouse.MouseListener;
import com.aliengui.event.view.ViewListener;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Base View that renders, handles events, and communicates with the controller.
 */
@SuppressWarnings("unused")
public class View {
    //Listeners for internal processes and applications to hook into
    protected EventListenerContainer<KeyListener> keyListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ModelListener> modelListeners = new EventListenerContainer<>();
    //mouse listeners as it relates to this view
    protected EventListenerContainer<MouseListener> mouseListeners = new EventListenerContainer<>();
    protected EventListenerContainer<ViewListener> viewListeners = new EventListenerContainer<>();

    //ViewController that handles this view
    protected ViewController controller;

    //Dimensions of this view
    protected DimensionComponent x, y, width, height, borderRadiusWidth, borderRadiusHeight, borderThickness, marginX, marginY;

    //The view this view is enclosed in
    protected View parentView;

    //Window this view is a part of
    protected WindowView windowView;

    //Views that are enclosed in this view
    protected Set<View> childViews = new HashSet<>();

    //Whether this view is ready to be used after all properties are initialized
    protected boolean initialized;

    //Visibility of this view, whether this view will be rendered or not
    protected boolean isVisible;

    //Whether this view will be ticked
    protected boolean isActive;

    //Whether the visibility of this view will affect the child views
    protected boolean visibilityAppliesToChildren;

    //The Z Index of this view, used to order views to be rendered
    protected int zIndex;

    //Whether this view can dynamically update the z index based on parent view's z index
    protected boolean dynamicZIndexUpdate;

    //Background and Frame Components
    protected ColorComponent backgroundColor;
    protected ImageComponent backgroundImage;
    protected ColorComponent frameColor;

    //used for rendering
    protected Shape foregroundShape, middleGroundShape, backgroundShape, frameShape;

    //used for detecting clicks and stuff (bounding boxes)
    protected Shape area, safeArea, absoluteArea, absoluteSafeArea;


    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder   Builder for this view
     */
    protected View(Builder<?> builder) {
        x = builder.x;
        y = builder.y;
        width = builder.width;
        height = builder.height;
        marginX = builder.marginX;
        marginY = builder.marginY;
        borderRadiusWidth = builder.borderRadiusWidth;
        borderRadiusHeight = builder.borderRadiusHeight;
        borderThickness = builder.borderThickness;

        isVisible = builder.isVisible;
        isActive = builder.isActive;
        visibilityAppliesToChildren = builder.visibilityAppliesToChildren;

        zIndex = builder.zIndex;
        dynamicZIndexUpdate = builder.dynamicZIndexUpdate;

        backgroundColor = builder.backgroundColor;
        backgroundImage = builder.backgroundImage;
        frameColor = builder.frameColor;

        getViewListeners().addListenerAtBeginning(new ViewListener() {
            @Override public void childViewAdded(ViewHierarchyEvent event) {
                super.childViewAdded(event);
                requestZIndexUpdate();
            }
            @Override public void childViewRemoved(ViewHierarchyEvent event) {
                super.childViewRemoved(event);
                requestZIndexUpdate();
            }
            @Override public void parentViewChanged(ViewHierarchyEvent event) {
                super.parentViewChanged(event);
                requestRenderUpdate();
            }
            @Override public void viewFocused(ViewEvent event) {
                super.viewFocused(event);
            }
            @Override public void viewUnfocused(ViewEvent event) {
                super.viewUnfocused(event);
            }
            @Override public void viewMoved(ViewEvent event) {
                super.viewMoved(event);
            }
            @Override public void viewHidden(ViewEvent event) {
                super.viewHidden(event);
                requestRenderUpdate();
            }
            @Override public void viewShown(ViewEvent event) {
                super.viewShown(event);
                requestRenderUpdate();
            }
            @Override public void viewDimensionChanged(ViewDimensionEvent event) {
                super.viewDimensionChanged(event);

                foregroundShape = new RoundRectangle2D.Float(x(),y(),width(),height(),borderRadiusX(),borderRadiusY());
                middleGroundShape = new RoundRectangle2D.Float(x(),y(),width(),height(),borderRadiusX(),borderRadiusY());
                backgroundShape = new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY());
                frameShape = new RoundRectangle2D.Float(x() - borderThickness(),y() - borderThickness(),
                        width() + borderThickness()<<1,height() + borderThickness()<<1,
                        borderRadiusX() + borderThickness(), borderRadiusY() + borderThickness());

                area = new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY());
                safeArea = new RoundRectangle2D.Float(safeX(), safeY(), safeWidth(), safeHeight(), borderRadiusX(), borderRadiusY());
                absoluteArea = new RoundRectangle2D.Float(absX(), absY(), width(), height(), borderRadiusX(), borderRadiusY());
                absoluteSafeArea = new RoundRectangle2D.Float(absSafeX(), absSafeY(), safeWidth(), safeHeight(), borderRadiusX(), borderRadiusY());

                requestRenderUpdate();
            }
            @Override public void viewStateChanged(ViewEvent event) {
                super.viewStateChanged(event);
                requestZIndexUpdate();
            }
        });

        //registering dependencies
        x.registerSubscriber(this);
        y.registerSubscriber(this);
        width.registerSubscriber(this);
        height.registerSubscriber(this);
        borderRadiusWidth.registerSubscriber(this);
        borderRadiusHeight.registerSubscriber(this);
        borderThickness.registerSubscriber(this);
        marginX.registerSubscriber(this);
        marginY.registerSubscriber(this);

        frameColor.registerSubscriber(this);
        backgroundColor.registerSubscriber(this);
        if (backgroundImage != null) backgroundImage.registerSubscriber(this);

        foregroundShape = new RoundRectangle2D.Float(x(),y(),width(),height(),borderRadiusX(),borderRadiusY());
        middleGroundShape = new RoundRectangle2D.Float(x(),y(),width(),height(),borderRadiusX(),borderRadiusY());
        backgroundShape = new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY());
        frameShape = new RoundRectangle2D.Float(x() - borderThickness(),y() - borderThickness(),
                width() + borderThickness()<<1,height() + borderThickness()<<1,
                borderRadiusX() + borderThickness(), borderRadiusY() + borderThickness());

        area = new RoundRectangle2D.Float(x(), y(), width(), height(), borderRadiusX(), borderRadiusY());
        safeArea = new RoundRectangle2D.Float(safeX(), safeY(), safeWidth(), safeHeight(), borderRadiusX(), borderRadiusY());
        absoluteArea = new RoundRectangle2D.Float(absX(), absY(), width(), height(), borderRadiusX(), borderRadiusY());
        absoluteSafeArea = new RoundRectangle2D.Float(absSafeX(), absSafeY(), safeWidth(), safeHeight(), borderRadiusX(), borderRadiusY());
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

        windowView.requestZIndexUpdate();
    }

    /**
     * Render update
     *
     * @param g Graphics object to be drawn on
     */
    public void render(Graphics g) {
        if (!initialized || !isVisible) return;
        //frame outline
        frameColor.draw(this,g,getFrameShape());

        //background
        backgroundColor.draw(this,g,getBackgroundShape());

        //background image
        if (backgroundImage != null)
            backgroundImage.draw(this,g,getBackgroundShape());
    }

    /**
     * Tick update
     */
    public void tick() {
        if (!initialized || !isActive) return;
        for (View childView : childViews) childView.tick();
    }

    public void requestZIndexUpdate() {
        if (getWindowView() != null)
            getWindowView().requestZIndexUpdate();
    }

    public void requestRenderUpdate() {
        if (getWindowView() != null)
            getWindowView().requestRenderUpdate();
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
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.x,x);
        this.x.unregisterSubscriber(this);
        this.x = x;
        this.x.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    public void setAbsX(DimensionComponent absX) {

    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param y The new y dimension
     */
    public void setY(DimensionComponent y) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.y,y);
        this.y.unregisterSubscriber(this);
        this.y = y;
        this.y.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    public void setAbsY(DimensionComponent absY) {

    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param width The new Width dimension
     */
    public void setWidth(DimensionComponent width) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.width,width);
        this.width.unregisterSubscriber(this);
        this.width = width;
        this.width.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param height The new Height dimension
     */
    public void setHeight(DimensionComponent height) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.height,height);
        this.height.unregisterSubscriber(this);
        this.height = height;
        this.height.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusWidth The new Border Radius X dimension (curved borders)
     */
    public void setBorderRadiusWidth(DimensionComponent borderRadiusWidth) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.borderRadiusWidth,borderRadiusWidth);
        this.borderRadiusWidth.unregisterSubscriber(this);
        this.borderRadiusWidth = borderRadiusWidth;
        this.borderRadiusWidth.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderRadiusHeight The new Border Radius Y dimension (curved borders)
     */
    public void setBorderRadiusHeight(DimensionComponent borderRadiusHeight) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.borderRadiusHeight,borderRadiusHeight);
        this.borderRadiusHeight.unregisterSubscriber(this);
        this.borderRadiusHeight = borderRadiusHeight;
        this.borderRadiusHeight.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param borderThickness The new Border Thickness dimension
     */
    public void setBorderThickness(DimensionComponent borderThickness) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.borderThickness,borderThickness);
        this.borderThickness.unregisterSubscriber(this);
        this.borderThickness = borderThickness;
        this.borderThickness.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginX The new Margin X dimension
     */
    public void setMarginX(DimensionComponent marginX) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.marginX,marginX);
        this.marginX.unregisterSubscriber(this);
        this.marginX = marginX;
        this.marginX.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets dimensions and updates dependencies
     *
     * @param marginY The new Margin Y dimension
     */
    public void setMarginY(DimensionComponent marginY) {
        ViewDimensionEvent event = new ViewDimensionEvent(this,this.marginY,marginY);
        this.marginY.unregisterSubscriber(this);
        this.marginY = marginY;
        this.marginY.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(event));
    }

    /**
     * Sets parent view of this view and properly updates connections.
     * Does so without depending on other public hierarchical update methods
     *
     * @param parentView    The new parent view of this view
     */
    public void setParentView(View parentView) {
        if (this.parentView == parentView) return;
        ViewHierarchyEvent event = new ViewHierarchyEvent(this, this.parentView, parentView);
        if ( this.parentView != null) this.parentView.getChildViews().remove(this);
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
        this.isVisible = false;
        this.getViewListeners().dispatch(listener -> listener.viewHidden(new ViewEvent(this)));
    }

    /**
     * Shows this view and requests a render update
     */
    public void setShown() {
        this.isVisible = true;
        this.getViewListeners().dispatch(listener -> listener.viewShown(new ViewEvent(this)));
    }

    public void setActive() {
        this.isActive = true;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setInactive() {
        this.isActive = false;
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
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

    //SHAPES
    public Shape getForegroundShape() { return foregroundShape; }
    public Shape getMiddleGroundShape() { return middleGroundShape; }
    public Shape getBackgroundShape() { return backgroundShape; }
    public Shape getFrameShape() { return frameShape; }

    //PROPERTIES
    public Shape getArea() { return area; }
    public Shape getSafeArea() { return safeArea; }
    public Shape getAbsoluteArea() { return absoluteArea; }
    public Shape getAbsoluteSafeArea() { return absoluteSafeArea; }


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
    public int absX() { return x() + parentAbsX(); } //potentially compose a new dimension component - perhaps a new type of component that is essentially a combination of 2 dimensions
    public int safeX() { return x() + marginX(); }
    public int absSafeX() { return marginX() + absX(); }
    private int parentAbsX() { return parentView == null ? 0 : parentView.absX(); }
    public DimensionComponent getY() { return y; }
    public int y() { return y.val(); }
    public int absY() { return y() + parentAbsY(); }
    public int safeY() { return y() + marginY(); }
    public int absSafeY() { return marginY() + absY(); }
    private int parentAbsY() { return parentView == null ? 0 : parentView.absY(); }
    public DimensionComponent getWidth() { return width; }
    public int width() { return width.val(); }
    public int safeWidth() { return width() - marginX() << 1; }
    public DimensionComponent getHeight() { return height; }
    public int height() { return height.val(); }
    public int safeHeight() { return height() - marginY() << 1; }
    public DimensionComponent getBorderRadiusWidth() { return borderRadiusWidth; }
    public int borderRadiusX() { return borderRadiusWidth.val(); }
    public DimensionComponent getBorderRadiusHeight() { return borderRadiusHeight; }
    public int borderRadiusY() { return borderRadiusHeight.val(); }
    public DimensionComponent getBorderThickness() { return borderThickness; }
    public int borderThickness() { return borderThickness.val(); }
    public DimensionComponent getMarginX() { return marginX; }
    public int marginX() { return marginX.val(); }
    public DimensionComponent getMarginY() { return marginY; }
    public int marginY() { return marginY.val(); }

    //VIEW HIERARCHY
    public View getParentView() { return parentView; }
    public WindowView getWindowView() { return windowView == null && parentView != null ? parentView.getWindowView() : windowView; } //attempts to return a valid window view
    public Set<View> getChildViews() { return childViews; }

    //RENDER PROPERTIES
    public boolean isInitialized() { return initialized; }
    public boolean isVisible() { return isVisible; }
    public boolean isActive() { return isActive; }
    public int getZIndex() { return zIndex; }
    public boolean doDynamicZIndexUpdate() { return dynamicZIndexUpdate; }
    public boolean doesVisibilityAppliesToChildren() { return visibilityAppliesToChildren; }
    public ColorComponent getBackgroundColor() { return backgroundColor; }
    public ImageComponent getBackgroundImage() { return backgroundImage; }
    public ColorComponent getFrameColor() { return frameColor; }

    //BUILDER
    @SuppressWarnings({"unused", "unchecked"})
    public static class Builder<T extends Builder<T>> {
        protected DimensionComponent x = StaticDimensionComponent.ZERO();
        protected DimensionComponent y = StaticDimensionComponent.ZERO();
        protected DimensionComponent width = StaticDimensionComponent.BASE_WIDTH();
        protected DimensionComponent height = StaticDimensionComponent.BASE_HEIGHT();
        protected DimensionComponent borderRadiusWidth = StaticDimensionComponent.ZERO();
        protected DimensionComponent borderRadiusHeight = StaticDimensionComponent.ZERO();
        protected DimensionComponent borderThickness = StaticDimensionComponent.ZERO();
        protected DimensionComponent marginX = StaticDimensionComponent.ZERO();
        protected DimensionComponent marginY = StaticDimensionComponent.ZERO();
        protected boolean isVisible = true;
        protected boolean isActive = true;
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
        public T borderRadiusWidth(DimensionComponent borderRadiusWidth) {
            this.borderRadiusWidth = borderRadiusWidth;
            return (T) this;
        }
        public T borderRadiusHeight(DimensionComponent borderRadiusHeight) {
            this.borderRadiusHeight = borderRadiusHeight;
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
        public T isVisible(boolean isVisible) {
            this.isVisible = isVisible;
            return (T) this;
        }
        public T isActive(boolean isActive) {
            this.isActive = isActive;
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
    @SuppressWarnings("unchecked")
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