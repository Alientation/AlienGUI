package com.alientation.aliengui.api.view;

import com.alientation.aliengui.component.text.TextComponent;

import java.awt.*;

@SuppressWarnings("unused")
public class TextLabelView extends View {

    protected TextComponent textLabel;

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public TextLabelView(Builder<?> builder) {
        super(builder);
    }


    @Override
    public void render(Graphics g) {
        super.render(g);

        if (textLabel != null)
            g.drawImage(textLabel.draw(this,g),x(),y(),null);
    }

    @Override
    public void tick() {
        super.tick();
    }


    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected TextComponent textLabel;
        public Builder() {

        }

        public T textLabel(TextComponent textLabel) {
            this.textLabel = textLabel;
            return (T) this;
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
    @SuppressWarnings("unchecked")
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