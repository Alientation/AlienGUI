package com.alientation.aliengui.component.image;

import com.alientation.aliengui.api.view.View;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO implement this with the main project and allow shaders (ie getColor(int x, int y))
 */
@SuppressWarnings("unused")
public class ImageComponent {

    protected BufferedImage image;
    protected float opacity;
    protected Set<View> dependencies = new HashSet<>();


}
