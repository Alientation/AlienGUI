package com.alientation.aliengui.api.view.collection.stack;

import com.alientation.aliengui.api.view.collection.CollectionView;

@SuppressWarnings("unused")
public class HorizontalStackView extends CollectionView { //TODO implement


    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public HorizontalStackView(Builder<?> builder) {
        super(builder);
    }


    public static class Builder<T extends Builder<T>> extends CollectionView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public HorizontalStackView build() {
            validate();
            return new HorizontalStackView(this);
        }
    }
    /* Boilerplate code
    public static class Builder<T extends Builder<T>> extends HorizontalStackView.Builder<T> {

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
