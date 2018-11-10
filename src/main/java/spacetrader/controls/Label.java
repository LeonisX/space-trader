package spacetrader.controls;

import javax.swing.*;
import java.awt.*;

public class Label extends BaseComponent {

    private static final String NEWLINE_LITERAL = "\n";
    private static final String END = "</HTML>";
    private static final String START = "<HTML>";
    private static final String NEWLINE = "<br>";

    private boolean convertedToHtml;
    private int linesCount;
    private int maxLineWidth;


    public Label() {
        super(new JLabel());
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

        measureText();
    }

    public void setText(int number) {
        this.setText(Integer.toString(number));
    }

    private void measureText() {
        FontMetrics metrics = asJLabel().getFontMetrics(asJLabel().getFont());
        String[] chunks = getText().split(" ");
        linesCount = 1;
        maxLineWidth = 0;
        int textWidth = 0;
        String text = "";
        for (String chunk: chunks) {
            text += " " + chunk;
            textWidth = metrics.stringWidth(text);
            if (textWidth > getWidth()) {
                linesCount++;
                maxLineWidth = Math.max(maxLineWidth, textWidth);
                text = chunk;
            }
        }
        maxLineWidth = Math.max(maxLineWidth, textWidth);
    }

    public int getLinesCount() {
        return linesCount;
    }

    public int getMaxLineWidth() {
        return maxLineWidth;
    }

    @Override
    public void setBackground(Color background) {
        asJLabel().setOpaque(background != null);
        super.setBackground(background);
    }

    public void setImage(Image image) {
        asJLabel().setIcon(new ImageIcon(image.asSwingImage()));
    }

    private JLabel asJLabel() {
        return (JLabel) swingComponent;
    }
}
