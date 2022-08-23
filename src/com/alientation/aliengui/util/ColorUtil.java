package com.alientation.aliengui.util;

public class ColorUtil {
    public static int rgbaFromRedGreenBlueAlpha(int red, int green, int blue, int alpha) {
        red = NumberUtil.clamp(red,0,255);
        green = NumberUtil.clamp(green,0,255);
        blue = NumberUtil.clamp(blue,0,255);
        alpha = NumberUtil.clamp(alpha,0,255);

        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int rgbFromRedGreenBlue(int red, int green, int blue) {
        return rgbaFromRedGreenBlueAlpha(red,green,blue,255);
    }

    public static int redFromRGBA(int rgba) {
        rgba = NumberUtil.clamp(rgba,0,0xffffffff);
        return (rgba & 0x000000ff);
    }

    public static int greenFromRGBA(int rgba) {
        rgba = NumberUtil.clamp(rgba,0,0xffffffff);
        return (rgba & 0x0000ff00) >>> 8;
    }

    public static int blueFromRGBA(int rgba) {
        rgba = NumberUtil.clamp(rgba,0,0xffffffff);
        return (rgba & 0x00ff0000) >>> 16;
    }

    public static int alphaFromRGBA(int rgba) {
        rgba = NumberUtil.clamp(rgba,0,0xffffffff);
        return (rgba & 0xff000000) >>> 24;
    }

    public static int opacityToAlpha(float opacity) {
        return (int)(opacity * 255);
    }

    public static float alphaToOpacity(int alpha) {
        return alpha / 255f;
    }
}
