package com.alientation.aliengui.test.basicapp;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.api.view.window.WindowView;
import com.alientation.aliengui.component.color.ColorComponent;
import com.alientation.aliengui.component.dimension.StaticDimensionComponent;

import java.awt.*;

@SuppressWarnings("unused")
public class BasicApp {
    public static void main(String[] args) {
        WindowView basicWindowView = new WindowView.Builder<>()
                .backgroundColor(new ColorComponent(Color.orange))
                .build();

        View basicWindowSubView = new View.Builder<>() //BUG doesn't fill screen
                .marginX(new StaticDimensionComponent.Builder<>().val(10).build())
                .marginY(new StaticDimensionComponent.Builder<>().val(10).build())
                .backgroundColor(new ColorComponent(Color.GRAY))
                .build();
        basicWindowSubView.init(basicWindowView);

    }
}
