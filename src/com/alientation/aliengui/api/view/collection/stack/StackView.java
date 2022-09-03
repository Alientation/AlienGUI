package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.collection.CollectionView;

public class StackView extends CollectionView {
    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public StackView(Builder<?> builder) {
        super(builder);
    }

    public static class Builder<T extends StackView.Builder<T>> extends CollectionView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public StackView build() {
            validate();
            return new StackView(this);
        }
    }

    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends StackView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public HorizontalStackView build() {
            validate();
            return new HorizontalStackView(this);
        }
    }
     */
}
