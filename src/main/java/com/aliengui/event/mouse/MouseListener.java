package com.aliengui.event.mouse;

import com.aliengui.event.EventListener;

@SuppressWarnings("unused")
public abstract class MouseListener extends EventListener {
    public void mouseClicked(MouseEvent event) { mouseAction(event); }
    public void mouseEntered(MouseEvent event) { mouseAction(event); }
    public void mouseExited(MouseEvent event) { mouseAction(event); }
    public void mousePressed(MouseEvent event) { mouseAction(event); }
    public void mouseReleased(MouseEvent event) { mouseAction(event); }
    public void mouseDragged(MouseEvent event) { mouseAction(event); }
    public void mouseMoved(MouseEvent event) { mouseAction(event); }
    public void mouseWheelMoved(MouseScrollEvent event) { mouseAction(event); }

    public void mouseAction(MouseEvent event) { }
}
