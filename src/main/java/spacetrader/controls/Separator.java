package spacetrader.controls;

import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.swingextra.JLineSeparator;

import javax.swing.*;
import java.awt.*;

public class Separator extends BaseComponent {

    Separator() {
        super(new JLineSeparator());
        setBorderStyle(BorderStyle.FIXED_SINGLE);
    }

    @Override
    public void setBackground(Color background) {
        asJLineSeparator().setBackground(background);
    }

    @Override
    public void setBorderStyle(BorderStyle borderStyle) {
        switch (borderStyle) {
            case FIXED_SINGLE:
                asJLineSeparator().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                break;

            default:
                throw new Error();
        }
    }

    JLineSeparator asJLineSeparator() {
        return (JLineSeparator) swingComponent;
    }
}
