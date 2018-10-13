package jwinforms;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import swingextra.JStatusBar;

public class StatusBar extends WinformControl
{
	public StatusBar()
	{
		super(new JStatusBar());
	}

	public StatusBar Panels = this;
	public EventHandler<Object, StatusBarPanelClickEventArgs> PanelClick;
	public boolean ShowPanels;
	public boolean SizingGrip;

	public void addAll(Iterable<StatusBarPanel> asList)
	{
		for (StatusBarPanel panel : asList)
			add(panel);
	}

	private void add(StatusBarPanel panel)
	{
		asJStatusBar().addSection(panel.asJStatusBarSection(), panel.AutoSize == StatusBarPanelAutoSize.Spring);
		panel.asJStatusBarSection().addMouseListener(new MouseAdapterExtension(panel));
	}

	private JStatusBar asJStatusBar()
	{
		return (JStatusBar)swingVersion;
	}

	private final class MouseAdapterExtension extends MouseAdapter
	{
		public MouseAdapterExtension(StatusBarPanel source)
		{
			super();
			this.source = source;
		}
		private final StatusBarPanel source;
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (PanelClick != null)
				PanelClick.handle(source, new StatusBarPanelClickEventArgs(source));
		}
	}
}
