package com.alientation.aliengui.component.image;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.util.ColorUtil;
import com.alientation.aliengui.util.NumberUtil;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO implement this with the main project and allow shaders (ie getColor(int x, int y))
 */
@SuppressWarnings("unused")
public class ImageComponent {

    /**
     *
     */
    protected BufferedImage image;

    /**
     *
     */
    protected float opacity;

    /**
     *
     */
    protected Set<View> dependencies = new HashSet<>();

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
     * @return The image to be drawn
     */
    public BufferedImage draw(View view) {
        BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; y < image.getHeight(); y++) {
                int newAlpha = ColorUtil.opacityToAlpha(ColorUtil.alphaToOpacity(getAlpha()) * opacity);
                newImage.setRGB(x, y, image.getRGB(x, y) & ((newAlpha << 24) | 0x00ffffff));
            }
        return newImage;
    }


    //SETTERS

    public void setImage(BufferedImage image) {
        this.image = image;
        stateChanged();
    }
    public void setOpacity(float opacity) {
        this.opacity = NumberUtil.clamp(opacity,0f,1f);
        stateChanged();
    }
    public void setAlpha(int alpha) {
        this.opacity = ColorUtil.alphaToOpacity(NumberUtil.clamp(alpha,0,255));
        stateChanged();
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

    public BufferedImage getImage() { return image; } //shouldn't use this
    public float getOpacity() { return opacity; }
    public int getAlpha() { return (int) (255 * opacity); }
    public List<View> getDependencies() { return dependencies.stream().toList(); }
}
