package com.alientation.aliengui.component.animation;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Figure out how to work this in with the current system
 *
 * perhaps each view can store a cached BufferedImage of the view (not including any subviews)
 * and only redraw it if there was a View State change
 *
 * all this class is responsible for is changing the way views are rendered, perhaps given a view, return a BufferedImage of
 * an animated frame of the view
 */
@SuppressWarnings("unused")
public class AnimationComponent extends Component {





    public void draw(View view, Graphics g, float deltaFrame) {



    }

    public void tick(float deltaTick) {

    }


    /**
     * Needs its own separate listener, AnimationListener
     */
    @Override
    public void notifySubscribers() {
        super.notifySubscribers();
    }
}
