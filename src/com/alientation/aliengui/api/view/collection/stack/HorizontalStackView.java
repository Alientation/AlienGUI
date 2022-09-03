package com.alientation.aliengui.api.view.collection.stack;


@SuppressWarnings("unused")
public class HorizontalStackView extends StackView { //TODO implement

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public HorizontalStackView(Builder<?> builder) {
        super(builder);
    }

    @Override
    public void resize() {

    }


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
