package com.alientation.aliengui.component.dimension;


import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewListener;

@SuppressWarnings("unused")
public class RelativeDimensionComponent extends DimensionComponent {
    protected View relTo;
    protected float relVal;
    protected boolean multiplied = true;
    protected DimensionRelation dimensionRelation;

    protected ViewListener viewListener = new ViewListener() {
        @Override
        public void viewDimensionChanged(ViewDimensionEvent event) {
            valueChanged();
        }
    };


    public RelativeDimensionComponent(View relTo, float relVal, DimensionRelation dimensionRelation) {
        this.relTo = relTo;
        this.relVal = relVal;
        this.dimensionRelation = dimensionRelation;

        this.relTo.getViewListeners().addListenerAtBeginning(viewListener);
    }

    public RelativeDimensionComponent(View relTo, float relVal, boolean multiplied, DimensionRelation dimensionRelation) {
        this(relTo,relVal,dimensionRelation);
        this.multiplied = multiplied;
    }

    @Override
    public void valueChanged() {
        val = Math.round(multiplied ? dimensionRelation.getDimension(relTo).val * relVal : dimensionRelation.getDimension(relTo).val + relVal);
        super.valueChanged();
    }

    public void setRelTo(View relTo) {
        this.relTo.getViewListeners().removeListener(viewListener);
        this.relTo = relTo;
        this.relTo.getViewListeners().addListener(viewListener);
        valueChanged();
    }

    public void setRelVal(float relVal) {
        this.relVal = relVal;
        valueChanged();
    }

    public void setMultiplied(boolean multiplied) {
        this.multiplied = multiplied;
        valueChanged();
    }

    public void setDimensionRelation(DimensionRelation dimensionRelation) {
        this.dimensionRelation = dimensionRelation;
        valueChanged();
    }

    public View getRelTo() {
        return relTo;
    }

    public float getRelVal() {
        return relVal;
    }

    public boolean isMultiplied() {
        return multiplied;
    }

    public DimensionRelation getDimensionRelation() {
        return dimensionRelation;
    }
}

abstract class DimensionRelation {
    public static final DimensionRelation X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getX();
        }
    };

    public static final DimensionRelation Y = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getY();
        }
    };

    public static final DimensionRelation WIDTH = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getWidth();
        }
    };

    public static final DimensionRelation HEIGHT = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getHeight();
        }
    };

    public static final DimensionRelation MARGIN_X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getMarginX();
        }
    };

    public static final DimensionRelation MARGIN_Y = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getMarginY();
        }
    };

    public static final DimensionRelation BORDER_RADIUS_X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusX();
        }
    };

    public static final DimensionRelation BORDER_RADIUS_Y = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusY();
        }
    };

    //TODO stack spacing etc

    public DimensionRelation() {

    }

    public abstract DimensionComponent getDimension(View view);
}