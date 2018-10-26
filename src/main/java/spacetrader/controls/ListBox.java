package spacetrader.controls;

import javax.swing.*;
import java.awt.*;
import spacetrader.controls.enums.BorderStyle;

public class ListBox extends BaseComponent {

    private final MyListModel<Object> items = new MyListModel<>();

    public ListBox() {
        super(new JList<>());
        asJList().setModel(items);
        //TODO scale
        asJList().setFixedCellHeight(14);
    }

    public MyListModel<Object> getItems() {
        return items;
    }

    @Override
    public void setBorderStyle(BorderStyle borderStyle) {
        if (borderStyle != BorderStyle.FIXED_SINGLE) {
            throw new Error("Unknown border style");
        }
        asJList().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public void clearSelected() {
        asJList().clearSelection();
    }

    @SuppressWarnings("unchecked")
    private JList<Object> asJList() {
        return (JList<Object>) swingComponent;
    }

    public void setSelectedIndexChanged(final EventHandler<Object, EventArgs> handler) {
        asJList().addListSelectionListener(e -> handler.handle(ListBox.this, null));
    }

    public int getSelectedIndex() {
        return asJList().getSelectedIndex();
    }

    public int getItemHeight() {
        return asJList().getFixedCellHeight();
    }

    public Object getSelectedItem() {
        return asJList().getSelectedValue();
    }

    public void setSelectedItem(Object selectedItem) {
        asJList().setSelectedValue(selectedItem, true);
    }
}
