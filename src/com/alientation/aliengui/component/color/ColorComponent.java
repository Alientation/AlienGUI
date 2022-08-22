package com.alientation.aliengui.component.color;

import java.awt.*;

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
}
