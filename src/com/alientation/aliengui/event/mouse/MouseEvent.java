package com.alientation.aliengui.event.mouse;

import com.alientation.aliengui.event.Event;

import java.awt.*;

public class MouseEvent extends Event {
    protected java.awt.event.MouseEvent event;
    public MouseEvent(java.awt.event.MouseEvent event) {
        this.event = event;
    }

    public java.awt.event.MouseEvent getEvent() {
        return event;
    }

    public int getX() {
        return event.getX();
    }

    public int getXOnScreen() {
       return event.getXOnScreen();
    }

    public int getY() {
        return event.getY();
    }

    public int getYOnScreen() {
        return event.getYOnScreen();
    }

    public int getButton() {
        return event.getButton();
    }

    public int getClickCount() {
        return event.getClickCount();
    }

    public Point getLocationOnScreen() {
        return event.getLocationOnScreen();
    }

    public long getWhen() {
        return event.getWhen();
    }
}
