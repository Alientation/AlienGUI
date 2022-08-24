package com.alientation.aliengui.component.text;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;
import com.alientation.aliengui.event.view.ViewEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class TextComponent extends Component {

    /**
     * Text to be displayed by lines
     */
    protected List<String> linedText;

    /**
     * Default font to be used for the text
     */
    protected Font font;

    /**
     * Fonts rules for each character in each line
     *
     * Essentially, if there is a font registered for the current character, change the drawn font to it and
     * continue drawing. If there is no font registered for the current character, continue with the current font
     */
    protected List<List<Font>> textFont;

    /**
     * Whether to dynamically wrap text
     */
    protected boolean dynamicWrapping = true;

    /**
     * Whether dynamic wrapping will wrap characters around
     */
    protected boolean wrappingOfCharacters = false;

    /**
     * Whether to dynamically resize text if dynamic wrapping is disabled
     */
    protected boolean dynamicResizing = true;

    public TextComponent(String... text) {
        linedText = new ArrayList<>(Arrays.stream(text).toList());
    }

    /**
     * Constructs a BufferedImage view of the text
     *
     * @param view  View to display the text in
     * @return      BufferedImage view of the text
     */
    public BufferedImage draw(View view) {
        BufferedImage image = new BufferedImage(view.width(),view.height(),BufferedImage.TYPE_INT_ARGB);


        return image;
    }



    public void addLine(String line, int index) {
        while (linedText.size() < index)
            linedText.add("");
        linedText.add(index,line);
        notifySubscribers();
    }
    public void addLine(String line) {
        addLine(line,linedText.size());
    }
    public void addLines(String... lines) {
        for (String line : lines) addLine(line);
    }
    public void removeLine(int index) {
        linedText.remove(index);
        notifySubscribers();
    }
    public void removeLines(int... indexes) {
        int shift = 0;
        for (int index : indexes) {
            removeLine(index + shift);
            shift--;
        }
    }

    public void setLinedText(List<String> linedText) {
        this.linedText = linedText;
        notifySubscribers();
    }

    public List<String> getLinedText() { return new ArrayList<>(linedText); }
    public int getNumberLines() { return linedText.size(); }
    public String getLine(int index) { return linedText.get(index); }

    @Override
    public void notifySubscribers() {
        for (View view : subscribers)
            view.getViewListeners().dispatch(listener -> listener.viewStateChanged(new ViewEvent(view)));
    }
}
