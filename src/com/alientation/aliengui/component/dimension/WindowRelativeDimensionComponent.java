package com.alientation.aliengui.component.dimension;

import com.alientation.aliengui.api.view.window.Window;
import com.alientation.aliengui.component.Observer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;

@SuppressWarnings("unused")
public class WindowRelativeDimensionComponent extends DimensionComponent {
    //who this WindowRelativeDimensionComponent is relative to
    protected Window relTo;
    //the relativity value to the relTo window
    protected float relVal;
    //whether to multiply or add the relVal and the relTo dimension of the window
    protected boolean multiplied;
    //accesses the proper dimension of the relTo window
    protected WindowDimensionRelation windowDimensionRelation;

    //Dimensions that are added to the value of this base dimension value
    protected Observer<WindowRelativeDimensionComponent,DimensionComponent> addedDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObservers(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObservers(parent); }
    };
    //Dimensions that are subtracted from the value of this base dimension value
    protected Observer<WindowRelativeDimensionComponent,DimensionComponent> subtractedDimensions = new Observer<>(this) {
        @Override
        public void notifyObservers() { parent.notifySubscribers(); }
        @Override
        public void unregister(DimensionComponent observed) { observed.unregisterDimensionObservers(parent); }
        @Override
        public void register(DimensionComponent observed) { observed.registerDimensionObservers(parent); }
    };

    protected ComponentAdapter componentListener = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            notifySubscribers();
        }
    };


    /**
     * Builds a DimensionComponent
     *
     * @param builder Builder
     */
    protected WindowRelativeDimensionComponent(Builder<?> builder) {
        super(builder);
        relTo = builder.relTo;
        relVal = builder.relVal;
        multiplied = builder.multiplied;
        windowDimensionRelation = builder.windowDimensionRelation;

        relTo.addComponentListener(componentListener);
        notifySubscribers();
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
        val = Math.round((multiplied ? windowDimensionRelation.getDimensionValue(relTo) * relVal : windowDimensionRelation.getDimensionValue(relTo) + relVal)
                + addedValue - subtractedValue);

        //super call - notifies any observing dimensions/views that this object has changed state
        super.notifySubscribers();
    }


    //SETTERS

    public void setRelTo(Window relTo) {
        this.relTo.removeComponentListener(componentListener);
        this.relTo = relTo;
        this.relTo.addComponentListener(componentListener);
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

    public void setDimensionRelation(WindowDimensionRelation windowDimensionRelation) {
        this.windowDimensionRelation = windowDimensionRelation;
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



    @SuppressWarnings("unused")
    public static abstract class WindowDimensionRelation {
        public static final WindowDimensionRelation LEFT_X = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getX();
            }
        };
        public static final WindowDimensionRelation RIGHT_X = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getX() + window.getWidth();
            }
        };
        public static final WindowDimensionRelation X = LEFT_X;
        public static final WindowDimensionRelation TOP_Y = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getY();
            }
        };
        public static final WindowDimensionRelation BOTTOM_Y = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getY() + window.getHeight();
            }
        };
        public static final WindowDimensionRelation Y = TOP_Y;
        public static final WindowDimensionRelation WIDTH = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getWidth();
            }
        };
        public static final WindowDimensionRelation HEIGHT = new WindowDimensionRelation() {
            @Override
            public int getDimensionValue(Window window) {
                return window.getHeight();
            }
        };

        public abstract int getDimensionValue(Window window);
    }

    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends DimensionComponent.Builder<T> {
        protected Window relTo;
        protected float relVal = 1f;
        protected boolean multiplied = true;
        protected WindowDimensionRelation windowDimensionRelation;

        public T relTo(Window relTo) {
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
        public T windowDimensionRelation(WindowDimensionRelation windowDimensionRelation) {
            this.windowDimensionRelation = windowDimensionRelation;
            return (T) this;
        }

        @Override
        public void validate() {
            super.validate();
            if (relTo == null) throw new IllegalStateException("relTo cannot be null");
            if (windowDimensionRelation == null) throw new IllegalStateException("windowDimensionRelation cannot be null");
        }

        @Override
        public WindowRelativeDimensionComponent build() {
            validate();
            return new WindowRelativeDimensionComponent(this);
        }
    }

}
