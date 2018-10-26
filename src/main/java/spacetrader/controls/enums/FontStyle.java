package spacetrader.controls.enums;

import java.awt.Font;

public enum FontStyle {

    Bold(Font.BOLD), Italics(Font.ITALIC), Regular(Font.PLAIN);

    public final int awtFontStyle;

    FontStyle(int awtFontStyle) {
        this.awtFontStyle = awtFontStyle;
    }

}
