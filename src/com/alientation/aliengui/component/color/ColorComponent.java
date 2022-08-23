package com.alientation.aliengui.component.color;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO implement this in the main project and allow shaders to be applied (ie getColor(int x, int y))
 */
@SuppressWarnings("unused")
public class ColorComponent {
    protected Color color;

    /**
     * Opacity value
     *
     * 1f for fully opaque
     * 0f for fully transparent
     */
    protected float opacity;

    /**
     * Views that depend on this color component
     */
    protected Set<View> dependencies = new HashSet<>();

    /**
     * Constructs a ColorComponent with opacity
     *
     * @param color     Color
     * @param opacity   Opacity
     */
    public ColorComponent(Color color, float opacity) {
        this.color = color;
        this.opacity = opacity;
    }

    /**
     * Constructs a ColorComponent without opacity
     *
     * @param color Color
     */
    public ColorComponent(Color color) {
        this(color,1f);
    }

    /**
     * Draws the shape in the specified color
     *
     * @param shape     The shape to be drawn
     */
    public BufferedImage draw(Shape shape) {
        Rectangle bounds = shape.getBounds();
        BufferedImage image = new BufferedImage(bounds.width,bounds.height,BufferedImage.TYPE_INT_ARGB);

        int alpha = (int)(255 * opacity);

        for (int x = 0; x < bounds.width; x++)
            for (int y = 0; y < bounds.height; y++)
                if (shape.contains(x,y))
                    image.setRGB(x,y,color.getRGB() & (alpha << 24));

        return image;
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

    /**
     * Dispatches event to registered dependencies
     */
    public void stateChanged() {
        for (View view : dependencies)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }


    //GETTERS

    public Color getColor() { return color; } //shouldn't use this
    public float getOpacity() { return opacity; }
    public List<View> getDependencies() { return dependencies.stream().toList(); }
}
