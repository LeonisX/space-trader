package jwinforms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinformMouseListener extends MouseAdapter
{
	private final EventHandler<Object, EventArgs> normalClick;
	private final EventHandler<Object, EventArgs> doubleClick;
	private final Object sender;

	public WinformMouseListener(Object sender, EventHandler<Object, EventArgs> normalClick,
			EventHandler<Object, EventArgs> doubleClick)
	{
		this.sender = sender;
		this.doubleClick = doubleClick;
		this.normalClick = normalClick;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		switch (e.getClickCount())
		{
		case 1:
			if (normalClick != null)
				normalClick.handle(sender, new MouseEventArgs(e));
			break;
		case 2:
		case 3:
			if (doubleClick != null)
				doubleClick.handle(sender, new MouseEventArgs(e));
			break;
		default:
			// ignore?
		}
	}

}
