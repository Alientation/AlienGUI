package com.alientation.aliengui.component.dimension;


import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Observer;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class RelativeDimensionComponent extends DimensionComponent {
    protected View relTo;
    protected float relVal;
    protected boolean multiplied;
    protected DimensionRelation dimensionRelation;

    protected Observer<RelativeDimensionComponent,DimensionComponent> addedDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObservers(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObservers(parent); }
    };
    protected Observer<RelativeDimensionComponent,DimensionComponent> subtractedDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObservers(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObservers(parent); }
    };

    protected ViewListener viewListener = new ViewListener() {
        @Override
        public void viewDimensionChanged(ViewDimensionEvent event) { notifySubscribers(); }
    };


    public RelativeDimensionComponent(Builder<?> builder) {
        super(builder);
        relTo = builder.relTo;
        relVal = builder.relVal;
        multiplied = builder.multiplied;
        dimensionRelation = builder.dimensionRelation;

        relTo.getViewListeners().addListenerAtBeginning(viewListener);
    }

    /**
     * Updates the value of this dimension because it has observed a change in state in one of its dependencies
     */
    @Override
    public void notifySubscribers() {
        float addedValue = 0f; //adds all the dimension values in the addedDimensions list
        for (DimensionComponent dimensionComponent : addedDimensions.getObserved())
            addedValue += dimensionComponent.val();

        float subtractedValue = 0f;//adds all the dimension values in the subtractedDimensions list
        for (DimensionComponent dimensionComponent : subtractedDimensions.getObserved())
            subtractedValue += dimensionComponent.val();

        //Calculates the value of this dimension
        val = Math.round((multiplied ? dimensionRelation.getDimension(relTo).val * relVal : dimensionRelation.getDimension(relTo).val + relVal)
                + addedValue - subtractedValue);

        //super call - notifies any observing dimensions/views that this object has changed state
        super.notifySubscribers();
    }

    public void setRelTo(View relTo) {
        this.relTo.getViewListeners().removeListener(viewListener);
        this.relTo = relTo;
        this.relTo.getViewListeners().addListener(viewListener);
        notifySubscribers();
    }

    public void setRelVal(float relVal) {
        this.relVal = relVal;
        notifySubscribers();
    }

    public void setMultiplied(boolean multiplied) {
        this.multiplied = multiplied;
        notifySubscribers();
    }

    public void setDimensionRelation(DimensionRelation dimensionRelation) {
        this.dimensionRelation = dimensionRelation;
        notifySubscribers();
    }
    public void setAddedDimension(Collection<DimensionComponent> addedDimensions) { this.addedDimensions.setObserved(addedDimensions); }
    public void addAddedDimensions(Collection<DimensionComponent> addedDimensions) { this.addedDimensions.registerObserved(addedDimensions); }
    public void addAddedDimensions(DimensionComponent... addedDimensions) { this.addedDimensions.registerObserved(addedDimensions); }
    public void addAddedDimension(DimensionComponent addedDimension) { this.addedDimensions.registerObserved(addedDimension); }
    public void setSubtractedDimensions(Collection<DimensionComponent> subtractedDimensions) { this.subtractedDimensions.setObserved(subtractedDimensions); }
    public void addSubtractedDimensions(Collection<DimensionComponent> subtractedDimensions) { this.subtractedDimensions.registerObserved(subtractedDimensions); }
    public void addSubtractedDimensions(DimensionComponent... subtractedDimensions) { this.subtractedDimensions.registerObserved(subtractedDimensions); }
    public void addSubtractedDimension(DimensionComponent subtractedDimension) { this.subtractedDimensions.registerObserved(subtractedDimension); }

    public View getRelTo() { return relTo; }
    public float getRelVal() { return relVal; }
    public boolean isMultiplied() { return multiplied; }
    public DimensionRelation getDimensionRelation() { return dimensionRelation; }
    public List<DimensionComponent> getAddedDimensions() { return new ArrayList<>(addedDimensions.getObserved()); }
    public List<DimensionComponent> getSubtractedDimensions() { return new ArrayList<>(subtractedDimensions.getObserved()); }
    public Observer<RelativeDimensionComponent,DimensionComponent> getAddedDimensionsObserver() { return addedDimensions; }
    public Observer<RelativeDimensionComponent,DimensionComponent> getSubtractedDimensionsObserver() { return subtractedDimensions; }

    @SuppressWarnings("unchecked")
    static class Builder<T extends Builder<T>> extends DimensionComponent.Builder<T> {
        protected View relTo;
        protected float relVal = 1f;
        protected boolean multiplied = true;
        protected DimensionRelation dimensionRelation;

        public Builder() {

        }

        public T relTo(View relTo) {
            this.relTo = relTo;
            return (T) this;
        }

        public T relVal(float relVal) {
            this.relVal = relVal;
            return (T) this;
        }

        public T multiplied(boolean multiplied) {
            this.multiplied = multiplied;
            return (T) this;
        }

        public T dimensionRelation(DimensionRelation dimensionRelation) {
            this.dimensionRelation = dimensionRelation;
            return (T) this;
        }
        @Override
        public void validate() {
            super.validate();
            if (relTo == null) throw new IllegalStateException("relTo cannot be null");
            if (dimensionRelation == null) throw new IllegalStateException("dimensionRelation cannot be null");
        }

        @Override
        public RelativeDimensionComponent build() {
            return new RelativeDimensionComponent(this);
        }
    }

}

@SuppressWarnings("unused")
abstract class DimensionRelation {
    public static final DimensionRelation LEFT_X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getX();
        }
    };

    public static final DimensionRelation RIGHT_X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getX();
        }
    };

    public static final DimensionRelation TOP_Y = new DimensionRelation() {
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
    public static final DimensionRelation BORDER_RADIUS_WIDTH = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusWidth();
        }
    };

    public static final DimensionRelation BORDER_RADIUS_HEIGHT = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusHeight();
        }
    };

    public static final DimensionRelation BORDER_RADIUS_X = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusWidth();
        }
    };

    public static final DimensionRelation BORDER_RADIUS_Y = new DimensionRelation() {
        @Override
        public DimensionComponent getDimension(View view) {
            return view.getBorderRadiusHeight();
        }
    };

    //TODO stack spacing etc

    public DimensionRelation() {

    }

    public abstract DimensionComponent getDimension(View view);
}