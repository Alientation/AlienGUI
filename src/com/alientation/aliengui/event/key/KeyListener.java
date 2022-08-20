package com.alientation.aliengui.event.key;

import com.alientation.aliengui.event.EventListener;

import java.awt.event.KeyAdapter;
import java.util.HashSet;
import java.util.Set;

public abstract class KeyListener extends EventListener {
    private static final Set<Integer> KEYS_DOWN = new HashSet<>();

    void keyPressed(KeyEvent event) { }
    void keyTyped(KeyEvent event) { }
    void keyReleased(KeyEvent event) { }

    public static Set<Integer> getKeysDown() {
        return KEYS_DOWN;
    }
}