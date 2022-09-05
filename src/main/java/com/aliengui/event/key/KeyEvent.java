package com.aliengui.event.key;

import com.aliengui.event.Event;

public class KeyEvent extends Event {
    private java.awt.event.KeyEvent event;

    public KeyEvent(java.awt.event.KeyEvent event) {
        this.event = event;
    }

    public java.awt.event.KeyEvent getEvent() {
        return event;
    }

    public int getExtendedKeyCode() {
        return event.getExtendedKeyCode();
    }

    public char getKeyChar() {
        return event.getKeyChar();
    }

    public int getKeyCode() {
        return event.getKeyCode();
    }

    public int getKeyLocation() {
        return event.getKeyLocation();
    }

    public boolean isActionKey() {
        return event.isActionKey();
    }

}
