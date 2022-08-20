package com.alientation.aliengui.event.mouse;

import com.alientation.aliengui.event.EventListener;

public abstract class MouseInputListener extends EventListener {
    void mouseClicked(MouseEvent mouseEvent) { }
    void mouseEntered(MouseEvent mouseInputEvent) { }
    void mouseExited(MouseEvent mouseInputEvent) { }
    void mousePressed(MouseEvent mouseInputEvent) { }
    void mouseReleased(MouseEvent mouseInputEvent) { }
    void mouseDragged(MouseEvent mouseInputEvent) { }
    void mouseWheelMoved(MouseScrollEvent mouseScrollEvent) { }
}
