package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class DimensionComponent {

    protected int val;
    protected DimensionComponent min, max;
    protected Set<View> subscribers = new HashSet<>();
    public DimensionComponent() {

    }

    public int val() {
        return Math.max(min.val,Math.min(max.val,val));
    }

    public int getAbsoluteVal() {
        return val;
    }

    public DimensionComponent getMinVal() {
        return min;
    }

    public DimensionComponent getMaxVal() {
        return max;
    }

    public void setVal(int val) {
        this.val = val;
        valueChanged();
    }

    public void setMin(DimensionComponent min) {
        this.min = min;
        valueChanged();
    }

    public void setMax(DimensionComponent max) {
        this.max = max;
        valueChanged();
    }

    public void valueChanged() { //TODO determine whether to create Observer interface and Subscriber interface for more structured format of these Observer patterns across this library
        for (View view : subscribers)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }

    public void registerSubscriber(View subscriber) {
        subscribers.add(subscriber);
    }
    public void unregisterSubscriber(View subscriber) {
        subscribers.remove(subscriber);
    }
    public List<View> getSubscribers() { return subscribers.stream().toList(); }
}
