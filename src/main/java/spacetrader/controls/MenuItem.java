package spacetrader.controls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import spacetrader.controls.enums.Shortcut;

public class MenuItem implements IName {

    public Shortcut shortcut;
    JMenuItem swingVersion;

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
