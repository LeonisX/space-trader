package jwinforms;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class Font extends java.awt.Font
{
	public Font(String name, float size)
	{
		this(name, FontStyle.Regular, size, GraphicsUnit.Point);
	}

	public Font(String name, float size, FontStyle style, GraphicsUnit unit, int b)
	{
		this(name, style, size, unit);
	}

	private Font(String name, FontStyle style, float size, GraphicsUnit unit)
	{
		super(name, style.awtFontstyle, (int)unit.toPixels(size));
		Name = getName();
		FontFamily = getFamily();
	}

	Font(java.awt.Font source)
	{
		super(source);
		Name = getName();
		FontFamily = getFamily();
	}

	public final String FontFamily;
	public final String Name;

	public static final String WINDOWS_DEFAULT_FONT_FAMILY;
	public static final String WINDOWS_DEFAULT_FONT_FAMILY_BOLD;

	static
	{
		String preferedFonts = "Microsoft Sans Serif";

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		HashSet<String> names = new HashSet<String>();
		for (String family : ge.getAvailableFontFamilyNames())
			names.add(family);

		if (names.contains(preferedFonts))
		{
			WINDOWS_DEFAULT_FONT_FAMILY = preferedFonts;
			WINDOWS_DEFAULT_FONT_FAMILY_BOLD = preferedFonts;
		} else
		{
			try
			{
				java.awt.Font def = makeAndRegisterFont("jwinforms/tahoma.ttf");
				WINDOWS_DEFAULT_FONT_FAMILY = def.getFamily();
				java.awt.Font bold = makeAndRegisterFont("jwinforms/tahomabd.ttf");
				WINDOWS_DEFAULT_FONT_FAMILY_BOLD = bold.getFamily();
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private static java.awt.Font makeAndRegisterFont(String name) throws FontFormatException, IOException
	{
		InputStream stream = Font.class.getClassLoader().getResourceAsStream(name);
		if (stream == null)
			throw new IOException("Resource not found: " + name);

		java.awt.Font font = java.awt.Font.createFont(Font.TRUETYPE_FONT, stream);
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		return font;
	}
}
