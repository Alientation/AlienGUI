package com.alientation.aliengui.event.mouse;

import java.awt.event.MouseWheelEvent;

public class MouseScrollEvent extends MouseEvent {
    protected double preciseWheelRotation;
    protected int scrollAmount, scrollType, unitsToScroll, wheelRotation;

    public MouseScrollEvent(java.awt.event.MouseEvent mouseEvent) {
        super(mouseEvent);
        if (mouseEvent instanceof MouseWheelEvent mouseWheelEvent) {
            preciseWheelRotation = mouseWheelEvent.getPreciseWheelRotation();
            scrollAmount = mouseWheelEvent.getScrollAmount();
            scrollType = mouseWheelEvent.getScrollType();
            unitsToScroll = mouseWheelEvent.getUnitsToScroll();
            wheelRotation = mouseWheelEvent.getWheelRotation();
        }
    }

    public double getPreciseWheelRotation() {
        return preciseWheelRotation;
    }

    public int getScrollAmount() {
        return scrollAmount;
    }

    public int getScrollType() {
        return scrollType;
    }

    public int getUnitsToScroll() {
        return unitsToScroll;
    }

    public int getWheelRotation() {
        return wheelRotation;
    }
}
