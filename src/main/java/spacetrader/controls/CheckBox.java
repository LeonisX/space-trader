package spacetrader.controls;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CheckBox extends BaseComponent {

    public CheckBox() {
        super(new JCheckBox());
    }

    public String getText() {
        return asJCheckBox().getText();
    }

    public void setText(String text) {
        asJCheckBox().setText(text);
    }

    public boolean isChecked() {
        return asJCheckBox().isSelected();
    }

    public void setChecked(boolean checked) {
        asJCheckBox().setSelected(checked);
    }

    private JCheckBox asJCheckBox() {
        return ((JCheckBox) swingComponent);
    }

    public void setCheckedChanged(final EventHandler<Object, EventArgs> handler) {
        asJCheckBox().addChangeListener(e -> handler.handle(CheckBox.this, null));
    }
}
