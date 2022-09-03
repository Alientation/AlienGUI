package com.alientation.aliengui.component.dimension;


@SuppressWarnings("unused")
public class StaticDimensionComponent extends DimensionComponent {
    public static StaticDimensionComponent MIN = new Builder<>().val(0).build();
    public static StaticDimensionComponent MAX = new Builder<>().val(Integer.MAX_VALUE).build();
    public static StaticDimensionComponent BASE = new Builder<>().val(200).build();

    public StaticDimensionComponent(Builder<?> builder) {
        super(builder);
        this.val = builder.val;
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
