package spacetrader.controls.enums;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public enum BorderStyle {

    FIXED_SINGLE(BorderFactory.createLineBorder(Color.BLACK, 1));

    private Border border;

    BorderStyle(Border border) {
        this.border = border;
    }

    public Border getBorder() {
        return border;
    }
}
