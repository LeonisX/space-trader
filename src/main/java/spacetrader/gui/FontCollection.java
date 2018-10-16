package spacetrader.gui;

import spacetrader.controls.Font;
import spacetrader.controls.FontStyle;
import spacetrader.controls.GraphicsUnit;

/**
 * @author Aviv
 */
final class FontCollection {

    private static final String FONT_FAMILY = Font.WINDOWS_DEFAULT_FONT_FAMILY;
    private static final String FONT_FAMILY_BOLD = Font.WINDOWS_DEFAULT_FONT_FAMILY_BOLD;

    static final Font regular825 = new Font(FONT_FAMILY, 8.25F, FontStyle.Regular, GraphicsUnit.Point);
    // was supposed to be 8.25f size, but it's too wide for the space allocated. Made it a bit smaller...
    static final Font bold825 = new Font(FONT_FAMILY_BOLD, 7F, FontStyle.Bold, GraphicsUnit.Point);
    static final Font bold10 = new Font(FONT_FAMILY_BOLD, 10F, FontStyle.Bold, GraphicsUnit.Point);
    static final Font bold12 = new Font(FONT_FAMILY_BOLD, 12F, FontStyle.Bold, GraphicsUnit.Point);

    private FontCollection() {
        // empty
    }
}
