package jwinforms;

import java.awt.Graphics;

public class PaintEventArgs extends EventArgs
{
	public final jwinforms.Graphics Graphics;

	public PaintEventArgs(jwinforms.Graphics graphics)
	{
		Graphics = graphics;
	}

	public PaintEventArgs(Graphics awtGraphics)
	{
		this(new jwinforms.Graphics(awtGraphics));
	}
}
