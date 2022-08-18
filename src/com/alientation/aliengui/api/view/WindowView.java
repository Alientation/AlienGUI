package com.alientation.aliengui.api.view;

public class WindowView extends View {

    public WindowView(Builder<?> builder) {
        super(builder);
    }

    static class Builder<T extends Builder<T>> extends View.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public WindowView build() {
            validate();
            return new WindowView(this);
        }
    }
}
