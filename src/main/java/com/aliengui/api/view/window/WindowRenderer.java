package com.aliengui.api.view.window;

import com.aliengui.api.view.View;

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

    /**
     * Returns the top most view at a given coordinate
     * <p>
     * Attempts to find a valid view, but will return null if no valid view was found
     *
     * @param absX  Absolute x position on the Window
     * @param absY  Absolute y position on the Window
     * @return      Topmost view at the given point if it exists
     */
    public View getViewAtPoint(int absX, int absY) {
        // TODO: 9/5/2022
        // instead of finding the topmost view every single time,
        // precalculate the topmost view at every single point on the windowView display area
        // and update every single time a render update happens
        // precalculate by iterating from the least z index to the highest
        // having a 2d array of pointers to the top most view at the point

        for (int i = 0; i < sortedViewsByZIndex.size(); i++) //don't use enhanced for loop because of potential concurrent modification
            if (sortedViewsByZIndex.get(i).getAbsoluteArea().contains(absX,absY))
                return sortedViewsByZIndex.get(i);
        return null;
    }

    /**
     * Compiles a Z Index ordering of the components while updating necessary components
     * if their z index is out of place (not greater than the z index of the parent view)
     */
    public void updateZIndexing() {
        if (!windowView.requireZIndexUpdate) return;
        windowView.requireZIndexUpdate = false;
        windowView.requireRenderUpdate = true; //when z index has been updated, it needs to render

        this.sortedViewsByZIndex.clear();

        //bfs through to find all renderables in this window
        Queue<View> bfs = new LinkedList<>();
        bfs.offer(windowView);

        Set<View> visitedViews = new HashSet<>(); //sanity check, this shouldn't be a problem, but who knows

        View cur;
        while (!bfs.isEmpty()) {
            cur = bfs.poll();
            if (visitedViews.contains(cur))
                throw new IllegalStateException("View " + cur + " is a subview of multiple views");
            visitedViews.add(cur);
            sortedViewsByZIndex.add(cur);
            for (View view : cur.getChildViews()) { //potential concurrent modification?
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
        if (!windowView.requireRenderUpdate) return false;
        System.out.println("render " + sortedViewsByZIndex.size());
        windowView.requireRenderUpdate = false;
        for (int i = 0; i < sortedViewsByZIndex.size(); i++) //don't use enhanced for loop because of potential concurrent modification
            sortedViewsByZIndex.get(i).render(g);
        return true;
    }

    public WindowView getWindow() { return windowView; }
    public List<View> getSortedViewsByZIndex() { return sortedViewsByZIndex; }
}
