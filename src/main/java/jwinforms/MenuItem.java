package jwinforms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItem {

    public Shortcut shortcut;
    protected JMenuItem swingVersion;

    public MenuItem() {
        this(new JMenuItem());
    }

    public static MenuItem separator() {
        MenuItem menuItem = new MenuItem();
        menuItem.setEnabled(false);
        return menuItem;
    }

    MenuItem(JMenuItem swingVersion) {
        this.swingVersion = swingVersion;
    }

    JMenuItem asJMenuItem() {
        return swingVersion;
    }

    public void setText(String text) {
        asJMenuItem().setText(text);
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
