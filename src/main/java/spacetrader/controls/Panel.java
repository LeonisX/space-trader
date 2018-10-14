package spacetrader.controls;

import javax.swing.*;

public class Panel extends BaseComponent {

    public boolean AutoScroll;
    public Panel Controls = this;

    public Panel() {
        super(new JPanel());
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

}
