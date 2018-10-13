package jwinforms;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WinformControl implements ISupportInitialize {

    static final Font DEFAULT_FONT = new Font(jwinforms.Font.WINDOWS_DEFAULT_FONT_FAMILY, Font.PLAIN, 11);

    protected final Component swingVersion;
    EventHandler<Object, EventArgs> click;
    EventHandler<Object, EventArgs> doubleClick;
    private String name;
    private Color foreColor;
    private int tabIndex;
    private Color backColor;
    private EventHandler<Object, EventArgs> mouseEnter, mouseLeave;
    private MouseListener mouseListener;

    @Deprecated
    public WinformControl() {
        this(null);
    }

    public WinformControl(Component swingVersion) {
        this.swingVersion = swingVersion;
        this.swingVersion.setFont(DEFAULT_FONT);
    }

    public void setDoubleClick(EventHandler<Object, EventArgs> doubleClick) {
        this.doubleClick = doubleClick;
    }

    MouseListener getMouseListener() {
        return new WinformMouseListener(this, click, doubleClick);
    }

    int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public Component asSwingObject() {
        return swingVersion;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
        asSwingObject().setBackground(backColor);
    }

    public jwinforms.Font getFont() {
        Font font = swingVersion.getFont();
        return font == null ? null : new jwinforms.Font(font);
    }

    public void setFont(jwinforms.Font font) {
        swingVersion.setFont(font);
    }

    public Color getForeColor() {
        return foreColor;
    }

    public void setForeColor(Color foreColor) {
        // todo  Under winforms, this also appears to change the border color.
        this.foreColor = foreColor;
        swingVersion.setForeground(foreColor);
    }

    public int getHeight() {
        return getSize().height;
    }

    public void setHeight(int height) {
        Dimension size = (Dimension) getSize().clone();
        size.height = height;
        setSize(size);
    }

    public int getLeft() {
        return swingVersion.getLocation().x;
    }

    public void setLeft(int left) {
        Point location = (Point) swingVersion.getLocation().clone();
        location.x = left;
        swingVersion.setLocation(location);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.swingVersion.setName(name);
    }

    public int getTop() {
        return swingVersion.getLocation().y;
    }

    public void setTop(int top) {
        Point location = (Point) swingVersion.getLocation().clone();
        location.y = top;
        swingVersion.setLocation(location);
    }

    public boolean getVisible() {
        return swingVersion.isVisible();
    }

    public void setVisible(boolean visible) {
        swingVersion.setVisible(visible);
    }

    public int getWidth() {
        return getSize().width;
    }

    public void setWidth(int width) {
        Dimension size = (Dimension) getSize().clone();
        size.width = width;
        setSize(size);
    }

    public void setAutoSize(boolean autoSize) {
        // /TODO impl.
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        if (!(swingVersion instanceof JComponent)) {
            throw new UnsupportedOperationException("Can't set border for this control: I don't know how. Control is "
                    + getClass());
        }
        ((JComponent) swingVersion).setBorder(borderStyle.getBorder());
    }

    public void setClick(EventHandler<Object, EventArgs> click) {
        this.click = click;
    }

    public void setEnabled(boolean enabled) {
        swingVersion.setEnabled(enabled);
    }

    /**
     * Occurs when the control is entered (According to MSDN).
     */
    public void setEnter(EventHandler<Object, EventArgs> enter) {
        // TODO implement: control.setEnter()
    }

    public void setLocation(Point location) {
        swingVersion.setLocation(location);
    }

    public void setMouseEnter(EventHandler<Object, EventArgs> mouseEnter) {
        this.mouseEnter = mouseEnter;
        CreateMouseListener();
    }

    public void setMouseLeave(EventHandler<Object, EventArgs> mouseLeave) {
        this.mouseLeave = mouseLeave;
        CreateMouseListener();
    }

    private void CreateMouseListener() {
        if (mouseListener != null) {
            return;
        }
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEnter.handle(WinformControl.this, new EventArgs());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseLeave.handle(WinformControl.this, new EventArgs());
            }
        };
        this.asSwingObject().addMouseListener(mouseListener);
    }

    Dimension getSize() {
        return swingVersion.getPreferredSize();
    }

    public void setSize(Dimension size) {
        swingVersion.setPreferredSize(size);
        swingVersion.setSize(size);
    }

    public void setTabStop(boolean tabStop) {
        swingVersion.setFocusable(tabStop);
    }

    public void suspendLayout() {
        // TODO Auto-generated method stub
    }

    public void beginInit() {
        // TODO Auto-generated method stub
    }

    public void endInit() {
        // TODO Auto-generated method stub
    }
}
