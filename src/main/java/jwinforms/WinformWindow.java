package jwinforms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class WinformWindow extends WinformPane
{
	private final JFrame frame;
	private final WinformJPanel panel;
	protected final WinformJPanel Controls;

	DialogResult result;

	public WinformWindow()
	{
		super(new JFrame());
		frame = (JFrame)swingVersion;
		frame.addWindowListener(new WindowListener());

		panel = new WinformJPanel(this);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		Controls = panel;
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private EventHandler<Object, CancelEventArgs> onClosing;
	private EventHandler<Object, EventArgs> onClosed;

	public void setClosing(EventHandler<Object, CancelEventArgs> closing)
	{
		onClosing = closing;
	}

	public void setClosed(EventHandler<Object, EventArgs> closed)
	{
		onClosed = closed;
	}

	private class WindowListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			CancelEventArgs args = new CancelEventArgs();
			if (onClosing != null)
				onClosing.handle(WinformWindow.this, args);
			if (!args.Cancel)
				frame.dispose();
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			if (onClosed != null)
				onClosed.handle(WinformWindow.this, new EventArgs());
			super.windowClosed(e);
		}
	}

	// ///////////// implementation ends here.
	protected enum FormWindowState
	{
		Normal
	}

	public void ShowWindow()
	{
		fixLocation();
		show();
	}

	private void fixLocation()
	{
		frame.setLocationRelativeTo(null);
	}

	protected Integer Left, Top;
	protected FormWindowState WindowState;

	public void Close()
	{
		WindowEvent wev = new WindowEvent((Window)swingVersion, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	// Must encapsulate most of these.
	private Size AutoScaleBaseSize;
	private FormBorderStyle FormBorderStyle;
	private boolean ControlBox;
	private boolean MinimizeBox;
	private boolean MaximizeBox;
	private String Title;

	public void setAutoScaleBaseSize(Size autoScaleBaseSize)
	{
		AutoScaleBaseSize = autoScaleBaseSize;
	}

	public Size getAutoScaleBaseSize()
	{
		return AutoScaleBaseSize;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setStartPosition(FormStartPosition startPosition)
	{
	//TODO implmnt method.
	}

	public void setMenu(MainMenu menu)
	{
		frame.getContentPane().add(menu.asSwingObject(), BorderLayout.PAGE_START);
	}

	public void setControlBox(boolean controlBox)
	{
		ControlBox = controlBox;
	}

	public boolean getControlBox()
	{
		return ControlBox;
	}

	public void setMinimizeBox(boolean minimizeBox)
	{
		MinimizeBox = minimizeBox;
	}

	public boolean getMinimizeBox()
	{
		return MinimizeBox;
	}

	public void setMaximizeBox(boolean maximizeBox)
	{
		MaximizeBox = maximizeBox;
	}

	public boolean getMaximizeBox()
	{
		return MaximizeBox;
	}

	public void setIcon(Icon icon)
	{
		frame.setIconImage(icon.asSwingImage());
	}

	public void setFormBorderStyle(FormBorderStyle formBorderStyle)
	{
		FormBorderStyle = formBorderStyle;
	}

	public FormBorderStyle getFormBorderStyle()
	{
		return FormBorderStyle;
	}

	public void setClientSize(Dimension clientSize)
	{
		// heigher, cause decorations count in swing.
		frame.setSize(new Dimension(clientSize.width, clientSize.height + 45));
	}

	public String getText()
	{
		return frame.getTitle();
	}

	public void setText(String text)
	{
		frame.setTitle(text);
	}

	public void setStatusBar(StatusBar statusBar)
	{
		frame.getContentPane().add(statusBar.asSwingObject(), BorderLayout.PAGE_END);
	}

	@Override
	public void dispose()
	{
		frame.dispose();
	}

	@Override
	public void setResult(DialogResult dialogResult)
	{
		result = dialogResult;
	}
}
