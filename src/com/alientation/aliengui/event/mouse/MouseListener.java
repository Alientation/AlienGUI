package com.alientation.aliengui.event.mouse;

import com.alientation.aliengui.event.EventListener;

public abstract class MouseListener extends EventListener {
    public void mouseClicked(MouseEvent mouseEvent) { }
    public void mouseEntered(MouseEvent mouseEvent) { }
    public void mouseExited(MouseEvent mouseEvent) { }
    public void mousePressed(MouseEvent mouseEvent) { }
    public void mouseReleased(MouseEvent mouseEvent) { }
    public void mouseDragged(MouseEvent mouseEvent) { }
    public void mouseMoved(MouseEvent mouseEvent) { }
    public void mouseWheelMoved(MouseScrollEvent mouseScrollEvent) { }
}
