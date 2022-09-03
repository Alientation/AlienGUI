package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.collection.CollectionView;

@SuppressWarnings("unused")
public class VerticalStackView extends StackView { //TODO implement


    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public VerticalStackView(Builder<?> builder) {
        super(builder);
    }

    public static class Builder<T extends Builder<T>> extends StackView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public VerticalStackView build() {
            validate();
            return new VerticalStackView(this);
        }
    }
    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends VerticalStackView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public VerticalStackView build() {
            validate();
            return new VerticalStackView(this);
        }
    }
     */
}
