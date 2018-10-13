package jwinforms;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public enum BorderStyle {

    FixedSingle(BorderFactory.createLineBorder(Color.black, 1));

    private Border border;

    BorderStyle(Border border) {
        this.border = border;
    }

    public Border getBorder() {
        return border;
    }
}
