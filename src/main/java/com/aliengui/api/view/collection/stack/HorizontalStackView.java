package com.aliengui.api.view.collection.stack;


import com.aliengui.component.dimension.StaticDimensionComponent;

@SuppressWarnings("unused")
public class HorizontalStackView extends StackView { //TODO implement

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    protected HorizontalStackView(Builder<?> builder) {
        super(builder);
    }

    @Override
    public void resize() {
        //resizes the CollectionElementViews
        int height = safeHeight();
        int width = Math.round((safeWidth() - (collection.size() - 1) * spacing()) / (float) collection.size());
        int currentX = safeX();

        int y = safeY();
        for (CollectionElementView collectionElementView : collection) { //test if the events system will cause recursion
            collectionElementView.setY(new StaticDimensionComponent.Builder<>().val(y).build()); //TODO use relative dimensions
            collectionElementView.setX(new StaticDimensionComponent.Builder<>().val(currentX).build()); //relative dimensions
            collectionElementView.setWidth(new StaticDimensionComponent.Builder<>().val(width).build()); //relative dimensions
            collectionElementView.setHeight(new StaticDimensionComponent.Builder<>().val(height).build()); //relative dimensions
            currentX += width + spacing();
        }
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
