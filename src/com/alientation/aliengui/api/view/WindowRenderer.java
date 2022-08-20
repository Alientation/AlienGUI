package com.alientation.aliengui.api.view;

import java.awt.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("unused")
public class WindowRenderer {
    private final WindowView windowView;
    private final List<View> sortedViewsByZIndex;

    public WindowRenderer(WindowView windowView) {
        this.windowView = windowView;
        this.sortedViewsByZIndex = new ArrayList<>();
    }

    public void update() {
        this.sortedViewsByZIndex.clear();

        //bfs through to find all renderables in this window
        Queue<View> bfs = new LinkedList<>();
        bfs.offer(windowView);

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
    }

    /**
     * Renders only if the window requires a render update
     *
     * @param g Graphical output
     */
    public boolean render(Graphics g) {
        if (!windowView.doesRequireRenderUpdate()) return false;
        for (View view : sortedViewsByZIndex)
            view.render(g);
        return true;
    }

    public WindowView getWindow() {
        return windowView;
    }
    public List<View> getSortedViewsByZIndex() {
        return sortedViewsByZIndex;
    }
}
