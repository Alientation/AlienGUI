package com.alientation.aliengui.api.view;

import com.alientation.aliengui.component.text.TextComponent;

@SuppressWarnings("unused")
public class TextLabelView extends View {

    protected TextComponent textLabel;
    //perhaps have additional margin for text (around the sides)




    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public TextLabelView(Builder<?> builder) {
        super(builder);
    }




    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
}

/*  Builder pattern boilerplate code
    public static class Builder<T extends Builder<T>> extends TextLabelView.Builder<T> {

        public Builder() {

        }

        public void validate() {

        }

        public View build() {
            validate();
            return new View(this);
        }
    }
 */