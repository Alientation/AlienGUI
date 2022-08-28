package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.event.view.ViewEvent;

import java.util.*;

@SuppressWarnings("unused")
public abstract class DimensionComponent extends Component {

    protected int val;
    protected List<DimensionComponent> min = new ArrayList<>();
    protected List<DimensionComponent> max = new ArrayList<>();

    protected Set<DimensionComponent> dimensionSubscribers = new HashSet<>();
    public DimensionComponent(Builder<?> builder) {

    }

    public int val() {
        int val = this.val;
        for (DimensionComponent dimensionComponent : min)
            val = Math.max(val,dimensionComponent.val);
        for (DimensionComponent dimensionComponent : max)
            val = Math.min(val, dimensionComponent.val);
        return val;
    }

    public int getAbsoluteVal() {
        return val;
    }

    public List<DimensionComponent> getMinValues() {
        return new ArrayList<>(min);
    }

    public List<DimensionComponent> getMaxValues() {
        return new ArrayList<>(max);
    }

    //TODO updaters for min and max, register and unregister dependencies

    public void setVal(int val) {
        this.val = val;
        notifySubscribers();
    }

    public void setMin(List<DimensionComponent> min) {
        this.min = min;
        notifySubscribers();
    }

    public void setMax(List<DimensionComponent> max) {
        this.max = max;
        notifySubscribers();
    }

    /**
     * Dispatches event to registered subscribers
     */
    public void notifySubscribers() {
        for (DimensionComponent dimensionComponent : dimensionSubscribers)
            dimensionComponent.notifySubscribers();
        for (View view : subscribers)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }

    public void registerDimensionSubscriber(DimensionComponent dimensionSubscriber) {
        this.dimensionSubscribers.add(dimensionSubscriber);
    }

    public void registerDimensionSubscribers(DimensionComponent... dimensionSubscribers) {
        this.dimensionSubscribers.addAll(Arrays.stream(dimensionSubscribers).toList());
    }

    public void registerDimensionSubscribers(Collection<DimensionComponent> dimensionSubscribers) {
        this.dimensionSubscribers.addAll(dimensionSubscribers);
    }

    public void unregisterDimensionSubscriber(DimensionComponent dimensionSubscriber) {
        this.dimensionSubscribers.remove(dimensionSubscriber);
    }

    public void unregisterDimensionSubscribers(DimensionComponent... dimensionSubscribers) {
        Arrays.asList(dimensionSubscribers).forEach(this.dimensionSubscribers::remove);
    }

    public void unregisterDimensionSubscribers(Collection<DimensionComponent> dimensionSubscribers) {
        this.dimensionSubscribers.removeAll(dimensionSubscribers);
    }

    public List<DimensionComponent> getDimensionSubscribers() { return dimensionSubscribers.stream().toList(); }

    @SuppressWarnings("unchecked")
    static abstract class Builder<T extends Builder<T>> {
        protected List<DimensionComponent> min = new ArrayList<>();
        protected List<DimensionComponent> max = new ArrayList<>();

        public Builder() {

        }

        public T min(Collection<DimensionComponent> min) {
            this.min = new ArrayList<>(min);
            return (T) this;
        }

        //TODO finish builder stuff


        public void validate() {

        }

        public abstract DimensionComponent build();
    }
}
