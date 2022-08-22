package com.alientation.aliengui.api.view.collection;

import com.alientation.aliengui.api.view.View;

public class CollectionView extends View {

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public CollectionView(Builder<?> builder) {
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
    /*
    public static class Builder<T extends Builder<T>> extends CollectionView.Builder<T> {

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
}
