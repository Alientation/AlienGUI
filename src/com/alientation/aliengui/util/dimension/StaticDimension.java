package com.alientation.aliengui.util.dimension;


@SuppressWarnings("unused")
public class StaticDimension extends Dimension {
    public static StaticDimension MIN = new StaticDimension(0);
    public static StaticDimension MAX = new StaticDimension(Integer.MAX_VALUE);
    public static StaticDimension BASE = new StaticDimension(200);

    public StaticDimension(int val) {
        this.val = val;
    }

    public StaticDimension(int val, Dimension min, Dimension max) {
        this.val = val;
        this.min = min;
        this.max = max;
    }
}
