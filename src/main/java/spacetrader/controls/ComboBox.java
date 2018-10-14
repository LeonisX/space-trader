package spacetrader.controls;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboBox extends BaseComponent {

    public final MyComboBoxModel Items = new MyComboBoxModel();
    // probably don't care.
    public ComboBoxStyle DropDownStyle;

    public ComboBox() {
        super(new JComboBox());
        asJComboBox().setModel(Items);
    }

    private JComboBox asJComboBox() {
        return (JComboBox) swingComponent;
    }

    public int getSelectedIndex() {
        return asJComboBox().getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        asJComboBox().setSelectedIndex(index);
    }

    public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler) {
        asJComboBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handler.handle(ComboBox.this, null);
            }
        });
    }

    public Object getSelectedItem() {
        return asJComboBox().getSelectedItem();
    }
}
