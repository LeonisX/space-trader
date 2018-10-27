package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import spacetrader.controls.enums.ContentAlignment;

public class Label extends BaseComponent {

    private static final String NEWLINE_LITERAL = "\n";
    private static final String END = "</HTML>";
    private static final String START = "<HTML>";
    private static final String NEWLINE = "<br>";

    private ContentAlignment textAlign;
    private ContentAlignment imageAlign;
    private boolean convertedToHtml;

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
    }

    public void setText(int number) {
        this.setText(Integer.toString(number));
    }

    @Override
    public void setBackground(Color background) {
        asJLabel().setOpaque(background != null);
        super.setBackground(background);
    }

    public void setImage(Image image) {
        asJLabel().setIcon(new ImageIcon(image.asSwingImage()));
    }

    public ContentAlignment getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(ContentAlignment textAlign) {
        this.textAlign = textAlign;
    }

    public ContentAlignment getImageAlign() {
        return imageAlign;
    }

    public void setImageAlign(ContentAlignment imageAlign) {
        this.imageAlign = imageAlign;
    }

    private JLabel asJLabel() {
        return (JLabel) swingComponent;
    }
}
