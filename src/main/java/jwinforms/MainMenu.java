package jwinforms;

import javax.swing.*;

public class MainMenu extends WinformControl
{
	public MainMenu()
	{
		super(new JMenuBar());
	}

	public void add(MenuItem item)
	{
		asJMenuBar().add(item.asJMenuItem());
	}

	public void addAll(MenuItem... items)
	{
		for (MenuItem item : items)
			this.add(item);

	}

	private JMenuBar asJMenuBar()
	{
		return (JMenuBar)swingVersion;
	}
}
