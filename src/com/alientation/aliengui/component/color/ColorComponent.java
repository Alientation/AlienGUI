package com.alientation.aliengui.component.color;

import java.awt.*;

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


    //GETTERS

    public Color getColor() { return color; }
    public float getOpacity() { return opacity; }
}
