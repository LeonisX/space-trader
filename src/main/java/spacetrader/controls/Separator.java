package spacetrader.controls;

import spacetrader.controls.swingextra.JLineSeparator;

import javax.swing.*;
import java.awt.*;

public class Separator extends BaseComponent {

    Separator() {
        super(new JLineSeparator());
        setBorderStyle(BorderStyle.FixedSingle);
    }

    @Override
    public void setBackground(Color background) {
        asJLineSeparator().setBackground(background);
    }

    @Override
    public void setBorderStyle(BorderStyle borderStyle) {
        switch (borderStyle) {
            case FixedSingle:
                asJLineSeparator().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                break;

            default:
                throw new Error();
        }
    }

    JLineSeparator asJLineSeparator() {
        return (JLineSeparator) swingComponent;
    }
}
