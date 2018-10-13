package spacetrader.gui;

import jwinforms.Font;
import jwinforms.FontStyle;
import jwinforms.GraphicsUnit;

/**
 * @author Aviv
 */
final class FontCollection
{
	private static final String FONT_FAMILY = Font.WINDOWS_DEFAULT_FONT_FAMILY;
	private static final String FONT_FAMILY_BOLD = Font.WINDOWS_DEFAULT_FONT_FAMILY_BOLD;

	static final Font regular825 = new Font(FONT_FAMILY, 8.25F, FontStyle.Regular, GraphicsUnit.Point, 0);

	// was supposed to be 8.25f size, but it's too wide for the space allocated. Made it a bit smaller...
	static final Font bold825 = new Font(FONT_FAMILY_BOLD, 7F, FontStyle.Bold, GraphicsUnit.Point, 0);
	static final Font bold10 = new Font(FONT_FAMILY_BOLD, 10F, FontStyle.Bold, GraphicsUnit.Point, 0);
	static final Font bold12 = new Font(FONT_FAMILY_BOLD, 12F, FontStyle.Bold, GraphicsUnit.Point, 0);

	private FontCollection()
	{}
}
