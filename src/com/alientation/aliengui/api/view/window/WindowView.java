package com.alientation.aliengui.api.view.window;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.dimension.WindowRelativeDimensionComponent;
import com.alientation.aliengui.event.key.KeyEvent;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.mouse.MouseScrollEvent;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.ViewHierarchyEvent;
import com.alientation.aliengui.event.view.ViewListener;

import java.awt.*;

/**
 * Wrapper view for a Window
 * <p>
 * TODO determine whether to combine the Window implementation to the WindowView wrapper as it is creating a lot of unnecessary code duplication
 */
@SuppressWarnings("unused")
public class WindowView extends View {
    //Base properties
    public static final int INIT_WIDTH = 800, INIT_HEIGHT = 1000, INIT_TPS = 120, INIT_FPS = 60;
    public static final String INIT_TITLE = "Untitled";

    //Use Resource Manager or something like that
    public static final String RESOURCE = "res\\";
    public static final String IMAGES = RESOURCE + "images\\";
    public static final String FONTS = RESOURCE + "fonts\\";


    //The Window Renderer that manages this specific WindowView
    protected WindowRenderer windowRenderer;

    //Whether this Window needs a render update, of which, the window renderer will check
    protected boolean requireRenderUpdate = true;

    //Whether this Window needs a z index update (ordering of views), of which, the window renderer will check
    protected boolean requireZIndexUpdate = true;

    //The Window this window view wraps
    protected Window window;

    //most recent view the mouse was over
    protected View recentlyEnteredView;

