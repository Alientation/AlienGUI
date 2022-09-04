package com.alientation.aliengui.api.view.window;

import com.alientation.aliengui.api.view.View;

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

    public View getViewAtPoint(int absX, int absY) {
        for (View view : sortedViewsByZIndex)
            if (view.getAbsoluteArea().contains(absX,absY)) //determine if the absolute area actually works properly
                return view;
        return null;
    }

    /**
     * Compiles a Z Index ordering of the components while updating necessary components
     * if their z index is out of place (not greater than the z index of the parent view)
     *
     */
    public void updateZIndexing() {
        if (!windowView.requireZIndexUpdate) return;
        windowView.requireRenderUpdate = true; //when z index has been updated, it needs to render

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
            for (View view : cur.getChildViews()) {
                if (view.getZIndex() <= cur.getZIndex()) //updates if required
                    view.setZIndex(cur.getZIndex()+1);
                bfs.offer(view);
            }
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
