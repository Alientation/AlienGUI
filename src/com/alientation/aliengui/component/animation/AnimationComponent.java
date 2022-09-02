package com.alientation.aliengui.component.animation;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.api.view.input.ButtonView;
import com.alientation.aliengui.component.Component;

import java.awt.*;

/**
 * Figure out how to work this in with the current system
 * <p>
 * perhaps each view can store a cached BufferedImage of the view (not including any subviews)
 * and only redraw it if there was a View State change
 * <p>
 * all this class is responsible for is changing the way views are rendered, perhaps given a view, return a BufferedImage of
 * an animated frame of the view
 */
@SuppressWarnings("unused")
public abstract class AnimationComponent extends Component {


    public AnimationComponent() {
        super(1); //default limit for animation components

    }

    public AnimationComponent(int maxSubscribers) {
        super(maxSubscribers);
    }

    /**
     *
     *
     * @param view          View calling this draw
     * @param g             Graphics context
     * @param deltaFrame    Time passed since last draw frame
     */
    public void draw(View view, Graphics g, float deltaFrame) {



    }

    /**
     * Tick event fired every tick of subscriber views
     * <p>
     * Since Component does not guarantee a single subscriber view,
     * it is important to track data corresponding to each view to properly animate
     * or to ensure Component only has a single subscriber view which is already the default
     * setting
     *
     * @param view          View calling this tick event
     * @param deltaTick     Time passed since last tick event
     */
    public void tick(View view, float deltaTick) {

    }


    /**
     * Needs its own separate listener, AnimationListener
     */
    @Override
    public void notifySubscribers() {
        super.notifySubscribers();
    }
}
