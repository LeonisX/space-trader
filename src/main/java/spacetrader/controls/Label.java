package spacetrader.controls;

import javax.swing.*;
import java.awt.*;

public class Label extends BaseComponent {

    private static final String NEWLINE_LITERAL = String.format("%n");
    private static final String END = "</HTML>";
    private static final String START = "<HTML>";
    private static final String NEWLINE = "<BR>";

    private boolean convertedToHtml;
    private int linesCount = -1;
    private int maxLineWidth = -1;


    public Label() {
        this(new JLabel());
    }

    public Label(String text) {
        this();
        asJLabel().setText(text);
    }

    public Label(JLabel label) {
        super(label);
        asJLabel().putClientProperty("baseComponent", this);
    }

    public String getText() {
        String text = asJLabel().getText();

        if (convertedToHtml) {
            text = text.substring(START.length(), text.length() - END.length());
            text = text.replaceAll(NEWLINE, NEWLINE_LITERAL);
        }

        return text;
    }

    public void setText(String text) {
        if (text.length() > 15) {
            convertedToHtml = true;
            text = START + text.replaceAll(NEWLINE_LITERAL + "\\s*", NEWLINE) + END;
        } else {
            convertedToHtml = false;
        }
        asJLabel().setText(text);
        Graphics.resizeIfNeed(swingComponent, isAutoSize(), isAutoWidth(), isAutoHeight(), getControlBinding());
    }

    public void setText(int number) {
        this.setText(Integer.toString(number));
    }

    private void measureText() {
        FontMetrics metrics = asJLabel().getFontMetrics(asJLabel().getFont());
        String labelText = getText().replace(NEWLINE_LITERAL, " " + NEWLINE_LITERAL + " ").replace("  ",  " ");
        String[] chunks = labelText.split(" ");
        linesCount = 1;
        maxLineWidth = 0;
        int textWidth = 0;
        String text = "";
        for (String chunk: chunks) {
            boolean newLine;
            if (chunk.equals(NEWLINE_LITERAL)) {
                newLine = true;
            } else {
                text += " " + chunk;
                textWidth = metrics.stringWidth(text);
                newLine = textWidth > getWidth();
            }
            if (newLine) {
                linesCount++;
                maxLineWidth = Math.max(maxLineWidth, textWidth);
                text = chunk;
            }
        }
        maxLineWidth = Math.max(maxLineWidth, textWidth);
    }

    public int getLinesCount() {
        if (linesCount < 0) {
            measureText();
        }
        return linesCount;
    }

    public int getMaxLineWidth() {
        if (maxLineWidth < 0) {
            measureText();
        }
        return maxLineWidth;
    }

    public Label withAutoSize(boolean autoSize) {
        setAutoSize(autoSize);
        return this;
    }

    @Override
    public void setBackground(Color background) {
        asJLabel().setOpaque(background != null);
        super.setBackground(background);
    }

    public void setImage(Image image) {
        asJLabel().setIcon(new ImageIcon(image.asSwingImage()));
    }

    JLabel asJLabel() {
        return (JLabel) swingComponent;
    }
}
