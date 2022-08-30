package com.alientation.aliengui.component.color;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.util.ColorUtil;
import com.alientation.aliengui.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO implement this in the main project and allow shaders to be applied (ie getColor(int x, int y))
 */
@SuppressWarnings("unused")
public class ColorComponent extends Component {
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
    protected Set<View> subscribers = new HashSet<>();

    /**
     * Constructs a ColorComponent with opacity
     *
     * @param color     Color
     * @param opacity   Opacity
     */
    public ColorComponent(Color color, float opacity) {
        setColor(color);
        setOpacity(opacity);
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
     * Override for custom draw (for shaders)
     *
     * @param view         The view requesting this draw call
     * @param g         The Graphics context
     * @param shape     The shape to be drawn
     */
    public void draw(View view, Graphics g, Shape shape) {
        Rectangle bounds = shape.getBounds();
        BufferedImage image = new BufferedImage(bounds.width,bounds.height,BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < bounds.height; y++) //increment x before y to efficiently access memory
            for (int x = 0; x < bounds.width; x++)
                if (shape.contains(x,y))
                    image.setRGB(x, y, color.getRGB() & ((getAlpha() << 24) | 0x00ffffff));

        g.drawImage(image,view.x(),view.y(),null);
    }

    //SETTERS

    public void setColor(Color color) {
        this.color = color;
        notifySubscribers();
    }
    public void setOpacity(float opacity) {
        this.opacity = NumberUtil.clamp(opacity,0f,1f);
        notifySubscribers();
    }
    public void setAlpha(int alpha) {
        this.opacity = ColorUtil.alphaToOpacity(NumberUtil.clamp(alpha, 0, 255));
        notifySubscribers();
    }

    /**
     * Dispatches event to registered subscribers
     */
    @Override
    public void notifySubscribers() {
        for (View view : subscribers)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }


    //GETTERS

    public Color getColor() { return color; } //shouldn't use this
    public float getOpacity() { return opacity; }
    public int getAlpha() { return ColorUtil.opacityToAlpha(opacity); }
}
