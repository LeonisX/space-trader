package jwinforms;

import java.awt.Font;

public enum FontStyle
{
	Bold(Font.BOLD), Italics(Font.ITALIC), Regular(Font.PLAIN);

	public final int awtFontstyle;

	private FontStyle(int awtFontstyle)
	{
		this.awtFontstyle = awtFontstyle;
	}
}
