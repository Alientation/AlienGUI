package com.alientation.aliengui.api.view.input;

import com.alientation.aliengui.api.view.TextLabelView;

@SuppressWarnings("unused")
public class ButtonView extends TextLabelView {

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public ButtonView(Builder<?> builder) {
        super(builder);
    }










    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public ButtonView build() {
            validate();
            return new ButtonView(this);
        }
    }
}
