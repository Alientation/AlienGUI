package com.alientation.aliengui.component.text;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class TextComponent extends Component {

    enum TextUpdateState {
        DYNAMIC_WRAPPING_WORDS, //Whether to dynamically wrap text
        DYNAMIC_WRAPPING_CHARACTERS, //Whether dynamic wrapping will wrap characters around
        DYNAMIC_RESIZING //Whether to dynamically resize text if dynamic wrapping is disabled
    }

    //Text to be displayed by lines
    protected List<AttributedString> linedText;

    //Alignment of each line
    //protected List<TextAlignment> alignments; TODO determine whether to have line specific text alignment (if so, rework vertical alignment)

    //Alignment of this text component
    protected TextAlignment textAlignment;

    //How the text should be measured
    protected FontRenderContext fontRenderContext = new FontRenderContext(null, false, false);


    //Text rendering state
    protected TextUpdateState textUpdateState = TextUpdateState.DYNAMIC_WRAPPING_WORDS;

    /**
     * Constructs a multi lined Text Component
     *
     * @param text  Text lines
     */
    public TextComponent(TextUpdateState textUpdateState, FontRenderContext fontRenderContext, AttributedString... text) {
        this(text);
        this.fontRenderContext = fontRenderContext;
        this.textUpdateState = textUpdateState;
    }

    public TextComponent(TextUpdateState textUpdateState, AttributedString... text) {
        this(text);
        this.textUpdateState = textUpdateState;
    }

    public TextComponent(AttributedString... text) {
        linedText = new ArrayList<>(Arrays.stream(text).toList());
    }

    public TextComponent(TextUpdateState textUpdateState, String... text) {
        this(text);
        this.textUpdateState = textUpdateState;
    }

    public TextComponent(String... text) {
        linedText = new ArrayList<>();
        for (String s : text)
            linedText.add(new AttributedString(s));
    }

    /**
     * Constructs a BufferedImage view of the text
     *
     * @param view  View to display the text in
     * @param g     The Graphics context
     * @return      BufferedImage view of the text
     */
    public BufferedImage draw(View view, Graphics g) {
        BufferedImage image = new BufferedImage(view.width(),view.height(),BufferedImage.TYPE_INT_ARGB);

        List<AttributedString> textToDisplay = new ArrayList<>();
        switch (textUpdateState) {
            case DYNAMIC_WRAPPING_WORDS, DYNAMIC_WRAPPING_CHARACTERS -> textToDisplay = wrappedLines(view.safeWidth());
            case DYNAMIC_RESIZING -> textToDisplay = resizedLines(view.safeHeight(),view.safeWidth());
        }

        //TODO implement custom y and x starting locations
        int yPosition = view.absSafeY();
        for (AttributedString attributedString : textToDisplay) {
            TextLayout textLayout = new TextLayout(attributedString.getIterator(),fontRenderContext);
            yPosition += (int) textLayout.getAscent();
            g.drawString(attributedString.getIterator(),view.absSafeX(),yPosition);
            yPosition += textLayout.getDescent() + textLayout.getLeading();
        }

        return image;
    }

    //https://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java
    private List<AttributedString> wrappedLines(int... maxWidths) {
        //update affineTransform of the fontRenderContext
        fontRenderContext = new FontRenderContext(null, false, false);

        List<AttributedString> wrappedString = new ArrayList<>();

        for (int realLine = 0; realLine < linedText.size(); realLine++)
            wrappedString.addAll(wrappedLine(linedText.get(realLine),maxWidths[Math.min(realLine,maxWidths.length-1)]));

        return wrappedString;
    }

    private List<AttributedString> wrappedLine(AttributedString attributedString, int maxWidth) {
        //final result
        List<AttributedString> wrappedString = new ArrayList<>();

        //used for the line breaker
        AttributedCharacterIterator attributedCharacterIterator = attributedString.getIterator();
        int start = attributedCharacterIterator.getBeginIndex();
        int end = attributedCharacterIterator.getEndIndex();

        //breaking the text into widths less than the maxWidth
        LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedCharacterIterator, fontRenderContext);

        //iterator for collecting the chars of each line
        AttributedCharacterIterator characterCollector = attributedString.getIterator();

        while (lineBreakMeasurer.getPosition() < end) { //while current attributed char is still within the attributed string
            TextLayout textLayout = lineBreakMeasurer.nextLayout(maxWidth);

            wrappedString.add(new AttributedString(characterCollector, characterCollector.getIndex(), lineBreakMeasurer.getPosition()));
        }

        return wrappedString;
    }

    private List<AttributedString> resizedLines(int maxHeight, int maxWidth) {
        List<AttributedString> resizedString = new ArrayList<>();

        //find the current minimum width and height of the current text's render
        int currentWidth = 0;
        int currentHeight = 0;

        for (AttributedString attributedString : linedText) {
            TextLayout textLayout = new TextLayout(attributedString.getIterator(),fontRenderContext);

            currentHeight += textLayout.getAscent();
            currentHeight += textLayout.getDescent() + textLayout.getLeading();

            currentWidth = Math.max(currentWidth,(int) textLayout.getAdvance());
        }

        //find the scale factor to resize the text's font to fit in the given bounds


        //update render technique (affine transform)


        return resizedString;
    }

    private List<AttributedString> resizedLines(Collection<Integer> maxHeights, Collection<Integer> maxWidths) {
        return null;
    }

    private List<AttributedString> resizedLines(int[] maxHeights, int[] maxWidths) {
        return null;
    }


    //SETTERS

    public void setStringLine(String line, int index) {
        setAttributedStringLine(new AttributedString(line), index);
    }

    public void setAttributedStringLine(AttributedString line, int index) {
        while (linedText.size() < index)
            linedText.add(new AttributedString(""));
        linedText.add(index,line);
        notifySubscribers();
    }

    public void addStringLine(String line) {
        setStringLine(line,linedText.size());
    }

    public void addAttributedStringLine(AttributedString line) {
        setAttributedStringLine(line, linedText.size());

    }

    public void addStringLines(String... lines) {
        for (String line : lines) addStringLine(line);
    }

    public void addStringLines(Collection<String> lines) {
        for (String line : lines) addStringLines(line);
    }

    public void addAttributedStringLines(AttributedString... lines) {
        for (AttributedString line : lines) addAttributedStringLine(line);
    }

    public void addAttributedStringLines(Collection<AttributedString> lines) {
        for (AttributedString line : lines) addAttributedStringLine(line);
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

    public void clearLines() {
        linedText.clear();
        notifySubscribers();
    }

    public void clearLines(int start) {
        linedText.subList(start,linedText.size()).clear();
    }

    public void clearLines(int start, int end) {
        linedText.subList(start,end).clear();
    }

    public void setLinedText(Collection<AttributedString> linedText) {
        this.linedText = new ArrayList<>(linedText);
        notifySubscribers();
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
        notifySubscribers();
    }

    public void setFontRenderContext(FontRenderContext fontRenderContext) {
        this.fontRenderContext = fontRenderContext;
        notifySubscribers();
    }

    public void setTextUpdateState(TextUpdateState textUpdateState) {
        this.textUpdateState = textUpdateState;
        notifySubscribers();
    }


    //GETTERS

    public List<AttributedString> getAttributedStringLinedText() { return new ArrayList<>(linedText); }
    public List<String> getStringLinedText() {
        List<String> stringLinedText = new ArrayList<>();
        for (int i = 0; i < linedText.size(); i++) stringLinedText.add(getStringLine(i));
        return stringLinedText;
    }
    public int getNumberLines() { return linedText.size(); }
    public AttributedString getAttributedStringLine(int index) { return linedText.get(index); }

    public String getStringLine(int index) {
        AttributedCharacterIterator it = linedText.get(index).getIterator();
        StringBuilder sb = new StringBuilder();

        char ch  = it.current();
        while (ch != CharacterIterator.DONE) {
            sb.append(ch);
            ch = it.next();
        }

        return sb.toString();
    }

    public TextAlignment getTextAlignment() { return textAlignment; }
    public FontRenderContext getFontRenderContext() { return fontRenderContext; }
    public TextUpdateState getTextUpdateState() { return textUpdateState; }


    @Override
    public void notifySubscribers() {
        super.notifySubscribers();
    }
}
