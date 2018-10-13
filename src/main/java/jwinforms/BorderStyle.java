package jwinforms;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public enum BorderStyle
{
	FixedSingle(BorderFactory.createLineBorder(Color.black, 1));

	private Border border;

	BorderStyle(Border border)
	{
		this.border = border;
	}

	public Border getBorder()
	{
		return border;
	}
}
