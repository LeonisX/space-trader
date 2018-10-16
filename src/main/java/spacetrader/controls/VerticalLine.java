package spacetrader.controls;

import javax.swing.*;

public class VerticalLine extends Separator {

    public VerticalLine() {
        setWidth(1);
        asJLineSeparator().setOrientation(SwingConstants.VERTICAL);
    }

}
