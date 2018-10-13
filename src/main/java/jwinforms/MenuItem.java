package jwinforms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItem {

    public int Index;
    public Shortcut Shortcut;
    protected JMenuItem swingVersion;

    public MenuItem() {
        this(new JMenuItem());
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