    /**
     * Builds a Window View
     *
     * @param builder   Builder
     */
    protected WindowView(Builder<?> builder) {
        super(builder);

        window = builder.window; //used if the user creates a Window object (the Window object instantiation will create a WindowView)
        if (window == null) //used if the user creates a WindowView object (the Window object will be created automatically)
            window = new Window.Builder<>().windowView(this).build();

        System.out.println("windowView width and height + " + width() + ", " + height());

        setX(new WindowRelativeDimensionComponent.Builder<>()
                .relTo(window)
                .windowDimensionRelation(WindowRelativeDimensionComponent.WindowDimensionRelation.LEFT_X)
                .build());
        setY(new WindowRelativeDimensionComponent.Builder<>()
                .relTo(window)
                .windowDimensionRelation(WindowRelativeDimensionComponent.WindowDimensionRelation.TOP_Y)
                .build());
        setWidth(new WindowRelativeDimensionComponent.Builder<>()
                .relTo(window)
                .windowDimensionRelation(WindowRelativeDimensionComponent.WindowDimensionRelation.WIDTH)
                .build());
        setHeight(new WindowRelativeDimensionComponent.Builder<>()
                .relTo(window)
                .windowDimensionRelation(WindowRelativeDimensionComponent.WindowDimensionRelation.HEIGHT)
                .build());

        System.out.println("windowView width and height + " + width() + ", " + height());

        windowRenderer = new WindowRenderer(this);
        mouseListeners.addListenerAtBeginning(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                WindowView.this.mouseClicked(event);
            }
            @Override
            public void mouseEntered(MouseEvent event) {
                super.mouseEntered(event);
                WindowView.this.mouseEntered(event);
            }
            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
                WindowView.this.mouseExited(event);
            }
            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);
                WindowView.this.mousePressed(event);
            }
            @Override
            public void mouseReleased(MouseEvent event) {
                super.mouseReleased(event);
                WindowView.this.mouseReleased(event);
            }
            @Override
            public void mouseDragged(MouseEvent event) {
                super.mouseDragged(event);
                WindowView.this.mouseDragged(event);
            }
            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
                WindowView.this.mouseMoved(event);
            }
            @Override
            public void mouseWheelMoved(MouseScrollEvent event) {
                super.mouseWheelMoved(event);
                WindowView.this.mouseWheelMoved(event);
            }
            @Override
            public void mouseAction(MouseEvent event) {
                super.mouseAction(event);
                WindowView.this.mouseAction(event);
            }
        });
        keyListeners.addListenerAtBeginning(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                super.keyPressed(event);
                WindowView.this.keyPressed(event);
            }

            @Override
            public void keyTyped(KeyEvent event) {
                super.keyTyped(event);
                WindowView.this.keyTyped(event);
            }

            @Override
            public void keyReleased(KeyEvent event) {
                super.keyReleased(event);
                WindowView.this.keyReleased(event);
            }
        });
        viewListeners.addListenerAtBeginning(new ViewListener() {
            @Override
            public void childViewAdded(ViewHierarchyEvent event) {
                super.childViewAdded(event);
                WindowView.this.childViewAdded(event);
            }

            @Override
            public void childViewRemoved(ViewHierarchyEvent event) {
                super.childViewRemoved(event);
                WindowView.this.childViewRemoved(event);
            }

            @Override
            public void parentViewChanged(ViewHierarchyEvent event) {
                super.parentViewChanged(event);
                WindowView.this.parentViewChanged(event);
            }

            @Override
            public void viewFocused(ViewEvent event) {
                super.viewFocused(event);
                WindowView.this.viewFocused(event);
            }

            @Override
            public void viewUnfocused(ViewEvent event) {
                super.viewUnfocused(event);
                WindowView.this.viewUnfocused(event);
            }

            @Override
            public void viewMoved(ViewEvent event) {
                super.viewMoved(event);
                WindowView.this.viewMoved(event);
            }

            @Override
            public void viewHidden(ViewEvent event) {
                super.viewHidden(event);
                WindowView.this.viewHidden(event);
            }

            @Override
            public void viewShown(ViewEvent event) {
                super.viewShown(event);
                WindowView.this.viewShown(event);
            }

            @Override
            public void viewDimensionChanged(ViewDimensionEvent event) {
                super.viewDimensionChanged(event);
                WindowView.this.viewDimensionChanged(event);
            }

            @Override
            public void viewActivated(ViewEvent event) {
                super.viewActivated(event);
                WindowView.this.viewActivated(event);
            }

            @Override
            public void viewDeactivated(ViewEvent event) {
                super.viewDeactivated(event);
                WindowView.this.viewDeactivated(event);
            }

            @Override
            public void viewStateChanged(ViewEvent event) {
                super.viewStateChanged(event);
                WindowView.this.viewStateChanged(event);
            }
        });



        initialized = true;
    }

    @Override
    public void init(View parentView) {
        throw new IllegalCallerException("WindowView is by default initialized on creation");
    }

    public void mouseClicked(MouseEvent event) {
        View viewClicked = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewClicked != null) viewClicked.getMouseListeners().dispatch(listener -> listener.mouseClicked(event));
    }
    public void mouseEntered(MouseEvent event) { //only fired when the mouse enters this Window

    }

    public void mouseExited(MouseEvent event) { //only fired when mouse leaves this window

    }

    public void mousePressed(MouseEvent event) {
        View viewPressed = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewPressed != null) viewPressed.getMouseListeners().dispatch(listener -> listener.mousePressed(event));
    }

    public void mouseReleased(MouseEvent event) {
        View viewReleased = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewReleased != null) viewReleased.getMouseListeners().dispatch(listener -> listener.mouseReleased(event));
    }

    public void mouseDragged(MouseEvent event) {
        View viewDragged = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewDragged != null) viewDragged.getMouseListeners().dispatch(listener -> listener.mouseDragged(event));
    }
    public void mouseMoved(MouseEvent event) {
        View viewMoved = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewMoved != null) viewMoved.getMouseListeners().dispatch(listener -> listener.mouseMoved(event));
    }

    public void mouseWheelMoved(MouseScrollEvent event) {
        View viewWheelMoved = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (viewWheelMoved != null) viewWheelMoved.getMouseListeners().dispatch(listener -> listener.mouseWheelMoved(event));
    }

    public void mouseAction(MouseEvent event) {
        View viewOver = windowRenderer.getViewAtPoint(event.getX(),event.getY());
        if (recentlyEnteredView != viewOver) { //mouse has exited a component and hovered over another component
            if (recentlyEnteredView != null) recentlyEnteredView.getMouseListeners().dispatch(listener -> listener.mouseExited(event));
            if (viewOver != null) viewOver.getMouseListeners().dispatch(listener -> listener.mouseEntered(event));
            recentlyEnteredView = viewOver;
        }
    }

    public void keyPressed(KeyEvent event) {

    }

    public void keyReleased(KeyEvent event) {

    }

    public void keyTyped(KeyEvent event) {

    }

    public void childViewAdded(ViewHierarchyEvent event) {

    }
    public void childViewRemoved(ViewHierarchyEvent event) {

    }
    public void parentViewChanged(ViewHierarchyEvent event) {

    }
    public void viewFocused(ViewEvent event) {

    }
    public void viewUnfocused(ViewEvent event) {

    }
    public void viewMoved(ViewEvent event) {

    }
    public void viewHidden(ViewEvent event) {

    }
    public void viewShown(ViewEvent event) {

    }
    public void viewDimensionChanged(ViewDimensionEvent event) {

    }
    public void viewActivated(ViewEvent event) {

    }
    public void viewDeactivated(ViewEvent event) {
        viewStateChanged(event);
    }


    public void viewStateChanged(ViewEvent event) {

    }

    /**
     *
     *
     * @param graphics Graphics object to be drawn on
     */
    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }

    @Override
    public void tick() {
        super.tick();
    }

    //SETTERS
    public void requestRenderUpdate() { requireRenderUpdate = true; } //maybe have events for these
    public void requestZIndexUpdate() { requireZIndexUpdate = true; } //maybe have events for these





    //GETTERS

    public WindowRenderer getWindowRenderer() { return windowRenderer; }
    public boolean doesRequireRenderUpdate() { return requireRenderUpdate; }
    public boolean doesRequireZIndexUpdate() { return requireZIndexUpdate; }
    public Window getWindow() { return window; }

    @Override
    public int absX() { return x.val(); }
    @Override
    public int absY() { return y.val(); }
    @Override
    public WindowView getWindowView() { return this; }


    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected Window window;
        public Builder() {
        }

        public T window(Window window) {
            this.window = window;
            return (T) this;
        }

        public void validate() {
            super.validate();
        }

        public WindowView build() {
            validate();
            return new WindowView(this);
        }
    }
}
