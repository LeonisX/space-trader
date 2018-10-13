package jwinforms;

import java.awt.Dimension;

import swingextra.JStatusBarSection;

public class StatusBarPanel implements ISupportInitialize
{
	public final StatusBarPanelAutoSize AutoSize;
	private final JStatusBarSection jpanel = new JStatusBarSection(" ");

	public StatusBarPanel()
	{
		this(StatusBarPanelAutoSize.None);
	}

	public StatusBarPanel(StatusBarPanelAutoSize autoSize)
	{
		AutoSize = autoSize;
		jpanel.setFont(WinformControl.DEFAULT_FONT);
	}

	public void setText(String text)
	{
		if (text.isEmpty()) text = "  ";
		jpanel.setText(text);
	}

	public void setWidth(int w)
	{
		int h = jpanel.getHeight();
		jpanel.setSize(w, h);
	}

	public JStatusBarSection asJStatusBarSection()
	{
		return jpanel;
	}

	public void setMinWidth(int width)
	{
		int height = jpanel.getPreferredSize().height;
		jpanel.setPreferredSize(new Dimension(width, height));
		jpanel.setMinimumSize(new Dimension(width, height));
	}

	public void BeginInit()
	{
	// TODO Auto-generated method stub

	}

	public void EndInit()
	{
	// TODO Auto-generated method stub

	}
}
