package jwinforms;

import javax.swing.JPanel;

public class Panel extends WinformControl
{
	public Panel()
	{
		super(new JPanel());
	}

	public boolean AutoScroll;
	public Panel Controls = this;

	public void add(WinformControl control)
	{
		((JPanel)swingVersion).add(control.swingVersion);
	}

}
