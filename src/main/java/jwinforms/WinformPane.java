package jwinforms;

import java.awt.Window;

/**
 * base class for windows / forms.
 *
 * @author Aviv
 */
public abstract class WinformPane extends WinformControl
{
	protected WinformPane(Window swingVersion)
	{
		super(swingVersion);
	}

	protected void show()
	{
		EventHandler<Object, EventArgs> loadHandler = Load;
		raise(loadHandler, this, null);
		swingVersion.setVisible(true);
	}

	protected <O, E> void raise(EventHandler<O, E> handler, O sender, E args)
	{
		if (handler != null)
			handler.handle(sender, args);
	}

	private EventHandler<Object, EventArgs> Load;

	public void setLoad(EventHandler<Object, EventArgs> load)
	{
		Load = load;
	}

	public abstract void setResult(DialogResult dialogResult);

	public abstract void dispose();
}
