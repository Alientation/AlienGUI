package com.alientation.aliengui.component.color;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO implement this in the main project and allow shaders to be applied (ie getColor(int x, int y))
 */
public class ColorComponent {
    protected Color color;

    /**
     * Opacity value
     *
     * 1f for fully opaque
     * 0f for fully transparent
     */
    protected float opacity;

    protected Set<View> dependencies = new HashSet<>();

    public ColorComponent(Color color, float opacity) {
        this.color = color;
        this.opacity = opacity;
    }

    public ColorComponent(Color color) {
        this(color,1f);
    }


    //SETTERS

    public void setColor(Color color) {
        this.color = color;
    }
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
    public void registerDependency(View dependency) { this.dependencies.add(dependency); }
    public void unregisterDependency(View dependency) { this.dependencies.remove(dependency); }
    public void stateChanged() {
        for (View view : dependencies)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }


    //GETTERS

    public Color getColor() { return color; }
    public float getOpacity() { return opacity; }
    public List<View> getDependencies() { return dependencies.stream().toList(); }
}
