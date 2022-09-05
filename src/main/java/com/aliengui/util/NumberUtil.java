package com.aliengui.util;

public class NumberUtil {
    public static long clamp(long val, long min, long max) {
        return Math.min(Math.max(val,min),max);
    }
    public static int clamp(int val, int min, int max) {
        return Math.min(Math.max(val,min),max);
    }
    public static float clamp(float val, float min, float max) {
        return Math.min(Math.max(val,min),max);
    }
    public static double clamp(double val, double min, double max) {
        return Math.min(Math.max(val,min),max);
    }
}
