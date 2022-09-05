package com.alientation.aliengui.component.dimension;


@SuppressWarnings("unused")
public class StaticDimensionComponent extends DimensionComponent {
    protected StaticDimensionComponent(Builder<?> builder) {
        super(builder);
        this.val = builder.val;
    }

    public static StaticDimensionComponent ZERO() {
        return new Builder<>().val(0).build();
    }
    public static StaticDimensionComponent BASE_HEIGHT() {
        return new Builder<>().val(225).build();
    }
    public static StaticDimensionComponent BASE_WIDTH() {
        return new Builder<>().val(400).build();
    }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends DimensionComponent.Builder<T> {
        protected int val;
        public Builder() {

        }

        public T val(int val) {
            this.val = val;
            return (T) this;
        }

        @Override
        public void validate() {
            super.validate();
        }

        @Override
        public StaticDimensionComponent build() {
            return new StaticDimensionComponent(this);
        }
    }
}
