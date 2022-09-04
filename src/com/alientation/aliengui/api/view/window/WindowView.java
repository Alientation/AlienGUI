package com.alientation.aliengui.api.view.window;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.mouse.MouseEvent;
import com.alientation.aliengui.event.mouse.MouseListener;
import com.alientation.aliengui.event.mouse.MouseScrollEvent;

import java.awt.*;

/**
 * Wrapper view for a Window
 */
@SuppressWarnings("unused")
public class WindowView extends View {
    /**
     * Base properties
     */
    public static final int INIT_WIDTH = 800, INIT_HEIGHT = 1000, INIT_TPS = 120, INIT_FPS = 60;
    public static final String INIT_TITLE = "Untitled";

    //Use Resource Manager or something like that
    public static final String RESOURCE = "res\\";
    public static final String IMAGES = RESOURCE + "images\\";
    public static final String FONTS = RESOURCE + "fonts\\";

    /**
     * The Window Renderer that manages this specific WindowView
     */
    protected WindowRenderer windowRenderer;

    /**
     * Whether this Window needs a render update, of which, the window renderer will check
     */
    protected boolean requireRenderUpdate = true;

    /**
     * Whether this Window needs a z index update (ordering of views), of which, the window renderer will check
     */
    protected boolean requireZIndexUpdate = true;

    /**
     * The Window this window view wraps
     */
    protected Window window;

    /**
     * Builds a Window View
     *
     * @param builder   Builder
     */
    public WindowView(Builder<?> builder) {
        super(builder);
        windowRenderer = new WindowRenderer(this);
        window = builder.window;

        //TODO implement (probably in the WindowView and make it iterate and find the top most view for mouse)
        mouseListeners.addListenerAtBeginning(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
            }

            @Override
            public void mouseEntered(MouseEvent event) {
                super.mouseEntered(event);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                super.mouseExited(event);
            }

            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                super.mouseReleased(event);
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                super.mouseDragged(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                super.mouseMoved(event);
            }

            @Override
            public void mouseWheelMoved(MouseScrollEvent event) {
                super.mouseWheelMoved(event);
            }

            @Override
            public void mouseAction(MouseEvent event) {

            }
        });
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

    //SETTERS

    /**
     * Updates requireRenderUpdate
     */
    public void requestRenderUpdate() { requireRenderUpdate = true; } //maybe have events for these

    /**
     * Updates requireZIndexUpdate
     */
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


    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected Window window;
        public Builder(Window window) {
            this.window = window;
        }

        public void validate() {
            super.validate();
            if (window == null) throw new IllegalStateException("Window must be valid");
        }

        public View build() {
            validate();
            return new View(this);
        }
    }
}
