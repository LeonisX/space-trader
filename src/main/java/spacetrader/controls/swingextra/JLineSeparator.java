package spacetrader.controls.swingextra;

import javax.swing.*;
import java.awt.*;

public class JLineSeparator extends JSeparator {

    @Override
    public void setSize(Dimension size) {
        switch (getOrientation()) {
            case SwingConstants.HORIZONTAL:
                super.setSize(new Dimension(size.width, 1));
                break;
            case SwingConstants.VERTICAL:
                super.setSize(new Dimension(1, size.height));
                break;
        }
    }
}
