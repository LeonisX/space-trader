package spacetrader.controls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItem {

    public Shortcut shortcut;
    JMenuItem swingVersion;

    public MenuItem(String name) {
        this(new JMenuItem());
        setName(name);
    }

    public static MenuItem separator() {
        MenuItem menuItem = new MenuItem("");
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

    public void setClick(final EventHandler<Object, EventArgs> eventHandler) {
        asJMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventHandler.handle(this, null);
            }
        });
    }

    public void setEnabled(boolean enabled) {
        asJMenuItem().setEnabled(enabled);
    }
}
