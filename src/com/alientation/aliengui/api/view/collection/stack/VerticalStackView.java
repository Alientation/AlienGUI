package com.alientation.aliengui.api.view.collection.stack;


import com.alientation.aliengui.component.dimension.StaticDimensionComponent;

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

    @Override
    public void resize() {
        //resizes the CollectionElementViews
        int height = Math.round((safeHeight() - (collection.size() - 1) * spacing()) / (float) collection.size());
        int width = safeWidth();
        int x = safeX();

        int currentY = safeY();
        for (CollectionElementView collectionElementView : collection) { //test if the events system will cause recursion
            collectionElementView.setY(new StaticDimensionComponent.Builder<>().val(currentY).build()); //TODO use relative dimensions
            collectionElementView.setX(new StaticDimensionComponent.Builder<>().val(x).build()); //relative dimensions
            collectionElementView.setWidth(new StaticDimensionComponent.Builder<>().val(width).build()); //relative dimensions
            collectionElementView.setHeight(new StaticDimensionComponent.Builder<>().val(height).build()); //relative dimensions
            currentY += height + spacing();
        }
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
