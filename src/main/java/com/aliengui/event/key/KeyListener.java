package com.aliengui.event.key;

import com.aliengui.event.EventListener;

import java.util.HashSet;
import java.util.Set;

public abstract class KeyListener extends EventListener {
    private static final Set<Integer> KEYS_DOWN = new HashSet<>();

    public void keyPressed(KeyEvent event) { }
    public void keyTyped(KeyEvent event) { }
    public void keyReleased(KeyEvent event) { }

    public static Set<Integer> getKeysDown() {
        return KEYS_DOWN;
    }
}