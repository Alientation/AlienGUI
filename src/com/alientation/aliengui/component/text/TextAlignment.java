package com.alientation.aliengui.component.text;

@SuppressWarnings("unused")
public class TextAlignment {
    enum HorizontalTextAlignment {
        CENTER, //horizontally middle
        JUSTIFY, //spread out characters to fill horizontal width
        LEFT, //left horizontal alignment
        RIGHT //right horizontal alignment
    }

    enum VerticalTextAlignment {
        CENTER, //vertically middle
        JUSTIFY, //spread out lines to fill vertical width
        TOP, //top vertical alignment
        BOTTOM //bottom vertical alignment
    }

    protected HorizontalTextAlignment horizontalTextAlignment;
    protected VerticalTextAlignment verticalTextAlignment;

    public TextAlignment(HorizontalTextAlignment horizontalTextAlignment, VerticalTextAlignment verticalTextAlignment) {
        this.horizontalTextAlignment = horizontalTextAlignment;
        this.verticalTextAlignment = verticalTextAlignment;
    }

    public HorizontalTextAlignment getHorizontalTextAlignment() { return horizontalTextAlignment; }
    public VerticalTextAlignment getVerticalTextAlignment() { return verticalTextAlignment; }
}
