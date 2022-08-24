package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.event.view.ViewEvent;

import java.util.List;

@SuppressWarnings("unused")
public abstract class DimensionComponent extends Component {

    protected int val;
    protected DimensionComponent min, max;
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
        notifySubscribers();
    }

    public void setMin(DimensionComponent min) {
        this.min = min;
        notifySubscribers();
    }

    public void setMax(DimensionComponent max) {
        this.max = max;
        notifySubscribers();
    }

    /**
     * Dispatches event to registered subscribers
     */
    public void notifySubscribers() {
        for (View view : subscribers)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }
}
