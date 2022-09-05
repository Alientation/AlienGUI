package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.component.Observer;
import com.alientation.aliengui.component.Subscriber;

import java.util.*;

/**
 * Base DimensionComponent that maintains a dimension value and handles Observers and Subscribers
 */
@SuppressWarnings("unused")
public abstract class DimensionComponent extends Component {
    //TODO add center dimensioning (and other related types)
    //This dimension's value
    protected int val;

    //Dimensions this dimension must be greater than. Observes it for any state changes and updates this dimension if so
    protected Observer<DimensionComponent,DimensionComponent> minDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObserver(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObservers(parent); }
    };

    //Dimensions this dimension must be less than. Observes it for any state changes and updates this dimension if so
    protected Observer<DimensionComponent,DimensionComponent> maxDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObserver(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObserver(parent); }
    };

    //Dimensions that observe this dimension for state changes, notifies them if so
    protected Subscriber<DimensionComponent,DimensionComponent> dimensionsSubscriber = new Subscriber<>(this) {
        @Override
        public void notifySubscribers() { dimensionsSubscriber.getSubscribed().forEach(DimensionComponent::notifySubscribers); }
        @Override
        public void unregister(DimensionComponent subscribed) { }
        @Override
        public void register(DimensionComponent subscribed) { }
    };

    /**
     * Builds a DimensionComponent
     *
     * @param builder   Builder
     */

    protected DimensionComponent(Builder<?> builder) {

    }

    /**
     * Notifies subscribers that observe this object for state changes
     */
    public void notifySubscribers() {
        super.notifySubscribers();
        dimensionsSubscriber.notifySubscribers();
    }

    public boolean isSubscriberOfThis(View view) {
        return subscribers.getSubscribed().contains(view);
    }


    //GETTERS

    /**
     * Returns the value of this dimension after it is constrained by the min and max dimensions
     *
     * @return  Value of this dimension
     */
    public int val() { //TODO throw an error if minDimensions conflicts with maxDimensions (IllegalStateException)
        int val = this.val;

        //val must be greater than all minDimensions
        for (DimensionComponent dimensionComponent : minDimensions.getObserved())
            val = Math.max(val,dimensionComponent.val);

        //val must be less than all maxDimensions
        for (DimensionComponent dimensionComponent : maxDimensions.getObserved())
            val = Math.min(val, dimensionComponent.val);

        return val;
    }

    /**
     * Returns the unconstrained dimension value
     *
     * @return  Unconstrained value of this dimension
     */
    public int getAbsoluteVal() {
        return val;
    }

    public List<DimensionComponent> getMinDimensions() { return new ArrayList<>(minDimensions.getObserved()); }
    public List<DimensionComponent> getMaxDimensions() { return new ArrayList<>(maxDimensions.getObserved()); }

    public Observer<DimensionComponent,DimensionComponent> getMinDimensionsObserver() { return minDimensions; }
    public Observer<DimensionComponent,DimensionComponent> getMaxDimensionsObserver() { return maxDimensions; }
    public Subscriber<DimensionComponent,DimensionComponent> getDimensionObserver() { return dimensionsSubscriber; }


    //SETTERS

    public void setVal(int val) {
        this.val = val;
        notifySubscribers();
    }

    //minDimensions
    public void setMinDimensions(Collection<DimensionComponent> minDimensions) { this.minDimensions.setObserved(minDimensions); }
    public void addMinDimensions(Collection<DimensionComponent> minDimensions) { this.minDimensions.registerObserved(minDimensions); }
    public void addMinDimensions(DimensionComponent... minDimensions) { this.minDimensions.registerObserved(minDimensions); }
    public void addMinDimension(DimensionComponent minDimension) { this.minDimensions.registerObserved(minDimension); }
    public void removeMinDimensions(Collection<DimensionComponent> minDimensions) { this.minDimensions.unregisterObserved(minDimensions); }
    public void removeMinDimensions(DimensionComponent... minDimensions) { this.minDimensions.unregisterObserved(minDimensions); }
    public void removeMinDimension(DimensionComponent minDimension) { this.minDimensions.unregister(minDimension); }

    //maxDimensions
    public void setMaxDimensions(Collection<DimensionComponent> maxDimensions) { this.maxDimensions.setObserved(maxDimensions); }
    public void addMaxDimensions(Collection<DimensionComponent> maxDimensions) { this.maxDimensions.registerObserved(maxDimensions); }
    public void addMaxDimensions(DimensionComponent... maxDimensions) { this.maxDimensions.registerObserved(maxDimensions); }
    public void addMaxDimension(DimensionComponent maxDimension) { this.maxDimensions.registerObserved(maxDimension); }
    public void removeMaxDimensions(Collection<DimensionComponent> maxDimensions) { this.maxDimensions.unregisterObserved(maxDimensions); }
    public void removeMaxDimensions(DimensionComponent... maxDimensions) { this.maxDimensions.unregisterObserved(maxDimensions); }
    public void removeMaxDimension(DimensionComponent maxDimension) { this.maxDimensions.unregisterObserved(maxDimension); }

    //dimensionSubscribers
    public void registerDimensionObserver(DimensionComponent dimensionObserver) { this.dimensionsSubscriber.registerSubscribed(dimensionObserver); }
    public void registerDimensionObservers(DimensionComponent... dimensionObservers) { this.dimensionsSubscriber.registerSubscribed(dimensionObservers); }
    public void registerDimensionObservers(Collection<DimensionComponent> dimensionObservers) { this.dimensionsSubscriber.registerSubscribed(dimensionObservers); }
    public void unregisterDimensionObserver(DimensionComponent dimensionObserver) { this.dimensionsSubscriber.unregisterSubscribed(dimensionObserver); }
    public void unregisterDimensionObservers(DimensionComponent... dimensionObservers) { this.dimensionsSubscriber.unregisterSubscribed(dimensionObservers); }
    public void unregisterDimensionObservers(Collection<DimensionComponent> dimensionObservers) { this.dimensionsSubscriber.unregisterSubscribed(dimensionObservers); }


    static abstract class Builder<T extends Builder<T>> {

        public Builder() {

        }
        public void validate() {

        }

        public abstract DimensionComponent build();
    }
}
