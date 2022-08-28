package com.alientation.aliengui.component.dimension;


import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.view.ViewDimensionEvent;
import com.alientation.aliengui.event.view.ViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class RelativeDimensionComponent extends DimensionComponent {
    protected View relTo;
    protected float relVal;
    protected boolean multiplied;
    protected DimensionRelation dimensionRelation;

    protected List<DimensionComponent> addedDimensions; //TODO implement these with val()
    protected List<DimensionComponent> subtractedDimensions;

    protected ViewListener viewListener = new ViewListener() {
        @Override
        public void viewDimensionChanged(ViewDimensionEvent event) {
            notifySubscribers();
        }
    };


    public RelativeDimensionComponent(Builder<?> builder) {
        super(builder);
        relTo = builder.relTo;
        relVal = builder.relVal;
        multiplied = builder.multiplied;
        dimensionRelation = builder.dimensionRelation;
        addedDimensions = builder.addedDimensions;
        subtractedDimensions = builder.subtractedDimensions;

        for (DimensionComponent dimensionComponent : addedDimensions) dimensionComponent.registerDimensionObservers(this);
        for (DimensionComponent dimensionComponent : subtractedDimensions) dimensionComponent.registerDimensionObservers(this);

        relTo.getViewListeners().addListenerAtBeginning(viewListener);
    }

    @Override
    public void notifySubscribers() {
        val = Math.round(multiplied ? dimensionRelation.getDimension(relTo).val * relVal : dimensionRelation.getDimension(relTo).val + relVal);
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

    ///HOLY THIS IS SO MUCH CODE REPETITION TODO CLEAN THIS STUFF UP!!!!
    //HAVE ANOTHER CLASS SIMPLY TO STORE THE HASHSET AND HANDLE ADDING AND REMOVING, THIS CLASS CAN SIMPLY JUST BE AN ADAPTER TO THAT
    //PROBABLY GONNA NEED TO SUPPLY AN INTERFACE FOR THE ACTUAL ADD AND REMOVE STUFF
    public void setAddedDimension(Collection<DimensionComponent> addedDimensions) {
        for (DimensionComponent dimensionComponent : this.addedDimensions) dimensionComponent.unregisterDimensionObservers(this);
        this.addedDimensions = new ArrayList<>(addedDimensions);
        for (DimensionComponent dimensionComponent : this.addedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addAddedDimensions(Collection<DimensionComponent> addedDimensions) {
        this.addedDimensions.addAll(addedDimensions);
        for (DimensionComponent dimensionComponent : addedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addAddedDimensions(DimensionComponent... addedDimensions) {
        this.addedDimensions.addAll(Arrays.stream(addedDimensions).toList());
        for (DimensionComponent dimensionComponent : addedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addAddedDimension(DimensionComponent addedDimension) {
        this.addedDimensions.add(addedDimension);
        addedDimension.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void subtractedDimensions(Collection<DimensionComponent> subtractedDimensions) {
        for (DimensionComponent dimensionComponent : this.subtractedDimensions) dimensionComponent.unregisterDimensionObservers(this);
        this.subtractedDimensions = new ArrayList<>(subtractedDimensions);
        for (DimensionComponent dimensionComponent : this.subtractedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addSubtractedDimensions(Collection<DimensionComponent> subtractedDimensions) {
        this.subtractedDimensions.addAll(subtractedDimensions);
        for (DimensionComponent dimensionComponent : subtractedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addSubtractedDimensions(DimensionComponent... subtractedDimensions) {
        this.subtractedDimensions.addAll(Arrays.stream(subtractedDimensions).toList());
        for (DimensionComponent dimensionComponent : subtractedDimensions) dimensionComponent.registerDimensionObservers(this);
        notifySubscribers();
    }

    public void addSubtractedDimension(DimensionComponent subtractedDimension) {
        this.subtractedDimensions.add(subtractedDimension);
        subtractedDimension.registerDimensionObservers(this);
        notifySubscribers();
    }

    public View getRelTo() { return relTo; }
    public float getRelVal() { return relVal; }
    public boolean isMultiplied() { return multiplied; }
    public DimensionRelation getDimensionRelation() { return dimensionRelation; }
    public List<DimensionComponent> getAddedDimensions() { return new ArrayList<>(addedDimensions); }
    public List<DimensionComponent> getSubtractedDimensions() { return new ArrayList<>(subtractedDimensions); }

    @SuppressWarnings("unchecked")
    static class Builder<T extends Builder<T>> extends DimensionComponent.Builder<T> {
        protected View relTo;
        protected float relVal = 1f;
        protected boolean multiplied = true;
        protected DimensionRelation dimensionRelation;

        protected List<DimensionComponent> addedDimensions = new ArrayList<>();
        protected List<DimensionComponent> subtractedDimensions = new ArrayList<>();

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

        public T addedDimension(Collection<DimensionComponent> addedDimensions) {
            this.addedDimensions = new ArrayList<>(addedDimensions);
            return (T) this;
        }

        public T addAddedDimensions(Collection<DimensionComponent> addedDimensions) {
            this.addedDimensions.addAll(addedDimensions);
            return (T) this;
        }

        public T addAddedDimensions(DimensionComponent... addedDimensions) {
            this.addedDimensions.addAll(Arrays.stream(addedDimensions).toList());
            return (T) this;
        }

        public T addAddedDimension(DimensionComponent addedDimension) {
            this.addedDimensions.add(addedDimension);
            return (T) this;
        }

        public T subtractedDimensions(Collection<DimensionComponent> addedDimensions) {
            this.subtractedDimensions = new ArrayList<>(addedDimensions);
            return (T) this;
        }

        public T addSubtractedDimensions(Collection<DimensionComponent> subtractedDimensions) {
            this.subtractedDimensions.addAll(subtractedDimensions);
            return (T) this;
        }

        public T addSubtractedDimensions(DimensionComponent... subtractedDimensions) {
            this.subtractedDimensions.addAll(Arrays.stream(subtractedDimensions).toList());
            return (T) this;
        }

        public T addSubtractedDimension(DimensionComponent subtractedDimension) {
            this.subtractedDimensions.add(subtractedDimension);
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