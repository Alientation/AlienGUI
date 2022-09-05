package com.aliengui.examples.basicapp;

import com.aliengui.api.view.View;
import com.aliengui.api.view.window.WindowView;
import com.aliengui.component.color.ColorComponent;
import com.aliengui.component.dimension.StaticDimensionComponent;

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
