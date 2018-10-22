package spacetrader.controls;

import javax.swing.*;

public class ComboBox<T> extends BaseComponent {

    private final MyComboBoxModel<T> items = new MyComboBoxModel<>();
    // probably don't care.
    private ComboBoxStyle dropDownStyle;

    public ComboBox() {
        super(new JComboBox<T>());
        asJComboBox().setModel(items);
    }

    public MyComboBoxModel<T> getItems() {
        return items;
    }

    public ComboBoxStyle getDropDownStyle() {
        return dropDownStyle;
    }

    public void setDropDownStyle(ComboBoxStyle dropDownStyle) {
        this.dropDownStyle = dropDownStyle;
    }

    @SuppressWarnings("unchecked")
    private JComboBox<T> asJComboBox() {
        return (JComboBox<T>) swingComponent;
    }

    public int getSelectedIndex() {
        return asJComboBox().getSelectedIndex();
    }

    public void setSelectedIndex(int index) {
        asJComboBox().setSelectedIndex(index);
    }

    public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler) {
        asJComboBox().addActionListener(e -> handler.handle(ComboBox.this, null));
    }

    public Object getSelectedItem() {
        return asJComboBox().getSelectedItem();
    }
}
