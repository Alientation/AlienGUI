package com.alientation.aliengui.component.text;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.component.Component;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
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

    /**
     * The update method of this text component
     */
    enum TextUpdateState {
        DYNAMIC_WRAPPING_WORDS, //Whether to dynamically wrap text
        DYNAMIC_WRAPPING_CHARACTERS, //Whether dynamic wrapping will wrap characters around
        DYNAMIC_RESIZING, //Whether to dynamically resize text if dynamic wrapping is disabled
        NONE
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
     * Constructs a TextComponent object
     *
     * @param textUpdateState       TextUpdateState of how to resize text
     * @param fontRenderContext     FontRenderContext of how to render the text's font
     * @param text                  Text to be displayed by this text component object
     */
    public TextComponent(TextUpdateState textUpdateState, FontRenderContext fontRenderContext, AttributedString... text) {
        this(text);
        this.fontRenderContext = fontRenderContext;
        this.textUpdateState = textUpdateState;
    }

    /**
     * Constructs a TextComponent object with a base FontRenderContext with no effects on the text's render
     *
     * @param textUpdateState   TextUpdateState of how to resize text
     * @param text              Text to be displayed by this text component object
     */
    public TextComponent(TextUpdateState textUpdateState, AttributedString... text) {
        this(text);
        this.textUpdateState = textUpdateState;
    }

    /**
     * Constructs a TextComponent object with a base FontRenderContext with no effects on the text's render
     * and a default dynamic word wrapping TextUpdateState
     *
     * @param text  Text to be displayed by this text component object
     */
    public TextComponent(AttributedString... text) {
        linedText = new ArrayList<>(Arrays.stream(text).toList());
    }

    /**
     * Constructs a TextComponent object with a base FontRenderContext with no effects on the text's render
     *
     * @param textUpdateState   TextUpdateState of how to resize text
     * @param text              Text to be displayed by this text component object
     */
    public TextComponent(TextUpdateState textUpdateState, String... text) {
        this(text);
        this.textUpdateState = textUpdateState;
    }

    /**
     * Constructs a TextComponent object with a base FontRenderContext with no effects on the text's render
     * and a default dynamic word wrapping TextUpdateState
     *
     * @param text  Text to be displayed by this text component object
     */
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

        List<AttributedString> textToDisplay = linedText;
        FontRenderContext fontRenderContext = this.fontRenderContext;
        switch (textUpdateState) {
            case DYNAMIC_WRAPPING_WORDS, DYNAMIC_WRAPPING_CHARACTERS -> textToDisplay = wrappedLines(view.safeWidth());
            case DYNAMIC_RESIZING -> fontRenderContext = resizedLines(view.safeWidth(), view.safeHeight());
            //TODO dynamic resizing for x, y, and both (more options)
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

    /**
     * Helper to wrap lines
     *
     * @param maxWidths MaxWidths of each line
     * @return          The new linedText
     */
    private List<AttributedString> wrappedLines(int... maxWidths) {
        List<AttributedString> wrappedString = new ArrayList<>();

        //loop through and wrap each lines with a corresponding maxWidth
        //if the next maxWidth does not exist (int the parameter maxWidths array), use the previous maxWidth
        for (int realLine = 0; realLine < linedText.size(); realLine++)
            wrappedString.addAll(wrappedLine(linedText.get(realLine),maxWidths[Math.min(realLine,maxWidths.length-1)]));

        return wrappedString;
    }

    /**
     * Helper to wrap lines
     *
     * @param attributedString  Original text
     * @param maxWidth          Max width of this line
     * @return                  The new linedText for this text
     */
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
            //get the next slotted text within the maxWidth
            TextLayout textLayout = lineBreakMeasurer.nextLayout(maxWidth);

            //keep track of the new wrapped string's wrapped lines
            wrappedString.add(new AttributedString(characterCollector, characterCollector.getIndex(), lineBreakMeasurer.getPosition()));
        }

        return wrappedString;
    }

    /**
     * Constructs a scaled FontRenderContext given width and height scale factors
     *
     * @param scaleFactorWidth  Scale factor of the width
     * @param scaleFactorHeight Scale factor of the height
     * @return                  Scaled FontRenderContext
     */
    public FontRenderContext getScaledFontRenderContext(float scaleFactorWidth, float scaleFactorHeight) {
        //update render technique (affine transform)
        AffineTransform scale = AffineTransform.getScaleInstance(scaleFactorWidth,scaleFactorHeight);

        //check if the current renderContext has a transform, if so, scale this transform
        if (fontRenderContext.getTransform() != null) scale.concatenate(fontRenderContext.getTransform());

        //returns a new font render context
        return new FontRenderContext(scale, fontRenderContext.getAntiAliasingHint(),
                fontRenderContext.getFractionalMetricsHint());
    }

    /**
     * Resizes lines given the constraints
     *
     * @param maxWidth  Width constraint
     * @param maxHeight Height constraint
     * @return          Scaled FontRenderContext
     */
    private FontRenderContext resizedLines(int maxWidth, int maxHeight) {
        if (maxHeight <= 0 || maxWidth <= 0)
            throw new IllegalStateException("TextComponent::resizedLines must have valid constraints");

        //find the current minimum width and height of the current text's render
        float currentWidth = 0;
        float currentHeight = 0;

        //for each line
        for (AttributedString attributedString : linedText) {
            TextLayout textLayout = new TextLayout(attributedString.getIterator(),fontRenderContext);

            //add all height measurements
            currentHeight += textLayout.getAscent() + textLayout.getDescent() + textLayout.getLeading();

            //update the currentWidth to be the largest width
            currentWidth = Math.max(currentWidth,(int) textLayout.getAdvance());
        }

        /* find the scale factor to resize the text's font to fit in the given bounds
         *
         * currentWidth * factor <= maxWidth
         * currentHeight * factor <= maxHeight
         *
         * factor <= maxWidth / currentWidth
         * factor <= maxHeight / currentHeight
         *
         * factor Math.min(maxWidth/currentWidth,maxHeight/currentHeight)
         */
        float scaleFactor = Math.min(maxWidth/currentWidth,maxHeight/currentHeight);

        //update render technique (affine transform)
        return getScaledFontRenderContext(scaleFactor,scaleFactor);
    }

    /**
     * Resizes lines given the constraints by line
     *
     * @param maxLineWidthsCollection   Width constraint per line
     * @param maxLineHeightsCollection  Height constraint per line
     * @return                          Scaled FontRenderContext
     */
    private FontRenderContext resizedLines(Collection<Integer> maxLineWidthsCollection, Collection<Integer> maxLineHeightsCollection) {
        //TODO determine whether to keep these state checkers for easier debugging for the user
        //sanity checks to help user debug
        if (maxLineHeightsCollection == null || maxLineHeightsCollection.size() == 0)
            throw new IllegalStateException("TextComponent::resizedLines maxLineHeightsCollection collection must be initialized with values");
        if (maxLineWidthsCollection == null || maxLineWidthsCollection.size() == 0)
            throw new IllegalStateException("TextComponent::resizedLines maxLineWidthsCollection collection must be initialized with values");

        //convert to arraylist
        ArrayList<Integer> maxLineHeights = new ArrayList<>(maxLineHeightsCollection);
        ArrayList<Integer> maxLineWidths = new ArrayList<>(maxLineWidthsCollection);

        //find the min scale factor
        float minScaleFactor = 1f;
        int maxHeightIndex = 0, maxWidthIndex = 0; //indexes of the maxHeight and maxWidth in the arraylist collection
        for (AttributedString attributedString : linedText) {
            TextLayout textLayout = new TextLayout(attributedString.getIterator(),fontRenderContext);

            //help visualize the math
            int maxWidth = maxLineWidths.get(maxWidthIndex);
            int maxHeight = maxLineHeights.get(maxHeightIndex);
            float currentWidth = textLayout.getAdvance();
            float currentHeight = textLayout.getAscent() + textLayout.getDescent() + textLayout.getLeading();

            //current scale factor required to resize
            float scaleFactor = Math.min(maxWidth/currentWidth,maxHeight/currentHeight);

            //find the smallest scale factor needed to resize this linedText
            minScaleFactor = Math.min(scaleFactor,minScaleFactor);

            //increment maxHeight and maxWidth indexes if possible
            if (maxHeightIndex < maxLineHeights.size() - 1) maxHeightIndex++;
            if (maxWidthIndex < maxLineWidths.size() - 1) maxWidthIndex++;
        }

        //update render technique (affine transform)
        return getScaledFontRenderContext(minScaleFactor,minScaleFactor);
    }

    /**
     * Resizes lines given the constraints
     *
     * @param maxLineWidths     Width constraint per line
     * @param maxLineHeights    Height constraint per line
     * @return                  Scaled FontRenderContext
     */
    private FontRenderContext resizedLines(int[] maxLineWidths, int[] maxLineHeights) {
        //TODO determine whether to keep these state checkers for easier debugging for the user
        //sanity checks to help user debug
        if (maxLineHeights == null || maxLineHeights.length == 0)
            throw new IllegalStateException("TextComponent::resizedLines maxLineHeights collection must be initialized with values");
        if (maxLineWidths == null || maxLineWidths.length == 0)
            throw new IllegalStateException("TextComponent::resizedLines maxLineWidths collection must be initialized with values");

        //Arraylist collection to pass it to the other overloaded method
        ArrayList<Integer> newMaxLineHeights = new ArrayList<>(maxLineHeights.length);
        ArrayList<Integer> newMaxLineWidths = new ArrayList<>(maxLineWidths.length);

        for (int maxLineHeight : maxLineHeights) newMaxLineHeights.add(maxLineHeight);
        for (int maxLineWidth : maxLineWidths) newMaxLineWidths.add(maxLineWidth);

        //reuse the overloaded method's code
        return resizedLines(newMaxLineWidths,newMaxLineHeights);
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
