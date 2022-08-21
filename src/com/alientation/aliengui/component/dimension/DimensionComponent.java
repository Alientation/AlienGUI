package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;

import java.util.HashSet;
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
            view.dimensionChanged(this);
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
