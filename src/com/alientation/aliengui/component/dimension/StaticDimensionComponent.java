package com.alientation.aliengui.component.dimension;


@SuppressWarnings("unused")
public class StaticDimensionComponent extends DimensionComponent {
    public static StaticDimensionComponent MIN = new StaticDimensionComponent(0);
    public static StaticDimensionComponent MAX = new StaticDimensionComponent(Integer.MAX_VALUE);
    public static StaticDimensionComponent BASE = new StaticDimensionComponent(200);

    public StaticDimensionComponent(int val) {
        this.val = val;
    }

    public StaticDimensionComponent(int val, DimensionComponent min, DimensionComponent max) {
        this.val = val;
        this.min = min;
        this.max = max;
    }
}
