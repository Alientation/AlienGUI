package com.alientation.aliengui.api.view;

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
    public void requestRenderUpdate() {
        requireRenderUpdate = true;
    }

    /**
     * Updates requireZIndexUpdate
     */
    public void requestZIndexUpdate() {
        requireZIndexUpdate = true;
    }

    //GETTERS

    public WindowRenderer getWindowRenderer() { return windowRenderer; }
    public boolean doesRequireRenderUpdate() { return requireRenderUpdate; }
    public boolean doesRequireZIndexUpdate() { return requireZIndexUpdate; }
    public Window getWindow() { return window; }

    @Override
    public int absX() { return x.val(); }
    @Override
    public int absY() { return y.val(); }

    @SuppressWarnings("unchecked")
    static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected int x = -1, y = -1, width = 720, height = 576, minWidth = -1, minHeight = -1, maxWidth = Integer.MAX_VALUE, maxHeight = Integer.MAX_VALUE, preferredWidth, preferredHeight; //make all these dimensions, have this window subscribe to it
        protected int targetTPS = 60, targetFPS = 60;
        protected String title = "Untitled", iconImagePath;
        protected boolean resizable = true, visible = true;
        public Builder() {

        }

        public T x(int x) {
            this.x = x;
            return (T) this;
        }

        public T y(int y) {
            this.y = y;
            return (T) this;
        }

        public T width(int width) {
            this.width = width;
            return (T) this;
        }

        public T height(int height) {
            this.height = height;
            return (T) this;
        }

        public T minWidth(int minWidth) {
            this.minWidth = minWidth;
            return (T) this;
        }

        public T minHeight(int minHeight) {
            this.minHeight = minHeight;
            return (T) this;
        }

        public T maxWidth(int maxWidth) {
            this.maxWidth = maxWidth;
            return (T) this;
        }

        public T maxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return (T) this;
        }

        public T preferredWidth(int preferredWidth) {
            this.preferredWidth = preferredWidth;
            return (T) this;
        }

        public T preferredHeight(int preferredHeight) {
            this.preferredHeight = preferredHeight;
            return (T) this;
        }

        public T targetTPS(int targetTPS) {
            this.targetTPS = targetTPS;
            return (T) this;
        }

        public T targetFPS(int targetFPS) {
            this.targetFPS = targetFPS;
            return (T) this;
        }

        public T title(String title) {
            this.title = title;
            return (T) this;
        }

        public T iconImagePath(String iconImagePath) {
            this.iconImagePath = iconImagePath;
            return (T) this;
        }

        public T resizable(boolean resizable) {
            this.resizable = resizable;
            return (T) this;
        }

        public T visible(boolean visible) {
            this.visible = visible;
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
