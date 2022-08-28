package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.event.view.ViewEvent;

import java.util.*;

@SuppressWarnings("unused")
public abstract class DimensionComponent extends Component {

    protected int val;
    protected Set<DimensionComponent> minDimensions = new HashSet<>();
    protected Set<DimensionComponent> maxDimensions = new HashSet<>();

    protected Set<DimensionComponent> dimensionSubscribers = new HashSet<>();
    public DimensionComponent(Builder<?> builder) {

    }

    public int val() {
        int val = this.val;
        for (DimensionComponent dimensionComponent : minDimensions)
            val = Math.max(val,dimensionComponent.val);
        for (DimensionComponent dimensionComponent : maxDimensions)
            val = Math.min(val, dimensionComponent.val);
        return val;
    }

    public int getAbsoluteVal() {
        return val;
    }

    public List<DimensionComponent> getMinDimensions() {
        return new ArrayList<>(minDimensions);
    }

    public List<DimensionComponent> getMaxDimensions() {
        return new ArrayList<>(maxDimensions);
    }

    //TODO updaters for min and max, register and unregister dependencies

    public void setVal(int val) {
        this.val = val;
        notifySubscribers();
    }

    //HOLY THIS IS SO MUCH CODE REPETITION TODO CLEAN THIS STUFF UP!!!!
    //HAVE ANOTHER CLASS SIMPLY TO STORE THE HASHSET AND HANDLE ADDING AND REMOVING, THIS CLASS CAN SIMPLY JUST BE AN ADAPTER TO THAT
    //PROBABLY GONNA NEED TO SUPPLY AN INTERFACE FOR THE ACTUAL ADD AND REMOVE STUFF
    public void setMinDimensions(Collection<DimensionComponent> minDimensions) {
        for (DimensionComponent dimensionComponent : this.minDimensions) dimensionComponent.unregisterDimensionSubscribers(this);
        this.minDimensions = new HashSet<>(minDimensions);
        for (DimensionComponent dimensionComponent : this.minDimensions) dimensionComponent.registerDimensionSubscriber(this);
        notifySubscribers();
    }

    public void addMinDimensions(Collection<DimensionComponent> minDimensions) {
        this.minDimensions.addAll(minDimensions);
        for (DimensionComponent dimensionComponent : this.minDimensions) dimensionComponent.registerDimensionSubscriber(this);
        notifySubscribers();
    }

    public void addMinDimensions(DimensionComponent... minDimensions) {
        this.minDimensions.addAll(Arrays.stream(minDimensions).toList());
        for (DimensionComponent dimensionComponent : minDimensions) dimensionComponent.registerDimensionSubscribers(this);
        notifySubscribers();
    }

    public void addMinDimension(DimensionComponent minDimension) {
        this.minDimensions.add(minDimension);
        minDimension.registerDimensionSubscribers(this);
        notifySubscribers();
    }

    public void removeMinDimensions(Collection<DimensionComponent> minDimensions) {
        this.minDimensions.removeAll(minDimensions);
        for (DimensionComponent dimensionComponent : minDimensions) dimensionComponent.unregisterDimensionSubscriber(this);
        notifySubscribers();
    }

    public void removeMinDimensions(DimensionComponent... minDimensions) {
        Arrays.stream(minDimensions).toList().forEach(this.minDimensions::remove);
        for (DimensionComponent dimensionComponent : minDimensions) dimensionComponent.unregisterDimensionSubscriber(this);
        notifySubscribers();
    }

    public void removeMinDimension(DimensionComponent minDimension) {
        this.minDimensions.remove(minDimension);
        minDimension.unregisterDimensionSubscriber(this);
        notifySubscribers();
    }

    public void setMaxDimensions(Collection<DimensionComponent> maxDimensions) {
        for (DimensionComponent dimensionComponent : this.maxDimensions) dimensionComponent.unregisterDimensionSubscribers(this);
        this.maxDimensions = new HashSet<>(maxDimensions);
        for (DimensionComponent dimensionComponent : this.maxDimensions) dimensionComponent.unregisterDimensionSubscribers(this);
        notifySubscribers();
    }

    public void addMaxDimensions(Collection<DimensionComponent> maxDimensions) {
        this.maxDimensions.addAll(maxDimensions);
        for (DimensionComponent dimensionComponent : this.maxDimensions) dimensionComponent.registerDimensionSubscriber(this);
        notifySubscribers();
    }

    public void addMaxDimensions(DimensionComponent... maxDimensions) {
        this.maxDimensions.addAll(Arrays.stream(maxDimensions).toList());
        for (DimensionComponent dimensionComponent : maxDimensions) dimensionComponent.registerDimensionSubscribers(this);
        notifySubscribers();
    }

    public void addMaxDimension(DimensionComponent maxDimension) {
        this.minDimensions.add(maxDimension);
        maxDimension.registerDimensionSubscribers(this);
        notifySubscribers();
    }

    public void removeMaxDimensions(Collection<DimensionComponent> maxDimensions) {
        this.maxDimensions.removeAll(maxDimensions);
        for (DimensionComponent dimensionComponent : maxDimensions) dimensionComponent.unregisterDimensionSubscriber(this);
        notifySubscribers();
    }

    public void removeMaxDimensions(DimensionComponent... maxDimensions) {
        Arrays.stream(maxDimensions).toList().forEach(this.maxDimensions::remove);
        for (DimensionComponent dimensionComponent : maxDimensions) dimensionComponent.unregisterDimensionSubscriber(this);
        notifySubscribers();
    }

    public void removeMaxDimension(DimensionComponent maxDimension) {
        this.maxDimensions.remove(maxDimension);
        maxDimension.unregisterDimensionSubscriber(this);
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
        protected List<DimensionComponent> minDimensions = new ArrayList<>();
        protected List<DimensionComponent> maxDimensions = new ArrayList<>();

        public Builder() {

        }

        public T minDimensions(Collection<DimensionComponent> minDimensions) {
            this.minDimensions = new ArrayList<>(minDimensions);
            return (T) this;
        }

        public T addMinDimensions(Collection<DimensionComponent> minDimensions) {
            this.minDimensions.addAll(minDimensions);
            return (T) this;
        }

        public T addMinDimensions(DimensionComponent... minDimensions) {
            this.minDimensions.addAll(Arrays.stream(minDimensions).toList());
            return (T) this;
        }

        public T addMinDimension(DimensionComponent dimensionComponent) {
            this.minDimensions.add(dimensionComponent);
            return (T) this;
        }

        public T maxDimensions(Collection<DimensionComponent> maxDimensions) {
            this.maxDimensions = new ArrayList<>(maxDimensions);
            return (T) this;
        }

        public T addMaxDimensions(Collection<DimensionComponent> maxDimensions) {
            this.maxDimensions.addAll(maxDimensions);
            return (T) this;
        }

        public T addMaxDimensions(DimensionComponent... maxDimensions) {
            this.maxDimensions.addAll(Arrays.stream(maxDimensions).toList());
            return (T) this;
        }

        public T addMaxDimension(DimensionComponent maxDimension) {
            this.maxDimensions.add(maxDimension);
            return (T) this;
        }

        public void validate() {

        }

        public abstract DimensionComponent build();
    }
}
