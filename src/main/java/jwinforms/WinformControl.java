package jwinforms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class WinformControl implements ISupportInitialize
{
	public static final Font DEFAULT_FONT = new Font(jwinforms.Font.WINDOWS_DEFAULT_FONT_FAMILY, Font.PLAIN, 11);

	protected final Component swingVersion;

	private String Name;
	private Color ForeColor;

	private int tabIndex;

	EventHandler<Object, EventArgs> click;
	EventHandler<Object, EventArgs> doubleClick;

	public void setDoubleClick(EventHandler<Object, EventArgs> doubleClick)
	{
		this.doubleClick = doubleClick;
	}

	public MouseListener getMouseListener()
	{
		return new WinformMouseListener(this, click, doubleClick);
	}

	public int getTabIndex()
	{
		return tabIndex;
	}

	@Deprecated
	public WinformControl()
	{
		this(null);
	}

	public WinformControl(Component swingVersion)
	{
		this.swingVersion = swingVersion;
		swingVersion.setFont(DEFAULT_FONT);
	}

	public Component asSwingObject()
	{
		return swingVersion;
	}

	private Color BackColor;

	public Color getBackColor()
	{
		return BackColor;
	}

	public void setBackColor(Color backColor)
	{
		BackColor = backColor;
		asSwingObject().setBackground(backColor);
	}

	public jwinforms.Font getFont()
	{
		Font font = swingVersion.getFont();
		return font == null ? null : new jwinforms.Font(font);
	}

	public Color getForeColor()
	{
		return ForeColor;
	}

	public int getHeight()
	{
		return getSize().height;
	}

	public int getLeft()
	{
		return swingVersion.getLocation().x;
	}

	public String getName()
	{
		return Name;
	}

	public int getTop()
	{
		return swingVersion.getLocation().y;
	}

	public boolean getVisible()
	{
		return swingVersion.isVisible();
	}

	public int getWidth()
	{
		return getSize().width;
	}

	public void setAutoSize(boolean autoSize)
	{
	// /TODO impl.
	}

	public void setBorderStyle(BorderStyle borderStyle)
	{
		if (!(swingVersion instanceof JComponent))
			throw new UnsupportedOperationException("Can't set border for this control: I don't know how. Control is "
					+ getClass());

		((JComponent)swingVersion).setBorder(borderStyle.getBorder());
	}

	public void setClick(EventHandler<Object, EventArgs> click)
	{
		this.click = click;
	}

	public void setEnabled(boolean enabled)
	{
		swingVersion.setEnabled(enabled);
	}

	/**
	 * Occurs when the control is entered (According to MSDN).
	 */
	public void setEnter(EventHandler<Object, EventArgs> enter)
	{
	// TODO implement: control.setEnter()
	}

	public void setFont(jwinforms.Font font)
	{
		swingVersion.setFont(font);
	}

	public void setForeColor(Color foreColor)
	{
		// todo  Under winforms, this also appears to change the border color.
		ForeColor = foreColor;
		swingVersion.setForeground(foreColor);
	}

	public void setHeight(int height)
	{
		Dimension size = (Dimension)getSize().clone();
		size.height = height;
		setSize(size);
	}

	public void setLeft(int left)
	{
		Point location = (Point)swingVersion.getLocation().clone();
		location.x = left;
		swingVersion.setLocation(location);
	}

	public void setLocation(Point location)
	{
		swingVersion.setLocation(location);
	}

	private EventHandler<Object, EventArgs> mouseEnter, mouseLeave;

	public void setMouseEnter(EventHandler<Object, EventArgs> mouseEnter)
	{
		this.mouseEnter = mouseEnter;
		CreateMouseListener();
	}

	public void setMouseLeave(EventHandler<Object, EventArgs> mouseLeave)
	{
		this.mouseLeave = mouseLeave;
		CreateMouseListener();
	}

	private MouseListener mouseListener;

	private void CreateMouseListener()
	{
		if (mouseListener != null)
			return;
		mouseListener = new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				mouseEnter.handle(WinformControl.this, new EventArgs());
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				mouseLeave.handle(WinformControl.this, new EventArgs());
			}
		};
		this.asSwingObject().addMouseListener(mouseListener);
	}

	/**
	 * I think this is nothing.
	 *
	 * @param name
	 */
	public void setName(String name)
	{
		Name = name;
	}

	public void setSize(Dimension size)
	{
		swingVersion.setPreferredSize(size);
		swingVersion.setSize(size);
	}

	Dimension getSize()
	{
		return swingVersion.getPreferredSize();
	}

	public void setTabIndex(int tabIndex)
	{
		this.tabIndex = tabIndex;
	}

	public void setTabStop(boolean tabStop)
	{
		swingVersion.setFocusable(tabStop);
	}

	public void setTop(int top)
	{
		Point location = (Point)swingVersion.getLocation().clone();
		location.y = top;
		swingVersion.setLocation(location);
	}

	public void setVisible(boolean visible)
	{
		swingVersion.setVisible(visible);
	}

	public void setWidth(int width)
	{
		Dimension size = (Dimension)getSize().clone();
		size.width = width;
		setSize(size);
	}

	public void SuspendLayout()
	{
	// TODO Auto-generated method stub

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
