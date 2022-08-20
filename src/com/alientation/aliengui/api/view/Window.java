package com.alientation.aliengui.api.view;

import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyEvent;
import com.alientation.aliengui.event.key.KeyListener;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static final int INIT_WIDTH = 800, INIT_HEIGHT = 1000, INIT_TPS = 120, INIT_FPS = 60;
    public static final String INIT_TITLE = "Untitled";

    //Use Resource Manager or something like that
    public static final String RESOURCE = "res\\";
    public static final String IMAGES = RESOURCE + "images\\";
    public static final String FONTS = RESOURCE + "fonts\\";
    protected View windowView;

    protected Graphics g;
    protected BufferStrategy bs;
    protected WindowRenderer windowRenderer;
    protected boolean requireUpdate;


    public Window(Builder<?> builder) {






        windowView = builder.windowView;
        windowView.keyListeners.addListener(new KeyListener() {
            public void keyPressed(KeyEvent event) {
                getKeysDown().add(event.getKeyCode());
            }

            public void keyReleased(KeyEvent event) {
                getKeysDown().remove(event.getKeyCode());
            }

        }, EventListenerContainer.PRIORITY_FIRST);





    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        render(g);
    }

    public void render(Graphics g) {

    }

    public void tick() {

    }

    @Override
    public void run() {

    }

    public synchronized void start() {

    }

    public synchronized void stop() {

    }

    @SuppressWarnings("unchecked")
    static class Builder<T extends Builder<T>> {
        View windowView;
        int x = -1, y = -1, width = 720, height = 576, minWidth = -1, minHeight = -1, maxWidth = Integer.MAX_VALUE, maxHeight = Integer.MAX_VALUE; //make all these dimensions, have this window subscribe to it
        int targetTPS = 60, targetFPS = 60;
        String title = "Untitled", iconImagePath;
        boolean resizable = true, visible = true;

        public Builder() {

        }

        public T windowView(View windowView) {
            this.windowView = windowView;
            return (T) this;
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


        public void validate() throws IllegalStateException {
            if (windowView == null) throw new IllegalStateException("windowView must be initialized.");
        }

        public Window build() {
            validate();
            return new Window(this);
        }
    }
}
