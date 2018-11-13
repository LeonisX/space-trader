package spacetrader.controls;

import javax.swing.*;
import java.util.Arrays;

public class SimpleVPanel extends BaseComponent {

    public SimpleVPanel controls = this;

    public SimpleVPanel() {
        super(new JPanel());
        (asJPanel()).setLayout(new BoxLayout((JPanel)  asSwingObject(), BoxLayout.Y_AXIS));
    }

    public void add(BaseComponent control) {
        ((JPanel) swingComponent).add(control.swingComponent);
    }

    public void addAll(BaseComponent... controls) {
        Arrays.stream(controls).forEach(control -> ((JPanel) swingComponent).add(control.swingComponent));
    }

    public JPanel asJPanel() {
        return (JPanel) asSwingObject();
    }
}
