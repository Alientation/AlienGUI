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




        return image;
    }

    //TODO determine whether to have line specific maxWidths and maxHeights -> could be useful for embedded components like images
    private List<AttributedString> wrappedLines(int... maxWidths) {
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

    private List<AttributedString> resizedLines(int maxHeights, int maxWidths) {
        List<AttributedString> resizedString = new ArrayList<>();

        //go through and resize the whole text's font so that it displays within the given bounds



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

    @Override
    public void notifySubscribers() {
        super.notifySubscribers();
    }
}
