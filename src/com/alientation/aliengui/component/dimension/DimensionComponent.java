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
    protected Set<View> dependencies = new HashSet<>();
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

    public void valueChanged() {
        for (View view : dependencies)
            //view.dimensionChanged(this); TODO determine whether to offload logic to the view
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }

    public void registerDependency(View v) {
        dependencies.add(v);
    }
    public void unregisterDependency(View v) {
        dependencies.remove(v);
    }
    public List<View> getDependencies() { return dependencies.stream().toList(); }
}
