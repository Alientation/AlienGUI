package com.alientation.aliengui.util.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewDimensionEvent;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class Dimension {

    protected int val;
    protected Dimension min, max;
    protected Set<View> dependencies = new HashSet<>();
    public Dimension() {

    }

    public int val() {
        return Math.max(min.val,Math.min(max.val,val));
    }

    public int getAbsoluteVal() {
        return val;
    }

    public Dimension getMinVal() {
        return min;
    }

    public Dimension getMaxVal() {
        return max;
    }

    public void setVal(int val) {
        this.val = val;
        valueChanged();
    }

    public void setMin(Dimension min) {
        this.min = min;
        valueChanged();
    }

    public void setMax(Dimension max) {
        this.max = max;
        valueChanged();
    }

    public void valueChanged() {
        for (View view : dependencies)
            view.getViewListeners().dispatch(listener -> listener.viewDimensionChanged(new ViewDimensionEvent(view,this)));
    }
    public void registerDependency(View v) { //Views that use this as a dimension
        dependencies.add(v);
    }
    public void unregisterDependency(View v) {
        dependencies.remove(v);
    }

    public Set<View> getDependencies() {
        return dependencies;
    }
}
