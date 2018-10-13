package jwinforms;

import javax.swing.*;

public class Panel extends WinformControl {

    public boolean AutoScroll;
    public Panel Controls = this;

    public Panel() {
        super(new JPanel());
    }

    public void add(WinformControl control) {
        ((JPanel) swingVersion).add(control.swingVersion);
    }

}
