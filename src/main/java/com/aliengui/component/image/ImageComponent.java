package com.aliengui.component.image;

import com.aliengui.api.view.View;
import com.aliengui.component.Component;
import com.aliengui.util.ColorUtil;
import com.aliengui.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * TODO implement this with the main project and allow shaders (ie getColor(int x, int y))
 */
@SuppressWarnings("unused")
public class ImageComponent extends Component {

    //Image contained by this ImageComponent
    protected BufferedImage image;

    //Opacity applied to the image
    protected float opacity;

    /**
     * Constructs an ImageComponent
     *
     * @param image     Image of this component
     */
    public ImageComponent(BufferedImage image) {
        this(image,1f);
    }

    /**
     * Constructs an ImageComponent with opacity
     *
     * @param image     Image of this component
     * @param opacity   Opacity of this component
     */
    public ImageComponent(BufferedImage image, float opacity) {
        this.image = image;
        this.opacity = Math.min(Math.max(opacity,0f),1f);
    }

    /**
     * Gets the updated image for the view
     *
     * Override for custom draw (for shaders)
     *
     * @param view  The view that requested this draw
     * @param g         The Graphics context
     * @param shape     The shape to be drawn
     */
    public void draw(View view, Graphics g, Shape shape) {
        BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {//increment x before y to efficiently access memory
            for (int x = 0; x < image.getWidth(); x++) {
                int newAlpha = ColorUtil.opacityToAlpha(ColorUtil.alphaToOpacity(getAlpha()) * opacity);
                newImage.setRGB(x, y, image.getRGB(x, y) & ((newAlpha << 24) | 0x00ffffff));
            }
        }
        g.drawImage(image,view.x(),view.y(),null);
    }


    //SETTERS

    public void setImage(BufferedImage image) {
        this.image = image;
        notifySubscribers();
    }
    public void setOpacity(float opacity) {
        this.opacity = NumberUtil.clamp(opacity,0f,1f);
        notifySubscribers();
    }
    public void setAlpha(int alpha) {
        this.opacity = ColorUtil.alphaToOpacity(NumberUtil.clamp(alpha,0,255));
        notifySubscribers();
    }


    //GETTERS

    public BufferedImage getImage() { return image; } //shouldn't use this
    public float getOpacity() { return opacity; }
    public int getAlpha() { return (int) (255 * opacity); }
}
