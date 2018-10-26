package spacetrader.controls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import spacetrader.controls.enums.Shortcut;

public class MenuItem implements IName {

    private Shortcut shortcut;
    private JMenuItem swingVersion;

    public MenuItem() {
        this(new JMenuItem());
    }

    public static MenuItem separator() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("separator");
        menuItem.setEnabled(false);
        return menuItem;
    }

    MenuItem(JMenuItem swingVersion) {
        this.swingVersion = swingVersion;
    }

    JMenuItem asJMenuItem() {
        return swingVersion;
    }

    public String getText() {
        return asJMenuItem().getText();
    }

    public void setText(String text) {
        asJMenuItem().setText(text);
    }

    public String getName() {
        return asJMenuItem().getName();
    }

    public void setName(String name) {
        asJMenuItem().setName(name);
    }

    public Shortcut getShortcut() {
        return shortcut;
    }

    public void setShortcut(Shortcut shortcut) {
        this.shortcut = shortcut;
    }

    public JMenuItem getSwingVersion() {
        return swingVersion;
    }

    public void setSwingVersion(JMenuItem swingVersion) {
        this.swingVersion = swingVersion;
    }

    public void setClick(final EventHandler<Object, EventArgs> eventHandler) {
        asJMenuItem().addActionListener(arg0 -> eventHandler.handle(getName(), null));
    }

    public void setEnabled(boolean enabled) {
        asJMenuItem().setEnabled(enabled);
    }
}
