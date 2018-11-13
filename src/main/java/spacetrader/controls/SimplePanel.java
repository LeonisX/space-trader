package spacetrader.controls;

import javax.swing.*;
import java.util.Arrays;

public class SimplePanel extends BaseComponent {

    public boolean autoScroll;
    public SimplePanel controls = this;

    public SimplePanel() {
        super(new JPanel());
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

    public void addAll(BaseComponent... controls) {
        Arrays.stream(controls).forEach(control -> ((JPanel) swingComponent).add(control.swingComponent));
    }

    public void setLayout(Layout layout) {
        ((JPanel) swingComponent).setLayout(layout.getLayout());
    }
}
