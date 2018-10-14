package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BaseComponent implements ISupportInitialize {

    static final Font DEFAULT_FONT = new Font(spacetrader.controls.Font.WINDOWS_DEFAULT_FONT_FAMILY, Font.PLAIN, 11);

    protected final Component swingComponent;

    private String name;
    private int tabIndex;

    private Color foreground;
    private Color background;

    private EventHandler<Object, EventArgs> click;
    private EventHandler<Object, EventArgs> doubleClick;
    private EventHandler<Object, EventArgs> mouseEnter, mouseLeave;

    private MouseListener mouseListener;

    @Deprecated
    public BaseComponent() {
        this(null);
    }

    public BaseComponent(Component swingComponent) {
        this.swingComponent = swingComponent;
        this.swingComponent.setFont(DEFAULT_FONT);
    }

    public Component asSwingObject() {
        return swingComponent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.swingComponent.setName(name);
    }

    int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public void setTabStop(boolean tabStop) {
        swingComponent.setFocusable(tabStop);
    }

    public int getLeft() {
        return swingComponent.getLocation().x;
    }

    public void setLeft(int left) {
        Point location = (Point) swingComponent.getLocation().clone();
        location.x = left;
        swingComponent.setLocation(location);
    }

    public int getTop() {
        return swingComponent.getLocation().y;
    }

    public void setTop(int top) {
        Point location = (Point) swingComponent.getLocation().clone();
        location.y = top;
        swingComponent.setLocation(location);
    }

    public int getHeight() {
        return getSize().height;
    }

    public void setHeight(int height) {
        Dimension size = (Dimension) getSize().clone();
        size.height = height;
        setSize(size);
    }

    public int getWidth() {
        return getSize().width;
    }

    public void setWidth(int width) {
        Dimension size = (Dimension) getSize().clone();
        size.width = width;
        setSize(size);
    }

    public void setLocation(Point location) {
        swingComponent.setLocation(location);
    }

    Dimension getSize() {
        return swingComponent.getPreferredSize();
    }

    public void setSize(Dimension size) {
        swingComponent.setPreferredSize(size);
        swingComponent.setSize(size);
    }

    public void setAutoSize(boolean autoSize) {
        // /TODO impl.
    }

    public boolean getVisible() {
        return swingComponent.isVisible();
    }

    public void setVisible(boolean visible) {
        swingComponent.setVisible(visible);
    }

    public void setEnabled(boolean enabled) {
        swingComponent.setEnabled(enabled);
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        if (!(swingComponent instanceof JComponent)) {
            throw new UnsupportedOperationException("Can't set border for this control: " + getClass());
        }
        ((JComponent) swingComponent).setBorder(borderStyle.getBorder());
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
        asSwingObject().setBackground(background);
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        // todo  Under winforms, this also appears to change the border color.
        this.foreground = foreground;
        swingComponent.setForeground(foreground);
    }

    public spacetrader.controls.Font getFont() {
        Font font = swingComponent.getFont();
        return font == null ? null : new spacetrader.controls.Font(font);
    }

    public void setFont(spacetrader.controls.Font font) {
        swingComponent.setFont(font);
    }

    public void setClick(EventHandler<Object, EventArgs> click) {
        this.click = click;
    }

    public EventHandler<Object, EventArgs> getClick() {
        return click;
    }

    public void setDoubleClick(EventHandler<Object, EventArgs> doubleClick) {
        this.doubleClick = doubleClick;
    }

    /**
     * Occurs when the control is entered (According to MSDN).
     */
    public void setEnter(EventHandler<Object, EventArgs> enter) {
        // TODO implement: control.setEnter()
    }

    public void setMouseEnter(EventHandler<Object, EventArgs> mouseEnter) {
        this.mouseEnter = mouseEnter;
        createMouseListener();
    }

    public void setMouseLeave(EventHandler<Object, EventArgs> mouseLeave) {
        this.mouseLeave = mouseLeave;
        createMouseListener();
    }

    private void createMouseListener() {
        if (mouseListener != null) {
            return;
        }
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEnter.handle(BaseComponent.this, new EventArgs());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseLeave.handle(BaseComponent.this, new EventArgs());
            }
        };
        this.asSwingObject().addMouseListener(mouseListener);
    }

    MouseListener getMouseListener() {
        return new WinformMouseListener(this, click, doubleClick);
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
