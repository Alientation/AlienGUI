package com.alientation.aliengui.api.view;

import com.alientation.aliengui.component.text.TextAlignment;
import com.alientation.aliengui.component.text.TextComponent;
import com.alientation.aliengui.event.EventListenerContainer;
import com.alientation.aliengui.event.view.ViewEvent;
import com.alientation.aliengui.event.view.textlabel.TextLabelListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.text.AttributedString;
import java.util.Collection;
import java.util.List;


@SuppressWarnings("unused")
public class TextLabelView extends View {
    protected EventListenerContainer<TextLabelListener> textLabelListeners = new EventListenerContainer<>();

    protected TextComponent textLabel;

    /**
     * Constructs a new view using the Builder pattern
     *
     * @param builder Builder for this view
     */
    public TextLabelView(Builder<?> builder) {
        super(builder);
        textLabel = builder.textLabel;
        textLabel.registerSubscriber(this);
    }


    @Override
    public void render(Graphics g) {
        super.render(g);
        textLabel.draw(this,g);
    }

    @Override
    public void tick() {
        super.tick();
    }


    //SETTERS

    public void setTextComponent(TextComponent textLabel) {
        this.textLabel.unregisterSubscriber(this);
        this.textLabel = textLabel;
        this.textLabel.registerSubscriber(this);
        this.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(this)));
    }

    public void setStringLine(String line, int index) { textLabel.setStringLine(line,index); }
    public void setAttributedStringLine(AttributedString line, int index) { textLabel.setAttributedStringLine(line, index); }
    public void addStringLine(String line) { textLabel.addStringLine(line); }
    public void addAttributedStringLine(AttributedString line) { textLabel.addAttributedStringLine(line); }
    public void addStringLines(String... lines) { textLabel.addStringLines(lines); }
    public void addStringLines(Collection<String> lines) { textLabel.addStringLines(lines); }
    public void addAttributedStringLines(AttributedString... lines) { textLabel.addAttributedStringLines(lines); }
    public void addAttributedStringLines(Collection<AttributedString> lines) { textLabel.addAttributedStringLines(lines); }
    public void removeLine(int index) { textLabel.removeLine(index); }
    public void removeLines(int... indexes) { textLabel.removeLines(indexes); }
    public void removeLines(Collection<Integer> indexes) { textLabel.removeLines(indexes); }
    public void clearLines() { textLabel.clearLines(); }
    public void clearLines(int start) { textLabel.clearLines(start); }
    public void clearLines(int start, int end) { textLabel.clearLines(start, end); }
    public void setText(Collection<String> text) { textLabel.setLinedText(text); }
    public void setText(String... text) { textLabel.setLinedText(text); }
    public void setAttributedText(Collection<AttributedString> attributedText) { textLabel.setAttributedLinedText(attributedText); }
    public void setAttributedText(AttributedString... attributedText) { textLabel.setAttributedLinedText(attributedText); }
    public void setTextAlignment(TextAlignment textAlignment) { textLabel.setTextAlignment(textAlignment); }
    public void setTextFontRenderContext(FontRenderContext fontRenderContext) { textLabel.setFontRenderContext(fontRenderContext); }
    public void setTextUpdateState(TextComponent.TextUpdateState textUpdateState) { textLabel.setTextUpdateState(textUpdateState); }


    //GETTERS
    public EventListenerContainer<TextLabelListener> getTextLabelListeners() { return textLabelListeners; }
    public TextComponent getTextComponent() { return textLabel; }
    public FontRenderContext getTextFontRenderContext() { return textLabel.getFontRenderContext(); }
    public TextComponent.TextUpdateState getTextUpdateState() { return textLabel.getTextUpdateState(); }
    public List<String> getText() { return textLabel.getStringLinedText(); }
    public String getTextLine(int index) { return textLabel.getStringLine(index); }
    public int getLinesCount() { return textLabel.getNumberLines(); }
    public TextAlignment getTextAlignment() { return textLabel.getTextAlignment(); }




    @SuppressWarnings("unchecked")
    public static class Builder<T extends Builder<T>> extends View.Builder<T> {
        protected TextComponent textLabel = new TextComponent("");
        public Builder() {

        }

        public T textLabel(@NotNull TextComponent textLabel) {
            this.textLabel = textLabel;
            return (T) this;
        }

        public void validate() {
            super.validate();
        }

        public TextLabelView build() {
            validate();
            return new TextLabelView(this);
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

        public TextLabelView build() {
            validate();
            return new TextLabelView(this);
        }
    }
 */