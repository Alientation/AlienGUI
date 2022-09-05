package com.alientation.aliengui.test.basicapp.gui;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.api.view.window.Window;
import com.alientation.aliengui.api.view.window.WindowView;

@SuppressWarnings("unused")
public class BasicApp {
    public static void main(String[] args) {
        Window basicWindow = new Window.Builder<>().build();
        WindowView basicWindowView = new WindowView.Builder<>().build();

        View basicWindowSubView = new View.Builder<>().build();
        basicWindowSubView.init(basicWindowView);

    }
}
