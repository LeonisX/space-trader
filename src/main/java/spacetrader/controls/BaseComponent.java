package spacetrader.controls;

import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.enums.ControlBinding;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BaseComponent implements IName {

    static final Font DEFAULT_FONT = new Font(spacetrader.controls.Font.WINDOWS_DEFAULT_FONT_FAMILY, Font.PLAIN, 11);

    protected final Component swingComponent;

    private String name;
    private int tabIndex;

    private Color foreground;
    private Color background;

    // 4 properties for Label, Button, Panel with binding
    private boolean autoSize;
    private boolean autoWidth;
    private boolean autoHeight;
    private ControlBinding controlBinding = ControlBinding.LEFT;

    private EventHandler<Object, EventArgs> click;
    private EventHandler<Object, EventArgs> doubleClick;
    private EventHandler<Object, EventArgs> mouseEnter, mouseLeave;

    private MouseListener mouseListener;

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
        setSize((int) getSize().getWidth(), height);
    }

    public int getWidth() {
        return getSize().width;
    }

    public void setWidth(int width) {
        Dimension size = (Dimension) getSize().clone();
        size.width = width;
        setSize(width, (int) getSize().getHeight());
    }

    public void setLocation(int x, int y) {
        swingComponent.setLocation(new Point(x, y));
    }

    Dimension getSize() {
        return swingComponent.getSize();
    }

    public void setSize(int width, int height) {
        Dimension size = new Dimension(width, height);
        swingComponent.setSize(size);
    }

    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
        Graphics.resizeIfNeed(swingComponent, autoSize, autoWidth, autoHeight, controlBinding);
    }

    public boolean isAutoSize() {
        return autoSize;
    }

    public boolean isAutoWidth() {
        return autoWidth;
    }

    public void setAutoWidth(boolean autoWidth) {
        this.autoWidth = autoWidth;
        Graphics.resizeIfNeed(swingComponent, autoSize, autoWidth, autoHeight, controlBinding);
    }

    public boolean isAutoHeight() {
        return autoHeight;
    }

    public void setAutoHeight(boolean autoHeight) {
        this.autoHeight = autoHeight;
    }

    public ControlBinding getControlBinding() {
        return controlBinding;
    }

    public void setControlBinding(ControlBinding controlBinding) {
        this.controlBinding = controlBinding;
    }

    public boolean isVisible() {
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
        Graphics.resizeIfNeed(swingComponent, autoSize, autoWidth, autoHeight, controlBinding);
    }

    public void setClick(EventHandler<Object, EventArgs> click) {
        this.click = click;
    }

    EventHandler<Object, EventArgs> getClick() {
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
}
