package jwinforms;

import java.awt.event.MouseEvent;

public class MouseEventArgs extends EventArgs
{
	public MouseEventArgs(MouseEvent e)
	{
		X = e.getX();
		Y = e.getY();
		Button = findMouseButton(e.getButton());
	}

	private MouseButtons findMouseButton(int button)
	{
		switch (button)
		{
		case MouseEvent.BUTTON1:
			return MouseButtons.Left;
		case MouseEvent.BUTTON2:
			return MouseButtons.Right;

		default:
			throw new Error("Unknown mouse button: " + button);
		}
	}

	public final MouseButtons Button;
	public final int X, Y;
}
