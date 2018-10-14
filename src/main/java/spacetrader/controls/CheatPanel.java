package spacetrader.controls;

import javax.swing.*;

public class CheatPanel extends BaseComponent {

    public boolean autoScroll;
    public CheatPanel controls = this;

    public CheatPanel() {
        super(new JPanel());
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

}
