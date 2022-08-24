package com.alientation.aliengui.api.view;

import java.awt.*;

public class TextLabelView extends View {


    protected Font font;





    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public TextLabelView(Builder<?> builder) {
        super(builder);
    }




    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
}

/*
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
 */
