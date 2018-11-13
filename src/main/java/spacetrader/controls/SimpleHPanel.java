package spacetrader.controls;

import javax.swing.*;
import java.util.Arrays;

public class SimpleHPanel extends BaseComponent {

    public SimpleHPanel controls = this;

    public SimpleHPanel() {
        super(new JPanel());
        ((JPanel) asSwingObject()).setLayout(new BoxLayout((JPanel)  asSwingObject(), BoxLayout.X_AXIS));
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

    public void addAll(BaseComponent... controls) {
        Arrays.stream(controls).forEach(control -> ((JPanel) swingComponent).add(control.swingComponent));
    }

    public void removeAll() {
        ((JPanel) swingComponent).removeAll();
        ((JPanel) swingComponent).revalidate();
    }
}
