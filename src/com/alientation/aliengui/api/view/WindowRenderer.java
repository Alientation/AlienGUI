package com.alientation.aliengui.api.view;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WindowRenderer {
    private final Window window;
    private final List<View> sortedViewsByZIndex;

    public WindowRenderer(Window canvas) {
        this.window = canvas;
        this.sortedViewsByZIndex = new ArrayList<>();
    }

    public void update() {
        this.sortedViewsByZIndex.clear();

        //bfs through to find all renderables in this window
        Queue<View> bfs = new LinkedList<>();
        bfs.offer(window.windowView);

        Set<View> visitedViews = new HashSet<>(); //sanity check, this shouldn't be a problem, but who knows

        View cur;
        while (!bfs.isEmpty()) {
            cur = bfs.poll();
            if (visitedViews.contains(cur)) {
                System.out.println("View " + cur + " is a subview of multiple views");
                continue;
            }
            visitedViews.add(cur);
            sortedViewsByZIndex.add(cur);
            for (View view : cur.getSubviews())
                bfs.offer(view);
        }
        sortedViewsByZIndex.sort(Comparator.comparingInt(View::getZIndex));
        /*
        for (Renderable renderable : sortedZIndex) {
            System.out.println(renderable.id());
        }*/
    }

    /**
     * Renders only the renderableComponents after the first one that requires a render update.
     * Still would want maybe to check to make sure renderable components are within their bounds
     *
     * @param g Graphical output
     * @return Whether a render update was actually required or not
     */
    public boolean render(Graphics2D g) {
        boolean requiredRenderUpdate = false;
        for (View renderable : sortedViewsByZIndex) {
            if (renderable.doesRequireRenderUpdate()) {
                requiredRenderUpdate = true;
                renderable.setRequireRenderUpdate(false);
            }
            //if (requiredRenderUpdate) TODO FIX BUG - requireRenderUpdate doesn't actually account for everything...
            //renderable.render(g);
        }
        if (requiredRenderUpdate) {
            g.setColor(Color.BLUE);
            g.fillRect(0,0,window.getWidth(),window.getHeight());
            for (View view : sortedViewsByZIndex)
                view.render(g);
        }
        return requiredRenderUpdate;
    }

    public Window getWindow() { return window; }
    public List<View> getSortedViewsByZIndex() { return sortedViewsByZIndex; }
}
