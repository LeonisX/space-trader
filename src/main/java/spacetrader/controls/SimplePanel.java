package spacetrader.controls;

import javax.swing.*;

public class SimplePanel extends BaseComponent {

    public boolean autoScroll;
    public SimplePanel controls = this;

    public SimplePanel() {
        super(new JPanel());
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

}
