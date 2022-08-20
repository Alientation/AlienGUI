package com.alientation.aliengui.api.view;

import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.key.KeyListener;
import com.alientation.aliengui.event.view.ViewEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;

@SuppressWarnings("unused")
public class Window extends Canvas implements Runnable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected WindowView windowView;

    protected Graphics g;
    protected BufferStrategy bs;

    protected static final int NANOSECONDS = 1000000000;
    protected double nsPerTick, nsPerFrame;
    protected int targetTPS, tps, targetFPS, fps;
    protected Thread windowThread;
    protected boolean running;

    protected JFrame frame;

    public Window(Builder<?> builder) {
        frame = new JFrame(builder.title);

        frame.setPreferredSize(new Dimension(builder.preferredWidth, builder.preferredHeight));
        frame.setSize(new Dimension(builder.width,builder.height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(builder.resizable);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        frame.setIgnoreRepaint(true);

        targetTPS = builder.targetTPS;
        targetFPS = builder.targetFPS;
        updateTimeBetweenUpdates();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mousePressed(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseReleased(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseMoved(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseWheelMoved(new com.alientation.aliengui.event.mouse.MouseScrollEvent(e)));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseClicked(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseDragged(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseEntered(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                windowView.getMouseListeners().dispatch(listener -> listener.mouseExited(new com.alientation.aliengui.event.mouse.MouseEvent(e)));
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                windowView.getKeyListeners().dispatch(listener -> listener.keyTyped(new com.alientation.aliengui.event.key.KeyEvent(e)));
            }
            @Override
            public void keyPressed(KeyEvent e) {
                windowView.getKeyListeners().dispatch(listener -> listener.keyPressed(new com.alientation.aliengui.event.key.KeyEvent(e)));
            }
            @Override
            public void keyReleased(KeyEvent e) {
                windowView.getKeyListeners().dispatch(listener -> listener.keyReleased(new com.alientation.aliengui.event.key.KeyEvent(e)));
            }
        });

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewFocused(new ViewEvent()));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewUnfocused(new ViewEvent()));
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewResized(new ViewEvent()));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewMoved(new ViewEvent()));
            }

            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewShown(new ViewEvent()));
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                windowView.getViewListeners().dispatch(listener -> listener.viewHidden(new ViewEvent()));
            }
        });

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
        windowView.render(g);
    }

    public void render() {
        //prerender
        bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        //render
        g = bs.getDrawGraphics();
        if (!windowView.getWindowRenderer().render(g)) {
            g.dispose();
            return; //no need to show anything, nothing was updated
        }

        //post render
        g.dispose();
        bs.show();

    }

    public void tick() {

    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        long now;
        long timer = System.currentTimeMillis();
        double deltaTick = 0;
        double deltaFrame = 0;
        int numTicks = 0;
        int numFrames = 0;
        while(this.running) {
            now = System.nanoTime();
            deltaTick += (now - lastTime) / nsPerTick;
            deltaFrame += (now - lastTime) / nsPerFrame;
            lastTime = now;
            while (deltaTick >= 1) {
                tick();
                numTicks++;
                deltaTick--;
            }

            while (deltaFrame >= 1) {
                render();
                numFrames++;
                deltaFrame--;
            }

            //update tps (fps too since they are the same as of right now)
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.tps = numTicks;
                this.fps = numFrames;
                numTicks = 0;
                numFrames = 0;
            }

            //so that the cpu doesn't waste resources
            sync(Math.min(System.nanoTime() + (1f - deltaTick) * nsPerTick,System.nanoTime() + (1f - deltaFrame) * nsPerFrame));
        }
        stop();
    }

    public void sync(double endTime) {
        while (System.nanoTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void start() {
        this.createBufferStrategy(3);
        bs = this.getBufferStrategy();
        g = bs.getDrawGraphics();

        running = true;
        windowThread = new Thread(this);
        windowThread.start();
    }

    public synchronized void stop() {
        try {
            windowThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void updateTimeBetweenUpdates() {
        nsPerTick = ((float)NANOSECONDS) / targetTPS;
        nsPerFrame = ((float) NANOSECONDS) / targetFPS;
    }

    public void setTargetTPS(int targetTPS) {
        this.targetTPS = targetTPS;
        updateTimeBetweenUpdates();
    }

    public void setTargetFPS(int targetFPS) {
        this.targetFPS = targetFPS;
        updateTimeBetweenUpdates();
    }

    public Graphics getGraphics() {
        return this.g;
    }

    public WindowView getWindowView() {
        return windowView;
    }

    public double getNsPerTick() {
        return nsPerTick;
    }

    public double getNsPerFrame() {
        return nsPerFrame;
    }

    public int getTargetTPS() {
        return targetTPS;
    }

    public int getTps() {
        return tps;
    }

    public int getTargetFPS() {
        return targetFPS;
    }

    public int getFps() {
        return fps;
    }

    public boolean isRunning() {
        return running;
    }

    @SuppressWarnings("unchecked")
    static class Builder<T extends Builder<T>> {
        protected WindowView windowView;
        protected String title;
        protected int preferredWidth, preferredHeight, width, height;
        protected boolean resizable;

        protected int targetFPS, targetTPS;

        public Builder() {

        }

        public T windowView(WindowView windowView) {
            this.windowView = windowView;
            return (T) this;
        }

        public T title(String title) {
            this.title = title;
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

        public T width(int width) {
            this.width = width;
            return (T) this;
        }

        public T height(int height) {
            this.height = height;
            return (T) this;
        }

        public T resizable(boolean resizable) {
            this.resizable = resizable;
            return (T) this;
        }

        public T targetFPS(int targetFPS) {
            this.targetFPS = targetFPS;
            return (T) this;
        }

        public T targetTPS(int targetTPS) {
            this.targetTPS = targetTPS;
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
